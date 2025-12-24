package com.yagubkov.baraholka.service;

import com.yagubkov.baraholka.entity.Landlord;
import com.yagubkov.baraholka.repository.LandlordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LandlordService {

    @Autowired
    private LandlordRepository landlordRepository;

    public List<Landlord> getAllLandlords() {
        return landlordRepository.findAll();
    }

    public Landlord getLandlordByName(String name) {
        return landlordRepository.findById(name).orElse(null);
    }

    public Landlord saveLandlord(Landlord landlord) {
        return landlordRepository.save(landlord);
    }

    public void deleteLandlord(String name) {
        landlordRepository.deleteById(name);
    }

    public List<Landlord> searchLandlordsByName(String name) {
        return landlordRepository.findByLandlordNameContainingIgnoreCase(name);
    }

    public boolean existsLandlord(String name) {
        return landlordRepository.existsById(name);
    }
}