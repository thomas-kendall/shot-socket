package shotsocket.websocket.message;

public abstract class ShotsocketMessage {

	private String action;

	public ShotsocketMessage(String action) {
		this.setAction(action);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return ShotsocketMessageMarshaller.toJSON(this);
	}
}
