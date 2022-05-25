package com.group18.getapet.web.controller;


import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.AdType;
import com.group18.getapet.model.exceptions.AdvertisementNotFoundException;
import com.group18.getapet.model.exceptions.UserNotFoundException;
import com.group18.getapet.service.AdvertisementService;
import com.group18.getapet.service.UserService;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/ads")
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final UserService userService;

    public AdvertisementController(AdvertisementService advertisementService, UserService userService) {
        this.advertisementService = advertisementService;
        this.userService = userService;
    }
/*
    @GetMapping
    public String getAllAdvertisements(Model model) {
        List<Advertisement> advertisementList = this.advertisementService.listAll();
        model.addAttribute("advertisementList", advertisementList);
        return "";
    }*/

    @GetMapping("/{id}")
    public String getAdvertisementById(@PathVariable Long id, Model model) {
        if (this.advertisementService.findById(id).isPresent()) {
            Advertisement advertisement = this.advertisementService.findById(id)
                    .orElseThrow(() -> new AdvertisementNotFoundException(id));
            model.addAttribute("advertisement", advertisement);
            return "";
        }
        return "redirect:/ads?error=Advertisement+was+not+found";
    }

    @GetMapping("/filter")
    public String getAdvertisementsByFilter(@RequestParam AdType adType,Model model) {
        List<Advertisement>advertisementList=this.advertisementService.listAllByAdType(adType);
        model.addAttribute("advertisementList",advertisementList);
        return "";
    }


    @PostMapping("/add")
    public String addAdvertisement(HttpServletRequest request,
                                   @RequestParam String title,
                                   @RequestParam AdType adType,
                                   @RequestParam Pet pet,
                                   @RequestParam User user,
                                   @RequestParam String location,
                                   @RequestParam Integer price) { //za site polinja
        String username =request.getRemoteUser();
        User user1=this.userService.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
        this.advertisementService.create(title,adType,pet,user1,location,price);

        return "redirect:/ads";
    }

    @PostMapping("/update/{id}")
    public String updatePet(HttpServletRequest request,
                            @PathVariable Long id,
                            @RequestParam String title,
                            @RequestParam AdType adType,
                            @RequestParam Pet pet,
                            @RequestParam User user,
                            @RequestParam String location,
                            @RequestParam Integer price) { //za site polinja
        String username =request.getRemoteUser();
        User user1=this.userService.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
        this.advertisementService.update(id,title,adType,pet,user1,location,price);
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAdvertisement(@PathVariable Long id) {
        if(this.advertisementService.findById(id).isPresent()){
            this.advertisementService.deleteById(id);
            return "redirect:/ads";

        }
        return "redirect:/ads?error=Advertisement+was+not+found";
    }
}
