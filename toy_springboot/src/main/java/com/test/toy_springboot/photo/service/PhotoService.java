package com.test.toy_springboot.photo.service;

import com.test.toy_springboot.photo.domain.Photo;
import com.test.toy_springboot.photo.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {
    private PhotoRepository dbAccess;

    @Autowired
    public PhotoService(PhotoRepository dbAccess) {
        this.dbAccess = dbAccess;
    }

    public List<Photo> getPhotoList(){
        return dbAccess.findAll();
    }

    public Photo addPhoto(Photo photo){
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
}
