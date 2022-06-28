package com.group18.getapet.repository;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.AdType;
import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAll();
    Optional<Advertisement> findById(Long id);
    void deleteById(Long id);
    Advertisement save(Advertisement advertisement);
    List<Advertisement> findAllByAdType(AdType adType);
    List<Advertisement> findAllByUser(User user);
}
