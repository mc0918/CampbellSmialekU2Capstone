package com.trilogyed.levelupservice.repository;

import com.trilogyed.levelupservice.model.LevelUp;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class LevelUpRepositoryTest {

    @Autowired
    private LevelUpRepository levelUpRepository;

    private LocalDate DATE = LocalDate.now();

    @Before
    public void setUp() throws Exception {
        levelUpRepository.findAll().stream().forEach(levelUp -> levelUpRepository.deleteById(levelUp.getLevelUpId()));
    }

    @Test
    void shouldFindAllByCustomerId() {
        LevelUp levelUp1 = new LevelUp(1, 10, DATE);
        LevelUp levelUpFromDb = levelUpRepository.save(levelUp1);
        levelUp1.setLevelUpId(levelUpFromDb.getLevelUpId());

        LevelUp levelUp2 = new LevelUp(2, 10, DATE);
        LevelUp levelUpFromDb2 = levelUpRepository.save(levelUp2);
        levelUp2.setLevelUpId(levelUpFromDb2.getLevelUpId());

        LevelUp LevelUp3 = new LevelUp(1, 10, DATE);
        LevelUp levelUpFromDb3 = levelUpRepository.save(LevelUp3);
        LevelUp3.setLevelUpId(levelUpFromDb3.getLevelUpId());

        List<LevelUp> expected1 = new ArrayList<>(Arrays.asList(levelUp1, LevelUp3));
        List<LevelUp> expected2 = new ArrayList<>(Arrays.asList(levelUp2));

        assertEquals(expected1.size(), levelUpRepository.findAllByCustomerId(1).size());
        assertEquals(expected1, levelUpRepository.findAllByCustomerId(1));
        assertEquals(expected2.size(), levelUpRepository.findAllByCustomerId(2).size());
        assertEquals(expected2, levelUpRepository.findAllByCustomerId(2));
    }
}