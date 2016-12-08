package ru.aviaj.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@SuppressWarnings({"OverlyBroadThrowsClause", "FieldCanBeLocal", "unused"})
@RunWith(SpringRunner.class)

public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    private static boolean isCreated = false;
    private static boolean isAuthenticated = false;


    private UserProfile createUser() throws Exception {
        if (accountService.getUserByLogin("login") == null)
        {
            final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final String encodedPassword = encoder.encode("password");
            final UserProfile userProfile = accountService.addUser("login", "email", encodedPassword);
            if (userProfile == null)
                throw new Exception();
            return userProfile;
        }
        return accountService.getUserByLogin("login");
    }

    private void authUser(UserProfile userProfile) throws Exception {
        if (sessionService.getUserIdBySession("testSession") != userProfile.getId()) {
            sessionService.addSession("testSession", userProfile.getId());
            if (sessionService.getUserIdBySession("testSession") != userProfile.getId())
                throw new Exception();
        }
    }

    @Test
    public void login() throws Exception {
        final UserProfile testUser = createUser();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .content("{\"login\":\"login\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value(testUser.getLogin()))
                .andExpect(jsonPath("email").value(testUser.getEmail()))
                .andExpect(jsonPath("id").value((int)testUser.getId()))
                .andExpect(jsonPath("rating").value((int)testUser.getRating()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .content("{\"login\":\"login\",\"password\":\"password111\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .content("{\"login\":\"login\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void authenticate() throws Exception {
        final UserProfile testUser = createUser();
        authUser(testUser);
       /* mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/authenticate").sessionAttr("JSESSIONID", "testSession"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value(testUser.getLogin()))
                .andExpect(jsonPath("email").value(testUser.getEmail()))
                .andExpect(jsonPath("id").value(testUser.getId()))
                .andExpect(jsonPath("rating").value(testUser.getRating())); */

        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/authenticate").sessionAttr("JSESSIONID", "Session"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void logout() throws Exception {
        final UserProfile testUser = createUser();
        authUser(testUser);
        /*mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/logout").sessionAttr("JSESSIONID", "testSession"))
                .andExpect(status().isOk());
        isAuthenticated = false; */
        sessionService.removeSession("testSession");
        isAuthenticated = false;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/logout").sessionAttr("JSESSIONID", "Session"))
                .andExpect(status().isUnauthorized());
    }

}