package ru.aviaj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ErrorList {

    private final List<Error> errors = new ArrayList<>();

    public ErrorList() { }

    public ErrorList(ErrorType defaultError) {
        errors.add(new Error(defaultError));
    }

    public void addError(ErrorType error) {
        errors.add(new Error(error));
    }

    public List<Error> getErrors() {
        return errors;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return errors.isEmpty();
    }

}
