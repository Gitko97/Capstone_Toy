package com.test.toy_springboot.photo.controller;

import com.test.toy_springboot.photo.service.PhotoService;
import com.test.toy_springboot.photo.domain.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photo")
public class PhotoRestApi {
    private PhotoService photoService;
    @Autowired
    public PhotoRestApi(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping
    public ResponseEntity<List<Photo>> getPhotoList(){
        List<Photo> photoList = photoService.getPhotoList();
        if(photoList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(photoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Photo> savePhoto(@RequestBody Photo photo) {
        Photo resultPhoto = photoService.addPhoto(photo);
        if(resultPhoto == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultPhoto,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Photo> updatePhoto(@RequestBody Photo photo){
        Photo resultPhoto = photoService.updatePhoto(photo);
        if(resultPhoto == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultPhoto,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Photo> deleteDevice(@RequestBody Photo photo){
        photoService.delete(photo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
