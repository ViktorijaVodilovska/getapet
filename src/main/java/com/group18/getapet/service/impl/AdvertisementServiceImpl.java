package com.group18.getapet.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.group18.getapet.model.enumerations.PetGender;
import com.group18.getapet.model.enumerations.PetSize;
import com.group18.getapet.model.enumerations.PetType;
import org.springframework.stereotype.Component;
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
    public Advertisement create(String title, String description, AdType adType, Pet pet, User user, String location) {
        Advertisement advertisement = new Advertisement(title, description, adType, pet, user, location);
        user.getAds().add(advertisement);
        userRepository.save(user);
        return this.advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement update(Long id,
                                String title,
                                String description,
                                AdType adType,
                                Pet pet,
                                User user,
                                String location) {
        Advertisement advertisement = this.advertisementRepository.getById(id);
        advertisement.setTitle(title);
        advertisement.setDescription(description);
        advertisement.setAdType(adType);
        advertisement.setPet(pet);
        advertisement.setUser(user);
        advertisement.setLocation(location);
        user.getAds().add(advertisement);
        userRepository.save(user);
        return this.advertisementRepository.save(advertisement);
    }


    @Override
    public List<Advertisement> listAllByUser(User user) {
        return this.advertisementRepository.findAllByUser(user);
    }


    @Override
    public List<Advertisement> filterAds(AdType adType, PetType petType, PetGender petGender, PetSize petSize, Integer petAge) {
        List<Advertisement> ads = this.advertisementRepository.findAll();
        if(adType != null){
            ads = this.advertisementRepository.findAllByAdType(adType);
        }
        if(petType != null){
            ads = ads.stream().filter(advertisement -> advertisement.getPet().getPetType().equals(petType)).collect(Collectors.toList());
        }
        if(petGender != null){
            ads = ads.stream().filter(advertisement -> advertisement.getPet().getPetGender().equals(petGender)).collect(Collectors.toList());
        }
        if(petSize != null){
            ads = ads.stream().filter(advertisement -> advertisement.getPet().getPetSize().equals(petSize)).collect(Collectors.toList());
        }
        if(petAge != null){
            ads = ads.stream().filter(advertisement -> advertisement.getPet().getAge().equals(petAge)).collect(Collectors.toList());
        }
        return ads;
    }


    @Override
    public List<Advertisement> listAllByAdType(AdType adType) {
        if(adType != null){
            return this.advertisementRepository.findAllByAdType(adType);
        }
        else{
            return this.advertisementRepository.findAll();
        }

    }
}
