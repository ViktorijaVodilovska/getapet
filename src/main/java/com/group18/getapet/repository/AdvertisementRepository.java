package com.group18.getapet.repository;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.enumerations.AdType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAll();
    Optional<Advertisement> findById(Long id);
    void deleteById(Long id);
    Advertisement save(Advertisement advertisement);
    List<Advertisement> findAllByAdType(AdType adType);
}
