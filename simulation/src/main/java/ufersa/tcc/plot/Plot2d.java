package ufersa.tcc.plot;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class Plot2d {
	public static void main(String[] args) throws Exception {
		String nome = "Sample Chart";
		double[] xData = new double[] { 0.0, 1.0, 2.0 };
		double[] yData = new double[] { 2.0, 1.0, 0.0 };

		// Create Chart
		XYChart chart = QuickChart.getChart(nome, "X", "Y", "y(x)", xData, yData);

		BitmapEncoder.saveBitmapWithDPI(chart, "./" + nome, BitmapFormat.PNG, 300);

		// Show it
		new SwingWrapper(chart).displayChart();

	}
}
