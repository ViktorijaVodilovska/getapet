package com.group18.getapet.repository;

import com.group18.getapet.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAll();
    Optional<Advertisement> findById(Long id);
    void deleteById(Long id);
    Advertisement save(Advertisement advertisement);
}
