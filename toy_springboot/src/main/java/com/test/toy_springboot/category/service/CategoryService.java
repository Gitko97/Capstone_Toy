package com.test.toy_springboot.category.service;

import com.test.toy_springboot.category.domain.Character;
import com.test.toy_springboot.category.domain.Genre;
import com.test.toy_springboot.category.repository.CharacterRepository;
import com.test.toy_springboot.category.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CharacterRepository character_dbAccess;
    private GenreRepository genre_dbAccess;

    @Autowired
    public CategoryService(CharacterRepository character_dbAccess, GenreRepository genre_dbAccess) {
        this.character_dbAccess = character_dbAccess;
        this.genre_dbAccess = genre_dbAccess;
    }

    public List<Character> getCharacterList() {
        return character_dbAccess.findAll();
    }

    public List<Genre> getGenreList() {
        return genre_dbAccess.findAll();
    }

    public Character addCharacter(Character character) {
        return character_dbAccess.save(character);
    }

    public Genre addGenre(Genre genre) {
        return genre_dbAccess.save(genre);
    }

    public void deleteCharacter(Character character) throws IllegalArgumentException {
        character_dbAccess.delete(character);
    }

    public void deleteGenre(Genre genre) throws IllegalArgumentException {
        genre_dbAccess.delete(genre);
    }

}
