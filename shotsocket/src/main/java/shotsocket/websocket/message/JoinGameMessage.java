package shotsocket.websocket.message;

public class JoinGameMessage extends ShotsocketMessage {
	public static final String ACTION = "join-game";

	private int gameId;

	public JoinGameMessage() {
		super(ACTION);
	}

	public JoinGameMessage(int gameId) {
		super(ACTION);
		setGameId(gameId);
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
}
