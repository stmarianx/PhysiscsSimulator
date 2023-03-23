package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {
    public MovingBody(String id, String gid, Vector2D p, Vector2D v, double m) {
        super(id, gid, p, v, m);
    }

    public void advance(double dt) {
    	Vector2D a;
    	if (m == 0) {
    	    a = new Vector2D(0, 0);
    	} else {
    	    a = f.scale(1 / m);
    	}

        Vector2D newP = p.plus(v.scale(dt)).plus(a.scale(0.5 * dt * dt));
        Vector2D newV = v.plus(a.scale(dt));
        p = newP;
        v = newV;
    }
}
