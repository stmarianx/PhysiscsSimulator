package simulator.model;

import java.util.*;

import org.json.*;

public class PhysicsSimulator {
    private double deltaTime;
    private ForceLaws defaultForceLaws;
    private Map<String, BodiesGroup> groups;
    private double currentTime;

    public PhysicsSimulator(ForceLaws defaultForceLaws, double deltaTime) {
        if (deltaTime <= 0) {
            throw new IllegalArgumentException("Delta-time must be positive");
        }
        if (defaultForceLaws == null) {
            throw new IllegalArgumentException("Force laws cannot be null");
        }
        this.deltaTime = deltaTime;
        this.defaultForceLaws = defaultForceLaws;
        this.groups = new LinkedHashMap<>();
        this.currentTime = 0.0;
    }

    public void advance() {
        for (BodiesGroup group : groups.values()) {
            group.advance(deltaTime);
        }
        currentTime += deltaTime;
    }

    public void addGroup(String id) {
        if (groups.containsKey(id)) {
            throw new IllegalArgumentException("Cannot add a group twice");
        }
        groups.put(id, new BodiesGroup(id, defaultForceLaws));
    }

    public void addBody(Body body) {
        String groupId = body.getgId();
        if (!groups.containsKey(groupId)) {
            throw new IllegalArgumentException("No group found with id");
        }
        groups.get(groupId).addBody(body);
    }

    public void setForceLaws(String groupId, ForceLaws forceLaws) {
        if (!groups.containsKey(groupId)) {
            throw new IllegalArgumentException("No group found with id");
        }
        groups.get(groupId).setForceLaws(forceLaws);
    }

    public JSONObject getState() {
        JSONObject stateJson = new JSONObject();
        stateJson.put("time", currentTime);
        List<JSONObject> groupJsons = new ArrayList<>();
        for (String groupId : groups.keySet()) {
            BodiesGroup group = groups.get(groupId);
            groupJsons.add(group.getState());
        }
        stateJson.put("groups", new JSONArray(groupJsons));
        return stateJson;
    }

    @Override
    public String toString() {
        return getState().toString();
    }
}
