package com.trilogyed.levelupqueueconsumer.util.feign;

import com.trilogyed.levelupqueueconsumer.util.messages.LevelUpEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "retail-api")
public interface LevelUpClient {
    @PostMapping("/invoices")
    public LevelUpEntry saveLevelUpToDb(LevelUpEntry levelUpEntry);
}
