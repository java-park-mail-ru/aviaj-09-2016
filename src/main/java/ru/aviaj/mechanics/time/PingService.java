package ru.aviaj.mechanics.time;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aviaj.mechanics.time.request.ClientPingData;
import ru.aviaj.websocket.ClientMessage;
import ru.aviaj.websocket.ClientService;

import java.io.IOException;
import java.sql.Time;
import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PingService {
    private static final long MAX_PING = TimeUnit.SECONDS.toMillis(1);
    private static final Logger LOGGER = LoggerFactory.getLogger(PingService.class);

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final Map<Long, Long> pendingRequests = new ConcurrentHashMap<>();
    private final Map<Long, Timing> clientTimings = new ConcurrentHashMap<>();

    private Executor updater = Executors.newSingleThreadExecutor();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ClientService clientService;

    @Autowired
    public PingService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void rememberPing(long userId, long timeStamp, long requestId) {
        final Long timeWas = pendingRequests.get(requestId);
        if (timeWas == null) {
            return;
        }

        final long now = Clock.systemDefaultZone().millis();
        final long ping = now - timeWas;
        if (ping > MAX_PING) {
            return;
        }

        final long clientTimeShift = now - (timeStamp - ping/2);

        clientTimings.put(userId, new Timing(ping, clientTimeShift));
    }

    public Timing getTiming(long userId) { return clientTimings.get(userId); }

    public void refreshPing(long userId) {
        if (!clientService.isClientConnected(userId)) {
            return;
        }

        updater.execute(() -> {
            final long id = idGenerator.getAndIncrement();
            final long now = Clock.systemDefaultZone().millis();
            final ClientPingData.Request request = ClientPingData.Request.createBuilder().id(id).build();
            try {
                ClientMessage message = new ClientMessage(ClientPingData.Request.class.getName(),
                        objectMapper.writeValueAsString(request));
                clientService.sendClientMessage(userId, message);
            } catch (IOException e) {
                LOGGER.error("Unable to send ping message to user with id " + Long.toString(userId) + "!", e);
                return;
            }

            pendingRequests.put(id, now);
        });
    }
}
