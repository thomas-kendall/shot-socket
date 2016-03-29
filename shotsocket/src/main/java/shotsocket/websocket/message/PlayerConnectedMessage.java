package shotsocket.websocket.message;

public class PlayerConnectedMessage extends ShotsocketMessage {
	public static final String ACTION = "player-connected";

	public PlayerConnectedMessage() {
		super(ACTION);
	}
}
