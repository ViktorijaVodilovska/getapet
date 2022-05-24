package com.group18.getapet.model;

import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PetType petType;

    private String breed;

    private Integer age;

    private PetSize petSize;

    private PetGender petGender;

    public Pet(PetType petType, String breed, Integer age, PetSize petSize, PetGender petGender) {
        this.petType = petType;
        this.breed = breed;
        this.age = age;
        this.petSize = petSize;
        this.petGender = petGender;
    }

    public Pet() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPetSize(PetSize petSize) {
        this.petSize = petSize;
    }

    public void setPetGender(PetGender petGender) {
        this.petGender = petGender;
    }
}
