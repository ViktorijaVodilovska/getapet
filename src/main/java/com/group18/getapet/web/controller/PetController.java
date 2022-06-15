package com.group18.getapet.web.controller;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import com.group18.getapet.model.exceptions.PetNotFoundException;
import com.group18.getapet.service.AdsService;
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
    private final AdsService adsService;

    public PetController(PetService petService, UserService userService, AdsService adsService) {
        this.petService = petService;
        this.userService = userService;
        this.adsService = adsService;
    }

    @GetMapping
    public String getAllPets(Model model) {
        List<Pet> pets = this.petService.listAll();
        List<Advertisement> ads = this.adsService.listAll();
        model.addAttribute("pets",pets);
        model.addAttribute("ads",ads);
        return "products";
    }

    @GetMapping("/{id}")
    public String getPetById(@PathVariable Long id,Model model) {
        Pet pet=this.petService.findById(id).orElseThrow(()-> new PetNotFoundException(id));
        model.addAttribute("pet",pet);
        return "single-product";
    }

    @GetMapping("/add")
    public String addPet() {
        return "addPet";
    }

    @PostMapping("/add")
    public String addPet(@RequestParam PetType petType,
                         @RequestParam String breed,
                         @RequestParam Integer age,
                         @RequestParam String image,
                         @RequestParam PetSize petSize,
                         @RequestParam PetGender petGender) {

        Pet p = new Pet(petType, breed, age, image, petSize, petGender);
        this.petService.save(p);
        return "redirect:/ads/add";
    }

    @PostMapping("/update/{id}")
    public String updatePet(@PathVariable Long id,
                            @RequestParam PetType petType,
                            @RequestParam(required = false) String breed,
                            @RequestParam Integer age,
                            @RequestParam String image,
                            @RequestParam PetSize petSize,
                            @RequestParam PetGender petGender){ //za site polinja
        if(this.petService.findById(id).isPresent()){
            Pet pet=this.petService.findById(id).orElseThrow(()-> new PetNotFoundException(id));
            this.petService.update(id,petType,breed,age,image,petSize,petGender);
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

    @GetMapping("/single-pet")
    public String getSinglePet(){
        return "pet";
    }
}
