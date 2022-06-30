package com.group18.getapet.web.controller;


import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.AdType;
import com.group18.getapet.service.AdvertisementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

//List ads for adoption (ADOPTING)
//List ads for buying
//See pet ads (for the button, redirect to /ads) *moze i so thymeleaf redirects
//Create an ad (for the button, redirect to /ads/add)
@Controller
@RequestMapping({"/home","/"})
public class HomeController {
    private final AdvertisementService adsservice;
    public HomeController(AdvertisementService adsservice){
        this.adsservice = adsservice;
    }

    @GetMapping
    public String seeAds(Model model) {
        List<Advertisement> adsForSale = this.adsservice.listAllByAdType(AdType.SELLING).stream().limit(12).collect(Collectors.toList());
        List<Advertisement> adsForAdoption = this.adsservice.listAllByAdType(AdType.GIVING).stream().limit(12).collect(Collectors.toList());

        model.addAttribute("adsForSale", adsForSale);
        model.addAttribute("adsForAdoption", adsForAdoption);
        model.addAttribute("bodyContent","index");
        return "master-template";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("bodyContent","about");
        return "master-template";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("bodyContent","contact");
        return "master-template";
    }
}