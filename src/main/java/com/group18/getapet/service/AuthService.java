package com.group18.getapet.service;

import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.UserRole;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String name, String surname, String number, UserRole role);
    User login(String username, String password);
}
