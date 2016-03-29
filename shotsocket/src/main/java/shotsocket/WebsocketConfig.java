package shotsocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import shotsocket.websocket.GameControllerHandler;
import shotsocket.websocket.GameDisplayHandler;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

	@Bean
	public WebSocketHandler gameControllerHandler() {
		return new GameControllerHandler();
	}

	@Bean
	public WebSocketHandler gameDisplayHandler() {
		return new GameDisplayHandler();
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(gameDisplayHandler(), "/game-display");
		registry.addHandler(gameControllerHandler(), "/game-controller");
	}

}
