package com.test.toy_springboot.category.controller;

import com.test.toy_springboot.category.domain.Character;
import com.test.toy_springboot.category.domain.Genre;
import com.test.toy_springboot.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRestApi {
    private CategoryService categoryService;
    @Autowired
    public CategoryRestApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/character")
    public ResponseEntity<List<Character>> getCharacterList(){
        List<Character> characterList = categoryService.getCharacterList();
        if(characterList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(characterList, HttpStatus.OK);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<Genre>> getGenreList(){
        List<Genre> genreList = categoryService.getGenreList();
        if(genreList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(genreList, HttpStatus.OK);
    }

    @PostMapping("/character")
    public ResponseEntity<Character> saveCharacter(@RequestBody Character character) {
        Character resultCharacter = categoryService.addCharacter(character);
        if(resultCharacter == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultCharacter,HttpStatus.OK);
    }

    @PostMapping("/genre")
    public ResponseEntity<Genre> saveGenre(@RequestBody Genre genre) {
        Genre resultGenre = categoryService.addGenre(genre);
        if(resultGenre == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(resultGenre,HttpStatus.OK);
    }

    @DeleteMapping("/character")
    public ResponseEntity<Character> deleteDevice(@RequestBody Character character){
        categoryService.deleteCharacter(character);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/genre")
    public ResponseEntity<Genre> deleteDevice(@RequestBody Genre genre){
        categoryService.deleteGenre(genre);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
