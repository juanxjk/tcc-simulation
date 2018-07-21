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

	public void setup() {
		t = 0;
		PApplet screen = this;
		// --- TERRA ---
		double earth_center_x = 0; // [km] Posição em referência ao centro da Terra
		double earth_center_y = 0; // [km] Posição em referência ao centro da Terra
		double earth_radiusLengh = 6371; // [km] Raio da Terra
		double earth_mass = 5.972e24; // [kg] Massa da Terra
		double earth_vx = 0; // [m/s] Velocidade tangencial da Terra em X
		double earth_vy = 0; // [m/s] Velocidade tangencial da Terra em Y
		earth = new Body(screen, earth_center_x, earth_center_y, earth_radiusLengh, earth_mass, earth_vx, earth_vy);
		earth.setName("Terra");

		// --- LUA ---
		double moon_distanceToEarth = 3.844e5; // [km] Distância da Lua em referência à Terra
		double moon_x = earth.x + earth_radiusLengh + moon_distanceToEarth; // [km] Posição em referência ao centro da
																			// Terra
		double moon_y = earth.y; // [km] Posição em referência ao centro da Terra
		double moon_radiusSize = 1737; // [km] Raio da Lua
		double moon_mass = 7.349e22; // [kg] Massa da Lua
		double moon_vx = 0; // [m/s] Velocidade tangencial da Lua em X
		double moon_vy = 3594.24; // [m/s] Velocidade tangencial da Lua em Y
		Body moonBody = new Body(screen, earth.x + moon_distanceToEarth, moon_y, moon_radiusSize, moon_mass, moon_vx,
				moon_vy);
		moon = new Satellite(earth, moonBody, moon_distanceToEarth, 6.3);
		moon.setName("Lua");

		// --- FOGUETE ---
		double spacecraft_radiusDistance = 35786; // Distância da Saturn V em referência à Terra - km
		double spacecraft_x = earth_center_x + earth_radiusLengh + spacecraft_radiusDistance;
		double spacecraft_y = earth_center_y;
		double spacecraft_radiusSize = 10 / screenObjectScale; // Tamanho do foguete (ignorando o escalonamento de tela)
		double spacecraft_mass = 2.97e6; // [kg] Massa da Saturn V
		double spacecraft_vx = 0; // [m/s] Velocidade tangencial em X
		double spacecraft_vy = 9.5; // [m/s] Velocidade tangencial em Y
		spacecraft = new Rocket(new Body(screen, spacecraft_x, spacecraft_y, spacecraft_radiusSize, spacecraft_mass,
				spacecraft_vx, spacecraft_vy));
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
