package simulator.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup {
    private final String id;
    private ForceLaws forceLaws;
    private List<Body> bodies;

    public BodiesGroup(String id, ForceLaws forceLaws) {
        if (id == null || id.trim().length() == 0 || forceLaws == null) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        this.id = id;
        this.forceLaws = forceLaws;
        this.bodies = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setForceLaws(ForceLaws fl) {
        if (fl == null) {
            throw new IllegalArgumentException("ForceLaws cannot be null");
        }
        this.forceLaws = fl;
    }

    public void addBody(Body b) {
        if (b == null) {
            throw new IllegalArgumentException("Body cannot be null");
        }
        if (bodies.contains(b)) {
            throw new IllegalArgumentException("Body already exists in the group");
        }
        bodies.add(b);
    }

    public void advance(double dt) {
        if (dt <= 0) {
            throw new IllegalArgumentException("dt must be positive");
        }
        for (Body b : bodies) {
            b.resetForce();
        }
        forceLaws.apply(bodies);
        for (Body b : bodies) {
            b.advance(dt);
        }
    }

    public JSONObject getState() {
        JSONObject groupJson = new JSONObject();
        groupJson.put("id", id);
        JSONArray bodiesJson = new JSONArray();
        for (Body b : bodies) {
            bodiesJson.put(b.getState());
        }
        groupJson.put("bodies", bodiesJson);
        return groupJson;
    }

    @Override
    public String toString() {
        return getState().toString();
    }
}
