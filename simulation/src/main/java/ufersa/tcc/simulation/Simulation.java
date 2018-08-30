package ufersa.tcc.simulation;

import static java.lang.Math.pow;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import ufersa.tcc.simulation.engine.Body;
import ufersa.tcc.simulation.engine.Rocket;
import ufersa.tcc.simulation.engine.Satellite;
import ufersa.tcc.simulation.engine.World;
import ufersa.tcc.simulation.engine.time.Time;

public class Simulation extends PApplet {
	public static void main(String[] args) {
		PApplet.main("ufersa.tcc.simulation.Simulation");
	}

	// SETTINGS = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	public void settings() {
		size(800, 600);
	}

	public static double screenScale = 7.75e-7; // Escala da posição dos objetos
	public static double screenObjectScale = 1e-5; // Escala dos objetos

	static Time time;

	private boolean pause = false;

	Body earth;
	private Satellite moon;
	private Rocket spacecraft;
	private World world;

	// SETUP = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	public void setup() {
		double dt = 1f; // [s] intervalo de tempo para cada iteração
		time = new Time(dt);
		PApplet screen = this;

		// --- TERRA ---
		double earth_center_x = 0 * 1e3; // [km]->[m] Posição em referência ao centro da Terra
		double earth_center_y = 0 * 1e3; // [km]->[m] Posição em referência ao centro da Terra
		double earth_radiusLengh = 6_371 * 1e3; // [km]->[m] Raio da Terra
		double earth_mass = 5.972e24; // [kg] Massa da Terra
		double earth_vx = 0; // [m/s] Velocidade tangencial da Terra em X
		double earth_vy = 0; // [m/s] Velocidade tangencial da Terra em Y
		earth = new Body(screen, earth_center_x, earth_center_y, earth_radiusLengh, earth_mass, earth_vx, earth_vy);
		earth.setName("Terra");

		// --- LUA ---
		double moon_distanceToEarth = 384_000 * 1e3; // [km]->[m] Distância da Lua em referência à Terra
		double moon_x = earth.x + earth_radiusLengh + moon_distanceToEarth; // [m] Posição em referência ao centro da
																			// Terra
		double moon_y = earth.y; // [m] Posição em referência ao centro da Terra
		double moon_radiusSize = 1_737 * 1e3; // [km]->[m] Raio da Lua
		double moon_mass = 7.349e22; // [kg] Massa da Lua
		double moon_vx = 0; // [m/s] Velocidade tangencial da Lua em X
		double moon_vy = Math.sqrt((Body.G * earth_mass) / moon_distanceToEarth); // [m/s] Velocidade tangencial da Lua
																					// em Y
		double angular_speed = 2 * PI / (27.322 * 24 * 60 * 60); // [rad/s] 27,3 horas período

		moon = new Satellite(earth, new Body(screen, moon_x, moon_y, moon_radiusSize, moon_mass, moon_vx, moon_vy),
				moon_distanceToEarth, angular_speed);
		moon.setName("Lua");

		// --- FOGUETE ---
		//double spacecraft_radiusDistance = 35786 * 1e3; // [km]->[m] Orbita geoestacionária
		double spacecraft_x = 42_164 * 1e3;/*earth_center_x + earth_radiusLengh + spacecraft_radiusDistance; // [km]->[m] Distância da
																								// Saturn V em
																								// referência à Terra*/
		double spacecraft_y = earth_center_y; // [m]
		double spacecraft_radius = 1000 * 1e3; // [km]->[m] Tamanho do foguete (ignorando o escalonamento de tela)
		double spacecraft_mass = 2.97e6; // [kg] Massa da Saturn V
		double spacecraft_vx = 0; // [m/s] Velocidade tangencial em X
		double spacecraft_vy = -3074.6; // [m/s] Velocidade tangencial em Y
		spacecraft = new Rocket(new Body(screen, spacecraft_x, spacecraft_y, spacecraft_radius, spacecraft_mass,
				spacecraft_vx, spacecraft_vy));
		spacecraft.setName("Foguete");
		double r1 = earth.calcDistance(spacecraft);
		double r2 = earth.calcDistance(moon);
		double deltaV1 = Math.sqrt(Body.G * earth_mass / r1) * (Math.sqrt(2.0 * r2 / (r1 + r2)) - 1.0); // [m/s]
		double deltaV2 = Math.sqrt(Body.G * earth_mass / r2) * (1.0 - Math.sqrt(2.0 * r1 / (r1 + r2))); // [m/s]
		double tf = Math.PI * Math.sqrt(Math.pow((r1 + r2) / 2, 3) / (Body.G * earth_mass)); // [s]
		double moon_period = 27.322 * 24 * 60 * 60;
		double spacecraft_period = 1 * 24 * 60 * 60;
		double duration = 300;
		double intecept_time = (tf + moon_period / 2) / (moon_period / spacecraft_period - 1);
		spacecraft.addStatement(new Statement(intecept_time - duration, intecept_time, 0f, 0f,
				spacecraft_mass * deltaV1 / duration, 0f));
		// spacecraft.addStatement(new Statement(intecept_time - duration + tf,
		// intecept_time + tf, 0f, 0f,
		// spacecraft_mass * deltaV2 / duration, 0f));
		// spacecraft.addStatement(
		// new Statement(507166 - duration, 507166, -2.7*spacecraft_mass * deltaV2 /
		// duration, 0f, 0f, 0f));
		spacecraft.addStatement(
				new Statement(507166, 507166 + duration, -0.47 * spacecraft_mass * deltaV2 / duration, 0f, 0f, 0f));

		world = new World();
		// world.addBody(spacecraft);
		world.addBody(earth);
		world.addBody(moon);
	}

	// DRAW = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
	public void draw() {
		background(0);
		text("Total time: " + time.getTotalTime(), 50, 20);
		text("Duration time: " + time.toString(), 50, 35);
		text("dt: " + Time.formatHMSM(time.dt) + " [s]", 50, 50);
		if (!pause) {
			for (int i = 0; i < 1e3; i++) {
				double value = Math.sqrt(Math.pow(spacecraft.x, 2) + Math.pow(spacecraft.y, 2));
				if (value > spacecraft.maxDistance) {
					spacecraft.maxDistance = value;
					spacecraft.maxDistanceTime = time.getTotalTime();
					spacecraft.maxDistanceVelX = spacecraft.vx;
					spacecraft.maxDistanceVelY = spacecraft.vy;
					spacecraft.maxDistance_Moon_Rocket_VectorX = spacecraft.x - moon.x;
					spacecraft.maxDistance_Moon_Rocket_VectorY = spacecraft.y - moon.y;
				}
				spacecraft.simulate(world, time.dt);
				moon.simulate(time.dt);
				time.step();
			}
		}
		world.draw();
		spacecraft.draw();
		showInfo();
	}

	private boolean showHelpInfo = false;
	private boolean showEarthInfo = false;
	private boolean showRocketInfo = false;
	private boolean showMoonInfo = false;

	private void showInfo() {

		if (showEarthInfo) {
			earth.showInfo(world, 10f, 50f);
		}
		if (showRocketInfo) {

			spacecraft.showInfo(world, 10f, height / 2);
		}
		if (showMoonInfo) {
			moon.showInfo(world, width / 2, 50f);
		}

		if (showHelpInfo) {

			showHelp();
		}
	}

	private void showHelp() {
		List<String> items = new ArrayList<String>();
		double dString = 15;
		items.add("[p] - Pausar");
		items.add("[-] [+] Alterar velocidade do tempo (alterar dt)");
		items.add("[7] [8] Alterar velocidade do foguete");
		int index = 1;
		for (String s : items) {
			text(s, (float) width - 300f, (float) (height - dString * index++));
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

		if (key == '7') {
			spacecraft.setPercentageVelocity(90);
		}

		if (key == '8') {
			spacecraft.setPercentageVelocity(110);
		}

		if (key == '9') {
			screenObjectScale *= 1.1;
		}

		if (key == '6') {
			screenObjectScale *= .9;
		}

		if (key == '-') {
			Time.dt = Time.dt * 0.9;
		}

		if (key == '+') {
			Time.dt = Time.dt * 1.1;
		}

		if (key == 'p') {
			this.pause = !this.pause;
		}

		if (key == 'h') {
			showHelpInfo = !showHelpInfo;
		}

		if (key == 'r')
			setup();
	}

}
