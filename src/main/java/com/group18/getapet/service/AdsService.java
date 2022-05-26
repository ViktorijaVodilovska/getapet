package com.group18.getapet.service;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.AdType;

import java.util.List;

public interface AdsService {
    List<Advertisement> listAll();
    List<Advertisement> filter(AdType type);
    void create(String title, AdType type, Pet pet, User user, String location, Integer price);
    void update(String title, AdType type, Pet pet, User user, String location, Integer price);
    void delete(String title);
}
