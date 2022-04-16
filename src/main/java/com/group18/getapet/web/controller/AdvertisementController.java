package com.group18.getapet.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ads")
public class AdvertisementController {

    @GetMapping
    public String getAllAdvertisements() {
        return "";
    }

    @GetMapping("/{id}")
    public String getAdvertisementById(@PathVariable Long id) {
        return "";
    }

    @GetMapping("/filter")
    public String getAdvertisementsByFilter(@RequestParam String filter) {
        return "";
    }

    @PostMapping("/add")
    public String addAdvertisement(@RequestParam String property1,
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
    public String deleteAdvertisement(@PathVariable Long id) {
        return "";
    }
}
