package com.yagubkov.baraholka.service;

import com.yagubkov.baraholka.entity.Rental;
import com.yagubkov.baraholka.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Integer id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public Rental saveRental(Rental rental) {
        // Автоматически рассчитать дни аренды, если не указаны
        if (rental.getRentalDays() == null && rental.getStartDate() != null && rental.getEndDate() != null) {
            long days = java.time.temporal.ChronoUnit.DAYS.between(
                    rental.getStartDate(), rental.getEndDate()
            );
            rental.setRentalDays((int) days);
        }
        return rentalRepository.save(rental);
    }

    public void deleteRental(Integer id) {
        rentalRepository.deleteById(id);
    }

    public List<Rental> getActiveRentals() {
        return rentalRepository.findByEndDateAfter(LocalDate.now());
    }

    public List<Rental> searchByCategory(String category) {
        return rentalRepository.findByProductCategoryContainingIgnoreCase(category);
    }

    public List<Rental> getRentalsByLandlord(String landlordName) {
        return rentalRepository.findByLandlordLandlordName(landlordName);
    }

    public List<Rental> getRentalsInPeriod(LocalDate startDate, LocalDate endDate) {
        return rentalRepository.findRentalsByDateRange(startDate, endDate);
    }
}