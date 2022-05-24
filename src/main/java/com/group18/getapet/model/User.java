package com.group18.getapet.model;

import com.group18.getapet.model.enumerations.UserRole;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "app_users")
public class User {

    @Id
    private String username;

    private String password;

    private String name;

    private String surname;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public User(String username, String password, String name, String surname, UserRole role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public User() {
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
