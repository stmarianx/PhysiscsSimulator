package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body> {

    public StationaryBodyBuilder() {
        super("st_body", "Stationary body");
    }

    @Override
    protected Body createInstance(JSONObject data) {
        if (!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("m")
                || data.getJSONArray("p").length() != 2) {
            throw new IllegalArgumentException("Incorrect format");
        }

        String id = data.getString("id");
        String gid = data.getString("gid");
        Vector2D pos = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
        double mass = data.getDouble("m");
        return new StationaryBody(id, gid, pos, mass);
    }
}
