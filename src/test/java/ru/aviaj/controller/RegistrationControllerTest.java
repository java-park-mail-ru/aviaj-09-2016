package ru.aviaj.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@SuppressWarnings({"OverlyBroadThrowsClause", "FieldCanBeLocal", "unused"})
@RunWith(SpringRunner.class)

public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    @Test
    public void signupTest() throws Exception {
        accountService.truncateAll();
        sessionService.truncateAll();

        UserProfile testUser1 = accountService.addUser("login1", "email1", "psw1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .content("{\"login\":\"login\",\"email\":\"email\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .content("{\"login\":\"login1\",\"email\":\"email\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        sessionService.addSession("testSession", testUser1.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                .content("{\"login\":\"login5\",\"email\":\"email5\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .sessionAttr("AVIAJSESSIONID", "testSession"))
                .andExpect(status().isForbidden());

    }

}