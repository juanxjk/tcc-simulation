package ufersa.tcc.plot;

import java.awt.BasicStroke;
import java.awt.Color;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Plot2d {
	public static void main(String[] args) throws Exception {
		String nome = "Sample Chart";
		double[] xData = new double[] { 0.0, 1.0, 2.0 };
		double[] yData = new double[] { 2.0, 1.0, 0.0 };

		// Criação do Gráfico
		XYChart chart = new XYChartBuilder().width(800).height(600).theme(ChartTheme.Matlab).title("Matlab Theme")
				.xAxisTitle("X").yAxisTitle("Y").build();

		// Estilização do Gráfico
		// chart.getStyler().setPlotGridLinesVisible(false);
		chart.getStyler().setPlotGridHorizontalLinesVisible(true);
		chart.getStyler().setXAxisTickMarkSpacingHint(100);

		// Adicionando série para plot - Sem marcador
		chart.addSeries(nome, xData, yData).setMarker(SeriesMarkers.NONE);

		// Adicionando série para plot - Com marcador (quadrado)
		chart.addSeries(nome + 2, xData, yData).setMarker(SeriesMarkers.RECTANGLE);
		chart.getSeriesMap().get(nome).setLineColor(Color.CYAN);

		// Salvando em arquivo
		BitmapEncoder.saveBitmapWithDPI(chart, "./" + nome, BitmapFormat.PNG, 300);

		// Mostrar em tela
		new SwingWrapper(chart).displayChart();

	}
}
