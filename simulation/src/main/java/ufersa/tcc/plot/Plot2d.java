package ufersa.tcc.plot;

import java.io.IOException;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Plot2d {

	/** Nome do plot */
	private String name;
	private XYChart chart;

	/**
	 * Construtor principal.
	 */
	public Plot2d(String name, int width, int height, String xDataLabel, double xData[], String yDataLabel,
			double yData[]) {
		this.name = name;
		// Criação do Gráfico
		this.chart = new XYChartBuilder().width(width).height(height).theme(ChartTheme.Matlab).title(name)
				.xAxisTitle(xDataLabel).yAxisTitle(yDataLabel).build();

		// Estilização do Gráfico
		chart.getStyler().setPlotGridLinesVisible(false);
		chart.getStyler().setXAxisTickMarkSpacingHint(100);

		// Adicionando série para plot - Sem marcador
		chart.addSeries(name, xData, yData).setMarker(SeriesMarkers.NONE);

	}

	/**
	 * @see Plot2d#Plot2d(String, int, int, String, double[], String, double[])
	 */
	public Plot2d(String name, String xDataLabel, double xData[], String yDataLabel, double yData[]) {
		this(name, 800, 600, xDataLabel, xData, yDataLabel, yData);
	}

	/**
	 * Mostrar plot na tela.
	 */
	public void display() {

		// Mostrar em tela
		new SwingWrapper(chart).displayChart();
	}

	/**
	 * Salva o plot em arquivo. Formato: PNG, 300 DPI.
	 */
	public void saveFile(String src) throws IOException {
		BitmapEncoder.saveBitmapWithDPI(chart, src + this.name, BitmapFormat.PNG, 300);
	}

	/**
	 * @see Plot2d#saveFile(String)
	 */
	public void saveFile() throws IOException {
		this.saveFile("./");
	}

	// GETTERS AND SETTERS
	public XYChart getChart() {
		return this.chart;
	}

}
