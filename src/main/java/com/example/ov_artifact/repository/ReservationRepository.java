package com.example.ov_artifact.repository;

import com.example.ov_artifact.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

       @Query("SELECT COUNT(res) > 0 FROM Reservation res " +
                     "WHERE res.room.roomId = :roomId " +
                     "AND res.status NOT IN (com.example.ov_artifact.util.ReservationStatus.CANCELLED, com.example.ov_artifact.util.ReservationStatus.COMPLETED) "
                     +
                     "AND (res.checkIn < :checkOut AND res.checkOut > :checkIn)")
       boolean existsOverlappingReservation(
                     @Param("roomId") String roomId,
                     @Param("checkIn") LocalDate checkIn,
                     @Param("checkOut") LocalDate checkOut);

       // 1. For All Time Incomes
       @Query("SELECT SUM(r.totalBill) FROM Reservation r WHERE r.status = 'COMPLETED'")
       BigDecimal calculateTotalAllTimeIncome();

       // 2. For Monthly Incomes
       @Query("SELECT SUM(r.totalBill) FROM Reservation r " +
                     "WHERE r.status = 'COMPLETED' " +
                     "AND YEAR(r.checkOut) = :year " +
                     "AND MONTH(r.checkOut) = :month")
       BigDecimal calculateMonthlyIncome(@Param("year") int year, @Param("month") int month);
}
