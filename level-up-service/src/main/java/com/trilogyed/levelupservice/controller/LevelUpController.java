package com.trilogyed.levelupservice.controller;

import com.trilogyed.levelupservice.exceptions.IdAlreadyExists;
import com.trilogyed.levelupservice.exceptions.IdNotFound;
import com.trilogyed.levelupservice.model.LevelUp;
import com.trilogyed.levelupservice.repository.LevelUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class LevelUpController {

    @Autowired
    private LevelUpRepository levelUpRepository;

    @PostMapping(value = "/levelUp")
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp submitLevelUp(@RequestBody @Valid LevelUp levelUp) {
        if (levelUpRepository.findByCustomerId(levelUp.getCustomerId()) != null) {  //Only one unique level up per customer, otherwise how do we keep track of points
            throw new IdAlreadyExists("level up with customer id " + levelUp.getLevelUpId() + " already exists");
        }
        return levelUpRepository.save(levelUp);
    }

    @GetMapping(value = "/levelUp/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp findLevelUpById(@PathVariable int id) throws IdNotFound {
        if (levelUpRepository.findById(id).isPresent()) {
            return levelUpRepository.findById(id).get();
        } else {
            throw new IdNotFound("Cannot find level up id " + id);
        }
    }

    @GetMapping(value = "/levelUp")
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> findAllLevelUps() {
        return levelUpRepository.findAll();
    }

//    @GetMapping(value = "/levelUp/customer/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<LevelUp> findLevelUpsByCustomerId(@PathVariable int id) throws IdNotFound {
//        try {
//            return levelUpRepository.findAllByCustomerId(id);
//        } catch (IllegalArgumentException e) {
//            throw new IdNotFound("Cannot find customer id " + id);
//        }
//    }

    @GetMapping(value = "/levelUp/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelUp findLevelUpByCustomerId(@PathVariable int id) throws IdNotFound {
        try {
            return levelUpRepository.findByCustomerId(id);
        } catch (IllegalArgumentException e) {
            throw new IdNotFound("Cannot find level up id " + id);
        }
    }

    @PutMapping(value = "/levelUp")
    @ResponseStatus(HttpStatus.OK)
    public void updateLevelUp(@RequestBody @Valid LevelUp levelUp) {
        levelUpRepository.save(levelUp);
    }

    @DeleteMapping(value = "/levelUp/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable int id) {
        if (levelUpRepository.findById(id).isPresent()) {
            levelUpRepository.deleteById(id);
        } else {
            throw new IdNotFound("Cannot find level up id " + id);
        }
    }
}
