package com.group18.getapet.web.controller;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.AdType;
import com.group18.getapet.service.AdsService;
import com.group18.getapet.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class AdController {

    private final AdsService adsservice;
    private final PetService petService;

    public AdController(AdsService adsservice, PetService petService){

        this.adsservice = adsservice;
        this.petService = petService;
    }

    @GetMapping({"/ads"})
    public String seeAds(@RequestParam AdType type, Model model) {
        List<Advertisement> ads;
        List<Pet> pets = this.petService.listAll();
        if(type == null)
            ads = this.adsservice.listAll();
        else {
            ads = this.adsservice.filter(type);
        }
        model.addAttribute("ads", ads);
        model.addAttribute("pets", pets);
        return "index";
    }
    @PostMapping("/ads")
    public String create(@RequestParam String title,
                         @RequestParam AdType adType,
                         @RequestParam Pet pet,
                         @RequestParam User user,
                         @RequestParam String location,
                         @RequestParam Integer price){
        this.adsservice.create(title, adType, pet, user, location, price);
        return "redirect:/ads";
    }
    @PostMapping("/ads/{title}")
    public String update(@PathVariable String title,
                         @RequestParam AdType adType,
                         @RequestParam Pet pet,
                         @RequestParam User user,
                         @RequestParam String location,
                         @RequestParam Integer price){
        this.adsservice.update(title, adType, pet, user, location, price);
        return "redirect:/ads";
    }
    @PostMapping("/ads/{title}/delete")
    public String delete (@PathVariable String title){
        this.adsservice.delete(title);
        return "redirect:/ads";
    }
}