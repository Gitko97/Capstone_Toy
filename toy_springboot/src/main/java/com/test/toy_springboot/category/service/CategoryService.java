package com.test.toy_springboot.category.service;

import com.test.toy_springboot.category.domain.Category_set;
import com.test.toy_springboot.category.domain.Character;
import com.test.toy_springboot.category.domain.Genre;
import com.test.toy_springboot.category.repository.Category_setRepository;
import com.test.toy_springboot.category.repository.CharacterRepository;
import com.test.toy_springboot.category.repository.GenreRepository;
import com.test.toy_springboot.toy.domain.Toy;
import com.test.toy_springboot.toy.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private CharacterRepository character_dbAccess;
    private GenreRepository genre_dbAccess;
    private Category_setRepository set_dbAccess;
    private ToyService toyService;
    @Autowired
    public CategoryService(CharacterRepository character_dbAccess, GenreRepository genre_dbAccess, Category_setRepository set_dbAccess, ToyService toyService) {
        this.character_dbAccess = character_dbAccess;
        this.genre_dbAccess = genre_dbAccess;
        this.set_dbAccess = set_dbAccess;
        this.toyService = toyService;
    }

    public List<Character> getCharacterList() {
        return character_dbAccess.findAll();
    }

    public List<Genre> getGenreList() {
        return genre_dbAccess.findAll();
    }

    public List<Category_set> getCategory_setList() {
        return set_dbAccess.findAll();
    }

    public Character addCharacter(Character character) {
        return character_dbAccess.save(character);
    }

    public Genre addGenre(Genre genre) {
        return genre_dbAccess.save(genre);
    }

    public Category_set addCategory_set(Category_set category_set) {
        return set_dbAccess.save(category_set);
    }

    public void deleteCharacter(Character character) throws IllegalArgumentException {
        character_dbAccess.delete(character);
    }

    public void deleteGenre(Genre genre) throws IllegalArgumentException {
        genre_dbAccess.delete(genre);
    }

    public void deleteCategory_set(Category_set category_set) throws IllegalArgumentException {
        set_dbAccess.delete(category_set);
    }

    public List<Long> checkSet(String set_name){
        List<Category_set> category_setList = set_dbAccess.findAllBySetName(set_name);
        List<Long> toy_id_list = new ArrayList<>();
        for (Category_set category_set : category_setList){
            Long toy_id = toyService.findToyBySetId(category_set.getSet_id());
            if (toy_id == null) return null;
            toy_id_list.add(toy_id);
        }
        return toy_id_list;
    }

    public Category_set getCategorySetByNameAndNum(String set_name, int set_num){
        Category_set category_set = set_dbAccess.getCategorySetByNameAndNum(set_name, set_num);
        return category_set;
    }
}
