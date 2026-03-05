package com.example.ov_artifact.repository;

import com.example.ov_artifact.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {

       @Query("SELECT r FROM Reservation r WHERE r.room.roomId = :roomId AND " +
                     "(r.checkIn < :checkOut AND r.checkOut > :checkIn) AND " +
                     "r.status != 'CANCELLED'")
       List<Reservation> findOverlappingReservations(@Param("roomId") String roomId,
                     @Param("checkIn") LocalDate checkIn,
                     @Param("checkOut") LocalDate checkOut);

       @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
                     "WHERE r.room.roomId = :roomId " +
                     "AND r.status != 'CANCELLED' " +
                     "AND (r.checkIn < :checkOut AND r.checkOut > :checkIn)")
       boolean existsOverlappingReservation(
                     @Param("roomId") String roomId,
                     @Param("checkIn") LocalDate checkIn,
                     @Param("checkOut") LocalDate checkOut);
}
