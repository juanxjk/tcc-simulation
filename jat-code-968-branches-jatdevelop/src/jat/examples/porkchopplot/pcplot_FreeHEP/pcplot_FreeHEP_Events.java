package jat.examples.porkchopplot.pcplot_FreeHEP;

import jat.core.spacetime.TimeAPL;
import jat.jat3D.FreeHEP.SurfacePlot;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class pcplot_FreeHEP_Events implements ActionListener {

	pcplot_FreeHEP_GUI pcpGUI;
	pcplot_FreeHEP_main main;
	
	public pcplot_FreeHEP_Events(pcplot_FreeHEP_GUI pcpGUI) {
		this.pcpGUI=pcpGUI;
	}
	
	public void setMain(pcplot_FreeHEP_main main) {
		this.main = main;
	}

	public void actionPerformed(ActionEvent ev) {

		if (ev.getSource() == pcpGUI.btn_make_plot) {
			//System.out.println("make plot button");
            int year = pcpGUI.depart_date_picker.getModel().getYear();
            int month = pcpGUI.depart_date_picker.getModel().getMonth();
            int day = pcpGUI.depart_date_picker.getModel().getDay();

			// System.out.println(year+"/"+day);
            
            main.pcplot_data.search_depart_time_start= new TimeAPL(year, month, day, 1, 1, 1);
            main.pcplot_data.search_arrival_time_start= new TimeAPL(year, month, day, 1, 1, 1);

            Object sp2=main.pcpGUI.spinner_days.getValue();
            int days=Integer.parseInt(sp2.toString() );
            Object sp1=main.pcpGUI.spinner_steps.getValue();
            int steps=Integer.parseInt(sp1.toString() );

            try {
				main.pcplot_data.make_porkchop_plot(main.pcplot_data.search_depart_time_start,days,steps);
			} catch (IOException e) {
				e.printStackTrace();
			}
            main.panel.remove(main.surf);
            main.surf = new SurfacePlot();
            main.surf.setData(main.pcplot_data);
    		main.panel.add(main.surf,BorderLayout.CENTER);
    		main.panel.revalidate();
    		main.panel.repaint();            
            
		}

	}// End of ActionPerformed

}
