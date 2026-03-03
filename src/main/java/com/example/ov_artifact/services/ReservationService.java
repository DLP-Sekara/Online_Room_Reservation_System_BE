package com.example.ov_artifact.services;

import com.example.ov_artifact.dto.ReservationDTO;
import com.example.ov_artifact.dto.ReservationDetailDTO;
import com.example.ov_artifact.entity.*;
import com.example.ov_artifact.repository.*;
import com.example.ov_artifact.util.ReservationStatus;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationDetailRepository reservationDetailRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    public boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Reservation> overlapping = reservationRepository.findOverlappingReservations(roomId, checkIn, checkOut);
        return overlapping.isEmpty();
    }

    public BigDecimal calculateBill(ReservationDTO reservationDTO) {
        Room room = roomRepository.findById(reservationDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        MealPlan mealPlan = mealPlanRepository.findById(reservationDTO.getPlanId())
                .orElseThrow(() -> new RuntimeException("Meal Plan not found"));

        long nights = ChronoUnit.DAYS.between(reservationDTO.getCheckIn(), reservationDTO.getCheckOut());
        if (nights <= 0) nights = 1; // Minimum 1 night charge

        BigDecimal roomTotal = room.getRoomType().getPricePerNight().multiply(BigDecimal.valueOf(nights));
        BigDecimal mealTotal = mealPlan.getPrice().multiply(BigDecimal.valueOf(nights));
        
        BigDecimal foodTotal = BigDecimal.ZERO;
        if (reservationDTO.getReservationDetails() != null) {
            for (ReservationDetailDTO detailDTO : reservationDTO.getReservationDetails()) {
                FoodItem item = foodItemRepository.findById(detailDTO.getItemId())
                        .orElseThrow(() -> new RuntimeException("Food Item not found"));
                foodTotal = foodTotal.add(item.getUnitPrice().multiply(BigDecimal.valueOf(detailDTO.getOrderedQty())));
            }
        }

        return roomTotal.add(mealTotal).add(foodTotal);
    }

    public String createReservation(ReservationDTO reservationDTO) {
        if (!isRoomAvailable(reservationDTO.getRoomId(), reservationDTO.getCheckIn(), reservationDTO.getCheckOut())) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        Guest guest = guestRepository.findById(reservationDTO.getGuestId())
                .orElseThrow(() -> new RuntimeException("Guest not found"));
        Room room = roomRepository.findById(reservationDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        MealPlan mealPlan = mealPlanRepository.findById(reservationDTO.getPlanId())
                .orElseThrow(() -> new RuntimeException("Meal Plan not found"));

        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setMealPlan(mealPlan);
        reservation.setCheckIn(reservationDTO.getCheckIn());
        reservation.setCheckOut(reservationDTO.getCheckOut());
        reservation.setStatus(ReservationStatus.PENDING);
        
        // Initial bill calculation
        reservation.setTotalBill(calculateBill(reservationDTO));

        Reservation savedReservation = reservationRepository.save(reservation);

        if (reservationDTO.getReservationDetails() != null) {
            List<ReservationDetail> details = reservationDTO.getReservationDetails().stream().map(d -> {
                FoodItem item = foodItemRepository.findById(d.getItemId())
                        .orElseThrow(() -> new RuntimeException("Food Item not found"));
                ReservationDetail detail = new ReservationDetail();
                detail.setReservation(savedReservation);
                detail.setFoodItem(item);
                detail.setOrderedQty(d.getOrderedQty());
                return detail;
            }).collect(Collectors.toList());
            reservationDetailRepository.saveAll(details);
        }

        return savedReservation.getResId();
    }

    public void updateReservation(ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findById(reservationDTO.getResId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        
        // Update basic fields if provided
        if (reservationDTO.getStatus() != null) reservation.setStatus(reservationDTO.getStatus());
        
        // Recalculate bill if needed
        reservation.setTotalBill(calculateBill(reservationDTO));
        
        reservationRepository.save(reservation);
    }

    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return modelMapper.map(reservations, new TypeToken<List<ReservationDTO>>() {}.getType());
    }

    public ReservationDTO getReservationById(String id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return modelMapper.map(reservation, ReservationDTO.class);
    }

    public void deleteReservation(String id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }
}
