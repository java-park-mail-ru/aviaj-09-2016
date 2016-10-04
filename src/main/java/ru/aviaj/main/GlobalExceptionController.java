package ru.aviaj.main;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.aviaj.model.Error;
import ru.aviaj.model.ErrorList;

/**
 * Created by sibirsky on 01.10.16.
 */

@ControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    @ResponseBody ErrorList
    handleHttpMessageNotReadable() {
        return new ErrorList(Error.ErrorType.WRONGBODY);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    @ResponseBody ErrorList
    handleMethodArgumentTypeMismatch() {
        return new ErrorList(Error.ErrorType.WRONGTYPE);
    }

}
