package com.example.ov_artifact.controller;

import com.example.ov_artifact.dto.FoodItemDTO;
import com.example.ov_artifact.services.FoodItemService;
import com.example.ov_artifact.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/food-items")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    @PostMapping("/add")
    public ResponseEntity<StandardResponse> addFoodItem(@RequestBody FoodItemDTO foodItemDTO) {
        foodItemService.addFoodItem(foodItemDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 201, "Food Item Added Successfully", null),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<StandardResponse> updateFoodItem(@RequestBody FoodItemDTO foodItemDTO) {
        foodItemService.updateFoodItem(foodItemDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Food Item Updated Successfully", null),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse> deleteFoodItem(@PathVariable String id) {
        foodItemService.deleteFoodItem(id);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Food Item Deleted Successfully", null),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllFoodItems() {
        List<FoodItemDTO> foodItems = foodItemService.getAllFoodItems();
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Food Items Fetched Successfully", foodItems),
                HttpStatus.OK);
    }
}
