package jat.application.SpaceTimeClock;

import javax.swing.JApplet;

public class SpaceTimeClockMain extends JApplet {

	private static final long serialVersionUID = -6701033069939386151L;
	public SpaceTimeClockGUI ucGUI;
	public SpaceTimeClockParameters ucParam;
	
	public void init() {
		ucParam= new SpaceTimeClockParameters();
		ucGUI=new SpaceTimeClockGUI(this);
		getContentPane().add(ucGUI);
	}

	public void start() {
	
	ucGUI.ucE.timer.start();
	}
}
