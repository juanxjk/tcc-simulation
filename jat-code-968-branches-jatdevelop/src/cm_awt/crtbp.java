package cm_awt;

/*
 *  File        :   crtbp.java
 *  Author      :   Tobias Berthold
 *  Date        :   10-8-2001
 *  Change      :   
 *  Description :   Circular restricted three body problem
 */



public class crtbp
{
    // Constants
       
    // Variables
    globals g;      // for read only
    double[] X_array; //holds current valuesof X
    double t,told; // current time 
    crtbp_derivs crtbp_num;     // numerical integration object
    //double mu;
    double x,y,z,xdot,ydot,zdot;
    double C;
    double r1,r2;
    double fac1,fac2;
    

    crtbp(globals g)
    {
        this.g=g;
        X_array=new double[18];
        System.arraycopy(g.X_array, 0, X_array, 0, 18);
	    // Initialize integrator
        crtbp_num=new crtbp_derivs(g);
   
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
            System.arraycopy(crtbp_num.step(t, X_array, 18, t-told),0,X_array,0,18);
  		    told=t;
        }        
    }

    public void propagate()
    {
        // Integrate trajectory
        System.arraycopy(crtbp_num.step(t, X_array, 18, g.t_step),0,X_array,0,18);
    }
    
    void compute_C()
    {
        x=X_array[0];
        y=X_array[1];
        z=X_array[2];
        xdot=X_array[3];
        ydot=X_array[4];
        zdot=X_array[5];
        r1=Math.sqrt((x+g.mu)*((x+g.mu))+y*y+z*z);
        r2=Math.sqrt((x-1+g.mu)*(x-1+g.mu)+y*y+z*z);
        fac1=(1-g.mu)/r1;
        fac2=g.mu/r2;

        C=x*x+y*y+2*fac1+2*fac2-xdot*xdot-ydot*ydot;
        
    }

 	
	//{{DECLARE_CONTROLS
	//}}
}
