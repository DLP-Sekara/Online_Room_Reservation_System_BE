package com.example.ov_artifact.services;

import com.example.ov_artifact.dto.GuestDTO;
import com.example.ov_artifact.entity.Guest;
import com.example.ov_artifact.repository.GuestRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void addGuest(GuestDTO guestDTO) {
        if (guestRepository.existsByNic(guestDTO.getNic())) {
            throw new IllegalArgumentException("Room number '" + guestDTO.getName() + "' already exists!");
        }

        Guest guest = modelMapper.map(guestDTO, Guest.class);
        guestRepository.save(guest);
    }

    public void updateGuest(GuestDTO guestDTO) {
        if (guestRepository.existsById(guestDTO.getGuestId())) {
            Guest guest = modelMapper.map(guestDTO, Guest.class);
            guestRepository.save(guest);
        } else {
            throw new RuntimeException("Guest not found for ID: " + guestDTO.getGuestId());
        }
    }

    public void deleteGuest(String id) {
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
        } else {
            throw new RuntimeException("Guest not found for ID: " + id);
        }
    }

    public List<GuestDTO> getAllGuests() {
        List<Guest> guests = guestRepository.findAll();
        return modelMapper.map(guests, new TypeToken<List<GuestDTO>>() {
        }.getType());
    }
}
