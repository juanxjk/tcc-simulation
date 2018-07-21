package ufersa.tcc.simulation.engine;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import ufersa.tcc.simulation.Simulation;

public class Body {
	public double x, y; // [km]
	public double mass; // [kg]
	public double vx, vy; // [km/s]
	public double radius = 10; // [km] Tamanho padrão do objeto
	private PApplet screen;
	private int[] rgb = { 255, 255, 255 };
	private String bodyName = "Não identificado";

	public final static double G = 6.67408e-11; // m^3 kg^-1 s^-2

	/**
	 * @param screen
	 *            Tela de display.
	 * @param x
	 *            posição x.
	 * @param y
	 *            posição y.
	 * @param radius
	 *            Tamanho do raio do circulo.
	 */
	public Body(PApplet screen, double x, double y, double radius, double mass, double vx, double vy) {
		this.screen = screen;
		this.x = x; // [km]
		this.y = y; // [km]
		this.radius = radius; // [km]
		this.mass = mass; // [kg]
		this.vx = vx; // [m/s]
		this.vy = vy; // [m/s]
	}

	protected Body(Body body) {
		this(body.screen, body.x, body.y, body.radius, body.mass, body.vx, body.vy);

	}

	protected double calcDistance(Body b) {
		return sqrt(pow(this.x - b.x, 2) + pow(this.y - b.y, 2));
	}

	public void interact(Body b, double t) {
		final double deltaX = (b.x - this.x) * 1e3; // [m]
		final double deltaY = (b.y - this.y) * 1e3; // [m]
		final double dist2 = pow(deltaX, 2) + pow(deltaY, 2); // [m^2]
		final double dist = sqrt(dist2); // [m]
		final double force = G * b.mass * this.mass / dist2; // [kg] * [m/s^2]
		final double accelX = force * deltaX / (dist * this.mass); // [m/s^2]
		final double accelY = force * deltaY / (dist * this.mass); // [m/s^2]
		this.vx += accelX * t; // [m/s]
		this.vy += accelY * t; // [m/s]
		this.x += vx * 1e-3 * t; // [km]
		this.y += vy * 1e-3 * t; // [km]
	}

	public void draw() {
		screen.translate(screen.width / 2, screen.height / 2);
		screen.fill(rgb[0], rgb[1], rgb[2], 200);
		screen.ellipse((float) (Simulation.screenScaleX * this.x), (float) (Simulation.screenScaleY * this.y),
				(float) (this.radius * Simulation.screenObjectScale),
				(float) (this.radius * Simulation.screenObjectScale));
		screen.translate(-screen.width / 2, -screen.height / 2);
	}

	@Override
	public String toString() {
		return "VelX" + this.vx;
	}

	public void showInfo(World w, double x, double y) {
		int line;
		List<String> strings = new ArrayList<>();
		strings.add(this.getName() + " Info: ");
		strings.add(String.format("Vel (%f, %f)", this.vx, this.vy));
		strings.add(String.format("Vel: %f", Math.sqrt(Math.pow(this.vx, 2) + Math.pow(this.vy, 2))));
		for (Body b : w.getBodies()) {
			if (!this.equals(b)) {
				strings.add(String.format("Distance to %s: (%f) ", b.getName(), this.calcDistance(b)));
			}
		}
		String t = "";
		for (String s : strings) {
			t += "\n" + s;
		}
		screen.text(t, (float) x, (float) y);
	}

	public double getSpeed() {
		return sqrt(pow(this.vx, 2) + pow(this.vy, 2));
	}

	public void setName(String name) {
		this.bodyName = name;
	}

	public String getName() {
		return this.bodyName;
	}

	public void setPercentageVelocity(double percentage) {
		double _percent = percentage / 100;
		this.vx *= _percent;
		this.vy *= _percent;
	}

}