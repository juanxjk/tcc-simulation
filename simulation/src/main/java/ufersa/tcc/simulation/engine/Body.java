package ufersa.tcc.simulation.engine;

import processing.core.PApplet;
import java.lang.Math;

public class Body {
	public double x, y, mass;
	public double vx, vy;
	private PApplet screen;
	public final static double G = 750;

	public Body(PApplet screen, double x, double y, double mass, double vx, double vy) {
		this.screen = screen;
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.vx = vx;
		this.vy = vy;
	}

	protected Body(Body body) {
		this(body.screen, body.x, body.y, body.mass, body.vx, body.vy);

	}

	public void interact(Body b, double t) {
		final double deltaX = b.x - this.x;
		final double deltaY = b.y - this.y;
		final double dist2 = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
		final double dist = Math.sqrt(dist2);
		final double force = G * b.mass * this.mass / dist2;
		final double accelX = force * deltaX / (dist * mass);
		final double accelY = force * deltaY / (dist * mass);
		this.vx += accelX * t;
		this.vy += accelY * t;
		this.x += vx * t;
		this.y += vy * t;
	}

	public void draw() {
		screen.fill(255, 255, 0, 200);
		screen.ellipse((float) this.x, (float) this.y, (float) this.mass, (float) this.mass);
	}

	@Override
	public String toString() {
		return "VelX" + this.vx;
	}
}