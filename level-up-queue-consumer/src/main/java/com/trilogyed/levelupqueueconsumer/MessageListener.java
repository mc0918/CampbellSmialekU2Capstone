package com.trilogyed.levelupqueueconsumer;

import com.trilogyed.levelupqueueconsumer.util.feign.LevelUpClient;
import com.trilogyed.levelupqueueconsumer.util.messages.LevelUpEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private LevelUpClient levelUpClient;

    public MessageListener(LevelUpClient levelUpClient) {
        this.levelUpClient = levelUpClient;
    }

    public void receiveMessage(LevelUpEntry msg) {
        System.out.println(msg.toString());
    }
}
