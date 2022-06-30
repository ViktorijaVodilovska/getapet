package com.group18.getapet.service;

import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> listAll();
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
    User save(User user);
    User create(String username, String password,String name, String surname, String number, UserRole role);
    User update(String username, String password, String name, String surname, String number, UserRole role);

}
