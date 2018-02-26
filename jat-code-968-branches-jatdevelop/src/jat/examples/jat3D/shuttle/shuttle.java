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

package jat.examples.jat3D.shuttle;

import jat.core.ephemeris.DE405Plus;
import jat.core.util.PathUtil;
import jat.coreNOSA.cm.cm;
import jat.jat3D.Axes3D;
import jat.jat3D.CapturingCanvas3D;
import jat.jat3D.Colors;
import jat.jat3D.Orbit;
import jat.jat3D.Planet3D;
import jat.jat3D.RGBAxes3D;
import jat.jat3D.jat_behavior;
import jat.jat3D.jat_light;
import jat.jat3D.jat_view;
import jat.jat3D.loader.LightWaveObject;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Locale;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.VirtualUniverse;
import javax.swing.Timer;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.applet.MainFrame;

/**
 * @author Tobias Berthold
 *
 */


/*
public class shuttle extends Applet implements ActionListener
{
	private static final long serialVersionUID = 8541940483497672731L;
	BranchGroup BG_root;
	BranchGroup BG_vp;
	TransformGroup TG_scene;
	Point3d origin = new Point3d(0.0f, 0.0f, 0.0f);
	BoundingSphere bounds = new BoundingSphere(origin, 1.e10); //100000000.0
	ControlPanel panel;
	Planet3D earth;
	RGBAxes3D axis;
	LightWaveObject shuttle;
	Orbit orb1;
	Axes3D mainaxis, shuttleaxis;
	public CapturingCanvas3D c;
	Timer animControl;
	int i;
	int steps = 2000; // steps in orbit

	public shuttle()
	{
		// Get path of this class, frames will be saved in subdirectory frames
//		String b = FileUtil.getClassFilePath("jat.demo.vr.shuttle", "shuttle");
//		System.out.println(b);

		PathUtil p=new PathUtil(this);
		String b=p.current_path;
		System.out.println(b);
		
		//Applet window
		setLayout(new BorderLayout());
		c = createCanvas(b + "frames/");
		add("Center", c);
		panel = new ControlPanel(BG_root);
		add("South", panel);

		// 3D Objects      
		BG_root = new BranchGroup();
		BG_root.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		BG_root.setBounds(bounds);
		TG_scene = new TransformGroup();
		TG_scene.addChild(earth = new Planet3D( DE405Plus.body.EARTH_MOON_BARY,1));
//Linux requires lowercase and uppercase letters to be consistent
		TG_scene.addChild(shuttle = new LightWaveObject("spaceshuttle.lws", 200.f));
		//TG_scene.addChild(axis = new RGBAxis3D(15000.0f));
		TG_scene.addChild(shuttleaxis = new Axes3D(10.0f));
		BG_root.addChild(TG_scene);
		BG_root.addChild(orb1 = new Orbit(cm.earth_radius + 1000., 0., 22.0, 0.0, 0.0, 0.0, Colors.pink, steps));
		i=1000;
		shuttle.set_position(orb1.x[i], orb1.y[i], orb1.z[i]);
		shuttle.set_scale(200.);
		double alpha = 1.1;
		shuttle.set_attitude(alpha, alpha, 0.);
		//shuttleaxis.set_position(cm.earth_radius + 500., 0., 0.);

		// Lights
		BG_root.addChild(jat_light.DirectionalLight(bounds));
		jat_light.setDirection(100.f, 1.f, -1.f);
		Background background = new Background();
		background.setApplicationBounds(bounds);
		background.setColor(Colors.gray);
		BG_root.addChild(background);

		// View
		BG_vp = jat_view.view(-180000, 0., -1.e4, c);

		// Behaviors
		jat_behavior.behavior(BG_root, BG_vp, bounds);
		jat_behavior.xyz_Behavior.set_translate(500);


		VirtualUniverse universe = new VirtualUniverse();
		Locale locale = new Locale(universe);
		locale.addBranchGraph(BG_root);
		locale.addBranchGraph(BG_vp);

		// Have Java 3D perform optimizations on this scene graph.
		//BG_root.compile();

		// Use Swing Timer for animation
		int delayValue = 50; // milliseconds
		animControl = new Timer(delayValue, this);
		animControl.start();

	}

	// This method is called each time a timer event occurs
	public void actionPerformed(ActionEvent e)
	{
		Transform3D T_3D = new Transform3D();
		if (i > 10 && i < 30)
		{
			System.out.println("" + i);
			//c.writeJPEG_ = true;
			//a.c.repaint();
		}

		i++;
		if (i > steps)
		{
			i = 0;
		}
		panel.label.setText(i + "  Time " + (long)orb1.t[i] + "  x " + (long)orb1.x[i] + "  y " + (long)orb1.y[i]);
		shuttle.set_position(orb1.x[i], orb1.y[i], orb1.z[i]);

		// Earth rotation
		earth.set_attitude(Math.PI / 2., 0.,i * 0.001f);
	}

	//private Canvas3D createCanvas()
	private CapturingCanvas3D createCanvas(String frames_path)
	{
		GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();
		GraphicsConfiguration gc1 =
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getBestConfiguration(template);
		return new CapturingCanvas3D(gc1, frames_path);
	}

	public void init()
	{

	}

	public static void main(String[] args)
	{
		shuttle em = new shuttle(); // Applet
		new MainFrame(em, 800, 600);
	}

}
*/