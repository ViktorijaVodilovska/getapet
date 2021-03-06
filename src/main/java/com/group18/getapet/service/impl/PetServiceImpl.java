package com.group18.getapet.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group18.getapet.model.Pet;
import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import com.group18.getapet.repository.PetRepository;
import com.group18.getapet.service.PetService;


@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public List<Pet> listAll() {
        return this.petRepository.findAll();
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return this.petRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.petRepository.deleteById(id);
    }

    @Override
    public Pet create(String name, PetType petType, String breed, Integer age, String image, PetSize petSize, PetGender petGender) {
        Pet pet = new Pet(name, petType, breed, age, image, petSize, petGender);
        return this.petRepository.save(pet);
    }

    @Override
    public Pet update(Long id, String name, PetType petType, String breed, Integer age, String image, PetSize petSize, PetGender petGender) {
        Pet pet = this.petRepository.getById(id);
        pet.setName(name);
        pet.setPetType(petType);
        pet.setBreed(breed);
        pet.setAge(age);
        pet.setPetSize(petSize);
        pet.setPetGender(petGender);
        pet.setImage(image);
        return this.petRepository.save(pet);
    }

}
