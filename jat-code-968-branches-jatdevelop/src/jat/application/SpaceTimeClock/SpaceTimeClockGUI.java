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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SpaceTimeClockGUI extends JPanel {
	private static final long serialVersionUID = 1321470082814219656L;
	JFormattedTextField yearfield;
	JFormattedTextField monthfield;
	JFormattedTextField dayfield;
	JFormattedTextField hourfield;
	JFormattedTextField minutefield;
	JFormattedTextField secondfield;
	JFormattedTextField timestepfield;
	JPanel level2_Pane_Plot;
	JPanel button_panel;
	public JButton btn_stop;
	public JButton btn_forward;
	public JButton btn_rewind;
	JCheckBox realtime_chk;
	SpaceTimeClockMain ucMain;
	public SpaceTimeClockEvents ucE;
	private JLabel lblUniversalCoordinatedTime;
	public JFormattedTextField field_UTCmjd;
	private JPanel panelControl;
	private JLabel lblTerrestrialDynamicalTime;
	public JFormattedTextField field_MJD_TT;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel lblTimezone;
	public JFormattedTextField field_timezone;

	public SpaceTimeClockGUI(SpaceTimeClockMain ucMain) {
		this.ucMain = ucMain;
		ucE = new SpaceTimeClockEvents(ucMain);
		setLayout(new BorderLayout(0, 0));

		JPanel panelDate = new JPanel();
		add(panelDate, BorderLayout.CENTER);
		panelDate.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Time Display",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDate.setLayout(new GridLayout(1, 2, 0, 0));

		SpaceTimeAnalogClock clock = new SpaceTimeAnalogClock();

		panelDate.add(clock);
		panel_1 = new JPanel();
		panelDate.add(panel_1);
		panel_1.setLayout(new GridLayout(4, 1, 0, 0));
		JPanel level5_Pane_numbers = new JPanel();
		panel_1.add(level5_Pane_numbers);
		level5_Pane_numbers.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:default"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(17dlu;default)"), }, new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, }));
		level5_Pane_numbers.setBorder(null);

		JLabel lblY = new JLabel("Y");
		lblY.setHorizontalAlignment(SwingConstants.CENTER);
		level5_Pane_numbers.add(lblY, "2, 1, center, default");

		JLabel lblM = new JLabel("M");
		lblM.setHorizontalAlignment(SwingConstants.CENTER);
		level5_Pane_numbers.add(lblM, "4, 1");

		JLabel lblD = new JLabel("D");
		level5_Pane_numbers.add(lblD, "6, 1, center, default");

		yearfield = new JFormattedTextField();
		yearfield.setColumns(4);
		yearfield.setValue(8000.);
		level5_Pane_numbers.add(yearfield, "2, 3, left, fill");
		yearfield.requestFocusInWindow();

		monthfield = new JFormattedTextField();
		monthfield.setColumns(2);
		monthfield.setValue(0.1);
		level5_Pane_numbers.add(monthfield, "4, 3, left, fill");

		dayfield = new JFormattedTextField();
		dayfield.setColumns(2);
		level5_Pane_numbers.add(dayfield, "6, 3, left, fill");

		JLabel lblHr = new JLabel("hr");
		level5_Pane_numbers.add(lblHr, "2, 5");

		JLabel lblMi = new JLabel("mi");
		level5_Pane_numbers.add(lblMi, "4, 5");

		JLabel lblSc = new JLabel("sc");
		level5_Pane_numbers.add(lblSc, "6, 5");

		hourfield = new JFormattedTextField();
		hourfield.setColumns(2);
		level5_Pane_numbers.add(hourfield, "2, 7, right, fill");

		minutefield = new JFormattedTextField();
		minutefield.setColumns(2);
		level5_Pane_numbers.add(minutefield, "4, 7, left, fill");

		secondfield = new JFormattedTextField();
		secondfield.setColumns(2);
		level5_Pane_numbers.add(secondfield, "6, 7, left, fill");

		lblTimezone = new JLabel("Timezone");
		panel_1.add(lblTimezone);

		field_timezone = new JFormattedTextField();
		panel_1.add(field_timezone);

		panel = new JPanel();
		panelDate.add(panel);
		panel.setLayout(new GridLayout(4, 1, 0, 0));

		lblUniversalCoordinatedTime = new JLabel("UTC mJD");
		panel.add(lblUniversalCoordinatedTime);

		field_UTCmjd = new JFormattedTextField();
		panel.add(field_UTCmjd);

		lblTerrestrialDynamicalTime = new JLabel("TDT mJD");
		panel.add(lblTerrestrialDynamicalTime);

		field_MJD_TT = new JFormattedTextField();
		panel.add(field_MJD_TT);

		panelControl = new JPanel();
		panelControl.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Time Control",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panelControl, BorderLayout.SOUTH);

		realtime_chk = new JCheckBox("Realtime");
		realtime_chk.setSelected(true);
		panelControl.add(realtime_chk);
		realtime_chk.setAlignmentX(Component.CENTER_ALIGNMENT);
		realtime_chk.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel = new JLabel("time step");
		panelControl.add(lblNewLabel);

		timestepfield = new JFormattedTextField();
		panelControl.add(timestepfield);
		timestepfield.setColumns(8);

		button_panel = new JPanel();
		panelControl.add(button_panel);
		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.X_AXIS));

		String iconPath = "/jat/application/SpaceTimeClock/icons/";

		btn_rewind = new JButton("");
		btn_rewind.setIcon(new ImageIcon(SpaceTimeClockGUI.class.getResource(iconPath + "Rewind12.gif")));
		button_panel.add(btn_rewind);

		btn_stop = new JButton("");
		btn_stop.setIcon(new ImageIcon(SpaceTimeClockGUI.class.getResource(iconPath + "Stop12.gif")));
		button_panel.add(btn_stop);

		btn_forward = new JButton("");
		btn_forward.setIcon(new ImageIcon(SpaceTimeClockGUI.class.getResource(iconPath + "FastForward12.gif")));
		button_panel.add(btn_forward);

	}
}
