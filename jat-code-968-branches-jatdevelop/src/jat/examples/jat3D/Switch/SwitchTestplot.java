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

package jat.examples.jat3D.Switch;

import jat.jat3D.RGBAxes3D;
import jat.jat3D.jatScene3D;
import jat.jat3D.plot3D.JatPlot3D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Node;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.Timer;

import com.sun.j3d.utils.geometry.ColorCube;

public class SwitchTestplot extends JatPlot3D implements ActionListener {
	private static final long serialVersionUID = 599884902601254854L;
	private static final int fullPlotChild = 0;
	private static final int sparsePlotChild = 1;
	private Switch targetSwitch;

	public Timer timer;
	int i;

	public SwitchTestplot() {
		super();
		createScene();
		timer = new Timer(1000, this);
		timer.start();
	}

	protected Node createScene() {
		// Build switch node to switch between full plot and sparse plot
		targetSwitch = new Switch();
		targetSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);
		targetSwitch.setCapability(Switch.ALLOW_CHILDREN_WRITE);

		jatScene = new jatScene3D();
		jatScene.add(new RGBAxes3D(2), "Axis");
		targetSwitch.addChild(jatScene);

		Transform3D rotate = new Transform3D();
		TransformGroup objRotate = new TransformGroup(rotate);
		objRotate.addChild(new ColorCube(0.5));
		targetSwitch.addChild(objRotate);

		targetSwitch.setWhichChild(sparsePlotChild); // full plot displayed by

		return targetSwitch;
	}

	public void actionPerformed(ActionEvent e) {
		i++;
		// if (i > 10) // give the app time to initialize, otherwise exception

		if (i == 2) {
			targetSwitch.setWhichChild(fullPlotChild); // full plot displayed by
		}

		if (i == 4) {
			targetSwitch.setWhichChild(sparsePlotChild); // full plot displayed by

		}

	}

}
