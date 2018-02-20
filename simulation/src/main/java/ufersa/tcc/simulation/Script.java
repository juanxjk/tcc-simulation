package ufersa.tcc.simulation;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import ufersa.tcc.simulation.engine.Body;

public class Script {
	private final List<Statement> statements = new ArrayList<Statement>();
	private PApplet screen;

	public Script(PApplet screen) {
		this.screen = screen;
	};

	public Script(ArrayList<Statement> statements) {
	};

	public void process(Body b, float dt) {
		float vx = b.vx;
		float vy = b.vy;

		float mv = screen.sqrt(screen.pow(vx, 2) + screen.pow(vy, 2));
		if (mv > 0) {
			vx /= mv;
			vy /= mv;
		}
		float vx_ = -vy;
		float vy_ = vx;

		for (Statement s : statements) {
			if (!s.isActive(Simulation.t))
				continue;
			float dvx = s.dX + (vx * s.dV + vx_ * s.dV_);
			float dvy = s.dY + (vy * s.dV + vy_ * s.dV_);
			b.vx += dt * (dvx) / b.mass;
			b.vy += dt * (dvy) / b.mass;
		}
	}

	public void addStatement(float ti, float tf, float dX, float dY, float dV, float dV_) {
		this.statements.add(new Statement(ti, tf, dX, dY, dV, dV_));
	}
}