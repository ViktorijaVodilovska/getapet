package com.group18.getapet.service;

import com.group18.getapet.model.Pet;
import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;

import java.util.List;
import java.util.Optional;

public interface PetService {
    List<Pet> listAll();
    Optional<Pet> findById(Long id);
    void deleteById(Long id);
    Pet save(Pet pet);
    Pet create(PetType petType, String breed, Integer age, PetSize petSize, PetGender petGender);
    Pet update(Long id, PetType petType, String breed, Integer age, PetSize petSize, PetGender petGender);
}
