package com.group18.getapet.repository;

import java.util.List;
import java.util.Optional;

import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group18.getapet.model.Pet;


@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAll();

    Optional<Pet> findById(Long id);

    void deleteById(Long id);

    Pet save(Pet pet);

}
