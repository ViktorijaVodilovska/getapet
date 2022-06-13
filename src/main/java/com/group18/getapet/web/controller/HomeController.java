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
    public String seeAds(@RequestParam(required = false) AdType type, Model model) {
        List<Advertisement> ads;
        if(type == null)
            ads = this.adsservice.listAll();
        else {
            ads = this.adsservice.listAllByAdType(type);
        }
        model.addAttribute("ads", ads);
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}