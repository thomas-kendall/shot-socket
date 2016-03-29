package shotsocket.model;

import org.springframework.web.socket.WebSocketSession;

public class Game {

	private int gameId;
	private WebSocketSession displaySession;
	private WebSocketSession controllerSession;

	public WebSocketSession getControllerSession() {
		return controllerSession;
	}

	public WebSocketSession getDisplaySession() {
		return displaySession;
	}

	public int getGameId() {
		return gameId;
	}

	public void setControllerSession(WebSocketSession controllerSession) {
		this.controllerSession = controllerSession;
	}

	public void setDisplaySession(WebSocketSession displaySession) {
		this.displaySession = displaySession;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
}
