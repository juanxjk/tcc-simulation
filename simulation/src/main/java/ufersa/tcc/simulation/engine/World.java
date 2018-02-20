package ufersa.tcc.simulation.engine;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class World {

	private ArrayList<Body> bodies = new ArrayList<Body>();

	public void addBody(PApplet screen, Body b) {
		bodies.add(new Body(screen, b.x, b.y, b.mass, b.vx, b.vy));
	}

	public List<Body> getBodies() {
		return bodies;
	}
}