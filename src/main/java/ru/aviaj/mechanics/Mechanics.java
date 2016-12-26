package ru.aviaj.mechanics;


import org.springframework.stereotype.Service;
import ru.aviaj.mechanics.snapshot.ClientSnaphot;

import javax.validation.constraints.NotNull;

@Service
public class Mechanics implements IMechanics {


    @Override
    public void addClientSnaphot(long userId, ClientSnaphot clientSnaphot) {

    }

    @Override
    public void addUser(@NotNull long userId) {

    }

    @Override
    public void gameStep(long frameTime) {

    }

    @Override
    public void reset() {

    }
}
