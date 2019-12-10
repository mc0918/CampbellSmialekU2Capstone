package com.trilogyed.levelupqueueconsumer.util.feign;

import com.trilogyed.levelupqueueconsumer.util.messages.LevelUpEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {
    @PostMapping("/levelUp")
    public LevelUpEntry submitLevelUp(@RequestBody LevelUpEntry levelUpEntry);

    @PutMapping("/levelUp")
    public void updateLevelUp(@RequestBody LevelUpEntry levelUp);

    @GetMapping("/levelUp/{id}")
    public LevelUpEntry findLevelUpById(int id);
}
