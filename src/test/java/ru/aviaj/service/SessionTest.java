package ru.aviaj.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SessionTest {

    @Autowired
    private SessionService sessionService;

    private boolean fillSession() throws Exception {
        for (int i = 1; i <= 10; i++) {
            if (!sessionService.addSession("session" + Integer.toString(i), i)) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void addSessionTest() throws Exception {
        assertTrue(sessionService.addSession("samplesession", 100));
        assertFalse(sessionService.addSession("samplesession", 100));
    }


    @Test
    public void getSessionTest() throws Exception {
        fillSession();
        assertEquals(5, sessionService.getUserIdBySession("session5"));
        assertEquals(0, sessionService.getUserIdBySession("NoSession"));
    }
}
