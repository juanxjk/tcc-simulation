/* JAT: Java Astrodynamics Toolkit
 * 
  Copyright 2012 Tobias Berthold

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package jat.examples.SimpleIntegrator2;

import jat.core.plot.plot.FrameView;
import jat.core.plot.plot.Plot2DPanel;
import jat.core.plot.plot.PlotPanel;
import jat.core.plot.plot.plots.LinePlot;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

public class CosSinODE implements FirstOrderDifferentialEquations {

	private double[] c;
	private double omega;
	static ArrayList<Double> time = new ArrayList<Double>();
	static ArrayList<Double> ysol = new ArrayList<Double>();

	public CosSinODE(double[] c, double omega) {
		this.c = c;
		this.omega = omega;
	}

	public int getDimension() {
		return 2;
	}

	public void computeDerivatives(double t, double[] y, double[] yDot) {
		yDot[0] = y[1];
		yDot[1] = -y[0];
	}

	static StepHandler stepHandler = new StepHandler() {
		public void init(double t0, double[] y0, double t) {
		}

		public void handleStep(StepInterpolator interpolator, boolean isLast) {
			double t = interpolator.getCurrentTime();
			double[] y = interpolator.getInterpolatedState();
			System.out.println(t + " " + y[0] + " " + y[1]);

			time.add(t);
			ysol.add(y[0]);
		}
	};

	public static void main(String[] args) {

		FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
		dp853.addStepHandler(stepHandler);

		FirstOrderDifferentialEquations ode = new CosSinODE(new double[] { 1.0, 1.0 }, 0.1);
		double[] y = new double[] { 0.0, 1.0 }; // initial state
		dp853.integrate(ode, 0.0, y, 10.0, y); // now y contains final state at
												// time t=16.0

		Double[] objArray = time.toArray(new Double[time.size()]);
		double[] timeArray = ArrayUtils.toPrimitive(objArray);
		double[] ysolArray = ArrayUtils.toPrimitive(ysol.toArray(new Double[time.size()]));

		double[][] XY = new double[timeArray.length][2];

		// int a=0;
		// System.arraycopy(timeArray,0,XY[a],0,timeArray.length);
		// System.arraycopy(ysolArray,0,XY[1],0,ysolArray.length);

		for (int i = 0; i < timeArray.length; i++) {
			XY[i][0] = timeArray[i];
			XY[i][1] = ysolArray[i];
		}
		Plot2DPanel p = new Plot2DPanel();
		LinePlot l = new LinePlot("sin", Color.RED, XY);
		l.closed_curve = false;
		l.draw_dot = true;
		p.addPlot(l);
		p.setLegendOrientation(PlotPanel.SOUTH);

		new FrameView(p).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		System.out.println("end");

	}

}