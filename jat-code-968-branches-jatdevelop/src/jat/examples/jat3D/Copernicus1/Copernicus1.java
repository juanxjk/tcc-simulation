/* JAT: Java Astrodynamics Toolkit
 *
 * Copyright (c) 2002 National Aeronautics and Space Administration and the Center for Space Research (CSR),
 * The University of Texas at Austin. All rights reserved.
 *
 * This file is part of JAT. JAT is free software; you can
 * redistribute it and/or modify it under the terms of the
 * NASA Open Source Agreement
 * 
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * NASA Open Source Agreement for more details.
 *
 * You should have received a copy of the NASA Open Source Agreement
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package jat.examples.jat3D.Copernicus1;

import jat.core.ephemeris.DE405Plus;
import jat.core.spacetime.TimeAPL;
import jat.coreNOSA.cm.Constants;
import jat.coreNOSA.cm.cm;
import jat.coreNOSA.ephemeris.DE405;
import jat.coreNOSA.ephemeris.DE405_Body;
import jat.coreNOSA.math.MatrixVector.data.Matrix;
import jat.coreNOSA.math.MatrixVector.data.RotationMatrix;
import jat.coreNOSA.spacetime.TimeUtils;
import jat.coreNOSA.util.FileUtil;
import jat.coreNOSA.util.pr;
import jat.jat3D.CapturingCanvas3D;
import jat.jat3D.Copernicus_Trajectory;
import jat.jat3D.Ephemeris3D;
import jat.jat3D.Orbit;
import jat.jat3D.Planet3D;
import jat.jat3D.RGBAxes3D;
import jat.jat3D.Star3D;
import jat.jat3D.jat_behavior;
import jat.jat3D.jat_light;
import jat.jat3D.jat_view;
import jat.jat3D.loader.ThreeDStudioObject;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Locale;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.VirtualUniverse;
import javax.swing.Timer;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.applet.MainFrame;

/**
 * @author Tobias Berthold Date : 11-18-2002 Description : Simulate
 *         Earth-Mars-Earth trajectory for Copernicus
 * 
 */

