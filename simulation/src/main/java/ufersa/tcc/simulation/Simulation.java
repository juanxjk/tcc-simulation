package ufersa.tcc.simulation;

import processing.core.PApplet;
import ufersa.tcc.simulation.engine.Body;
import ufersa.tcc.simulation.engine.Rocket;
import ufersa.tcc.simulation.engine.World;

public class Simulation extends PApplet {
	public static void main(String[] args) {

		PApplet.main("ufersa.tcc.simulation.Simulation");
	}

	public void settings() {
		size(800, 600);

	}

	private float dt = 0.1f;
	private Body b;
	private Rocket r1;
	private World world1;

	public void setup() {
		b = new Body(this, width / 2, height / 2, 100, 0, 0);
		r1 = new Rocket(new Body(this, width / 2, height / 2 + 200, 10, 20, 0));
		world1 = new World();
		world1.addBody(this, b);
	}

	static float t = 0.0f;

	public void draw() {
		background(0);
		text(t, 50, 50);
		b.draw();
		// r1.interact(b, dt);
		// r1.draw();
		r1.addStatement(new Statement(10f, 11f, 0f, 0f, 0.1f, 0f));
		r1.simulate(world1, dt);
		t += dt;
	}
}
