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
    private final UserRepository userRepository;

    public AdvertisementServiceImpl(UserRepository userRepository,
                                    PetRepository petRepository,
                                    AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
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
        User user = advertisement.getUser();
        user.getAds().add(advertisement);
        userRepository.save(user);
        return this.advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement create(String title, AdType adType, Pet pet, User user, String location) {
        Advertisement advertisement = new Advertisement(title, adType, pet, user, location);
        user.getAds().add(advertisement);
        userRepository.save(user);
        return this.advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement update(Long id,
                                String title,
                                AdType adType,
                                Pet pet,
                                User user,
                                String location) {
        Advertisement advertisement = this.advertisementRepository.getById(id);
        advertisement.setTitle(title);
        advertisement.setAdType(adType);
        advertisement.setPet(pet);
        advertisement.setUser(user);
        advertisement.setLocation(location);
        user.getAds().add(advertisement);
        userRepository.save(user);
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
