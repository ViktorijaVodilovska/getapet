package com.group18.getapet.web.controller;


import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.*;
import com.group18.getapet.model.exceptions.*;
import com.group18.getapet.service.AdvertisementService;
import com.group18.getapet.service.PetService;
import com.group18.getapet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public String getAllAdvertisements(
            @RequestParam(required = false) AdType adType,
            @RequestParam(required = false) PetType petType,
            @RequestParam(required = false) PetGender petGender,
            @RequestParam(required = false) PetSize petSize,
            @RequestParam(required = false) Integer petAge,
            Model model) {
        List<Advertisement> advertisementList;
        if(adType==null && petType == null && petGender == null && petSize == null && petAge==null){
            advertisementList = this.advertisementService.listAll();
        }else{
            advertisementList = this.advertisementService.filterAds(adType,petType,petGender,petSize,petAge);
        }
        model.addAttribute("advertisementList", advertisementList);
        List<AdType> types = List.of(AdType.values());
        model.addAttribute("adTypes", types);
        List<PetType> petTypes = List.of(PetType.values());
        model.addAttribute("petTypes", petTypes);
        List<PetColor> petColors = List.of(PetColor.values());
        model.addAttribute("petColors", petColors);
        List<PetGender> petGenders = List.of(PetGender.values());
        model.addAttribute("petGenders", petGenders);
        List<PetSize> petSizes = List.of(PetSize.values());
        model.addAttribute("petSizes", petSizes);
        model.addAttribute("bodyContent","ads");
        return "master-template";
    }
    @GetMapping("/{id}")
    public String getAdvertisementById(@PathVariable Long id, Model model) {
        if (this.advertisementService.findById(id).isPresent()) {
            Advertisement advertisement = this.advertisementService.findById(id)
                    .orElseThrow(() -> new AdvertisementNotFoundException(id));
            model.addAttribute("ad", advertisement);
            model.addAttribute("pet", advertisement.getPet());
            model.addAttribute("user", advertisement.getUser());
            model.addAttribute("bodyContent","ad-page");
            return "master-template";
        }
        return "redirect:/ads?error=Advertisement+was+not+found";
    }


    @GetMapping("/filter")
    public String getAdvertisementsByFilter(@RequestParam AdType adType, Model model) {
        List<Advertisement> advertisementList = this.advertisementService.listAllByAdType(adType);
        model.addAttribute("advertisementList", advertisementList);
        return "ads";
    }

    @GetMapping("/add")
    public String addAdvertisement(Model model) {
        List<Pet> pets = this.petService.listAll();
        model.addAttribute("pets", pets);
        model.addAttribute("bodyContent","addAdvertisement");
        model.addAttribute("adTypes", AdType.values());

        return "master-template";
    }

    @GetMapping("/edit/{id}")
    public String editAdvertisment(HttpServletRequest request,@PathVariable Long id, Model model) {
        if (this.advertisementService.findById(id) != null) {
            String username = request.getRemoteUser();

            Advertisement ad = this.advertisementService.findById(id).orElseThrow(() -> new AdvertisementNotFoundException(id));
            User user = this.userService.findByUsername(username).orElseThrow(() -> new  UserNotFoundException(username));
            List<Pet> pets = this.petService.listAll();

            model.addAttribute("ad", ad);
            model.addAttribute("user", user);
            model.addAttribute("pets", pets);
            model.addAttribute("bodyContent","editAdvertisment");
            return "master-template";
        }
        return "redirect:/ads";
    }

    @PostMapping("/edit/{id}")
    public String updateAdvertisment(
            @PathVariable("id") String id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam AdType adType,
            @RequestParam Long petID,
            @RequestParam String location
    ) {
        try {
            Long adId = Long.parseLong(id);
            Advertisement ad = advertisementService.findById(adId).orElseThrow(() -> new AdvertisementNotFoundException(adId));
            Pet pet = petService.findById(petID).orElseThrow(() -> new PetNotFoundException(petID));
            this.advertisementService.update(adId, title, description, adType, pet, ad.getUser(), location);

            return "redirect:/ads/"+id;
        } catch (UsernameAlreadyExistsException | InvalidArgumentsException | PasswordsDoNotMatchException | PetNotFoundException ex) {
            // TODO:
            return "redirect:/ads/edit?hasError=true&&error=" + ex.getMessage();
        }
    }

    @PostMapping("/add")
    public String addAdvertisement(HttpServletRequest request,
                                   @RequestParam String title,
                                   @RequestParam String description,
                                   @RequestParam AdType adType,
                                   @RequestParam Long pet,
                                   @RequestParam String location) {
        String username = request.getRemoteUser();
//        String username = "jovanaM";

        User user1 = this.userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Pet p = this.petService.findById(pet).orElseThrow(() -> new PetNotFoundException(pet));
        this.advertisementService.create(title, description, adType, p, user1, location);

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
