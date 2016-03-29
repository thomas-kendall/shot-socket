package shotsocket.websocket.message;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShotsocketMessageMarshaller {

	public static ShotsocketMessage fromJSON(String json) {
		JSONObject jsonObject = new JSONObject(json);
		String action = jsonObject.getString("action");
		return fromJSON(json, action);
	}

	private static ShotsocketMessage fromJSON(String json, String action) {
		ObjectMapper mapper = new ObjectMapper();
		ShotsocketMessage message;
		try {
			if (action.equals("new-game")) {
				message = mapper.readValue(json, NewGameMessage.class);
			} else if (action.equals("join-game")) {
				message = mapper.readValue(json, JoinGameMessage.class);
			} else if (action.equals("device-orientation")) {
				message = mapper.readValue(json, DeviceOrientationMessage.class);
			} else {
				throw new Exception("action not supported: " + action);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return message;
	}

	public static String toJSON(ShotsocketMessage message) {
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception converting message to JSON.");
		}
		return json;
	}
}
