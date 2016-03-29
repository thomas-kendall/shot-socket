package shotsocket.websocket.message;

public class PlayerDisconnectedMessage extends ShotsocketMessage {
	public static final String ACTION = "player-disconnected";

	public PlayerDisconnectedMessage() {
		super(ACTION);
	}
}
