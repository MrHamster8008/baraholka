package com.yagubkov.baraholka.controller;

import com.yagubkov.baraholka.entity.Information;
import com.yagubkov.baraholka.entity.Rental;
import com.yagubkov.baraholka.service.InformationService;
import com.yagubkov.baraholka.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/information")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private RentalService rentalService;

    // Показать всю информацию
    @GetMapping
    public String getAllInformation(Model model) {
        try {
            List<Information> informationList = informationService.getAllInformation();
            model.addAttribute("informationList", informationList);
            return "information/list";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при загрузке информации: " + e.getMessage());
            return "error";
        }
    }

    // Создание информации
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        try {
            Information info = new Information();
            List<Rental> rentals = rentalService.getAllRentals();
            model.addAttribute("information", info);
            model.addAttribute("rentals", rentals);
            return "information/create";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при загрузке формы: " + e.getMessage());
            return "error";
        }
    }

    // Сохранить новую информацию
    @PostMapping
    public String saveInformation(@ModelAttribute Information information,
                                  @RequestParam("rentalId") Integer rentalId,
                                  Model model) {
        try {
            System.out.println("Сохранение информации. rentalId: " + rentalId);

            if (rentalId == null) {
                throw new IllegalArgumentException("Не выбрана аренда");
            }

            Rental rental = rentalService.getRentalById(rentalId);
            if (rental == null) {
                throw new IllegalArgumentException("Аренда не найдена с ID: " + rentalId);
            }

            information.setRental(rental);

            if (information.getSaleDate() == null) {
                information.setSaleDate(java.time.LocalDate.now());
            }

            Information savedInfo = informationService.saveInformation(information);
            System.out.println("Информация сохранена с ID: " + savedInfo.getOrderId());

            return "redirect:/information";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ошибка при сохранении информации: " + e.getMessage());
            model.addAttribute("information", information);
            model.addAttribute("rentals", rentalService.getAllRentals());
            return "information/create";
        }
    }

    // Редактировать информацию
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Information info = informationService.getInformationById(id);
        if (info == null) {
            return "redirect:/information";
        }
        List<Rental> rentals = rentalService.getAllRentals();
        model.addAttribute("information", info);
        model.addAttribute("rentals", rentals);
        return "information/edit";
    }

    // Обновить информацию
    @PostMapping("/update/{id}")
    public String updateInformation(@PathVariable String id,
                                    @ModelAttribute Information information,
                                    @RequestParam Integer rentalId) {
        Information existingInfo = informationService.getInformationById(id);
        if (existingInfo != null) {
            existingInfo.setSaleDate(information.getSaleDate());

            Rental rental = rentalService.getRentalById(rentalId);
            if (rental != null) {
                existingInfo.setRental(rental);
            }

            informationService.saveInformation(existingInfo);
        }
        return "redirect:/information";
    }

    // Удалить информацию
    @GetMapping("/delete/{id}")
    public String deleteInformation(@PathVariable String id) {
        informationService.deleteInformation(id);
        return "redirect:/information";
    }

    // Поиск информации
    @GetMapping("/search")
    public String searchInformation(@RequestParam(required = false) Integer rentalId, Model model) {
        List<Information> informationList;
        if (rentalId != null) {
            informationList = informationService.getInformationByRentalId(rentalId);
            model.addAttribute("searchQuery", rentalId);
        } else {
            informationList = informationService.getAllInformation();
        }
        model.addAttribute("informationList", informationList);
        return "information/list";
    }
}