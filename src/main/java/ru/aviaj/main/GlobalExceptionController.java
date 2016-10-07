package ru.aviaj.main;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.aviaj.model.ErrorList;
import ru.aviaj.model.ErrorType;

@SuppressWarnings("unused")
@ControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody ErrorList
    handleHttpMessageNotReadable() {
        return new ErrorList(ErrorType.WRONGBODY);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody ErrorList
    handleMethodArgumentTypeMismatch() {
        return new ErrorList(ErrorType.WRONGTYPE);
    }

}
