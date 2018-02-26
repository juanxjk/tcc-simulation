package jat.examples.SimpleIntegrator2;

import jat.core.plot.plot.FrameView;
import jat.core.plot.plot.Plot2DPanel;
import jat.core.plot.plot.PlotPanel;
import jat.core.plot.plot.plots.LinePlot;

import java.awt.Color;

import javax.swing.JFrame;


public class SimpleIntegrator2 {

	public static void main(String[] args) {
		Plot2DPanel p2 = new Plot2DPanel();

		double[][] XY = new double[100][2];
		for (int j = 0; j < XY.length; j++) {
			XY[j][0] = 2 * Math.PI * (double) j / XY.length;
			XY[j][1] = Math.sin(XY[j][0]);
		}

		double[][] XY2 = new double[100][2];
		for (int j = 0; j < XY2.length; j++) {
			XY2[j][0] = 2 * Math.PI * (double) j / XY2.length;
			XY2[j][1] = Math.cos(XY2[j][0]);
			// XY[j][1] = 1.2;
		}

		// Plot2DCanvas pc = new Plot2DCanvas();
		// pc.addLinePlot(l);
		// pc.addLinePlot(l2);
		// new FrameView(pc).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// p2.addLinePlot("sin", XY);
		// p2.setLegendOrientation(PlotPanel.SOUTH);
		// new FrameView(p2).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LinePlot l = new LinePlot("sin", Color.RED, XY);
		l.closed_curve = false;
		LinePlot l2 = new LinePlot("cos", Color.blue, XY2);
		Plot2DPanel p3 = new Plot2DPanel();
		p3.addPlot(l2);
		p3.addPlot(l);
		p3.setLegendOrientation(PlotPanel.SOUTH);
		new FrameView(p3).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
