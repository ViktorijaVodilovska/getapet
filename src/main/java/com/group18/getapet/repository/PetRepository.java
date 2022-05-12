package com.group18.getapet.repository;

import com.group18.getapet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAll();
    Optional<Pet> findById(Long id);
    void deleteById(Long id);
    Pet save(Pet pet);
}