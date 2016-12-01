package ru.aviaj.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;

@SuppressWarnings("OverlyBroadThrowsClause")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class SessionTest {

    @Autowired
    private SessionService sessionService;

    private void fillSession() throws Exception {
        for (int i = 1; i <= 10; i++) {
            sessionService.addSession("session" + Integer.toString(i), i);
        }
    }

    @Test
    public void addSessionTest() throws Exception {
        sessionService.addSession("samplesession", 100);
        assertEquals(100, sessionService.getUserIdBySession("samplesession"));
    }


    @Test
    public void getSessionTest() throws Exception {
        fillSession();
        assertEquals(5, sessionService.getUserIdBySession("session5"));
        assertEquals(0, sessionService.getUserIdBySession("NoSession"));
        sessionService.removeSession("session5");
        assertEquals(0, sessionService.getUserIdBySession("session5"));
        sessionService.removeAll();
        assertEquals(0, sessionService.getUserIdBySession("session1"));
    }
}
