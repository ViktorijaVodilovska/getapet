package com.group18.getapet.web.controller;

import com.group18.getapet.model.Pet;
import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import com.group18.getapet.model.exceptions.PetNotFoundException;
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
    private  final UserService userService;

    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllPets(Model model) {
        List<Pet> pets=petService.listAll();
        model.addAttribute("pets",pets);
        return "products";
    }

    @GetMapping("/{id}")
    public String getPetById(@PathVariable Long id,Model model) {
        Pet pet=this.petService.findById(id).orElseThrow(()-> new PetNotFoundException(id));
        model.addAttribute("pet",pet);
        return "single-product";
    }

    @PostMapping("/add")
    public String addPet(@RequestParam PetType petType,
                         @RequestParam String breed,
                         @RequestParam Integer age,
                         @RequestParam PetSize petSize,
                         @RequestParam PetGender petGender
    ) {

        this.petService.create(petType,breed,age,petSize,petGender);
        return "redirect:/pets";
    }

    @PostMapping("/update/{id}")
    public String updatePet(@PathVariable Long id,
                            @RequestParam PetType petType,
                            @RequestParam String breed,
                            @RequestParam Integer age,
                            @RequestParam PetSize petSize,
                            @RequestParam PetGender petGender ){ //za site polinja
        if(this.petService.findById(id).isPresent()){
            Pet pet=this.petService.findById(id).orElseThrow(()-> new PetNotFoundException(id));
            this.petService.update(id,petType,breed,age,petSize,petGender);
            return "redirect:/pets";
        }



        return "redirect:/pets?error=Pet+Not+Found";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        if(this.petService.findById(id).isPresent()){
            this.petService.deleteById(id);
            return "redirect:/pets";
        }

        return "redirect:/pets?error=Pet+Not+Found";
    }
}
