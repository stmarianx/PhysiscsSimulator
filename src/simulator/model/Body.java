package simulator.model;

import simulator.misc.Vector2D;

import org.json.JSONObject;

public abstract class Body {
	protected String id;
	protected String gid;
	protected Vector2D v;
	protected Vector2D f;
	protected Vector2D p;
	protected double m;

	public Body(String id, String gid, Vector2D p, Vector2D v, double m) {
		if (id == null || gid == null || v == null || p == null) {
			throw new IllegalArgumentException("Id cannot be empty");
		}
		if (id.trim().length() == 0 || gid.trim().length() == 0) {
			throw new IllegalArgumentException("Id must have at least one char that is not white space");
		}
		if (m <= 0) {
			throw new IllegalArgumentException("mass cannot be negative");
		}
		this.id = id;
		this.gid = gid;
		this.v = new Vector2D(v.getX(), v.getY());
		this.p = new Vector2D(p.getX(), p.getY());
		this.m = m;
		this.f = new Vector2D(0, 0);
	}

	public String getId() {
		return id;
	}

	public String getgId() {
		return gid;
	}

	public Vector2D getVelocity() {
		return v;
	}

	public Vector2D getPosition() {
		return p;
	}

	public Vector2D getForce() {
		return f;
	}

	public double getMass() {
		return m;
	}

	public void addForce(Vector2D force) {
		f = f.plus(force);
	}

	public void resetForce() {
		f = new Vector2D(0, 0);
	}

	public abstract void advance(double dt);

	public JSONObject getState() {
		JSONObject state = new JSONObject();
		state.put("id", this.id);
		state.put("m", this.m);
		state.put("p", this.p.asJSONArray());
		state.put("v", this.v.asJSONArray());
		state.put("f", this.f.asJSONArray());
		return state;
	}

	public String toString() {
		return getState().toString();
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof Body)) {
			return false;
		}

		Body other = (Body) o;

		return (this.getId() == other.getId());
	}

}
