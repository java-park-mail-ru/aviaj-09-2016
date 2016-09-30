package ru.aviaj.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sibirsky on 01.10.16.
 */
public class ErrorList {

    private final List<Error> errors = new ArrayList<Error>();

    public ErrorList() { }

    public ErrorList(Error.ErrorType defaultError) {
        errors.add(new Error(defaultError));
    }

    public void addError(Error.ErrorType error) {
        errors.add(new Error(error));
    }

    public List<Error> getErrors() {
        return errors;
    }

}
