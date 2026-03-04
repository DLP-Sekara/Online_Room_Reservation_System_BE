package com.example.ov_artifact.repository;

import com.example.ov_artifact.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    boolean existsByRoomNumber(String roomNumber);
}
