package ufersa.tcc.simulation;

import java.util.ArrayList;
import java.util.List;

import ufersa.tcc.simulation.engine.Body;

public class Script {
	private final List<Statement> statements = new ArrayList<Statement>();

	public Script() {
	};

	public Script(ArrayList<Statement> statements) {
	};

	public void process(Body b, double dt) {
		double vx = b.vx;
		double vy = b.vy;

		double mv = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
		if (mv > 0) {
			vx /= mv;
			vy /= mv;
		}
		double vx_ = -vy;
		double vy_ = vx;

		for (Statement s : statements) {
			if (!s.isActive(Simulation.time.getTotalTime()))
				continue;
			double dvx = s.dX + (vx * s.dV + vx_ * s.dV_);
			double dvy = s.dY + (vy * s.dV + vy_ * s.dV_);
			b.vx += dt * (dvx) / b.mass;
			b.vy += dt * (dvy) / b.mass;
		}
	}

	public void addStatement(double ti, double tf, double dX, double dY, double dV, double dV_) {
		this.statements.add(new Statement(ti, tf, dX, dY, dV, dV_));
	}
}