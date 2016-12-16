package ru.aviaj.database.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@SuppressWarnings({"OverlyBroadThrowsClause", "FieldCanBeLocal", "unused", "MethodNameSameAsClassName"})
@RunWith(SpringRunner.class)

public class DbExceptionTest {

    @Test
    public void dbExceptionTest() throws Exception {
        final DbException dbException = new DbException("Exception", new SQLException());
        assertEquals(dbException.getMessage(), "Exception");
    }
}