package simulator.model;

import simulator.misc.Vector2D;

public class StationaryBody extends Body {

    public StationaryBody(String id, String gid, Vector2D p, double m) {
        super(id, gid, p, new Vector2D(0, 0), m);
    }

    public void advance(double dt) {
        // do nothing, as the body is stationary
    }
}
