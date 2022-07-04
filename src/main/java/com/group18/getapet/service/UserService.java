package com.group18.getapet.service;

import com.group18.getapet.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User update(String username, String name, String surname, String image, String number);
    UserDetails loadUserByUsername(String username);
}
