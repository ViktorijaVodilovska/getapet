package com.group18.getapet.service;

import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User create(String username, String password,String name, String surname, String number, UserRole role);
    User update(String username, String password, String name, String surname, String number, UserRole role);
    UserDetails loadUserByUsername(String username);
}