/*
public class Copernicus1 extends Applet implements ActionListener {
	private static final long serialVersionUID = 8003649237851623293L;
	public CapturingCanvas3D c;
	BranchGroup BG_root;
	BranchGroup BG_vp;
	TransformGroup TG_scene;
	Point3d origin = new Point3d(0.0f, 0.0f, 0.0f);
	BoundingSphere bounds = new BoundingSphere(origin, 1.e11); // 100000000.0
	Planet3D mercury, venus, earth, mars, jupiter;
	Star3D sun;
	RGBAxes3D axis;
	ControlPanel panel;
	Copernicus_Trajectory ct1, ct2;
	// ColorCube3D spacecraft;
	ThreeDStudioObject spacecraft;
	Orbit orbit_mars, orbit_earth_moon;
	Ephemeris3D ephemeris_mercury, ephemeris_venus, ephemeris_earth, ephemeris_mars;
	Timer animControl;
	// int delayValue = 50; // milliseconds
	// Timer animControl = new Timer(delayValue, this);
	DE405 my_eph; // Ephemeris class
	DE405Plus myEph; // Ephemeris class
	TimeAPL time;
	double jd;
	Calendar cal;
	SimpleDateFormat sdf;
	Vector3d V = new Vector3d(0.f, 0.f, 0.0f);
	int i = 0;
	Matrix MRot;
	int steps = 300;

	public Copernicus1() {
		// Get path of this class, frames will be saved in subdirectory frames
		String b = FileUtil.getClassFilePath("jat.demo.vr.Copernicus1", "Copernicus1");
		System.out.println(b);

		// Applet window
		setLayout(new BorderLayout());
		c = createCanvas(b + "frames/");
		add("Center", c);
		panel = new ControlPanel(BG_root);
		add("South", panel);

		// Ephemeris data
		myEph = new DE405Plus();

//		String fs = FileUtil.file_separator();
//		my_eph = new DE405(FileUtil.getClassFilePath("jat.eph", "eph") + fs + "DE405" + fs);
//		jd = cm.juliandate(2018, 6, 2, 12, 0, 0);
		time = new TimeAPL(jd);
		MRot = new RotationMatrix(1, cm.Rad(Constants.eps));

		// Date related functions
		sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aaa");

		// 3D Objects
		BG_root = new BranchGroup();
		BG_root.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		BG_root.setBounds(bounds);
		float scale_factor = 1000.f; // exaggerate planets by factor
		TG_scene = new TransformGroup();
		TG_scene.addChild(axis = new RGBAxes3D(1.2f * cm.mars_a));
		TG_scene.addChild(sun = new Star3D(1.0f));
		TG_scene.addChild(mercury = new Planet3D(DE405Plus.body.MERCURY, scale_factor));
		TG_scene.addChild(venus = new Planet3D(DE405Plus.body.VENUS, scale_factor));
		TG_scene.addChild(earth = new Planet3D(DE405Plus.body.EARTH_MOON_BARY, scale_factor));
		TG_scene.addChild(mars = new Planet3D(DE405Plus.body.MARS, scale_factor));
		TG_scene.addChild(spacecraft = new ThreeDStudioObject("carrier.3DS", 50000.f));
		spacecraft.thrusters_on();
		// TG_scene.addChild(spacecraft = new ColorCube3D(1.e7));
		BG_root.addChild(ephemeris_mercury = new Ephemeris3D(myEph,DE405Plus.body.MERCURY, time, 100));
		BG_root.addChild(ephemeris_venus = new Ephemeris3D(myEph,DE405Plus.body.VENUS, time, 300));
		BG_root.addChild(ephemeris_earth = new Ephemeris3D(myEph,DE405Plus.body.EARTH_MOON_BARY, time, 365));
		BG_root.addChild(ephemeris_mars = new Ephemeris3D(myEph,DE405Plus.body.MARS, time, 600));
		BG_root.addChild(TG_scene);
		// Copernicus Trajectory
		// ct1=new Copernicus_Trajectory(b+"Mission.txt",steps);
		ct1 = new Copernicus_Trajectory(b + "segment_1.txt", steps);
		BG_root.addChild(ct1);
		// ct2=new Copernicus_Trajectory(b+"segment_2.txt",steps);
		// BG_root.addChild(ct2);

		// Lights
		BG_root.addChild(jat_light.PointLight(bounds));
		// BG_root.addChild(jat_light.DirectionalLight(bounds));

		// View
		BG_vp = jat_view.view(0., 0., 1.e9, c);
		// BG_vp = jat_view.view(0., 0., 2.e8, c);
		// BG_vp = jat_view.view(2.e8,-3.e8, 1.e6, c);
		jat_view.set_FrontBackClipDistance(1.e5, 1.e10);

		// Behaviors
		jat_behavior.behavior(BG_root, BG_vp, bounds);
		jat_behavior.xyz_Behavior.set_translate(1.e7f);

		// Have Java 3D perform optimizations on this scene graph.
		BG_root.compile();

		VirtualUniverse universe = new VirtualUniverse();
		Locale locale = new Locale(universe);
		locale.addBranchGraph(BG_root);
		locale.addBranchGraph(BG_vp);

		// Use Timer for animation
		int delayValue = 20; // milliseconds
		animControl = new Timer(delayValue, this);
		animControl.start();
		// System.out.println("i x y z xy_angle xz_angle");
	}

	// This method is called each time a timer event occurs
	public void actionPerformed(ActionEvent e) {
		// System.out.println("" + i);
		i++;

		// if (i > 300 && i < 580)
		// if(i > 10 && i<steps)
		if (i < steps) {

			jd = ct1.t[i];
			// jd+=ct1.t[1]-ct1.t[0];

	
			// set new spacecraft position and attitude
			double x, y, z;
			x = ct1.ux[i];
			y = ct1.uy[i];
			z = ct1.uz[i];
			double xy_angle = -Math.atan2(z, Math.sqrt(x * x + y * y)); // Angle
																		// between
																		// vector
																		// and
																		// x-z-plane
			double xz_angle = Math.atan2(y, x); // Angle between vector and
												// x-z-plane
			// System.out.println(i+" "+x+" "+y+" "+z+" "+Constants.rad2deg*xy_angle+" "+Constants.rad2deg*xz_angle);
			spacecraft.set_pos_attitude(ct1.x[i], ct1.y[i], ct1.z[i], 0., xy_angle, xz_angle);

			// For above spacecraft view
			Vector3d look_to = new Vector3d(ct1.x[i], ct1.y[i], ct1.z[i]);
			Vector3d pos = new Vector3d(ct1.x[i], ct1.y[i], ct1.z[i] + 2.5e8);
			jat_view.set_pos_direction(pos, look_to);

			// Planet positions
			double mjd_tt = TimeUtils.JDtoMJD(jd);
			mercury.set_position(MRot.times(my_eph.get_planet_pos(DE405_Body.MERCURY, mjd_tt)));
			venus.set_position(MRot.times(my_eph.get_planet_pos(DE405_Body.VENUS, mjd_tt)));
			earth.set_position(MRot.times(my_eph.get_planet_pos(DE405_Body.EARTH, mjd_tt)));
			mars.set_position(MRot.times(my_eph.get_planet_pos(DE405_Body.MARS, mjd_tt)));

			// Take frame screenshot
			try {
				Thread.sleep(300);
				// System.out.println("waiting..");
			} catch (Exception f) {
			}
			;
			c.takePicture();

		} // else
			// i = 0;

		// Update text in panel
		// V=jat_view.get_vp();
		cal = cm.JD_to_Calendar(jd);
		String jds = pr.fwdts(jd, 10, 6);
		panel.label.setText("JD: " + jds + "   Greg: " + sdf.format(cal.getTime()) + "   Observer:  x:" + (long) V.x
				+ "  y: " + (long) V.y + "  z: " + (long) V.z);

	}

	private CapturingCanvas3D createCanvas(String frames_path) {
		GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
		GraphicsConfiguration gc1 = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getBestConfiguration(template);
		return new CapturingCanvas3D(gc1, frames_path);
	}

	public void init() {

	}

	public static void main(String[] args) {
		Copernicus1 c1 = new Copernicus1(); // Applet
		MainFrame m = new MainFrame(c1, 800, 600);
		m.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
	}
}

*/