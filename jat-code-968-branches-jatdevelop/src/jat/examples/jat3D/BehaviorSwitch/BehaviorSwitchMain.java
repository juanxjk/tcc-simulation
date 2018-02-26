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

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class BehaviorSwitchMain {
	BehaviorSwitchPlot bplot;
	BehaviorSwitchGUI bGUI;
	int appletwidth = 900; // Width of Applet
	int appletheight = 700;
	com.tornadolabs.j3dtree.Java3dTree m_Java3dTree = null;

	/**
	 * Used when run as an application
	 * 
	 * @param args
	 *            (String[]) Argument
	 */
	public static void main(String[] args) {
		BehaviorSwitchMain bmain=new BehaviorSwitchMain();
		bmain.m_Java3dTree = new com.tornadolabs.j3dtree.Java3dTree();
		bmain.bplot = new BehaviorSwitchPlot();
		bmain.bGUI=new BehaviorSwitchGUI(bmain);
		bmain.m_Java3dTree.recursiveApplyCapability(bmain.bplot.jatScene );
		//bmain.m_Java3dTree.recursiveApplyCapability( viewBranchGroup );

		JFrame sFrame = new JFrame();
		sFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// When running this file as a stand-alone app, add the applet to
		// the frame.
		sFrame.getContentPane().add(bmain.bGUI, BorderLayout.NORTH);
		sFrame.getContentPane().add(bmain.bplot, BorderLayout.CENTER);
		sFrame.setSize(bmain.appletwidth, bmain.appletheight);
		sFrame.setVisible(true);
		bmain.m_Java3dTree.updateNodes( bmain.bplot.universe );

	}// End of main()

}
