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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        String username=request.getRemoteUser();
        User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        List<Advertisement> ads = advertisementService.listAllByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("userAds", ads);
        return "user";
    }

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
            HttpServletRequest request
    ) {
        try {
            String username = request.getRemoteUser();
            User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
            this.userService.update(username, password, name, surname, user.getRole());
            return "redirect:/profile";
        } catch (UsernameAlreadyExistsException | InvalidArgumentsException | PasswordsDoNotMatchException ex) {
            return "redirect:/profile/edit?hasError=true&&error=" + ex.getMessage();
        }
    }

}