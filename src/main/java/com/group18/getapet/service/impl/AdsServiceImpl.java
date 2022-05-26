package com.group18.getapet.service.impl;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.AdType;
import com.group18.getapet.service.AdsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {
    @Override
    public List<Advertisement> listAll() {
        return null;
    }

    @Override
    public List<Advertisement> filter(AdType type) {
        return null;
    }

    @Override
    public void create(String title, AdType type, Pet pet, User user, String location, Integer price) {

    }

    @Override
    public void update(String title, AdType type, Pet pet, User user, String location, Integer price) {

    }

    @Override
    public void delete(String title) {

    }
}
