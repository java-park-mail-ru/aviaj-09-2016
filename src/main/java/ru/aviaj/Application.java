package ru.aviaj;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import ru.aviaj.websocket.GameWebSocket;

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"SpringFacetCodeInspection", "AnonymousInnerClassMayBeStatic"})
@SpringBootApplication
public class Application {

    public static final long IDLE_TIMEOUT = TimeUnit.MINUTES.toMillis(1);
    public static final int BUFFER_SIZE = 8192;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {
        final WebSocketPolicy webSocketPolicy = new WebSocketPolicy(WebSocketBehavior.SERVER);
        webSocketPolicy.setInputBufferSize(BUFFER_SIZE);
        webSocketPolicy.setIdleTimeout(IDLE_TIMEOUT);

        return new DefaultHandshakeHandler(
                new JettyRequestUpgradeStrategy(new WebSocketServerFactory(webSocketPolicy))
        );
    }

    @Bean
    public WebSocketHandler gameWebSocketHandler() {
        return new PerConnectionWebSocketHandler(GameWebSocket.class);
    }


}
