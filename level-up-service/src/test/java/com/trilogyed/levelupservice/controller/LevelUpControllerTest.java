package com.trilogyed.levelupservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.levelupservice.exceptions.IdNotFound;
import com.trilogyed.levelupservice.model.LevelUp;
import com.trilogyed.levelupservice.repository.LevelUpRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;


@RunWith(SpringRunner.class)
@WebMvcTest(LevelUpController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class LevelUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LevelUpRepository levelUpRepository;

    private ObjectMapper mapper = new ObjectMapper();

    private static final LocalDate DATE_OBJECT = LocalDate.now();
    private static final LevelUp LEVEL_UP_TO_SAVE = new LevelUp(1, 10, DATE_OBJECT);
    private static final LevelUp LEVEL_UP_SAVED = new LevelUp(1,1, 10, DATE_OBJECT);

    private static final int BAD_ID = 272;

    @Before
    public void setUp() throws Exception {
        when(levelUpRepository.save(LEVEL_UP_TO_SAVE)).thenReturn(LEVEL_UP_SAVED);
        when(levelUpRepository.findById(1)).thenReturn(Optional.of(LEVEL_UP_SAVED));
        when(levelUpRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(LEVEL_UP_SAVED)));
//        when(levelUpRepository.findAllByCustomerId(1)).thenReturn(new ArrayList<>(Arrays.asList(LEVEL_UP_SAVED)));
        when(levelUpRepository.findById(BAD_ID)).thenThrow(new IdNotFound("Cannot find level up id " + BAD_ID));
        when(levelUpRepository.findByCustomerId(BAD_ID)).thenThrow(new IdNotFound("Cannot find customer id " + BAD_ID));
    }

    @Test
    public void submitLevelUp() throws Exception {
        String inputJson = mapper.writeValueAsString(LEVEL_UP_TO_SAVE);
        String outputJson = mapper.writeValueAsString(LEVEL_UP_SAVED);

        mockMvc.perform(post("/levelUp")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void findLevelUpById() throws Exception {
        String outputJson = mapper.writeValueAsString(LEVEL_UP_SAVED);

        mockMvc.perform(get("/levelUp/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void findAllLevelUps() throws Exception {
        List<LevelUp> levelUps = new ArrayList<>(Arrays.asList(LEVEL_UP_SAVED));
        String outputJson = mapper.writeValueAsString(levelUps);

        mockMvc.perform(get("/levelUp"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

//    @Test
//    public void findLevelUpsByCustomerId() throws Exception {
//        List<LevelUp> levelUps = new ArrayList<>(Arrays.asList(LEVEL_UP_SAVED));
//        String outputJson = mapper.writeValueAsString(levelUps);
//
//        mockMvc.perform(get("/levelUp/customer/{id}", 1))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json(outputJson));
//    }

    @Test
    public void updateLevelUp() throws Exception {
        String inputJson = mapper.writeValueAsString(LEVEL_UP_TO_SAVE);

        mockMvc.perform(put("/levelUp")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteLevelUp() throws Exception {
        mockMvc.perform(delete("/levelUp/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldThrowIdNotFoundForBadLevelUpIdInGetMethod() throws Exception{
        mockMvc.perform(get("/levelUp/{id}", BAD_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Cannot find level up id")));
    }

    @Test
    public void shouldThrowIdNotFoundForBadLevelUpIdInDeleteMethod()throws Exception{
        mockMvc.perform(delete("/levelUp/{id}", BAD_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Cannot find level up id")));
    }

    @Test
    public void shouldThrowIdNotFoundForBadCustomerId() throws Exception{
        mockMvc.perform(get("/levelUp/customer/{id}", BAD_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Cannot find customer id")));
    }

    @Test
    public void shouldThrowIdAlreadyExistsForSameCustomerId() throws Exception {}

}