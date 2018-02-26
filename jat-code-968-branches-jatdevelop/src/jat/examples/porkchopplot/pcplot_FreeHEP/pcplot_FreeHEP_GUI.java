package jat.examples.porkchopplot.pcplot_FreeHEP;

import jat.jat3D.FreeHEP.Plot3D;
import jat.jat3D.FreeHEP.SurfacePlot;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;


public class pcplot_FreeHEP_GUI extends javax.swing.JPanel {
	private static final long serialVersionUID = 7907463395861359469L;
	pcplot_FreeHEP_Events pcpE;
	private JPanel SearchIntervalPanel;
	private JPanel MissionSelectPanel;
	private JPanel OptionPanel;
	private JPanel Panel1;
	private JPanel Panel2;
	private JCheckBox jCheckBox1;
	private JCheckBox jCheckBox2;
	private SurfacePlot surf;
	private Plot3D plot;
	public JButton btn_make_plot;
	private JLabel lbldepart;
	private JLabel lblarrive;
	JDatePicker depart_date_picker;
	JDatePicker arrival_date_picker;
	JDatePickerImpl datePicker1;
	private JCheckBox chckbx_same_time;
	JSpinner spinner_days;
	private JLabel lblDays;
	JSpinner spinner_steps;
	private JLabel lblSteps;
	private JButton btnSelectFlight;
	private JLabel lblDepartureDate;
	private JFormattedTextField field_selected_departure_date;
	private JLabel lblArrivalDate;
	private JFormattedTextField field_selected_arrival_date;
	private JLabel lblTotalDeltav;
	private JFormattedTextField field_total_deltav;
	private JLabel lblNewLabel;

	public pcplot_FreeHEP_GUI(SurfacePlot plot) {
		this.surf = plot;
		this.plot = plot;
		pcpE = new pcplot_FreeHEP_Events(this);
		SearchIntervalPanel = new JPanel();
		OptionPanel = new JPanel();
		MissionSelectPanel = new JPanel();
		jCheckBox1 = new JCheckBox();
		jCheckBox2 = new JCheckBox();
		Panel1 = new JPanel();
		Panel2 = new JPanel();
		Panel1.setLayout(new GridLayout(5, 1, 5, 5));
		Panel2.setLayout(new GridLayout(3, 2, 5, 5));

		SearchIntervalPanel.setBorder(new TitledBorder(null, "Search Interval", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		MissionSelectPanel.setBorder(new TitledBorder(null, "Flight Selection", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		depart_date_picker = JDateComponentFactory.createJDatePicker();
		depart_date_picker = (JDatePickerImpl) JDateComponentFactory.createJDatePicker();
		depart_date_picker.setTextEditable(true);
		depart_date_picker.setShowYearButtons(true);
		depart_date_picker.getModel().setYear(2012);
		depart_date_picker.getModel().setSelected(true);

		arrival_date_picker = JDateComponentFactory.createJDatePicker();
		depart_date_picker.setTextEditable(true);
		depart_date_picker.setShowYearButtons(true);

		add(SearchIntervalPanel);

		SearchIntervalPanel.setLayout(new BoxLayout(SearchIntervalPanel, BoxLayout.Y_AXIS));
		SearchIntervalPanel.add(Panel1);
		SearchIntervalPanel.add(Panel2);

		lbldepart = new JLabel("Departure");
		Panel1.add(lbldepart);
		Panel1.add((JComponent) depart_date_picker);

		lblarrive = new JLabel("Arrival");
		Panel1.add(lblarrive);

		chckbx_same_time = new JCheckBox("Same Date");
		chckbx_same_time.setSelected(true);
		Panel1.add(chckbx_same_time);

		Panel1.add((JComponent) arrival_date_picker);

		spinner_days = new JSpinner();
		Panel2.add(spinner_days);
		spinner_days.setModel(new SpinnerNumberModel(new Integer(500), null, null, new Integer(1)));

		lblDays = new JLabel("Days");
		Panel2.add(lblDays);

		spinner_steps = new JSpinner();
		Panel2.add(spinner_steps);
		spinner_steps.setModel(new SpinnerNumberModel(new Integer(10), null, null, new Integer(1)));

		lblSteps = new JLabel("Steps");
		Panel2.add(lblSteps);

		btn_make_plot = new JButton("Make Plot");
		SearchIntervalPanel.add(btn_make_plot);

		btn_make_plot.addActionListener(pcpE);

		add(MissionSelectPanel);
		// MissionSelectPanel.setLayout(new BoxLayout(MissionSelectPanel,
		// BoxLayout.Y_AXIS));
		MissionSelectPanel.setLayout(new GridLayout(7, 1));

		btnSelectFlight = new JButton("Select Flight");
		MissionSelectPanel.add(btnSelectFlight);

		lblDepartureDate = new JLabel("Departure Date");
		MissionSelectPanel.add(lblDepartureDate);

		field_selected_departure_date = new JFormattedTextField();

		Dimension maximumSize = new Dimension(1, 1);
		// field_selected_departure_date.setMaximumSize(maximumSize);
		MissionSelectPanel.add(field_selected_departure_date);

		lblArrivalDate = new JLabel("Arrival Date");
		MissionSelectPanel.add(lblArrivalDate);

		field_selected_arrival_date = new JFormattedTextField();
		MissionSelectPanel.add(field_selected_arrival_date);

		lblTotalDeltav = new JLabel("Total DeltaV");
		MissionSelectPanel.add(lblTotalDeltav);

		field_total_deltav = new JFormattedTextField();
		MissionSelectPanel.add(field_total_deltav);

		OptionPanel.setLayout(new javax.swing.BoxLayout(OptionPanel, javax.swing.BoxLayout.Y_AXIS));

		OptionPanel.setBorder(new javax.swing.border.TitledBorder("Options"));
		jCheckBox1.setSelected(surf.getLogZscaling());
		jCheckBox1.setText("Log Z Axis");

		jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jCheckBox1ActionPerformed(evt);
			}
		});

		OptionPanel.add(jCheckBox1);

		jCheckBox2.setToolTipText("Perspective or Parallel projection");
		jCheckBox2.setText("Perspective");
		jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JCheckBox2ActionPerformed(evt);
			}
		});

		OptionPanel.add(jCheckBox2);

		add(OptionPanel);

	}

	private void JCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {
		plot.setParallelProjection(!jCheckBox2.isSelected());
	}

	private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {
		surf.setLogZscaling(jCheckBox1.isSelected());
	}

}
