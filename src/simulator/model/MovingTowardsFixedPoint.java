package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {

    private Vector2D c;
    private double g;

    public MovingTowardsFixedPoint(Vector2D c, double g) {
        if (c == null) {
            throw new IllegalArgumentException("c cannot be 0");
        }
        if (g <= 0) {
            throw new IllegalArgumentException("g must be positive");
        }
        this.c = c;
        this.g = g;
    }

    @Override
    public void apply(List<Body> bodies) {
        for (Body body : bodies) {
            Vector2D direction = c.minus(body.getPosition()).direction();
            Vector2D force = direction.scale(g * body.getMass());
            body.addForce(force);
        }
    }
}
