package com.group18.getapet.service;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.AdType;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {
    List<Advertisement> listAll();
    Optional<Advertisement> findById(Long id);
    void deleteById(Long id);
    Advertisement save(Advertisement advertisement);
    Advertisement create(String title, AdType adType, Pet pet, User user, String location, Integer price);
    Advertisement update(Long id, String title, AdType adType, Pet pet, User user, String location, Integer price);
}
