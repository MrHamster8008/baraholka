package com.yagubkov.baraholka.service;

import com.yagubkov.baraholka.entity.Information;
import com.yagubkov.baraholka.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class InformationService {

    @Autowired
    private InformationRepository informationRepository;

    public List<Information> getAllInformation() {
        return informationRepository.findAll();
    }

    public Information getInformationById(String id) {
        return informationRepository.findById(id).orElse(null);
    }

    public Information saveInformation(Information information) {
        return informationRepository.save(information);
    }

    public void deleteInformation(String id) {
        informationRepository.deleteById(id);
    }

    public List<Information> getInformationByRentalId(Integer rentalId) {
        return informationRepository.findByRentalPlacementId(rentalId);
    }

    public List<Information> getInformationBySaleDate(LocalDate saleDate) {
        return informationRepository.findBySaleDate(saleDate);
    }

    public List<Information> getInformationByDateRange(LocalDate startDate, LocalDate endDate) {
        return informationRepository.findBySaleDateBetween(startDate, endDate);
    }

    public boolean existsInformation(String orderId) {
        return informationRepository.existsById(orderId);
    }
}