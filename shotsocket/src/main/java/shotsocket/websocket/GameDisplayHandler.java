package shotsocket.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import shotsocket.model.Game;
import shotsocket.service.ShotsocketService;
import shotsocket.websocket.message.NewGameMessage;
import shotsocket.websocket.message.ShotsocketMessageMarshaller;

public class GameDisplayHandler extends TextWebSocketHandler {

	@Autowired
	private ShotsocketService shotsocketService;

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Game game = shotsocketService.findGame(session);
		if (game != null) {
			if (game.getControllerSession() != null) {
				game.getControllerSession().close();
			}
			System.out.println("Game " + game.getGameId() + " - game removed");
			shotsocketService.removeGame(game);
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Game game = shotsocketService.createNewGame(session);
		System.out.println("Game " + game.getGameId() + " - game created");
		NewGameMessage message = new NewGameMessage(game.getGameId());
		session.sendMessage(new TextMessage(ShotsocketMessageMarshaller.toJSON(message)));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	}
}
