package jat.examples.SimpleIntegrator2;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

public class CircleODE implements FirstOrderDifferentialEquations {

	private double[] c;
	private double omega;

	public CircleODE(double[] c, double omega) {
		this.c = c;
		this.omega = omega;
	}

	public int getDimension() {
		return 2;
	}

	public void computeDerivatives(double t, double[] y, double[] yDot) {
		yDot[0] = omega * (c[1] - y[1]);
		yDot[1] = omega * (y[0] - c[0]);
	}

	
	
	
	
	static StepHandler stepHandler = new StepHandler() {
	    public void init(double t0, double[] y0, double t) {
	    }
	            
	    public void handleStep(StepInterpolator interpolator, boolean isLast) {
	        double   t = interpolator.getCurrentTime();
	        double[] y = interpolator.getInterpolatedState();
	        System.out.println(t + " " + y[0] + " " + y[1]);
	    }
	};

	
	
	
	
	public static void main(String[] args) {
		
		FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
		dp853.addStepHandler(stepHandler);	

		FirstOrderDifferentialEquations ode = new CircleODE(new double[] { 1.0, 1.0 }, 0.1);
		double[] y = new double[] { 0.0, 1.0 }; // initial state
		dp853.integrate(ode, 0.0, y, 16.0, y); // now y contains final state at
												// time t=16.0

	
	
	}

}