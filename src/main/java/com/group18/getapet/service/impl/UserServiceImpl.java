package com.group18.getapet.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.UserRole;
import com.group18.getapet.model.exceptions.UserAlreadyExistsException;
import com.group18.getapet.model.exceptions.UserNotFoundException;
import com.group18.getapet.repository.UserRepository;
import com.group18.getapet.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> listAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return this.userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void deleteByUsername(String username) {
        if (this.userRepository.findByUsername(username).isPresent()) {
            this.userRepository.deleteByUsername(username);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User create(String username, String password, String name, String surname, String number, UserRole role) {
        if (this.userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException();
        } else {
            User user = new User(username, password, name, surname, number, role);
            return this.userRepository.save(user);
        }
    }

    @Override
    public User update(String username, String password, String name, String surname, String number, UserRole role) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setPhoneNumber(number);
        user.setRole(role);
        return this.userRepository.save(user);
    }
}
