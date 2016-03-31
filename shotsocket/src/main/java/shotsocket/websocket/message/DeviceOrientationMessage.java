package shotsocket.websocket.message;

public class DeviceOrientationMessage extends ShotsocketMessage {
	public static final String ACTION = "device-orientation";

	private double alpha;
	private double beta;
	private double gamma;

	public DeviceOrientationMessage() {
		super(ACTION);
	}

	public DeviceOrientationMessage(double alpha, double beta, double gamma) {
		super(ACTION);
		setAlpha(alpha);
		setBeta(beta);
		setGamma(gamma);
	}

	public double getAlpha() {
		return alpha;
	}

	public double getBeta() {
		return beta;
	}

	public double getGamma() {
		return gamma;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

}
