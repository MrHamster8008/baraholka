package com.yagubkov.baraholka.repository;

import com.yagubkov.baraholka.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

    List<Rental> findByProductCategoryContainingIgnoreCase(String category);

    List<Rental> findByEndDateAfter(LocalDate date);

    List<Rental> findByLandlordLandlordName(String landlordName);

    @Query("SELECT r FROM Rental r WHERE r.startDate BETWEEN :startDate AND :endDate")
    List<Rental> findRentalsByDateRange(@Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);
}