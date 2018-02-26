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

package jat.examples.java3D.AddRemoveLine;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JRadioButton;

public class BehaviorSwitchGUI extends JApplet {
	private static final long serialVersionUID = 1321470082814219656L;
	BehaviorSwitchMain bmain;
	BehaviorSwitchEvents bE;
	JRadioButton rdbtnRotate;
	JRadioButton rdbtnTranslate;

	public BehaviorSwitchGUI(BehaviorSwitchMain bmain) {
		bE = new BehaviorSwitchEvents(this);
		this.bmain = bmain;
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		rdbtnRotate = new JRadioButton("Rotate");
		rdbtnRotate.setSelected(true);
		getContentPane().add(rdbtnRotate);

		rdbtnTranslate = new JRadioButton("Translate");
		getContentPane().add(rdbtnTranslate);

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnRotate);
		group.add(rdbtnTranslate);

		rdbtnRotate.addActionListener(bE);
		rdbtnTranslate.addActionListener(bE);

	}
}
