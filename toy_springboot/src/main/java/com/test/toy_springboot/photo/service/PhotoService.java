package com.test.toy_springboot.photo.service;

import com.test.toy_springboot.photo.domain.Photo;
import com.test.toy_springboot.photo.repository.PhotoRepository;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class PhotoService {
    private PhotoRepository dbAccess;
    private ToyService toyService;

    @Autowired
    public PhotoService(PhotoRepository dbAccess, ToyService toyService) {
        this.toyService = toyService;
        this.dbAccess = dbAccess;
    }

    public List<Photo> getPhotoList(){
        return dbAccess.findAll();
    }

    public Photo addPhoto(Photo photo){
        return dbAccess.save(photo);
    }

    public Photo addPhotoWithToyId(Photo photo, Long toyId){
        Toy toy = toyService.getToyById(toyId);
        photo.setToy(toy);
        return dbAccess.save(photo);
    }

    public Photo updatePhoto(Photo photo) {
        return dbAccess.save(photo);
    }

    public void delete(Photo photo) throws IllegalArgumentException{
        dbAccess.delete(photo);
    }

    public Photo getPhotoById(Long photo_id){
        return dbAccess.getById(photo_id);
    }

    @Transactional
    public Toy addPhotoToToy(@RequestParam Long toy_id, @RequestParam Long photo_id) {
        Photo resultPhoto = getPhotoById(photo_id);
        Toy toy = toyService.getToyById(toy_id);
        resultPhoto.setToy(toy);
        updatePhoto(resultPhoto);
        return toy;
    }
}
