package cm_awt;

/*
 *  File        :   hill.java
 *  Author      :   Tobias Berthold
 *  Date        :   11-4-2001
 *  Change      :   
 *  Description :   Hill's problem
 */


import java.text.*;
import java.io.*;   

public class hill
{
    // Constants
       
    // Variables
    globals g;      // for read only
    double[] X_array; //holds current valuesof X
    double t,told; // current time 
    double C;       // Jacobi
    hill_derivs hill_num;     // numerical integration object
    

    hill(globals g)
    {
        this.g=g;
        X_array=new double[18];
        System.arraycopy(g.X_array, 0, X_array, 0, 18);
	    // Initialize integrator
        hill_num=new hill_derivs(g);
   
		//{{INIT_CONTROLS
		//}}
	}
   
    
    public void iterate()
    {
        int it;

        t=0;
        told=0;
        System.out.println("pos x1 y1");
        while(t<=10)
        {
            t+=.11;
            // Integrate trajectory                               
            System.arraycopy(hill_num.step(t, X_array, 18, t-told),0,X_array,0,18);
  		    told=t;
        }        
    }

    public void propagate()
    {
        // Integrate trajectory
        System.arraycopy(hill_num.step(t, X_array, 18, g.t_step),0,X_array,0,18);
    }
    
    void compute_C()
    {
        double x,y,z,xdot,ydot,zdot;
        double r,vsquared;
        double U;

        x=X_array[0];
        y=X_array[1];
        z=X_array[2];
        xdot=X_array[3];
        ydot=X_array[4];
        zdot=X_array[5];
        r=Math.sqrt(x*x+y*y);
        vsquared=xdot*xdot+ydot*ydot;
        U=1.5*x*x+1/r;

        C=2*U-vsquared;
        
    }

 	
	//{{DECLARE_CONTROLS
	//}}
}
