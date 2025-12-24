package com.yagubkov.baraholka.controller;

import com.yagubkov.baraholka.entity.Rental;
import com.yagubkov.baraholka.entity.Landlord;
import com.yagubkov.baraholka.service.RentalService;
import com.yagubkov.baraholka.service.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private LandlordService landlordService;

    // Показать все аренды
    @GetMapping
    public String getAllRentals(Model model) {
        try {
            List<Rental> rentals = rentalService.getAllRentals();
            model.addAttribute("rentals", rentals);
            return "rentals/list";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при загрузке аренд: " + e.getMessage());
            return "error";
        }
    }

    // Cоздать новую аренду
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        try {
            Rental rental = new Rental();
            List<Landlord> landlords = landlordService.getAllLandlords();
            model.addAttribute("rental", rental);
            model.addAttribute("landlords", landlords);
            return "rentals/create";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при загрузке формы: " + e.getMessage());
            return "error";
        }
    }

    // Сохранить новую аренду
    @PostMapping
    public String saveRental(@ModelAttribute Rental rental,
                             @RequestParam("landlordName") String landlordName,
                             Model model) {
        try {
            System.out.println("Сохранение аренды. landlordName: " + landlordName);

            if (landlordName == null || landlordName.trim().isEmpty()) {
                throw new IllegalArgumentException("Не выбран арендодатель");
            }

            Landlord landlord = landlordService.getLandlordByName(landlordName);
            if (landlord == null) {
                throw new IllegalArgumentException("Арендодатель не найден: " + landlordName);
            }

            rental.setLandlord(landlord);

            if (rental.getStartDate() == null) {
                rental.setStartDate(LocalDate.now());
            }
            if (rental.getEndDate() == null) {
                rental.setEndDate(LocalDate.now().plusDays(30));
            }
            if (rental.getRentalCost() == null) {
                rental.setRentalCost(new BigDecimal("1000.00"));
            }
            if (rental.getRentalDays() == null && rental.getStartDate() != null && rental.getEndDate() != null) {
                long days = java.time.temporal.ChronoUnit.DAYS.between(
                        rental.getStartDate(), rental.getEndDate()
                );
                rental.setRentalDays((int) days);
            }

            Rental savedRental = rentalService.saveRental(rental);
            System.out.println("Аренда сохранена с ID: " + savedRental.getPlacementId());

            return "redirect:/rentals";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка при сохранении аренды: " + e.getMessage());
            model.addAttribute("rental", rental);
            model.addAttribute("landlords", landlordService.getAllLandlords());
            return "rentals/create";
        }
    }

    // Редактировать аренду
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Rental rental = rentalService.getRentalById(id);
        if (rental == null) {
            return "redirect:/rentals";
        }
        List<Landlord> landlords = landlordService.getAllLandlords();
        model.addAttribute("rental", rental);
        model.addAttribute("landlords", landlords);
        return "rentals/edit";
    }

    // Обновить аренду
    @PostMapping("/update/{id}")
    public String updateRental(@PathVariable Integer id,
                               @ModelAttribute Rental rental,
                               @RequestParam String landlordName) {
        Rental existingRental = rentalService.getRentalById(id);
        if (existingRental != null) {
            existingRental.setProductCategory(rental.getProductCategory());
            existingRental.setStartDate(rental.getStartDate());
            existingRental.setEndDate(rental.getEndDate());
            existingRental.setRentalDays(rental.getRentalDays());
            existingRental.setRentalCost(rental.getRentalCost());
            existingRental.setShelfId(rental.getShelfId());

            Landlord landlord = landlordService.getLandlordByName(landlordName);
            if (landlord != null) {
                existingRental.setLandlord(landlord);
            }

            rentalService.saveRental(existingRental);
        }
        return "redirect:/rentals";
    }

    // Удалить аренду
    @GetMapping("/delete/{id}")
    public String deleteRental(@PathVariable Integer id) {
        rentalService.deleteRental(id);
        return "redirect:/rentals";
    }

    // Показать активные аренды
    @GetMapping("/active")
    public String getActiveRentals(Model model) {
        List<Rental> rentals = rentalService.getActiveRentals();
        model.addAttribute("rentals", rentals);
        model.addAttribute("isActive", true);
        return "rentals/list";
    }

    // Поиск аренд
    @GetMapping("/search")
    public String searchRentals(@RequestParam(required = false) String category, Model model) {
        List<Rental> rentals;
        if (category != null && !category.trim().isEmpty()) {
            rentals = rentalService.searchByCategory(category);
            model.addAttribute("searchQuery", category);
        } else {
            rentals = rentalService.getAllRentals();
        }
        model.addAttribute("rentals", rentals);
        return "rentals/list";
    }
}