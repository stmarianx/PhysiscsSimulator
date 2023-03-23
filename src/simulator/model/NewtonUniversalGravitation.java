package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

    private final double G;

    public NewtonUniversalGravitation(double G) {
        if (G <= 0) {
            throw new IllegalArgumentException("G must be positive");
        }
        this.G = G;
    }

    @Override
    /*
     * It won't pass the Junit test with very small precision difference between the expected result
     * and the actual one.
     * One way to increase the accuracy of this method is to use the Barnes-Hut algorithm.
     */
    public void apply(List<Body> bodies) {
    	// for(Body b : bodies)
        for (int i = 0; i < bodies.size(); i++) {
            Body b1 = bodies.get(i);
            Vector2D f = new Vector2D();
            for (int j = 0; j < bodies.size(); j++) {
                if (i != j) {
                    Body b2 = bodies.get(j);
                    Vector2D d = b2.getPosition().minus(b1.getPosition());
                    double distSquared = d.magnitude() * d.magnitude();
                    if (distSquared > 0) {
                        f = f.plus(d.direction().scale(G * b1.getMass() * b2.getMass() / distSquared));
                    }
                }
            }
            b1.addForce(f);
        }
    }
}
