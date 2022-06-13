package com.group18.getapet.model;

import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private PetType petType;

    private String breed;

    private Integer age;

    @Enumerated(value = EnumType.STRING)
    private PetSize petSize;

    @Enumerated(value = EnumType.STRING)
    private PetGender petGender;

    private String image;

    public Pet(PetType petType, String breed, Integer age, String image, PetSize petSize, PetGender petGender) {
        this.petType = petType;
        this.breed = breed;
        this.age = age;
        this.petSize = petSize;
        this.petGender = petGender;
        this.image = image;
    }

    public Pet(String name, PetType petType, String breed, Integer age, String image, PetSize petSize, PetGender petGender) {
        this.name = name;
        this.petType = petType;
        this.breed = breed;
        this.age = age;
        this.petSize = petSize;
        this.petGender = petGender;
        this.image = image;
    }
}
