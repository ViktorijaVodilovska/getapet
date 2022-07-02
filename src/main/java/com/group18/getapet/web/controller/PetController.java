package com.group18.getapet.web.controller;

import com.group18.getapet.model.Pet;
import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import com.group18.getapet.model.exceptions.AdvertisementNotFoundException;
import com.group18.getapet.model.exceptions.PetNotFoundException;
import com.group18.getapet.service.AdvertisementService;
import com.group18.getapet.service.PetService;
import com.group18.getapet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/edit/{id}")
    public String editPet(HttpServletRequest request, @PathVariable Long id, Model model) {
        if (this.petService.findById(id).isPresent()) {

            Pet pet = this.petService.findById(id).orElseThrow(() -> new AdvertisementNotFoundException(id));
            List<Pet> pets = this.petService.listAll();

            model.addAttribute("pet", pet);
            model.addAttribute("bodyContent", "addPet");
            return "master-template";
        }
        return "redirect:/ads";
    }

    @PostMapping("/add")
    public String savePet(@RequestParam(required = false) Long id,
                          @RequestParam(required = false) String name,
                          @RequestParam PetType petType,
                          @RequestParam(required = false) String breed,
                          @RequestParam(required = false) Integer age,
                          @RequestParam(required = false) String image,
                          @RequestParam PetSize petSize,
                          @RequestParam PetGender petGender) {
        if (id == null) {
            if(!petService.findById(id).isPresent()){
                return "redirect:/pets?error=Pet+Not+Found";
            }
            this.petService.create(name, petType, breed, age, image, petSize, petGender);
        } else {
            this.petService.update(id, name, petType, breed, age, image, petSize, petGender);
        }
        return "redirect:/ads/add";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        if (this.petService.findById(id).isPresent()) {
            this.petService.deleteById(id);
            return "redirect:/pets";
        }

        return "redirect:/pets?error=Pet+Not+Found";
    }
}
