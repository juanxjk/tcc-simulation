package ufersa.tcc.simulation.engine;

import processing.core.PApplet;
import ufersa.tcc.simulation.Script;
import ufersa.tcc.simulation.Statement;

public class Rocket {
	private Body b;
	private Script script;
	private PApplet screen;

	public Rocket(PApplet screen, float x, float y, float mass, float vx, float vy) {
		this.screen = screen;
		this.b = new Body(screen, x, y, mass, vx, vy);
		this.script = new Script(screen);
	}

	public void interact(Body b, float t) {
		this.b.interact(b, t);
	}

	public void draw() {
		b.draw();
	}

	public void addStatement(Statement s) {
		this.script.addStatement(s.ti, s.tf, s.dX, s.dY, s.dV, s.dV_);
	}

	public void simulate(World w, float dt) {
		for (Body body : w.getBodies()) {
			this.b.interact(body, dt);
		}
		this.script.process(this.b, dt);
		this.b.draw();
	}
}