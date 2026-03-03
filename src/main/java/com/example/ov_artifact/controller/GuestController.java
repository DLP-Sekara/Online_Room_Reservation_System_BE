package com.example.ov_artifact.controller;

import com.example.ov_artifact.dto.GuestDTO;
import com.example.ov_artifact.services.GuestService;
import com.example.ov_artifact.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping("/add")
    public ResponseEntity<StandardResponse> addGuest(@RequestBody GuestDTO guestDTO) {
        guestService.addGuest(guestDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 201, "Guest Added Successfully", null),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<StandardResponse> updateGuest(@RequestBody GuestDTO guestDTO) {
        guestService.updateGuest(guestDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Guest Updated Successfully", null),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse> deleteGuest(@PathVariable String id) {
        guestService.deleteGuest(id);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Guest Deleted Successfully", null),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllGuests() {
        List<GuestDTO> guests = guestService.getAllGuests();
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Guests Fetched Successfully", guests),
                HttpStatus.OK);
    }
}
