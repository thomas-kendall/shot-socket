package shotsocket.websocket.message;

public class NewGameMessage extends ShotsocketMessage {
	public static final String ACTION = "new-game";

	private int gameId;

	public NewGameMessage() {
		super(ACTION);
	}

	public NewGameMessage(int gameId) {
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
