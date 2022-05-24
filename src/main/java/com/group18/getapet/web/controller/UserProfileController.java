package com.group18.getapet.web.controller;

import com.group18.getapet.model.User;
import com.group18.getapet.model.exceptions.InvalidArgumentsException; //ne raboti zosto ima vovedeno novi exceptions
import com.group18.getapet.model.exceptions.PasswordsDoNotMatchException;
import com.group18.getapet.model.exceptions.UserNotFoundException;
import com.group18.getapet.model.exceptions.UsernameAlreadyExistsException;
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

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getProfilePage(Model model, HttpServletRequest request){
        String username=request.getRemoteUser();
        User user=userService.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
        model.addAttribute("user",user);

        return "";
    }
    @GetMapping("/edit")
    public String editProfile(Model model, HttpServletRequest request){
        String username=request.getRemoteUser();
        User user=userService.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
        model.addAttribute("user",user);

        return "";
    }

    @PostMapping
    public String saveInfo(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String password,
            HttpServletRequest request
    ) {
        try {
            if (name == "" || surname == "" | password == "") {
                return "redirect:/profile";
            }
            String username=request.getRemoteUser();
            User user=userService.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
            this.userService.update( username,  password,  name,surname,user.getRole() );
            return "redirect:/profile";
        } catch (UsernameAlreadyExistsException | InvalidArgumentsException | PasswordsDoNotMatchException ex) {
            return "redirect:/profile/edit?hasError=true&&error=" + ex.getMessage();
        }

    }
}

