package com.test.toy_springboot.photo.controller;

import com.test.toy_springboot.photo.domain.Photo;
import com.test.toy_springboot.photo.service.PhotoService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/photo")
public class PhotoRestApi {
    private PhotoService photoService;

    @Value("${file.path}")
    String save_image_file_path;

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

    @GetMapping(value = "/{image_id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userSearch(@PathVariable("image_id") long image_id) throws IOException {
        Photo photo = photoService.getPhotoById(image_id);
        InputStream imageStream = new FileInputStream(photo.getFilePath());
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return new ResponseEntity<byte[]>(imageByteArray, HttpStatus.OK);
    }

    @PostMapping("/upload_image")
    public ResponseEntity<Photo> uploadSource(@RequestParam("file") MultipartFile sourceFile) throws IOException {
        String filePath = save_image_file_path +"/" + new Date().toString()+"-"+sourceFile.getOriginalFilename();
        System.out.println(filePath);
        byteArrayConvertToImageFile(sourceFile.getBytes(), filePath);
        Photo photo = photoService.addPhoto(new Photo(filePath));
        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    public void byteArrayConvertToImageFile(byte[] imageByte, String filePath) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageByte);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        ImageIO.write(bufferedImage, "png", new File(filePath)); //저장하고자 하는 파일 경로를 입력합니다.
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
