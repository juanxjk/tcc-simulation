package ufersa.tcc.simulation.engine;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

public class Satellite extends Body {

	private Body centerBody;
	private double radius_distance; // [m]
	private double angular_vel; // [rad/s]
	private double theta = 0; // rad

	public Satellite(Body centerBody, Body body, double radius_distance, double angular_vel) {
		super(body);
		this.centerBody = centerBody;
		this.radius_distance = radius_distance;
		this.angular_vel = angular_vel;
	}

	public void simulate(double dt) {
		theta += angular_vel * dt;
		this.x = centerBody.x + radius_distance * cos(theta);
		this.y = centerBody.y - radius_distance * sin(theta);
	}

}
