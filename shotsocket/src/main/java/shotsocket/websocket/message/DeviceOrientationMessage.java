package shotsocket.websocket.message;

public class DeviceOrientationMessage extends ShotsocketMessage {
	public static final String ACTION = "device-orientation";

	private double tiltLR;
	private double tiltFB;
	private double dir;

	public DeviceOrientationMessage() {
		super(ACTION);
	}

	public DeviceOrientationMessage(double tiltLR, double tiltFB, double dir) {
		super(ACTION);
		setTiltLR(tiltLR);
		setTiltFB(tiltFB);
		setDir(dir);
	}

	public double getDir() {
		return dir;
	}

	public double getTiltFB() {
		return tiltFB;
	}

	public double getTiltLR() {
		return tiltLR;
	}

	public void setDir(double dir) {
		this.dir = dir;
	}

	public void setTiltFB(double tiltFB) {
		this.tiltFB = tiltFB;
	}

	public void setTiltLR(double tiltLR) {
		this.tiltLR = tiltLR;
	}

}
