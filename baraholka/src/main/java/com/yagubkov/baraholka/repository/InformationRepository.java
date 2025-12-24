package com.yagubkov.baraholka.repository;

import com.yagubkov.baraholka.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InformationRepository extends JpaRepository<Information, String> {

    List<Information> findByRentalPlacementId(Integer placementId);

    List<Information> findBySaleDate(LocalDate saleDate);

    List<Information> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
}