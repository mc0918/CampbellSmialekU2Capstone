package com.trilogyed.adminapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.adminapi.exception.IdNotFound;
import com.trilogyed.adminapi.model.LevelUp;
import com.trilogyed.adminapi.service.ServiceLayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LevelUpController.class)
public class LevelUpControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ServiceLayer service;

    private static final LocalDate DATE = LocalDate.of(1212,12,12);
    private static final LevelUp LevelUp_NO_ID = new LevelUp(1, 10, DATE);
    private static final LevelUp LevelUp_ID = new LevelUp(1, 1, 10, DATE);
    private static final List<LevelUp> LevelUp_LIST = new ArrayList<>(Arrays.asList(LevelUp_ID));
    private static final LevelUp LevelUp_UPDATED = new LevelUp(1, 1, 13, DATE);
    private static final LevelUp LevelUp_BAD_UPDATE = new LevelUp(7, 1, 10, DATE);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Fail";

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUpMock() { //might need to be changed to fit with jpa methods
        when(service.saveLevelUp(LevelUp_NO_ID)).thenReturn(LevelUp_ID);
        when(service.getLevelUp(1)).thenReturn(LevelUp_ID);
        when(service.getAllLevelUps()).thenReturn(LevelUp_LIST);

        doThrow(new IdNotFound("bad thing")).when(service).updateLevelUp(LevelUp_BAD_UPDATE);

        //success and failure messages sent from service layer if applicable
        //when(service.updateLevelUp(LevelUp_UPDATED)).thenReturn("Update: "+ SUCCESS);
        //when(service.deleteLevelUp(1)).thenReturn("Delete: " + SUCCESS);
        //when(service.updateLevelUp(LevelUp_BAD_UPDATE)).thenReturn("Update: "+ FAIL);
        //when(service.deleteLevelUp(1)).thenReturn("Delete: " + FAIL);

        //exceptions
        //when(service.updateLevelUp(LevelUp_BAD_UPDATE)).thenThrow(new IdNotFound("bad thing"));
        //when(service.deleteLevelUp(7)).thenThrow(new IdNotFound("bad thing"));        
    }

    @Test
    public void saveLevelUp() throws Exception {
        String input_json = mapper.writeValueAsString(LevelUp_NO_ID);
        String output_json = mapper.writeValueAsString(LevelUp_ID);

        mvc.perform(post("/levelUp")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getLevelUp() throws Exception {
        String output_json = mapper.writeValueAsString(LevelUp_ID);

        mvc.perform(get("/levelUp/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void getAllLevelUps() throws Exception {
        String output_json = mapper.writeValueAsString(LevelUp_LIST);

        mvc.perform(get("/levelUp"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output_json));
    }

    @Test
    public void updateLevelUp() throws Exception {
        String input_json = mapper.writeValueAsString(LevelUp_UPDATED);

        mvc.perform(put("/levelUp")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

        //for things with random or json parsing errors
        //.andExpect(jsonPath("$.id").value("" + REAL_LOCATION.getId()))
        //.andExpect(jsonPath("$.description").value(REAL_LOCATION.getDescription()))
        //.andExpect(jsonPath("$.location").value(REAL_LOCATION.getLocation()));
    }

    @Test
    public void deleteLevelUp() throws Exception {
        mvc.perform(delete("/levelUp/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //exception test
    @Test
    public void exceptionTest() throws Exception {
        String input_json = mapper.writeValueAsString(LevelUp_BAD_UPDATE);
        mvc.perform(put("/levelUp")
                .content(input_json)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()) //or whatever status code you set your exception to be, this is a default value
                .andExpect(content().string(containsString("bad thing")));
    }
}
