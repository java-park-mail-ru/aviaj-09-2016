package ru.aviaj.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.aviaj.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("OverlyBroadThrowsClause")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class AccountTest {

    @Autowired
    private AccountService accountService;

    List<UserProfile> mockUsers = new ArrayList<>();

    public AccountTest() throws Exception {
        for (int i = 1; i <= 15; i++) {
            mockUsers.add(new UserProfile(
                    "User" + Integer.toString(i),
                    "user" + Integer.toString(i) + "@mail.ru",
                    "samplepassword",
                    i,
                    0
            ));
        }
    }

    private boolean fillUser() throws Exception {
        for (UserProfile user : mockUsers) {
            final UserProfile userProfile = accountService.addUser(user.getLogin(), user.getEmail(), user.getPassword());
            if (userProfile == null)
                return false;
        }
        return true;
    }

    @Test
    public void addUserTest() throws Exception {
        final UserProfile user = accountService.addUser("TestUser", "test@mail.ru", "psw");
        assertEquals("TestUser", user.getLogin());
        assertEquals("test@mail.ru", user.getEmail());
        assertNotNull(accountService.getUserExistance("TestUser", "test@mail.ru"));

        accountService.truncateAll();
    }


    @Test
    public void getUserTest() throws Exception {
        fillUser();
        final UserProfile mockUser3 = mockUsers.get(2);
        final UserProfile user3 = accountService.getUserByLogin(mockUser3.getLogin());
        assertNotNull(user3);
        assertEquals(user3.getLogin(), mockUser3.getLogin());
        assertEquals(mockUser3.getLogin(),accountService.getUserExistance(mockUser3.getLogin(), mockUser3.getEmail())
            .getLogin());

        assertNotNull(accountService.getUserByLogin("User5"));
        assertNull(accountService.getUserByLogin("NoUser"));

        final UserProfile user5 = accountService.getUserByLogin("User5");
        assertNotNull(user5);
        assertEquals(user5.getLogin(), accountService.getUserExistance(user5.getLogin(), user5.getEmail()).getLogin());
        assertEquals(user5, accountService.getUserById(user5.getId()));

        accountService.truncateAll();
    }

    @Test
    public void updateRatingTest() throws Exception {
        fillUser();
        final UserProfile user = accountService.getUserByLogin(mockUsers.get(7).getLogin());
        assertNotNull(user);
        accountService.updateUserRating(user.getId(), 100);
        final UserProfile userUpd = accountService.getUserByLogin(user.getLogin());
        assertNotNull(userUpd);
        assertEquals(userUpd.getId(), user.getId());
        final List<UserProfile> top = accountService.getTopUsers();
        assertEquals(100, top.get(0).getRating());

        accountService.truncateAll();
    }

    @Test
    public void getUsersTest() throws Exception {
        fillUser();

        assertEquals(accountService.getAllUsers().size(), 15);

        assertEquals(accountService.getTopUsers().size(), 10);
        assertEquals(accountService.getTopUsers().get(1).getRating(), 0);

        accountService.truncateAll();

    }

}
