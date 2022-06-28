package com.group18.getapet.web.controller;

import java.util.List;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.User;
import com.group18.getapet.model.exceptions.InvalidArgumentsException;
import com.group18.getapet.model.exceptions.PasswordsDoNotMatchException;
import com.group18.getapet.model.exceptions.UserNotFoundException;
import com.group18.getapet.model.exceptions.UsernameAlreadyExistsException;
import com.group18.getapet.service.AdvertisementService;
import com.group18.getapet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private final UserService userService;
    private final AdvertisementService advertisementService;

    public UserProfileController(UserService userService,
                                 AdvertisementService advertisementService) {
        this.userService = userService;
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public String getProfilePage(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();


        User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<Advertisement> ads = advertisementService.listAllByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("userAds", ads);
        model.addAttribute("bodyContent", "user");
        return "master-template";
    }

//    @GetMapping("/{username}")
//    public String getProfilePageByUsername(@PathVariable String username, Model model, HttpServletRequest request) {
//        User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
//        List<Advertisement> ads = advertisementService.listAllByUser(user);
//        model.addAttribute("user", user);
//        model.addAttribute("userAds", ads);
//        model.addAttribute("bodyContent", "user");
//        return "master-template";
//    }

    @GetMapping("/edit")
    public String editProfile(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();

        User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        model.addAttribute("user", user);

        return "";
    }

    @PostMapping("/edit")
    public String saveInfo(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String password,
            @RequestParam String number,
            HttpServletRequest request
    ) {
        try {
            if (name.equals("") || surname.equals("") || password.equals("")) {
                return "redirect:/profile";
            }
            String username = request.getRemoteUser();

            User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
            this.userService.update(username, password, name, surname, number, user.getRole());
            return "redirect:/profile";
        } catch (UsernameAlreadyExistsException | InvalidArgumentsException | PasswordsDoNotMatchException ex) {
            return "redirect:/profile/edit?hasError=true&&error=" + ex.getMessage();
        }
    }
}