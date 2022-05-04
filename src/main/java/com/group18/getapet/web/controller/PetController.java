package com.group18.getapet.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pets")
public class PetController {

    @GetMapping
    public String getAllPets() {
        return "products";
    }

    @GetMapping("/{id}")
    public String getPetById(@PathVariable Long id) {
        return "";
    }

    @PostMapping("/add")
    public String addPet(@RequestParam String property1,
                         @RequestParam String property2) { //za site polinja
        return "";
    }

    @PostMapping("/update/{id}")
    public String updatePet(@PathVariable Long id,
                            @RequestParam String property1,
                            @RequestParam String property2) { //za site polinja
        return "";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        return "";
    }
}
