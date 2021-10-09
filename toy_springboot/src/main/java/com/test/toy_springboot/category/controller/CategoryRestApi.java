package com.test.toy_springboot.category.controller;

import com.test.toy_springboot.category.domain.Category_set;
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

    @GetMapping("/category_set")
    public ResponseEntity<List<Category_set>> getSetList(){
        List<Category_set> setList = categoryService.getCategory_setList();
        if(setList == null) new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(setList, HttpStatus.OK);
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

    @PostMapping("/category_set")
    public ResponseEntity<String> saveCategory_set(@RequestParam String set_name, @RequestParam int product_total_num) {
        for (int i=1;i<=product_total_num;i++){
            categoryService.addCategory_set(new Category_set(set_name, i));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/character")
    public ResponseEntity<Character> deleteCharacter(@RequestBody Character character){
        categoryService.deleteCharacter(character);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/genre")
    public ResponseEntity<Genre> deleteGenre(@RequestBody Genre genre){
        categoryService.deleteGenre(genre);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/category_set")
    public ResponseEntity<Category_set> deleteCategory_set(@RequestBody Category_set category_set){
        categoryService.deleteCategory_set(category_set);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
