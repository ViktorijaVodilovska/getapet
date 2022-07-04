package com.group18.getapet.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.group18.getapet.model.enumerations.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_users")
public class User implements UserDetails {
    @Id
    private String username;
    private String password;
    private String name;
    private String surname;
    private String image;
    private String phoneNumber;

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Advertisement> ads;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String username, String password, String name, String surname, String image, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.image = image;
    }

    public User(String username, String password, String name, String surname, String phoneNumber, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
    }
}
