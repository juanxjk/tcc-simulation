package jat.examples.porkchopplot.pcplot_FreeHEP;

//import org.freehep.j3d.plot.*;

import jat.jat3D.FreeHEP.SurfacePlot;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class pcplot_FreeHEP_main extends JPanel {
	private static final long serialVersionUID = -2566352019563339568L;
	static JPanel panel;
	SurfacePlot surf;
	pcplot_FreeHEP_GUI pcpGUI;
	pcplot_FreeHEP_Data pcplot_data;
	
	public pcplot_FreeHEP_main() throws java.io.IOException {
		super(new BorderLayout());

		pcplot_data=new pcplot_FreeHEP_Data();
		surf = new SurfacePlot();
		surf.setData(pcplot_data);

		pcpGUI = new pcplot_FreeHEP_GUI(surf);
		pcpGUI.pcpE.setMain(this);
		
		panel = new JPanel(new BorderLayout());
		panel.add(surf, BorderLayout.CENTER);
		panel.add(pcpGUI, BorderLayout.WEST);
		add(panel, BorderLayout.CENTER);

	}

	public static void main(String[] args) throws java.io.IOException {
		JFrame f = new JFrame("Mission Date Chooser");
		f.setContentPane(new pcplot_FreeHEP_main());
		// f.pack();
		f.setSize(new Dimension(600, 600));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
