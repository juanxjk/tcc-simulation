package jat.examples.jat3D.Switch;

import java.applet.Applet;
import java.awt.BorderLayout;

import com.sun.j3d.utils.applet.MainFrame;


public class SwitchTest extends Applet
{
	
	private static final long serialVersionUID = -1119517782991384420L;
	public SwitchTest()
	{
		setLayout(new BorderLayout());
		SwitchTestplot st = new SwitchTestplot();
		add(st,BorderLayout.CENTER);
	}
	
	public static void main(String[] argv)
	{
		new MainFrame(new SwitchTest(),300,300);
	}
}
