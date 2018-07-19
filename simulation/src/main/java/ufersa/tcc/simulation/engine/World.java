package ufersa.tcc.simulation.engine;

import java.util.ArrayList;
import java.util.List;

public class World {

	private ArrayList<Body> bodies = new ArrayList<Body>();

	public void addBody(Body b) {
		bodies.add(b);
	}

	public List<Body> getBodies() {
		return bodies;
	}
}