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

package jat.examples.jat3D.AddRemove;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class AddRemovemain {
	static AddRemovemain armain;
	AddRemoveplot helloplot;
	JFrame sFrame;
	static int appletwidth = 900; // Width of Applet
	static int appletheight = 700;
	static com.tornadolabs.j3dtree.Java3dTree m_Java3dTree = null;
	private static boolean Java3dTree_debug = true;
//	private static boolean Java3dTree_debug = false;

	/**
	 * Used when run as an application
	 * 
	 * @param args
	 *            (String[]) Argument
	 */
	public static void main(String[] args) {
		armain=new AddRemovemain();
		armain.helloplot = new AddRemoveplot();

		armain.sFrame = new JFrame();
		armain.sFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// When running this file as a stand-alone app, add the applet to
		// the frame.
		armain.sFrame.getContentPane().add(armain.helloplot, BorderLayout.CENTER);
		armain.sFrame.setSize(appletwidth, appletheight);
		armain.sFrame.setVisible(true);

		if (Java3dTree_debug) {
			m_Java3dTree = new com.tornadolabs.j3dtree.Java3dTree();
			m_Java3dTree.recursiveApplyCapability(armain.helloplot.jatScene);
			m_Java3dTree.updateNodes(armain.helloplot.universe);
		}

		new AddRemoveEvents(armain);
				
	}// End of main()

}
