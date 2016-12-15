package ru.aviaj.model;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@SuppressWarnings({"OverlyBroadThrowsClause", "FieldCanBeLocal", "unused"})
@RunWith(SpringRunner.class)

public class ModelTest {

    @Test
    public void userProfileTest() throws Exception {
        UserProfile user1 = new UserProfile("login1", "email1", "psw", 1, 0);
        UserProfile user2 = new UserProfile("login1", "email1", "psw");
        UserProfile user3 = new UserProfile("login1", "email1", "psw", 1, 0);

        assertTrue(user1.equals(user3));
        assertFalse(user2.equals(user1));

        assertEquals(user1.getLogin(), "login1");
        assertEquals(user1.getEmail(), "email1");
        assertEquals(user1.getPassword(), "psw");
        assertEquals(user1.getRating(), 0);
        assertEquals(user1.getId(), 1);

        assertEquals(user1.hashCode(), user3.hashCode());

    }

    @Test
    public void ErrorListTest() throws Exception {
        ErrorList errorList = new ErrorList();
        ErrorList otherErrorList = new ErrorList(ErrorType.NOUSERID);

        assertTrue(errorList.isEmpty());
        errorList.addError(ErrorType.ALREADYLOGIN);
        assertFalse(errorList.isEmpty());

        assertEquals(errorList.getErrors().size(), 1);

    }
}
