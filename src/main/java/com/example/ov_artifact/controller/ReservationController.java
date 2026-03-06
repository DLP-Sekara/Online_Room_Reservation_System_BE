package com.example.ov_artifact.controller;

import com.example.ov_artifact.dto.ReservationDTO;
import com.example.ov_artifact.dto.RoomDTO;
import com.example.ov_artifact.services.ReservationService;
import com.example.ov_artifact.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<StandardResponse> createReservation(@RequestBody ReservationDTO reservationDTO) {
        String resId = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 201, "Reservation Created Successfully", resId),
                HttpStatus.CREATED);
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<StandardResponse> getAvailableRooms(
            @RequestParam String typeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {

        List<RoomDTO> availableRooms = reservationService.getAvailableRoomsForBooking(typeId, checkIn, checkOut);

        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Available Rooms Fetched", availableRooms),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Reservations Fetched Successfully", reservations),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> getReservationById(@PathVariable String id) {
        ReservationDTO reservationDTO = reservationService.getReservationById(id);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Reservation Fetched Successfully", reservationDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse> deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Reservation Deleted Successfully", null),
                HttpStatus.OK);
    }

    @PutMapping("/checkout/{resId}")
    public ResponseEntity<StandardResponse> checkOutGuest(@PathVariable String resId) {
        reservationService.checkOutGuest(resId);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Guest Checked Out Successfully. Room moved to Maintenance.", null),
                HttpStatus.OK);
    }

    @GetMapping("/income-stats")
    public ResponseEntity<StandardResponse> getIncomeStats(
            @RequestParam int year,
            @RequestParam int month) {

        Map<String, BigDecimal> stats = reservationService.getIncomeStats(year, month);

        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Income Stats Fetched", stats),
                HttpStatus.OK);
    }
}
