package ufersa.tcc.simulation;

import processing.core.PApplet;
import ufersa.tcc.simulation.engine.Body;
import ufersa.tcc.simulation.engine.Rocket;
import ufersa.tcc.simulation.engine.Satellite;
import ufersa.tcc.simulation.engine.World;

public class Simulation extends PApplet {
	public static void main(String[] args) {

		PApplet.main("ufersa.tcc.simulation.Simulation");
	}

	public void settings() {
		size(800, 600);

	}

	private float dt = 0.1f;
	private Body earth;
	private Satellite moon;
	private Rocket spacecraft;
	private World world;

	double initialRocketVx = 9.5;
	double initialRocketVy = 0;

	public void setup() {
		t = 0;

		// TERRA
		earth = new Body(this, width / 2, height / 2, 100, 0, 0);
		earth.setName("Terra");

		// LUA
		Body moonBody = new Body(this, width / 2 + 250, height / 2, 25, 20, 50);
		moon = new Satellite(earth, moonBody, 2.75e2, 1e-1);
		moon.setName("Lua");

		// FOGUETE
		spacecraft = new Rocket(new Body(this, width / 2, height / 2 + 200, 10, initialRocketVx, initialRocketVy));
		spacecraft.setName("Foguete");
		spacecraft.addStatement(new Statement(74f, 76f, 0f, 0f, 10f, 0f));

		world = new World();
		// world.addBody(spacecraft);
		world.addBody(earth);
		world.addBody(moon);
	}

	static float t = 0.0f;

	public void draw() {
		background(0);
		text(t, 50, 50);
		world.draw();
		world.simulate(dt);
		// r1.interact(b, dt);
		moon.simulate(dt);
		spacecraft.simulate(world, dt);
		spacecraft.draw();
		t += dt;
		showInfo();
	}

	private boolean showEarthInfo = false;
	private boolean showRocketInfo = false;
	private boolean showMoonInfo = false;

	private void showInfo() {
		// TODO Auto-generated method stub
		if (showEarthInfo) {
			earth.showInfo(world, 10f, 10f);
		}
		if (showRocketInfo) {

			spacecraft.showInfo(world, 10f, height / 2);
		}
		if (showMoonInfo) {
			moon.showInfo(world, width / 2, 10f);
		}

	}

	@Override
	public void keyTyped() {
		// TODO Auto-generated method stub
		super.keyTyped();
		if (key == '1') {
			showEarthInfo = !showEarthInfo;

		}
		if (key == '2') {
			showRocketInfo = !showRocketInfo;

		}
		if (key == '3') {
			showMoonInfo = !showMoonInfo;
		}

		if (key == '+') {
			spacecraft.setPercentageVelocity(110);
		}

		if (key == '-') {
			spacecraft.setPercentageVelocity(90);
		}

		if (key == 'r')
			setup();
	}

}
