package shotsocket.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import shotsocket.model.Game;
import shotsocket.service.ShotsocketService;
import shotsocket.websocket.message.DeviceOrientationMessage;
import shotsocket.websocket.message.JoinGameMessage;
import shotsocket.websocket.message.PlayerConnectedMessage;
import shotsocket.websocket.message.PlayerDisconnectedMessage;
import shotsocket.websocket.message.ShotsocketMessage;
import shotsocket.websocket.message.ShotsocketMessageMarshaller;

public class GameControllerHandler extends TextWebSocketHandler {

	@Autowired
	private ShotsocketService shotsocketService;

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Game game = shotsocketService.findGame(session);
		if (game != null) {
			PlayerDisconnectedMessage playerDisconnectedMessage = new PlayerDisconnectedMessage();
			game.getDisplaySession()
					.sendMessage(new TextMessage(ShotsocketMessageMarshaller.toJSON(playerDisconnectedMessage)));
			game.setControllerSession(null);
			System.out.println("Game " + game.getGameId() + " - player disconnected");
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
		ShotsocketMessage message = ShotsocketMessageMarshaller.fromJSON(textMessage.getPayload());

		if (message instanceof JoinGameMessage) {
			JoinGameMessage joinGameMessage = (JoinGameMessage) message;
			Game game = shotsocketService.joinGame(joinGameMessage.getGameId(), session);
			System.out.println("Game " + game.getGameId() + " - player connected");
			PlayerConnectedMessage playerConnectedMessage = new PlayerConnectedMessage();
			game.getDisplaySession()
					.sendMessage(new TextMessage(ShotsocketMessageMarshaller.toJSON(playerConnectedMessage)));
		} else if (message instanceof DeviceOrientationMessage) {
			// Simply forward the message to the display
			Game game = shotsocketService.findGame(session);
			game.getDisplaySession().sendMessage(textMessage);
		}
	}
}
