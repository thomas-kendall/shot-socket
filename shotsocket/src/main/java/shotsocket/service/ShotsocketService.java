package shotsocket.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import shotsocket.model.Game;

@Service
public class ShotsocketService {
	private static int nextGameId = 1;

	private static Set<Game> games = new HashSet<>();

	public Game createNewGame(WebSocketSession displaySession) {

		Game game = new Game();
		game.setGameId(nextGameId++);
		game.setDisplaySession(displaySession);
		games.add(game);
		return game;
	}

	public Game findGame(int gameId) {
		Game result = null;
		for (Game game : games) {
			if (game.getGameId() == gameId) {
				result = game;
				break;
			}
		}
		return result;
	}

	public Game findGame(WebSocketSession session) {
		Game result = null;
		for (Game game : games) {
			if (game.getDisplaySession() == session) {
				result = game;
				break;
			}
			if (game.getControllerSession() == session) {
				result = game;
				break;
			}
		}
		return result;
	}

	public Game joinGame(int gameId, WebSocketSession controllerSession) {
		Game game = findGame(gameId);
		if (game == null) {
			throw new RuntimeException("Game with id " + gameId + " does not exist.");
		}
		game.setControllerSession(controllerSession);
		return game;
	}

	public void removeGame(Game game) {
		games.remove(game);
	}
}
