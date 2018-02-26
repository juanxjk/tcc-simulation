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

package jat.examples.jat3D.BehaviorSwitch;

import jat.jat3D.ColorCube3D;
import jat.jat3D.jatScene3D;
import jat.jat3D.behavior.jat_KeyBehavior_Translate;
import jat.jat3D.plot3D.BoundingBox3D;
import jat.jat3D.plot3D.JatPlot3D;

import javax.media.j3d.Group;
import javax.media.j3d.Node;

public class BehaviorSwitchPlot extends JatPlot3D {
	private static final long serialVersionUID = 599884902601254854L;

	public Node createScene() {
		Group g = new Group();
		jatScene = new jatScene3D();
		jatScene.add(new ColorCube3D(.3), "cube");
		g.addChild(jatScene);

		// initial zoom: exponent of ten times kilometers
		exponent = 0;
		bbox = new BoundingBox3D(-.5f,.5f);
		bbox.createAxes( exponent);
		g.addChild(bboxgroup);

		
		return g;
	}
	
	public void addBehavior() {
		jat_KeyBehavior_Translate keyBehavior_t = new jat_KeyBehavior_Translate(this);
		keyBehavior_t.setSchedulingBounds(getDefaultBounds());
		keyBehaviorSwitch.addChild(keyBehavior_t);

	}

}
