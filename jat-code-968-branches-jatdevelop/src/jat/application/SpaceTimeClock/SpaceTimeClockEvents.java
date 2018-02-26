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

package jat.application.SpaceTimeClock;

import jat.core.spacetime.TimeAPL;
import jat.coreNOSA.spacetime.CalDate;
import jat.jat3D.behavior.jat_Rotate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.Timer;

class SpaceTimeClockEvents implements ActionListener, ItemListener {
	SpaceTimeClockGUI ucGUI;
	jat_Rotate jat_rotate;
	public Timer timer;
	int i;
	int time_advance = 10; // seconds
	private SpaceTimeClockMain ucMain;

	public SpaceTimeClockEvents(SpaceTimeClockMain ucMain) {
		this.ucMain = ucMain;
		timer = new Timer(50, this);
		// timer = new Timer(1000, this);
		// timer.start();
	}

	public void actionPerformed(ActionEvent ev) {
		this.ucGUI = ucMain.ucGUI;
		i++;

		if (ev.getSource() == ucGUI.btn_stop) {
			time_advance = 0;
		}

		if (ev.getSource() == ucGUI.btn_rewind) {
			int sign = (int) Math.signum(time_advance);
			switch (sign) {
			case -1:
				time_advance *= 2;
				break;
			case -0:
				time_advance = -10;
				break;
			case 1:
				time_advance /= 2;
				break;
			}
		}
		if (ev.getSource() == ucGUI.btn_forward) {
			int sign = (int) Math.signum(time_advance);
			switch (sign) {
			case -1:
				time_advance /= 2;
				break;
			case -0:
				time_advance = 10;
				break;
			case 1:
				time_advance *= 2;
				break;
			}
		}

		// Periodic timer events

		CalDate caldate;
		if (ucGUI.realtime_chk.isSelected()) {

			Calendar cal = Calendar.getInstance();
			int Y, M, D, h, m, s;
			Y = cal.get(Calendar.YEAR);
			M = cal.get(Calendar.MONTH);
			D = cal.get(Calendar.DAY_OF_MONTH);
			h = cal.get(Calendar.HOUR_OF_DAY);
			m = cal.get(Calendar.MINUTE);
			s = cal.get(Calendar.SECOND);
			caldate = new CalDate(Y, M, D, h, m, s);
			ucMain.ucParam.simulationDate = new TimeAPL(caldate);
			TimeZone tz = cal.getTimeZone();  
			//System.out.println(tz.getDisplayName()); // (i.e. Moscow Standard Time)  
			ucGUI.field_timezone.setText(tz.getDisplayName());
		} else {
			ucMain.ucParam.simulationDate.step_seconds(time_advance);
			ucGUI.timestepfield.setText("" + time_advance);
			caldate = new CalDate(ucMain.ucParam.simulationDate.mjd_utc());
		}

		ucGUI.yearfield.setText("" + caldate.year());
		ucGUI.monthfield.setText("" + caldate.month());
		ucGUI.dayfield.setText("" + caldate.day());
		ucGUI.hourfield.setText("" + caldate.hour());
		ucGUI.minutefield.setText("" + caldate.min());
		ucGUI.secondfield.setText("" + (int) caldate.sec());
		ucGUI.field_UTCmjd.setText(""+ucMain.ucParam.simulationDate.mjd_utc());
		ucGUI.field_MJD_TT.setText(""+ucMain.ucParam.simulationDate.mjd_tt());
	
	}// End of ActionPerformed

	public void itemStateChanged(ItemEvent e) {

		Object source = e.getItemSelectable();

		if (source == ucGUI.realtime_chk) {
		}
	}

}
