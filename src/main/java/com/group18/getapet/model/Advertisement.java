package com.group18.getapet.model;

import com.group18.getapet.model.enumerations.AdType;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
@Setter
@Data
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private AdType adType;

    @ManyToOne
    private Pet pet;

    @ManyToOne
    private User user;

    private String location;

    private Integer price;

    public Advertisement(String title, AdType adType, Pet pet, User user, String location, Integer price) {
        this.title = title;
        this.adType = adType;
        this.pet = pet;
        this.user = user;
        this.location = location;
        this.price = price;
    }

    public Advertisement() {
    }


}
