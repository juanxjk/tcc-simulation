package cm_awt;

/*
 *  File        :   trajectory_panel.java
 *  Author      :   Tobias Berthold
 *  Date        :   9-19-2001
 *  Description :   graphics panel for plotting trajectories
 *  Change      :   10-8-2001   save as text
 *              :   11-4-2001   Hill    
 */

import java.awt.*;
import java.io.*;

public class trajectory_panel extends Panel
{
    nbody nbody;
    crtbp crtbp;
    hill hill;
    int x1pos,y1pos,x2pos,y2pos,x3pos,y3pos;
    int p_x,p_y;    // panel size x,y
    int o_x,o_y;    // origin x,y
    double E0;
    //double mu;
    double L1pos=0.4380759585383650;
    double L4xpos=0.3;
    double L4ypos=0.8660254037844386;
    int cmx_pos, cmy_pos, cmz_pos;
    Graphics draw_gr;
    globals g;      // vars
    
	public trajectory_panel()
	{
		//{{INIT_CONTROLS
		setLayout(null);
		setBackground(java.awt.Color.white);
		setSize(306,192);
		button_start.setLabel("Start");
		add(button_start);
		button_start.setBackground(java.awt.Color.lightGray);
		button_start.setBounds(180,12,39,23);
		button_reset.setLabel("Reset");
		add(button_reset);
		button_reset.setBackground(java.awt.Color.lightGray);
		button_reset.setBounds(240,12,47,23);
		//}}
	
		//{{REGISTER_LISTENERS
		SymMouse aSymMouse = new SymMouse();
		button_start.addMouseListener(aSymMouse);
		SymAction lSymAction = new SymAction();
		button_start.addActionListener(lSymAction);
		button_reset.addActionListener(lSymAction);
		//}}
		
		// my stuff
        g=new globals();
        //this.g =g;
        nbody=new nbody(g);
        crtbp=new crtbp(g);
        hill=new hill(g);
        //zoom=g.zoom;
	}
    
	//{{DECLARE_CONTROLS
	java.awt.Button button_start = new java.awt.Button();
	java.awt.Button button_reset = new java.awt.Button();
	//}}

	/* Format double with Fixed width double to string  Fw.d */
    public static String fwdts (double x, int w, int d) 
    {
        java.text.DecimalFormat fmt = new java.text.DecimalFormat();
        fmt.setMaximumFractionDigits(d);
        fmt.setMinimumFractionDigits(d);
        fmt.setGroupingUsed(false);
        String s = fmt.format(x);
        while (s.length() < w) 
        {
            s = " " + s;
        }
        return s;
    }
	
	public void update(Graphics g)
	{
        paint(g);	    
	}

	public void paint(Graphics g)
	{
	    if(draw_gr==null)
	        draw_gr=this.getGraphics();
        p_x=this.getWidth();
        p_y=this.getHeight();
       
        o_x=p_x/2;
        o_y=p_y/2;
	    g.drawString("o", o_x, o_y);
        draw_bodies(1);
	    //g.drawString("size: "+ p_x+ "  "+p_y,10,10);
	    // crtbp
	    //draw_initial_crtbp(4);
	}

	void redraw()
	{
        nbody=new nbody(g);
        crtbp=new crtbp(g);
        hill=new hill(g);
        draw_gr.setColor(this.getBackground());
        draw_gr.fillRect(0,0,getSize().width,getSize().height);
		repaint();
		//finalize();
	    
	}

