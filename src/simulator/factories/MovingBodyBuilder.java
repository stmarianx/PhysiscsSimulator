package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {

    public MovingBodyBuilder() {
    	super("mv_body", "Moving body");
    }

    @Override
    protected Body createInstance(JSONObject data) {
    	if (!data.has("id") || 	!data.has("gid") || !data.has("v") || !data.has("p") || !data.has("m")
    			|| data.getJSONArray("p").length()!=2
    			|| data.getJSONArray("v").length()!=2
    		) {
    		throw new IllegalArgumentException("Incorrect format");
        }
    	
    	String id = data.getString("id");
    	String gid = data.getString("gid");
        Vector2D pos = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
        Vector2D vel = new Vector2D(data.getJSONArray("v").getDouble(0), data.getJSONArray("v").getDouble(1));
        double mass = data.getDouble("m");
        return new MovingBody(id, gid, pos, vel, mass);
    }

}
