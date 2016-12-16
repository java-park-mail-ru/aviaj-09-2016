package ru.aviaj.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@SuppressWarnings({"OverlyBroadThrowsClause", "FieldCanBeLocal", "unused"})
@RunWith(SpringRunner.class)

public class UserSelectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    List<UserProfile> mockUsers = new ArrayList<>();

    private void fillUsers() throws Exception {
        for (UserProfile user : mockUsers) {
            final UserProfile userProfile = accountService.addUser(user.getLogin(), user.getEmail(), user.getPassword());
            assertNotNull(userProfile);
        }
    }

    public UserSelectionControllerTest() throws Exception {
        for (int i = 1; i <= 5; i++) {
            mockUsers.add(new UserProfile(
                    "User" + Integer.toString(i),
                    "user" + Integer.toString(i) + "@mail.ru",
                    "samplepassword",
                    i,
                    0
            ));
        }
    }

    @Test
    public void getUsers() throws Exception {
        accountService.truncateAll();

        fillUsers();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("users").isArray());

    }

    @Test
    public void getTop() throws Exception {
        accountService.truncateAll();

        fillUsers();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/top"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("users").isArray());
    }

    @Test
    public void getById() throws Exception {
        accountService.truncateAll();

        fillUsers();
        final UserProfile testUser = accountService.getUserByLogin("User1");
        assertNotNull(testUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/id/" + Long.toString(testUser.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value("User1"));
    }

    @Test
    public void getByLogin() throws Exception {
        accountService.truncateAll();

        fillUsers();
        final UserProfile testUser = accountService.getUserByLogin("User5");
        assertNotNull(testUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/login/" + testUser.getLogin()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value((int)testUser.getId()));
    }
}