	void draw_bodies(int size)
	{
	    double x=0,y=0;

	    if(g.mode=='N')
        {
            // Reference frame origin
	        switch(g.origin_display)
	        {
		        case '1': x=nbody.X_array[0];y=nbody.X_array[1]; break;
		        case '2': x=nbody.X_array[6];y=nbody.X_array[7]; break;
		        case '3': x=nbody.X_array[12];y=nbody.X_array[13]; break;
		        case 'I': ; break;
		        case 'C': ; break;  // to be implemented
		        default: x=0;y=0; break;
	        }

	        // center of mass
            cmx_pos=o_x+(int)(g.zoom*(nbody.cmx-x));
            cmy_pos=o_y-(int)(g.zoom*(nbody.cmy-y));
            draw_gr.setColor(Color.red);
            draw_gr.drawRect(cmx_pos,cmy_pos,size,size);
            // Body 1
	        x1pos=o_x+(int)(g.zoom*(nbody.X_array[0]-x));
            y1pos=o_y-(int)(g.zoom*(nbody.X_array[1]-y));
            draw_gr.setColor(Color.black);
            draw_gr.drawRect(x1pos,y1pos,size,size);
            // Body 2
	        x2pos=o_x+(int)(g.zoom*(nbody.X_array[6]-x));
            y2pos=o_y-(int)(g.zoom*(nbody.X_array[7]-y));
            draw_gr.setColor(Color.green);
            draw_gr.drawRect(x2pos,y2pos,size,size);
            // Body 3
            /*
	        x3pos=o_x+(int)(g.zoom*(nbody.X_array[12]-x));
            y3pos=o_y-(int)(g.zoom*(nbody.X_array[13]-y));
            draw_gr.setColor(Color.blue);
            draw_gr.drawRect(x3pos,y3pos,size,size);
            */
        }

	    if(g.mode=='C')
	    {
            // Reference frame origin
	        switch(g.origin_display)
	        {
		        case '1': x=nbody.X_array[0];y=nbody.X_array[1]; break;
		        case '2': x=nbody.X_array[6];y=nbody.X_array[7]; break;
		        case '3': x=nbody.X_array[12];y=nbody.X_array[13]; break;
		        case 'I': ; break;
		        // borrow C for Li
		        case 'C': x=L1pos;y=0; break;
		        //case 'C': x=L4xpos;y=L4ypos; break;   
		        default: x=0;y=0; break;
	        }

            // primary 1
	        x1pos=o_x+(int)(g.zoom*(-g.mu-x));
            y1pos=o_y-(int)(g.zoom*(0-y));
            draw_gr.setColor(Color.magenta);
    	    draw_gr.drawRect(x1pos,y1pos,size,size);
            // primary 2
	        x1pos=o_x+(int)(g.zoom*(1-g.mu-x));
            y1pos=o_y-(int)(g.zoom*(0-y));
            draw_gr.setColor(Color.magenta);
    	    draw_gr.drawRect(x1pos,y1pos,size,size);
            
            // moving body (body 1 in class) 
	        x1pos=o_x+(int)(g.zoom*(crtbp.X_array[0]-x));
            y1pos=o_y-(int)(g.zoom*(crtbp.X_array[1]-y));
            draw_gr.setColor(Color.black);
    	    draw_gr.drawRect(x1pos,y1pos,size,size);
        }

   	    if(g.mode=='H')
	    {
            // Reference frame origin
	        switch(g.origin_display)
	        {
		        case '1': x=nbody.X_array[0];y=nbody.X_array[1]; break;
		        case '2': x=nbody.X_array[6];y=nbody.X_array[7]; break;
		        case '3': x=nbody.X_array[12];y=nbody.X_array[13]; break;
		        case 'I': ; break;
		        case 'C': ; break;
		        default: x=0;y=0; break;
	        }

            // moving body (body 1 in class) 
	        x1pos=o_x+(int)(g.zoom*(hill.X_array[0]-x));
            y1pos=o_y-(int)(g.zoom*(hill.X_array[1]-y));
            draw_gr.setColor(Color.black);
    	    draw_gr.drawRect(x1pos,y1pos,size,size);
        }

   	    if(g.mode=='P')
	    {
            x=0;y=0;

            
            // moving body (body 1 in class) 
            
	        x1pos=o_x+(int)(g.zoom*(hill.X_array[0]-x));
            y1pos=o_y-(int)(g.zoom*(hill.X_array[1]-y));
            draw_gr.setColor(Color.black);
    	    draw_gr.drawRect(x1pos,y1pos,size,size);
    	    
    	    
    	    // Surface of section plot
    	    /*
	        x1pos=o_x+(int)(g.zoom*(hill.X_array[0]-x));
            y1pos=o_y-(int)(g.zoom*(hill.X_array[3]-y));
            draw_gr.setColor(Color.black);
    	    draw_gr.drawRect(x1pos,y1pos,size,size);
    	    */
    	    
    	    
        }

	}	
	
