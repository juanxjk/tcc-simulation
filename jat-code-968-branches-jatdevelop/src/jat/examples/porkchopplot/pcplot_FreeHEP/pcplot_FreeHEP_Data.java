package jat.examples.porkchopplot.pcplot_FreeHEP;

import jat.core.ephemeris.DE405Plus;
import jat.core.ephemeris.DE405Body.body;
import jat.core.math.MatrixVector.util.LabeledMatrix;
import jat.core.spacetime.TimeAPL;
import jat.coreNOSA.cm.Constants;
import jat.coreNOSA.cm.Lambert;
import jat.coreNOSA.cm.LambertException;
import jat.coreNOSA.math.MatrixVector.data.VectorN;
import jat.jat3D.FreeHEP.Binned2DData;
import jat.jat3D.FreeHEP.Rainbow;

import java.io.IOException;

import javax.vecmath.Color3b;

public class pcplot_FreeHEP_Data implements Binned2DData {
	private int xBins;
	private int yBins;
	private Rainbow rainbow = new Rainbow(0, 100);
	private float[][] data;
	public LabeledMatrix A;
	TimeAPL search_depart_time_start;
	TimeAPL search_arrival_time_start;
	double totaldv, maxtotaldv;

	public void make_porkchop_plot(TimeAPL search_depart_time_start, int days, int steps) throws IOException {
		DE405Plus my_eph = new DE405Plus();
		TimeAPL search_depart_time = new TimeAPL(search_depart_time_start.mjd_utc());
		TimeAPL search_arrival_time = new TimeAPL(search_arrival_time_start.mjd_utc());
		int search_time = 86400 * days;
		int time_increment = search_time / steps;

		System.out.print(" days " + days);
		System.out.print(" search_time " + search_time);
		System.out.print(" steps " + steps);
		System.out.println(" time_increment " + time_increment);
		// search_arrival_time_start.print();

		A = new LabeledMatrix(steps, steps);
		maxtotaldv = 0;

		A.cornerlabel = "Dep / Arr";
		String dateformat = "%tD";
		for (int i = 0; i < steps; i++) {
			A.RowLabels[i] = String.format(dateformat, search_depart_time.getCalendar());
			for (int j = 0; j < steps; j++) {

				A.ColumnLabels[j] = String.format(dateformat, search_arrival_time.getCalendar());

				double tof = TimeAPL.minus(search_arrival_time, search_depart_time) * 86400.0;

				Lambert lambert = new Lambert(Constants.GM_Sun / 1.e9);
				VectorN r0 = my_eph.get_planet_pos(body.EARTH, search_depart_time);
				VectorN v0 = my_eph.get_planet_vel(body.EARTH, search_depart_time);
				// r0.print("r0");
				// v0.print("v0");
				// System.out.println("orbital velocity of earth " + v0.mag());
				VectorN rf = my_eph.get_planet_pos(body.MARS, search_arrival_time);
				VectorN vf = my_eph.get_planet_vel(body.MARS, search_arrival_time);
				// rf.print("rf");
				// vf.print("vf");
				// System.out.println("orbital velocity of Mars " + vf.mag());

				try {
					totaldv = lambert.compute(r0, v0, rf, vf, tof);
				} catch (LambertException e) {
					totaldv = -1;
					// System.out.println(e.message);
					// e.printStackTrace();
				}
				if (totaldv > maxtotaldv)
					maxtotaldv = totaldv;
				// lambert.deltav0.print("deltav0");
				// lambert.deltavf.print("deltavf");
				// System.out.println("Total DeltaV " + totaldv);
				A.A[i][j] = totaldv;
				search_arrival_time.step_seconds(time_increment);
			}
			search_arrival_time = new TimeAPL(search_arrival_time_start.mjd_utc());
			search_depart_time.step_seconds(time_increment);
		}
		A.print();
		// search_arrival_time_start.print();

		xBins = steps;
		yBins = steps;
		data = new float[xBins][yBins];
		for (int i = 0; i < xBins; i++)
			for (int j = 0; j < yBins; j++)
				if (A.A[i][j] < 0)
					data[i][j] = (float)maxtotaldv;
				else
					data[i][j] = (float) A.A[i][j];
	}

	public pcplot_FreeHEP_Data() throws IOException {

		// make_porkchop_plot(search_depart_time_start);
		// xBins = steps;
		// yBins = steps;
		// data = new float[xBins][yBins];
		// for (int i = 0; i < xBins; i++)
		// for (int j = 0; j < yBins; j++)
		// data[i][j] = (float) A.A[i][j];
	}

	public int xBins() {
		return xBins;
	}

	public int yBins() {
		return yBins;
	}

	public float xMin() {
		return 0f;
	}

	public float xMax() {
		return 1f;
	}

	public float yMin() {
		return 0f;
	}

	public float yMax() {
		return 1f;
	}

	public float zMin() {
		return 0f;
	}

	public float zMax() {
		return 1f;
	}

	public float zAt(int xIndex, int yIndex) {
		return data[xIndex][yIndex];
	}

	public Color3b colorAt(int xIndex, int yIndex) {
		return rainbow.colorFor(zAt(xIndex, yIndex));
	}
}
