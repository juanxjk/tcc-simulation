package ufersa.tcc.simulation.engine;

import ufersa.tcc.simulation.Script;
import ufersa.tcc.simulation.Statement;
import ufersa.tcc.simulation.engine.time.Time;

public class Rocket extends Body {
	private Script script;

	/**
	 * @param body
	 *            Instância com as características físicas do foguete.
	 */
	public Rocket(Body body) {
		super(body);
		this.script = new Script();
	}

	public void addStatement(Statement s) {
		this.script.addStatement(s.ti, s.tf, s.dX, s.dY, s.dV, s.dV_);
	}

	public void simulate(World w, double dt) {
		for (Body body : w.getBodies()) {
			this.interact(body, dt);
		}

		this.x += vx * dt; // [m]
		this.y += vy * dt; // [m]

		this.script.process(this, dt);
	}

}
