package com.group18.getapet.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.group18.getapet.model.Pet;
import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import com.group18.getapet.model.exceptions.PetNotFoundException;
import com.group18.getapet.service.PetService;


@Controller
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/{id}")
    public String getPetById(@PathVariable Long id, Model model) {
        Pet pet = this.petService.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        model.addAttribute("pet", pet);
        return "pet";
    }

    @GetMapping("/add")
    public String addPet() {
        return "addPet";
    }

    @PostMapping("/add")
    public String addPet(@RequestParam(required = false) String name,
                         @RequestParam PetType petType,
                         @RequestParam(required = false) String breed,
                         @RequestParam Integer age,
                         @RequestParam String image,
                         @RequestParam PetSize petSize,
                         @RequestParam PetGender petGender) {

        Pet pet = new Pet(name, petType, breed, age, image, petSize, petGender);
        this.petService.save(pet);
        return "redirect:/ads/add";
    }

    @PostMapping("/update/{id}")
    public String updatePet(@PathVariable Long id,
                            @RequestParam(required = false) String name,
                            @RequestParam PetType petType,
                            @RequestParam(required = false) String breed,
                            @RequestParam Integer age,
                            @RequestParam String image,
                            @RequestParam PetSize petSize,
                            @RequestParam PetGender petGender) {
        if (this.petService.findById(id).isPresent()) {
            this.petService.update(id, name, petType, breed, age, image, petSize, petGender);
            return "redirect:/pets/{id}";
        }

        return "redirect:/pets?error=Pet+Not+Found";
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
