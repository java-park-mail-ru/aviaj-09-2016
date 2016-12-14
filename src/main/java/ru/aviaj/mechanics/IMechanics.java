package ru.aviaj.mechanics;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface IMechanics {

    // void addClientSnaphot();

    void addUser(@NotNull long userId);

    void gameStep(long frameTime);

    void reset();
}
