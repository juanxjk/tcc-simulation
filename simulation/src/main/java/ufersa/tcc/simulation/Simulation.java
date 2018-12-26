package ufersa.tcc.simulation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.markers.Marker;

import processing.core.PApplet;
import ufersa.tcc.plot.Plot2d;
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
		size(800, 700);
	}

	private boolean recordVideo = false;
	private boolean enable_moon = false;
	private boolean enable_moonDraw = false;
	private boolean enable_dv1_hohmannn = true;
	private boolean enable_dv2_hohmannn = true;
	private boolean enable_dv2 = false;
	private boolean enable_changeTimeThrottle = false;
	public static double velocity = 1e3;
	public static double screenScale = 9e-7; // Escala da posição dos objetos
	public static double screenObjectScale = 1e-5; // Escala dos objetos

	public static Time time;

	Body earth;
	private Satellite moon;
	private Rocket spacecraft;
	private World world;

	// =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
	// SETUP
	// =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
	public void setup() {
		double dt = 1f; // [s] intervalo de tempo para cada iteração
		time = new Time(dt);
		PApplet screen = this;

		// --- TERRA ---
		double earth_center_x = 0; // [km]->[m] Posição em referência ao centro da Terra
		double earth_center_y = 0; // [km]->[m] Posição em referência ao centro da Terra
		double earth_radiusLengh = 6_371 * 1e3; // [km]->[m] Raio da Terra
		double earth_mass = 5.9723e24; // [kg] Massa da Terra
		double earth_vx = 0; // [m/s] Velocidade tangencial da Terra em X
		double earth_vy = 0; // [m/s] Velocidade tangencial da Terra em Y
		earth = new Body(screen, earth_center_x, earth_center_y, earth_radiusLengh, earth_mass, earth_vx, earth_vy);
		earth.setName("Terra");

		// --- LUA ---
		double moon_OrbitalPeriod = 27.3217 * 24 * 60 * 60; // [s]
		// [km]->[m] Distância da Lua em referência à Terra
		double moon_distanceToEarth = Body.calcOrbitalRadiusFrom(moon_OrbitalPeriod, earth); // [m]
		double moon_x = earth.x + moon_distanceToEarth, moon_y = earth.y; // [m] Posição inicial
		double moon_radiusSize = 1_736 * 1e3; // [km]->[m] Raio da Lua
		double moon_mass = 7.346e22; // [kg] Massa da Lua
		double moon_vx = 0; // [m/s] Velocidade tangencial da Lua em X
		double moon_vy = Math.sqrt((Body.G * earth_mass) / moon_distanceToEarth); // [m/s] Velocidade tangencial da Lua
																					// em Y
		double angular_speed = 2 * PI / moon_OrbitalPeriod; // [rad/s] 27,3 horas período

		moon = new Satellite(earth, new Body(screen, moon_x, moon_y, moon_radiusSize, moon_mass, moon_vx, moon_vy),
				moon_distanceToEarth, angular_speed);
		moon.setName("Lua");

		// --- FOGUETE ---
		// double spacecraft_radiusDistance = 35786 * 1e3; // [km]->[m] Orbita
		// geoestacionária
		double spacecraft_OrbitalPeriod = 24 * 60 * 60;
		double spacecraft_x = Body.calcOrbitalRadiusFrom(spacecraft_OrbitalPeriod,
				earth); /*
						 * earth_center_x + earth_radiusLengh + spacecraft_radiusDistance; // [km]->[m]
						 * Distância da // Saturn V em // referência à Terra
						 */
		double spacecraft_y = earth_center_y; // [m]
		double spacecraft_radius = 1000 * 1e3; // [km]->[m] Tamanho do foguete (ignorando o escalonamento de tela)
		double spacecraft_mass = 2.97e5; // [kg] Massa da Saturn V
		double spacecraft_vx = 0; // [m/s] Velocidade tangencial em X
		// double spacecraft_vy = -3074.6; // [m/s] Velocidade tangencial em Y
		double spacecraft_vy = -Math.sqrt(Body.G * earth_mass / spacecraft_x); // [m/s] Velocidade tangencial em Y
		spacecraft = new Rocket(new Body(screen, spacecraft_x, spacecraft_y, spacecraft_radius, spacecraft_mass,
				spacecraft_vx, spacecraft_vy));
		spacecraft.setName("Foguete");

		double r1 = earth.calcDistance(spacecraft);
		double r2 = moon.radius_distance;
		double deltaV1 = Math.sqrt(Body.G * earth_mass / r1) * (Math.sqrt(2.0 * r2 / (r1 + r2)) - 1.0); // [m/s]
		double deltaV2 = Math.sqrt(Body.G * earth_mass / r2) * (1.0 - Math.sqrt(2.0 * r1 / (r1 + r2))); // [m/s]
		double tf = Math.PI * Math.sqrt(Math.pow((r1 + r2) / 2, 3) / (Body.G * earth_mass)); // [s]
		double moon_period = 27.322 * 24 * 60 * 60;
		double duration = 4 * 60;
		double intecept_time;
		if (!enable_changeTimeThrottle) {
			intecept_time = (tf + moon_period / 2) / (moon_period / spacecraft_OrbitalPeriod - 1);
		} else {
			intecept_time = 10 * 24 * 60 * 60;
		}

		// = = = = = = = = = DELTA V1 (com interceptação na Lua) = = = = = = = = =
		if (enable_dv1_hohmannn)
			spacecraft.addStatement(new Statement(intecept_time - duration, intecept_time, 0f, 0f,
					spacecraft_mass * deltaV1 / duration, 0f));

		// = = = = = = = = = DELTA V2 (SEM LUA) = = = = = = = = =
		if (enable_dv2_hohmannn)
			spacecraft.addStatement(new Statement(intecept_time - duration + tf, intecept_time + tf, 0f, 0f,
					spacecraft_mass * deltaV2 / duration, 0f));

		// = = = = = = = = = DELTA V2 (COM LUA) = = = = = = = = =
		double duration2 = 2 * 60;
		// double timeD2 = 416223;
		double timeD2 = 474657;
		// double dvx = -823.223;
		double dvx = -588.491;
		// double dvy = 400.270;
		double dvy = -355.811;
		if (enable_dv2 && enable_moon)
			spacecraft.addStatement(new Statement(timeD2 - duration2, timeD2, dvx * spacecraft_mass / (duration2),
					dvy * spacecraft_mass / (duration2), 0f, 0f));

		world = new World();
		world.addBody(earth);
		if (enable_moon)
			world.addBody(moon);
	}

	List<Double[]> data_spacecraft_earth_Distance = new ArrayList<Double[]>();
	List<Double[]> data_spacecraft_speed = new ArrayList<Double[]>();
	List<Double[]> data_spacecraft_moon_Distance = new ArrayList<Double[]>();
	List<Double[]> data_moon_earth_Distance = new ArrayList<Double[]>();

	double deltaV2X;
	double deltaV2Y;

	// =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
	// DRAW
	// =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
	public void draw() {
		background(255);
		fill(125);
		text("Total time: " + time.getTotalTime(), 50, 20);
		text("Duration time: " + time.toString(), 50, 35);
		text("dt: " + Time.formatHMSM(time.dt) + " [s]", 50, 50);
		if (showHelpInfo) {
			text("Delta V1 H: " + enable_dv1_hohmannn, 50, 65);
			text("Delta V2 H: " + enable_dv2_hohmannn, 50, 80);
			text("Delta V2: " + enable_dv2, 50, 95);
			text("Moon: " + enable_moon, 50, 110);
		}
		text(looping ? "" : "Pausado", 50, 125);
		if (looping) {
			for (int i = 0; i < velocity; i++) {
				// Máxima distancia do foguete-terra
				updateMaxDistance(spacecraft, earth, time);

				// Mínima distancia do foguete-Lua
				updateMinMoonDistance(spacecraft, moon, time);

				// Atualiza informações para o acionamento do V2
				updateV2(spacecraft, moon, time);

				spacecraft.simulate(world, time.dt);
				moon.simulate(time.dt);
				time.step();
			}
		}
		if (time.getTotalTime() < 60 * 60 * 24 * 60) {
			this.getPlotData();
		}

		world.draw();
		spacecraft.draw();
		if (enable_moon || enable_moonDraw)
			moon.draw();
		showInfo();
		if (recordVideo) {
			saveFrame("video/frame####.png");
		}
	}

	private void getPlotData() {
		data_spacecraft_earth_Distance
				.add(new Double[] { time.getTotalTime() / (60 * 60 * 24), spacecraft.calcDistance(earth) * 1e-3 });
		data_spacecraft_speed.add(new Double[] { time.getTotalTime() / (60 * 60 * 24), spacecraft.getSpeed() });
		data_spacecraft_moon_Distance
				.add(new Double[] { time.getTotalTime() / (60 * 60 * 24), spacecraft.calcDistance(moon) * 1e-3 });
		data_moon_earth_Distance
				.add(new Double[] { time.getTotalTime() / (60 * 60 * 24), moon.calcDistance(earth) * 1e-3 });
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
		if (enable_moon)
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

		if (key == '-') {
			velocity *= 0.9;
		}

		if (key == '+') {
			velocity *= 1.1;
		}

		if (key == 'p') {
			if (!looping) {
				this.loop();
			} else {
				this.noLoop();
				this.draw();
			}
		}

		if (key == 's') {
			this.saveFrame("screenshots/" + LocalDate.now() + "_####.png");
		}
		if (key == 'd') {
			recordVideo = !recordVideo;
		}
		if (key == 'n') {
			plot();
		}

		if (key == 'h') {
			showHelpInfo = !showHelpInfo;
		}

		if (key == '8') {
			enable_dv1_hohmannn = !enable_dv1_hohmannn;
		}

		if (key == '9') {
			enable_dv2_hohmannn = !enable_dv2_hohmannn;
		}

		if (key == '0') {
			enable_dv2 = !enable_dv2;
		}

		if (key == 'l') {
			enable_moon = !enable_moon;
		}

		if (key == '.') {
			time.dt = -time.dt;
			spacecraft.minDeltaV2 = Double.MAX_VALUE;
			spacecraft.minMoonDistance = Double.MAX_VALUE;
		}

		if (key == '/') {
			screenScale *= .9;
		}
		if (key == '*') {
			screenScale *= 1.1;
		}

		if (key == 'r') {
			resetPlotData();
			setup();
		}
	}

	private void plot() {
		String str_isMoonActive;
		if (world.getBodies().contains(moon)) {
			str_isMoonActive = "com Lua";
		} else {
			str_isMoonActive = "sem Lua";
		}
		String str_impulse = "";
		if (!enable_dv1_hohmannn & !enable_dv2_hohmannn & !enable_dv2) {
			str_impulse = "geoestacionária";
		} else {
			str_impulse = "com ";
		}

		if (enable_dv1_hohmannn) {
			str_impulse += "primeiro";
		}

		if (enable_dv2 || enable_dv2_hohmannn) {
			str_impulse += " e segundo";
		}
		if (enable_dv1_hohmannn || enable_dv2_hohmannn || enable_dv2) {
			str_impulse += " impulso";
		}

		double[] xData = Plot2d.doubleArray(data_spacecraft_earth_Distance, 0);
		double[] yData = Plot2d.doubleArray(data_spacecraft_earth_Distance, 1);
		Plot2d plot1 = new Plot2d("Distância Foguete-Terra", 800, 400, "Tempo [dias]", "Distância [km]");
		XYChart chart1 = plot1.getChart();
//		 chart1.getStyler().setDecimalPattern("0");
		// chart1.getToolTips().addData(20*24*60*60, 350000, "asdasdasd");
		plot1.addSeries("Trajetória " + str_impulse + " (" + str_isMoonActive + ")", xData, yData);
//		 chart1.getStyler().setYAxisMin(0.0);
//		 chart1.getStyler().setYAxisMax(2 * 42_164.0);
		plot1.display();

		xData = Plot2d.doubleArray(data_spacecraft_speed, 0);
		yData = Plot2d.doubleArray(data_spacecraft_speed, 1);
		Plot2d plot2 = new Plot2d("Velocidade do Foguete", 800, 400, "Tempo [dias]", "Velocidade [m/s]");
		plot2.addSeries("Trajetória " + str_impulse + " (" + str_isMoonActive + ")", xData, yData);
		plot2.display();

		xData = Plot2d.doubleArray(data_spacecraft_moon_Distance, 0);
		yData = Plot2d.doubleArray(data_spacecraft_moon_Distance, 1);
		Plot2d plot3 = new Plot2d("Distância Foguete-Lua", 800, 400, "Tempo [dias]", "Distância [km]");
		plot3.addSeries("Trajetória " + str_impulse + " (" + str_isMoonActive + ")", xData, yData);
		// .getChart();
		plot3.display();

		// xData = Plot2d.doubleArray(data_moon_earth_Distance, 0);
		// yData = Plot2d.doubleArray(data_moon_earth_Distance, 1);
		// XYChart chart3 = new Plot2d("Distância Lua-Terra", "Tempo [dias]", xData,
		// "Distância [km]", yData)
		// .getChart();

		// Plot2d.displayChartMatrix(Arrays.asList(chart1));

	}

	private void resetPlotData() {
		data_spacecraft_earth_Distance = new ArrayList<Double[]>();
		data_spacecraft_speed = new ArrayList<Double[]>();
		data_spacecraft_moon_Distance = new ArrayList<Double[]>();
		data_moon_earth_Distance = new ArrayList<Double[]>();
	}

	public void updateV2(Body spacecraft, Satellite moon, Time time) {
		updateMinMoonDistance(spacecraft, moon, time);
		// Velocidade órbita circular em torno da lua
		double vo = Math.sqrt(Body.G * moon.mass / spacecraft.calcDistance(moon));
		double lf = spacecraft.calcDistance(moon); // Vetor: lua-foguete
		double lf_dir_x = (spacecraft.x - moon.x) / lf, lf_dir_y = (spacecraft.y - moon.y) / lf;
		double voX = -vo * lf_dir_y;
		double voY = vo * lf_dir_x;

		// Velocidade da lua
		double moon_r = moon.getDistanceFromCenter(); // Raio de órbita do satelite
		double vlX = -moon_r * moon.angular_vel * Math.sin(moon.angular_vel * time.getTotalTime());
		double vlY = moon_r * moon.angular_vel * Math.cos(moon.angular_vel * time.getTotalTime());
		double vfX = voX + vlX;
		double vfY = voY + vlY;
		spacecraft.deltaV2X = vfX - spacecraft.vx;
		spacecraft.deltaV2Y = vfY - spacecraft.vy;
		double deltaV2 = Math
				.sqrt(spacecraft.deltaV2X * spacecraft.deltaV2X + spacecraft.deltaV2Y * spacecraft.deltaV2Y);
		if (deltaV2 < spacecraft.minDeltaV2) {
			spacecraft.minDeltaV2 = deltaV2;
			spacecraft.minDeltaV2X = spacecraft.deltaV2X;
			spacecraft.minDeltaV2Y = spacecraft.deltaV2Y;
			spacecraft.minDeltaV2YTime = time.getTotalTime();
		}
	}

	public void updateMaxDistance(Body b1, Body b, Time time) {
		double value = b1.calcDistance(b);
		if (value > b1.maxDistance) {
			b1.maxDistance = value;
			b1.maxDistanceTime = time.getTotalTime();
			b1.maxDistanceVelX = b1.vx;
			b1.maxDistanceVelY = b1.vy;
			b1.maxDistance_Moon_Rocket_VectorX = b1.x - b1.x;
			b1.maxDistance_Moon_Rocket_VectorY = b1.y - b1.y;
		}
	}

	public void updateMinMoonDistance(Body b1, Body b, Time time) {
		double value = b1.calcDistance(b);
		if (value < b1.minMoonDistance) {
			b1.minMoonDistance = value;
			b1.minMoonDistanceTime = time.getTotalTime();
			b1.minMoonDistanceMoon_PosX = b.x;
			b1.minMoonDistanceMoon_PosY = b.y;
			b1.minMoonDistancePosX = b1.x;
			b1.minMoonDistancePosY = b1.y;
			b1.minMoonDistanceVelX = b1.vx;
			b1.minMoonDistanceVelY = b1.vy;

		}
	}

}
