package ru.aviaj.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.aviaj.model.UserProfile;
import ru.aviaj.service.AccountService;
import ru.aviaj.service.SessionService;

import javax.jws.soap.SOAPBinding;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"OverlyBroadThrowsClause", "FieldCanBeLocal", "unused"})
@TestPropertySource(locations = "classpath:test.properties")
@WebMvcTest
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
            UserProfile userProfile = accountService.addUser("login", "email", "password");
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
                .andExpect(jsonPath("id").value(testUser.getId()))
                .andExpect(jsonPath("rating").value(testUser.getRating()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .content("{\"login\":\"login\",\"password\":\"password1\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .content("{\"login\":\"login\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        authUser(testUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .content("{\"login\":\"login\",\"password\":\"password\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void authenticate() throws Exception {
        final UserProfile testUser = createUser();
        authUser(testUser);
        mockMvc.perform(MockMvcRequestBuilders.get("api/auth/authenticate").sessionAttr("JSESSIONID", "testSession"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("login").value(testUser.getLogin()))
                .andExpect(jsonPath("email").value(testUser.getEmail()))
                .andExpect(jsonPath("id").value(testUser.getId()))
                .andExpect(jsonPath("rating").value(testUser.getRating()));

        mockMvc.perform(MockMvcRequestBuilders.get("api/auth/authenticate").sessionAttr("JSESSIONID", "Session"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void logout() throws Exception {
        final UserProfile testUser = createUser();
        authUser(testUser);
        mockMvc.perform(MockMvcRequestBuilders.post("api/auth/logout").sessionAttr("JSESSIONID", "testSession"))
                .andExpect(status().isOk());
        isAuthenticated = false;

        mockMvc.perform(MockMvcRequestBuilders.post("api/auth/logout").sessionAttr("JSESSIONID", "Session"))
                .andExpect(status().isUnauthorized());
    }

}