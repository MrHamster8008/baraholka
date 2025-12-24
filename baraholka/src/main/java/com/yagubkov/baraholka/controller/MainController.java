package com.yagubkov.baraholka.controller;

import com.yagubkov.baraholka.service.LandlordService;
import com.yagubkov.baraholka.service.RentalService;
import com.yagubkov.baraholka.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private LandlordService landlordService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private InformationService informationService;

    @GetMapping("/")
    public String home(Model model) {
        try {
            long landlordCount = landlordService.getAllLandlords() != null ? landlordService.getAllLandlords().size() : 0;
            long rentalCount = rentalService.getAllRentals() != null ? rentalService.getAllRentals().size() : 0;
            long activeRentalCount = rentalService.getActiveRentals() != null ? rentalService.getActiveRentals().size() : 0;
            long infoCount = informationService.getAllInformation() != null ? informationService.getAllInformation().size() : 0;

            model.addAttribute("landlordCount", landlordCount);
            model.addAttribute("rentalCount", rentalCount);
            model.addAttribute("activeRentalCount", activeRentalCount);
            model.addAttribute("infoCount", infoCount);

            return "index";
        } catch (Exception e) {
            model.addAttribute("landlordCount", 0);
            model.addAttribute("rentalCount", 0);
            model.addAttribute("activeRentalCount", 0);
            model.addAttribute("infoCount", 0);
            return "index";
        }
    }
}