	class SymMouse extends java.awt.event.MouseAdapter
	{
		public void mouseClicked(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == button_start)
				button_start_MouseClicked(event);
		}
	}

	void button_start_MouseClicked(java.awt.event.MouseEvent event)
	{
		// to do: code goes here.
			 
		button_start_MouseClicked_Interaction1(event);
	}

	void button_start_MouseClicked_Interaction1(java.awt.event.MouseEvent event)
	{
        double t=0.;

	    if(g.mode=='N')
        {

            nbody.compute_energy();
            E0=nbody.E;
            System.out.println("t             x          Delta E         Energy  ");
            while(t<=g.t_final)
            {
                t=t+g.t_step;
                nbody.propagate(); 
        	    draw_bodies(0);
                System.out.println(fwdts(t,4,2)+"    "
                +fwdts(nbody.X_array[0],8,6)+"  "
                +fwdts((E0-nbody.E),8,6)+"  "
                +fwdts(E0,8,6));
            	
                //System.out.println("pos x1 y1");
                //System.out.println(""+nbody.X_array[0]);
            }   
        }
        
	    if(g.mode=='C')
	    {
            crtbp.compute_C();
            System.out.println("C "+crtbp.C);
            System.out.println("t             x            y          xdot        ydot      C");
            while(t<=g.t_final)
            {
                // subtract L1
                System.out.println(fwdts(t,5,3)+"   "
                +fwdts(crtbp.X_array[0]-L1pos,12,10)+"  "
                +fwdts(crtbp.X_array[1],14,12)+"  "
                +fwdts(crtbp.X_array[3],8,6)+"  "
                +fwdts(crtbp.X_array[4],8,6)+"  "
                +crtbp.C);
                // subtract L4
                /*System.out.println(fwdts(t,5,3)+"   "
                +fwdts(crtbp.X_array[0]-L4xpos,12,10)+"  "
                +fwdts(crtbp.X_array[1]-L4ypos,14,12)+"  "
                +fwdts(crtbp.X_array[3],8,6)+"  "
                +fwdts(crtbp.X_array[4],8,6)+"  "
                +crtbp.C);*/
                
                t=t+g.t_step;
                crtbp.propagate(); 
                crtbp.compute_C();
        	    draw_bodies(0);
            }   
        }        

  	    if(g.mode=='H')
	    {
            hill.compute_C();
            System.out.println("C "+hill.C);
            System.out.println("t             x            y          xdot        ydot      C");
            while(t<=g.t_final)
            {
                System.out.println(fwdts(t,5,3)+"   "
                +fwdts(hill.X_array[0],12,10)+"  "
                +fwdts(hill.X_array[1],14,12)+"  "
                +fwdts(hill.X_array[3],8,6)+"  "
                +fwdts(hill.X_array[4],8,6)+"  "
                +hill.C);
                t=t+g.t_step;
                hill.propagate(); 
                hill.compute_C();
        	    draw_bodies(0);
            }   
        }        

  	    if(g.mode=='P')
	    {
	        double x0,ydot0,C,yprevious;
	        boolean stop;
	        int ncross;  //number of crossings
	        
            // Initialize
            //System.out.println("Poincare map");
            //hill.t_step=0.;
            hill.X_array[0]=0.;
            hill.X_array[1]=0.;
            hill.X_array[2]=0.;
            hill.X_array[3]=0.;
            hill.X_array[4]=0.;
            hill.X_array[5]=0.;
            g.zoom=500;
            g.t_step=0.001;
            g.t_final=3.;
            C=4.0;
            x0=0.321;
            //for(x0=-1.5;x0<1.5;x0+=0.1)
            //for(x0=-0.8;x0<=0.4;x0+=0.01)
            {
                // new initial conditions
                hill.X_array[1]=0.;
                hill.X_array[3]=0.;
                ydot0=Math.sqrt(-C+3*x0*x0+2/Math.sqrt(x0*x0));
                hill.X_array[0]=x0;
                hill.X_array[4]=ydot0;
                stop=false;                
                ncross=0;
                t=0.;
                yprevious=hill.X_array[1];
                System.out.println("x0, ydot0 "+x0+"    "+ydot0+" C "+hill.C);

                // Propagate trajectory
                while(stop==false)
                {
                    t=t+g.t_step;
                    hill.propagate(); 
                    hill.compute_C();
                    // Stopping conditions
                    //if(t>g.t_final) stop=true;
                    
                    //System.out.println("y "+hill.X_array[1]);
                    if(Math.abs(hill.X_array[0])+Math.abs(hill.X_array[1])>20.) break;  // Stop when distance too large
                    if(hill.C>4.001 || hill.C<3.99) break;  // pass too close to primary
                    // Poincare dots
                    // Test for sign change
                    if(yprevious<0 &&hill.X_array[1]>0)
                    {
                        ncross++;
                        //draw_bodies(0);
                        //System.out.println("x0 "+x0+" yprev "+yprevious+"  ynew "+hill.X_array[1]);
                        System.out.println("ncross "+ncross+"  x0 "+x0+" x "+hill.X_array[0]+"  xdot "+hill.X_array[3]);
                        //System.out.println("s");
                    }
                    if(ncross>100) stop=true;
                    yprevious=hill.X_array[1];
        	        // trajectories
        	        draw_bodies(0);
                }
            }
        }        

        
	}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == button_start)
				button_start_ActionPerformed(event);
			if (object == button_reset)
				buttonReset_ActionPerformed(event);
			
			
		}
	}

	void button_start_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
	}
	

	void buttonReset_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		button_reset_ActionPerformed_Interaction1(event);
	}

	void button_reset_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
        redraw();		
	}

	void openFileAction(String filename)
	{
		try{
		    FileInputStream fileIn = new FileInputStream(filename);
		    ObjectInputStream in = new ObjectInputStream(fileIn);
		    g=(globals)in.readObject();
		}
		catch (Exception e) {System.out.println(e);}
		redraw();
    }

	void saveFileAction(String filename)
	{
		try{
		    FileOutputStream fileOut = new FileOutputStream(filename);
		    ObjectOutputStream out = new ObjectOutputStream(fileOut);
		    out.writeObject(g);
		}
		catch (Exception e) {System.out.println(e);}

    }

	void saveTextFileAction(String filename)
	{
	    int i;
		try{
		    File out=new File(filename);
		    FileWriter fw=new FileWriter(out);
		    PrintWriter pw=new PrintWriter(fw,true);
		    pw.println("origin_display "+g.origin_display);
		    pw.println("zoom "+g.zoom);
		    pw.println("t_final "+g.t_final);
		    pw.println("t_step "+g.t_step);
		    pw.println("m1 "+g.m1);
		    pw.println("m2 "+g.m2);
		    pw.println("m3 "+g.m3);
		    for(i=0;i<18;i++)
		        pw.println("X_array["+i+"] "+g.X_array[i]);
		    pw.println("mu "+g.mu);
		}
		catch (Exception e) {System.out.println(e);}

    }


}	

