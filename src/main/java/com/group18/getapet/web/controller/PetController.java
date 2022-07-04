package com.group18.getapet.web.controller;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import com.group18.getapet.model.exceptions.PetNotFoundException;
import com.group18.getapet.service.AdvertisementService;
import com.group18.getapet.service.PetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;
    private final AdvertisementService advertisementService;

    public PetController(PetService petService, AdvertisementService advertisementService) {
        this.petService = petService;
        this.advertisementService = advertisementService;
    }


    @GetMapping("/{id}")
    public String getPetById(@PathVariable Long id, Model model) {
        Pet pet = this.petService.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        List<Advertisement> ads = pet.getAds();
        List<Advertisement> adsWithPetType = this.advertisementService.filterAds(null,pet.getPetType(),null,null,null).stream().limit(12).collect(Collectors.toList());
        model.addAttribute("pet", pet);
        model.addAttribute("ads", ads);
        model.addAttribute("adsWithPetType", adsWithPetType);
        model.addAttribute("user", ads.get(0).getUser());
        model.addAttribute("bodyContent", "pet-page");
        return "master-template";
    }

    @GetMapping("/add")
    @PreAuthorize( value="isAuthenticated()")
    public String addPet(Model model) {
        model.addAttribute("bodyContent", "addPet");
        return "master-template";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize( value="isAuthenticated()")
    public String editPet(@PathVariable Long id, Model model) {
        if (this.petService.findById(id).isPresent()) {

            Pet pet = this.petService.findById(id).orElseThrow(() -> new PetNotFoundException(id));

            model.addAttribute("pet", pet);
            model.addAttribute("bodyContent", "addPet");
            return "master-template";
        }
        return "redirect:/ads";
    }

    @PostMapping("/add")
    @PreAuthorize( value="isAuthenticated()")
    public String addPet(@RequestParam(required = false) Long id,
                         @RequestParam(required = false) String name,
                         @RequestParam PetType petType,
                         @RequestParam(required = false) String breed,
                         @RequestParam(required = false) Integer age,
                         @RequestParam String image,
                         @RequestParam PetSize petSize,
                         @RequestParam PetGender petGender) {

        if (id == null) {
            this.petService.create(name, petType, breed, age, image, petSize, petGender);
        } else {
            if(!this.petService.findById(id).isPresent()){
                throw new PetNotFoundException(id);
            }
            this.petService.update(id, name, petType, breed, age, image, petSize, petGender);
        }
        return "redirect:/ads/add";
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePet(@PathVariable Long id) {
        if (this.petService.findById(id).isPresent()) {
            this.petService.deleteById(id);
            return "redirect:/pets";
        }

        return "redirect:/pets?error=Pet+Not+Found";
    }
}
