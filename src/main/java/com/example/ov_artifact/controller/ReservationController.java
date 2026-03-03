package com.example.ov_artifact.controller;

import com.example.ov_artifact.dto.ReservationDTO;
import com.example.ov_artifact.services.ReservationService;
import com.example.ov_artifact.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/check-availability")
    public ResponseEntity<StandardResponse> checkAvailability(
            @RequestParam String roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        boolean available = reservationService.isRoomAvailable(roomId, checkIn, checkOut);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Availability Checked", available),
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

    @PutMapping("/update")
    public ResponseEntity<StandardResponse> updateReservation(@RequestBody ReservationDTO reservationDTO) {
        reservationService.updateReservation(reservationDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Reservation Updated Successfully", null),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse> deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Reservation Deleted Successfully", null),
                HttpStatus.OK);
    }
}
