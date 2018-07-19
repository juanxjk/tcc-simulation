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
	private Body earth;
//	private Body moon;
	private Rocket r1;
	private World world1;

	public void setup() {
		earth = new Body(this, width / 2, height / 2, 100, 0, 0);
		earth.setName("Earth");
//		moon = new Body(this, width / 2 + 250, height / 2, 55, 20, 50);
//		moon.setName("Moon");
		r1 = new Rocket(new Body(this, width / 2, height / 2 + 200, 10, 20, 0));
		r1.addStatement(new Statement(74f, 76f, 0f, 0f, 10f, 0f));
		world1 = new World();
		world1.addBody(earth);
//		world1.addBody(moon);
	}

	static float t = 0.0f;

	public void draw() {
		background(0);
		text(t, 50, 50);
		earth.draw();
//		moon.draw();
		// r1.interact(b, dt);
		// r1.draw();
		r1.simulate(world1, dt);
		t += dt;
		showInfo();
	}

	private boolean showEarthInfo = false;
	private boolean showRocketInfo = false;
	private boolean showMoonInfo = false;

	private void showInfo() {
		// TODO Auto-generated method stub
		if (showEarthInfo) {
			earth.showInfo(world1, 10f, 10f);
		}
		if (showRocketInfo) {

			r1.showInfo(world1, 10f, height / 2);
		}
		if (showMoonInfo) {

		}

	}

	@Override
	public void keyTyped() {
		// TODO Auto-generated method stub
		super.keyTyped();
		if (key == '1') {
			showEarthInfo = !showEarthInfo;
			System.out.println("Info: Terra");

		}
		if (key == '2') {
			System.out.println("Info: Foguete");
			showRocketInfo = !showRocketInfo;

		}
		if (key == '3') {
			System.out.println("Info: Lua");
		}

		if (key == 'r')
			setup();
	}
}
