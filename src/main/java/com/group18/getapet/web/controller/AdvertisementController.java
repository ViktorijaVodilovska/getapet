package com.group18.getapet.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.AdType;
import com.group18.getapet.model.exceptions.AdvertisementNotFoundException;
import com.group18.getapet.model.exceptions.PetNotFoundException;
import com.group18.getapet.model.exceptions.UserNotFoundException;
import com.group18.getapet.service.AdvertisementService;
import com.group18.getapet.service.PetService;
import com.group18.getapet.service.UserService;


@Controller
@RequestMapping("/ads")
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final UserService userService;
    private final PetService petService;

    public AdvertisementController(AdvertisementService advertisementService,
                                   UserService userService,
                                   PetService petService) {
        this.advertisementService = advertisementService;
        this.userService = userService;
        this.petService = petService;
    }

    @GetMapping
    public String getAllAdvertisements(Model model) {
        List<Advertisement> advertisementList = this.advertisementService.listAll();
        model.addAttribute("ads", advertisementList);
        return "ads";
    }

    @GetMapping("/{id}")
    public String getAdvertisementById(@PathVariable Long id, Model model) {
        if (this.advertisementService.findById(id).isPresent()) {
            Advertisement advertisement = this.advertisementService.findById(id)
                .orElseThrow(() -> new AdvertisementNotFoundException(id));
            model.addAttribute("ad", advertisement);
            return "ad";
        }
        return "redirect:/ads?error=Advertisement+was+not+found";
    }

    @GetMapping("/filter")
    public String getAdvertisementsByFilter(@RequestParam AdType adType, Model model) {
        List<Advertisement> advertisementList = this.advertisementService.listAllByAdType(adType);
        model.addAttribute("ads", advertisementList);
        return "";
    }

    @GetMapping("/add")
    public String addAdvertisement(Model model) {
        List<Pet> pets = this.petService.listAll();
        model.addAttribute("pets", pets);
        return "addAdvertisement";
    }

    @PostMapping("/add")
    public String addAdvertisement(HttpServletRequest request,
                                   @RequestParam String title,
                                   @RequestParam String description,
                                   @RequestParam AdType adType,
                                   @RequestParam Long pet,
                                   @RequestParam String location) {
        String username = request.getRemoteUser();
        User user1 = this.userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Pet p = this.petService.findById(pet).orElseThrow(() -> new PetNotFoundException(pet));
        this.advertisementService.create(title, description, adType, p, user1, location);

        return "redirect:/ads";
    }

    @GetMapping("/update/{id}")
    public String updatePet(@PathVariable Long id, Model model) {
        if (this.advertisementService.findById(id).isPresent()) {
            Advertisement ad =
                this.advertisementService.findById(id).orElseThrow(() -> new AdvertisementNotFoundException(id));
            model.addAttribute("ad", ad);
            return "addAdvertisement";
        }
        return "redirect:/ads";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAdvertisement(@PathVariable Long id) {
        if (this.advertisementService.findById(id).isPresent()) {
            this.advertisementService.deleteById(id);
            return "redirect:/ads";

        }
        return "redirect:/ads?error=Advertisement+was+not+found";
    }
}
