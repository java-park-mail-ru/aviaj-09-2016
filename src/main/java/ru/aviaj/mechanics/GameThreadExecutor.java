package ru.aviaj.mechanics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SuppressWarnings("unused")
@Service
public class GameThreadExecutor implements Runnable {

    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(GameThreadExecutor.class);

    private static final long STEP_TIME = GameConfig.GAME_STEP_TIME;

    @NotNull
    private final IMechanics gameMechanics;

    @NotNull
    private Clock clock = Clock.systemDefaultZone();
    private Executor gameTickExecutor = Executors.newSingleThreadExecutor();

    @Autowired
    public GameThreadExecutor(@NotNull IMechanics gameMechanics) {
        this.gameMechanics = gameMechanics;
    }

    @PostConstruct
    void afterCreated() {
        gameTickExecutor.execute(this);
    }

    @Override
    public void run() {
        long lastFrameTime = STEP_TIME;

        while(true) {
            final long before = clock.millis();
            gameMechanics.gameStep(lastFrameTime);
            final long after = clock.millis();

            try {
                Thread.sleep(STEP_TIME - (after - before));
            } catch (InterruptedException e) {
                LOGGER.error("GameThreadExecutor sleep error:", e);
            }

            if (Thread.currentThread().isInterrupted()) {
                gameMechanics.reset();
                return;
            }

            final long afterSleep = clock.millis();
            lastFrameTime = afterSleep - before;
        }
    }

}
