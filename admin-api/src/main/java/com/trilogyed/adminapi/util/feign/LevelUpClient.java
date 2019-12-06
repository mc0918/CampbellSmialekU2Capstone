package com.trilogyed.adminapi.util.feign;

import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "levelUp-service")
public interface LevelUpClient {
    @RequestMapping(value = "/levelUp", method = RequestMethod.POST)
    public LevelUp saveLevelUp(@RequestBody LevelUp o);

    @RequestMapping(value = "/levelUp/{id}", method = RequestMethod.GET)
    public LevelUp getLevelUp(@PathVariable int id) throws IdNotFound;

    @RequestMapping(value = "/levelUp", method = RequestMethod.GET)
    public List<LevelUp> getAllLevelUps();

    @RequestMapping(value = "/levelUp", method = RequestMethod.PUT)
    public String updateLevelUp(@RequestBody LevelUp o) throws IdNotFound;

    @RequestMapping(value = "/levelUp/{id}", method = RequestMethod.DELETE)
    public String deleteLevelUp(@PathVariable int id) throws IdNotFound;
}
