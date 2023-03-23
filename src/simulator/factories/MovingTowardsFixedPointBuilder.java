package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;


import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

    public MovingTowardsFixedPointBuilder() {
        super("mtfp", "Moving towards a fixed point");
    }

    @Override
    protected ForceLaws createInstance(JSONObject data) {
        Vector2D c = new Vector2D(0, 0);
        double g = 9.81;

        
        if (data.has("c")) {
        	JSONArray cData = data.getJSONArray("c");
            if (cData.length() != 2) {
                throw new IllegalArgumentException("Invalid c vector");
            }
            c = new Vector2D(cData.getDouble(0), cData.getDouble(1));
        }

        if (data.has("g")) {
            g = data.getDouble("g");
        }

        return new MovingTowardsFixedPoint(c, g);
    }
}
