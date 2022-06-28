package com.group18.getapet.web.controller;

import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.UserRole;
import com.group18.getapet.model.exceptions.InvalidArgumentsException;
import com.group18.getapet.model.exceptions.InvalidUserCredentialsException;
import com.group18.getapet.model.exceptions.PasswordsDoNotMatchException;
import com.group18.getapet.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String number,
                           @RequestParam UserRole role) {
        try{
            this.authService.register(username, password, repeatedPassword, name, surname, number, role);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        User user = null;
        try{
            user = this.authService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/ads";
        }
        catch (InvalidUserCredentialsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "redirect:/login";
        }
    }



}
