package com.group18.getapet.model;

import com.group18.getapet.model.enumerations.AdType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private AdType adType;

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private User user;

    private String location;

    public Advertisement(String title, String description, AdType adType, Pet pet, User user, String location) {
        this.title = title;
        this.description = description;
        this.adType = adType;
        this.pet = pet;
        this.user = user;
        this.location = location;
    }
}
