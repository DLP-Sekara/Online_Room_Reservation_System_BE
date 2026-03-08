package com.example.ov_artifact.controller;

import com.example.ov_artifact.dto.RoomTypeDTO;
import com.example.ov_artifact.services.RoomTypeService;
import com.example.ov_artifact.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/room-types")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping("/add")
    public ResponseEntity<StandardResponse> addRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        roomTypeService.addRoomType(roomTypeDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 201, "Room Type Added Successfully", null),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<StandardResponse> updateRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        roomTypeService.updateRoomType(roomTypeDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Room Type Updated Successfully", null),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse> deleteRoomType(@PathVariable String id) {
        roomTypeService.deleteRoomType(id);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Room Type Deleted Successfully", null),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllRoomTypes() {
        List<RoomTypeDTO> roomTypes = roomTypeService.getAllRoomTypes();
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Room Types Fetched Successfully", roomTypes),
                HttpStatus.OK);
    }
}