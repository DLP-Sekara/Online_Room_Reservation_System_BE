package com.example.ov_artifact.services;

import com.example.ov_artifact.dto.MealPlanDTO;
import com.example.ov_artifact.entity.MealPlan;
import com.example.ov_artifact.repository.MealPlanRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void addMealPlan(MealPlanDTO mealPlanDTO) {
        if (mealPlanRepository.existsByName(mealPlanDTO.getName())) {
            throw new IllegalArgumentException("Meal Plan name '" + mealPlanDTO.getName() + "' already exists!");
        }
        MealPlan mealPlan = modelMapper.map(mealPlanDTO, MealPlan.class);
        mealPlanRepository.save(mealPlan);
    }

    public void updateMealPlan(MealPlanDTO mealPlanDTO) {
        if (mealPlanRepository.existsById(mealPlanDTO.getPlanId())) {
            MealPlan mealPlan = modelMapper.map(mealPlanDTO, MealPlan.class);
            mealPlanRepository.save(mealPlan);
        } else {
            throw new RuntimeException("Meal Plan not found for ID: " + mealPlanDTO.getPlanId());
        }
    }

    public void deleteMealPlan(String id) {
        if (mealPlanRepository.existsById(id)) {
            mealPlanRepository.deleteById(id);
        } else {
            throw new RuntimeException("Meal Plan not found for ID: " + id);
        }
    }

    public List<MealPlanDTO> getAllMealPlans() {
        List<MealPlan> mealPlans = mealPlanRepository.findAll();
        return mealPlans.stream()
                .map(mealPlan -> modelMapper.map(mealPlan, MealPlanDTO.class))
                .collect(Collectors.toList());
    }
}
