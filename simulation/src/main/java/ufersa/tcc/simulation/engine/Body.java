package ufersa.tcc.simulation.engine;

import processing.core.PApplet;

public class Body {
	public float x, y, mass;
	public float vx, vy;
	private PApplet screen;
	public final static float G = 750;

	public Body(PApplet screen, float x, float y, float mass, float vx, float vy) {
		this.screen = screen;
		this.x = x;
		this.y = y;
		this.mass = mass;
		this.vx = vx;
		this.vy = vy;
	}

	public void interact(Body b, float t) {
		final float deltaX = b.x - this.x;
		final float deltaY = b.y - this.y;
		final float dist2 = screen.pow(deltaX, 2) + screen.pow(deltaY, 2);
		final float dist = screen.sqrt(dist2);
		final float force = G * b.mass * this.mass / dist2;
		final float accelX = force * deltaX / (dist * mass);
		final float accelY = force * deltaY / (dist * mass);
		this.vx += accelX * t;
		this.vy += accelY * t;
		this.x += vx * t;
		this.y += vy * t;
	}

	public void draw() {
		screen.fill(255, 255, 0, 200);
		screen.ellipse(this.x, this.y, this.mass, this.mass);
	}

	@Override
	public String toString() {
		return "VelX" + this.vx;
	}
}