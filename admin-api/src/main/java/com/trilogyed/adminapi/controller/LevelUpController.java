package com.trilogyed.adminapi.controller;

//import com.trilogyed.adminapi.controller.ServiceLayer;
//import com.trilogyed.adminapi.controller.LevelUp;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.LevelUp;
import com.trilogyed.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LevelUpController {
/*
    @Autowired
    private ServiceLayer service;

    @RequestMapping(value = "/levelUp", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp saveLevelUp(@RequestBody LevelUp o) {
        return service.saveLevelUp(o);
    }

    @RequestMapping(value = "/levelUp/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUp(@PathVariable int id) throws IllegalArgumentException {
        try {
            return service.getLevelUp(id);
        } catch (NullPointerException n) {
            throw new IllegalArgumentException("illegal argument or another exception idk");
        }
    }

    @RequestMapping(value = "/levelUp", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> getAllLevelUps() {
        return service.getAllLevelUps();
    }

    @RequestMapping(value = "/levelUp", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public String updateLevelUp(@RequestBody LevelUp o) throws IdNotFound {
        try {
            service.updateLevelUp(o);
            return "Update: Successful";
        } catch (Exception e) {
            throw new IdNotFound("bad thing");
        }
    }

    @RequestMapping(value = "/levelUp/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLevelUp(@PathVariable int id) {
        try {
            service.deleteLevelUp(id);
            return "Delete: Success";
        } catch (Exception e) {
            return "Delete: Fail";
        }
    }*/
}
