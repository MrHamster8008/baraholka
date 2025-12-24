package com.yagubkov.baraholka.controller;

import com.yagubkov.baraholka.entity.Landlord;
import com.yagubkov.baraholka.service.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/landlords")
public class LandlordController {

    @Autowired
    private LandlordService landlordService;

    // Показать всех арендодателей
    @GetMapping
    public String getAllLandlords(Model model) {
        List<Landlord> landlords = landlordService.getAllLandlords();
        model.addAttribute("landlords", landlords);
        return "landlords/list";
    }

    // Созданть нового арендодателя
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("landlord", new Landlord());
        return "landlords/create";
    }

    // Сохранить нового арендодателя
    @PostMapping
    public String saveLandlord(@ModelAttribute Landlord landlord) {
        landlordService.saveLandlord(landlord);
        return "redirect:/landlords";
    }

    // Редактировать арендодателя
    @GetMapping("/edit/{name}")
    public String showEditForm(@PathVariable String name, Model model) {
        Landlord landlord = landlordService.getLandlordByName(name);
        if (landlord == null) {
            return "redirect:/landlords";
        }
        model.addAttribute("landlord", landlord);
        return "landlords/edit";
    }

    // Обновить арендодателя
    @PostMapping("/update/{name}")
    public String updateLandlord(@PathVariable String name, @ModelAttribute Landlord landlord) {
        landlordService.saveLandlord(landlord);
        return "redirect:/landlords";
    }

    // Удалить арендодателя
    @GetMapping("/delete/{name}")
    public String deleteLandlord(@PathVariable String name) {
        landlordService.deleteLandlord(name);
        return "redirect:/landlords";
    }

    // Поиск арендодателей
    @GetMapping("/search")
    public String searchLandlords(@RequestParam(required = false) String name, Model model) {
        List<Landlord> landlords;
        if (name != null && !name.trim().isEmpty()) {
            landlords = landlordService.searchLandlordsByName(name);
            model.addAttribute("searchQuery", name);
        } else {
            landlords = landlordService.getAllLandlords();
        }
        model.addAttribute("landlords", landlords);
        return "landlords/list";
    }
}