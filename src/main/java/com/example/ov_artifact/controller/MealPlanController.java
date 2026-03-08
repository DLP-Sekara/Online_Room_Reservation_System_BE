package com.example.ov_artifact.controller;

import com.example.ov_artifact.dto.MealPlanDTO;
import com.example.ov_artifact.services.MealPlanService;
import com.example.ov_artifact.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/meal-plans")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

    @PostMapping("/add")
    public ResponseEntity<StandardResponse> addMealPlan(@RequestBody MealPlanDTO mealPlanDTO) {
        mealPlanService.addMealPlan(mealPlanDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 201, "Meal Plan Added Successfully", null),
                HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<StandardResponse> updateMealPlan(@RequestBody MealPlanDTO mealPlanDTO) {
        mealPlanService.updateMealPlan(mealPlanDTO);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Meal Plan Updated Successfully", null),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StandardResponse> deleteMealPlan(@PathVariable String id) {
        mealPlanService.deleteMealPlan(id);
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Meal Plan Deleted Successfully", null),
                HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllMealPlans() {
        List<MealPlanDTO> mealPlans = mealPlanService.getAllMealPlans();
        return new ResponseEntity<>(
                new StandardResponse(true, 200, "Meal Plans Fetched Successfully", mealPlans),
                HttpStatus.OK);
    }
}
