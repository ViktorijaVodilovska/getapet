package com.group18.getapet.web.controller;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import com.group18.getapet.model.exceptions.PetNotFoundException;
import com.group18.getapet.service.AdvertisementService;
import com.group18.getapet.service.PetService;
import com.group18.getapet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;
    private final UserService userService;
    private final AdvertisementService adsService;

    public PetController(PetService petService, UserService userService, AdvertisementService adsService) {
        this.petService = petService;
        this.userService = userService;
        this.adsService = adsService;
    }

    @GetMapping("/{id}")
    public String getPetById(@PathVariable Long id, Model model) {
        Pet pet = this.petService.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        model.addAttribute("pet", pet);
        model.addAttribute("ads", pet.getAds());
        model.addAttribute("bodyContent", "pet-page");
        return "master-template";
    }

    @GetMapping("/add")
    public String addPet(Model model) {
        model.addAttribute("bodyContent", "addPet");
        return "master-template";
    }

    @PostMapping("/add")
    public String addPet(@RequestParam(required = false) String name,
                         @RequestParam PetType petType,
                         @RequestParam(required = false) String breed,
                         @RequestParam Integer age,
                         @RequestParam String image,
                         @RequestParam PetSize petSize,
                         @RequestParam PetGender petGender) {

        Pet p = new Pet(name,petType, breed, age, image, petSize, petGender);
        this.petService.save(p);
        return "redirect:/ads/add";
    }

    @PostMapping("/{id}/update")
    public String updatePet(@PathVariable Long id,
                            @RequestParam(required = false) String name,
                            @RequestParam PetType petType,
                            @RequestParam(required = false) String breed,
                            @RequestParam Integer age,
                            @RequestParam String image,
                            @RequestParam PetSize petSize,
                            @RequestParam PetGender petGender) {
        if (this.petService.findById(id).isPresent()) {
            Pet pet = this.petService.findById(id).orElseThrow(() -> new PetNotFoundException(id));
            this.petService.update(id, name, petType, breed, age, image, petSize, petGender);
            return "redirect:/pets";
        }


        return "redirect:/pets?error=Pet+Not+Found";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePet(@PathVariable Long id) {
        if (this.petService.findById(id).isPresent()) {
            this.petService.deleteById(id);
            return "redirect:/pets";
        }

        return "redirect:/pets?error=Pet+Not+Found";
    }
}
