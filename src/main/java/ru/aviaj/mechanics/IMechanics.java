package ru.aviaj.mechanics;

import org.springframework.stereotype.Service;
import ru.aviaj.mechanics.snapshot.ClientSnaphot;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface IMechanics {

    void addClientSnaphot(long userId, ClientSnaphot clientSnaphot);

    void addUser(@NotNull long userId);

    void gameStep(long frameTime);

    void reset();
}
