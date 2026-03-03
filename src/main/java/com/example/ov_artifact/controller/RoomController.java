package com.example.ov_artifact.controller;

import com.example.ov_artifact.dto.RoomDTO;
import com.example.ov_artifact.services.RoomService;
import com.example.ov_artifact.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/add")
    public ResponseEntity<StandardResponse> addRoom(@RequestBody RoomDTO roomDTO) {
        roomService.addRoom(roomDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 201, "Room Added Successfully", null),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<StandardResponse> updateRoom(@RequestBody RoomDTO roomDTO) {
        roomService.updateRoom(roomDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Room Updated Successfully", null),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse> deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Room Deleted Successfully", null),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllRooms() {
        List<RoomDTO> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Rooms Fetched Successfully", rooms),
                HttpStatus.OK);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<StandardResponse> getRoomById(@PathVariable String id) {
        RoomDTO roomDTO = roomService.getRoomById(id);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Room Fetched Successfully", roomDTO),
                HttpStatus.OK);
    }
}
