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

package jat.examples.jat3D.Rainbow;

import jat.jat3D.BodyGroup3D;
import jat.jat3D.ColorCube3D;
import jat.jat3D.Line3D;
import jat.jat3D.jatScene3D;
import jat.jat3D.plot3D.BoundingBox3D;
import jat.jat3D.plot3D.JatPlot3D;
import jat.jat3D.plot3D.Rainbow3f;

import javax.media.j3d.Group;
import javax.media.j3d.Node;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

public class RainbowPlot extends JatPlot3D {
	private static final long serialVersionUID = 599884902601254854L;
	Rainbow3f rainbow = new Rainbow3f(0, 10);

	public Node createScene() {
		Group g = new Group();
		jatScene = new jatScene3D();
		jatScene.add(new ColorCube3D(.3), "cube");
		g.addChild(jatScene);

		// initial zoom: exponent of ten times kilometers
		exponent = 0;
		bbox = new BoundingBox3D(-.5f, .5f);
		bbox.createAxes(exponent);
		bboxgroup = new BodyGroup3D(bbox, "Box");
		g.addChild(bboxgroup);
		// g.addChild(new Line3D(new Point3f(-1, 0, 0), new Point3f(1, 0, 0),
		// rainbow.colorFor(2)));

		Point3f pleft = new Point3f(0, 0, 0);
		Point3f pright = new Point3f(1, 0, 0);
		int maxcol = 80;
		float dmaxcol = maxcol;
		if (true)
			for (int i = 0; i < maxcol; i++) {
				pleft.z = i / dmaxcol;
				pright.z = i / dmaxcol;
				Color3f col = rainbow.colorFor(i );

				g.addChild(new Line3D(pleft, pright, col));

			}
		// jatScene.remove("cube");
		//g.addChild(new Line3D(pleft, pright, rainbow.colorFor(1)));

		return g;
	}

}
