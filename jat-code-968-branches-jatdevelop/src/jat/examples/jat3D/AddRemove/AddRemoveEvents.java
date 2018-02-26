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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

class AddRemoveEvents implements ActionListener {

	public Timer timer;
	int i;
	int time_advance = 10;
	private AddRemovemain armain;

	public AddRemoveEvents(AddRemovemain armain) {
		this.armain = armain;
		timer = new Timer(1000, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent ev) {
		// This is executed every time the timer event occurs
		i++;

		if (i == 2)
			armain.helloplot.jatScene.remove("cube2");

		// System.out.println("removing cube");

	}// End of ActionPerformed

}
