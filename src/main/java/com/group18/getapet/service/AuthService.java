package com.group18.getapet.service;

import com.group18.getapet.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname, String number);
    User login(String username, String password);
}
