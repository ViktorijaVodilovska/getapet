package com.group18.getapet.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group18.getapet.model.Advertisement;
import com.group18.getapet.model.Pet;
import com.group18.getapet.model.User;
import com.group18.getapet.model.enumerations.AdType;
import com.group18.getapet.model.exceptions.AdTypeNotFound;
import com.group18.getapet.repository.AdvertisementRepository;
import com.group18.getapet.repository.PetRepository;
import com.group18.getapet.repository.UserRepository;
import com.group18.getapet.service.AdvertisementService;


@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public AdvertisementServiceImpl(UserRepository userRepository,
                                    PetRepository petRepository,
                                    AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public List<Advertisement> listAll() {
        return advertisementRepository.findAll();
    }

    @Override
    public Optional<Advertisement> findById(Long id) {
        return this.advertisementRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.advertisementRepository.deleteById(id);

    }

    @Override
    public Advertisement save(Advertisement advertisement) {
        return this.advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement create(String title, AdType adType, Pet pet, User user, String location, Integer price) {
        Advertisement advertisement = new Advertisement(title, adType, pet, user, location, price);
        return this.advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement update(Long id,
                                String title,
                                AdType adType,
                                Pet pet,
                                User user,
                                String location,
                                Integer price) {
        Advertisement advertisement = this.advertisementRepository.getById(id);
        advertisement.setTitle(title);
        advertisement.setAdType(adType);
        advertisement.setPet(pet);
        advertisement.setUser(user);
        advertisement.setLocation(location);
        advertisement.setPrice(price);
        return this.advertisementRepository.save(advertisement);
    }

    @Override
    public List<Advertisement> listAllByAdType(AdType adType) {
        if (adType != null) {
            return this.advertisementRepository.findAllByAdType(adType);
        } else {
            throw new AdTypeNotFound();
        }
    }

    @Override
    public List<Advertisement> listAllByUser(User user) {
        return this.advertisementRepository.findAllByUser(user);
    }

}
