package ru.aviaj.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.aviaj.model.ErrorType;

import static org.junit.Assert.*;


@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
@SuppressWarnings({"OverlyBroadThrowsClause", "FieldCanBeLocal", "unused", "InstanceMethodNamingConvention"})
@RunWith(SpringRunner.class)

public class GlobalExceptionControllerTest {

    private GlobalExceptionController globalExceptionController = new GlobalExceptionController();

    @Test
    public void handleHttpMessageNotReadableTest() throws Exception {
        assertEquals(globalExceptionController.handleHttpMessageNotReadable().getErrors().get(0).getCode(),
                ErrorType.WRONGBODY.getCode());
    }

    @Test
    public void handleMethodArgumentTypeMismatchTest() throws Exception {
        assertEquals(globalExceptionController.handleMethodArgumentTypeMismatch().getErrors().get(0).getCode(),
                ErrorType.WRONGTYPE.getCode());
    }

}