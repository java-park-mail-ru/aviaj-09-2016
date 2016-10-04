package ru.aviaj.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ErrorList {

    private final List<Error> errors = new ArrayList<>();

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
