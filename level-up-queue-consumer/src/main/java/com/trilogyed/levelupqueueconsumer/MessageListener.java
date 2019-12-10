package com.trilogyed.levelupqueueconsumer;

import com.trilogyed.levelupqueueconsumer.util.feign.LevelUpClient;
import com.trilogyed.levelupqueueconsumer.util.messages.LevelUpEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private LevelUpClient levelUpClient;

    public MessageListener(LevelUpClient levelUpClient) {
        this.levelUpClient = levelUpClient;
    }

    @RabbitListener(queues = LevelUpQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(LevelUpEntry msg) {
        System.out.println(msg.toString());

        try {
            levelUpClient.submitLevelUp(msg);
            System.out.println("level up saved!");
        } catch (Exception e) {
            try {
                levelUpClient.updateLevelUp(msg);
                System.out.println("level up updated!");
            } catch (Exception e2){
                System.out.println("Level up cannot be saved... Exception " + e2.getLocalizedMessage());
            }
        }
    }
}
