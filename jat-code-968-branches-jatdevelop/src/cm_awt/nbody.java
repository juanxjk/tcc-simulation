package cm_awt;

/*
 *  File        :   nbody.java
 *  Author      :   Tobias Berthold
 *  Date        :   9-14-2001
 *  Change      :   
 *  Description :   
 */


import java.text.*;
import java.io.*;   

public class nbody
{
    // Constants
       
    // Variables
    globals g;      // for read only
    double[] X_array; //holds current valuesof X
    double M;   // total mass
    double cmx, cmy, cmz;   // center of mass
    double r12,r23,r13;
    double v1,v2,v3;   
    double v1squared,v2squared,v3squared;    
    double E,T,U; // total, kinetic, potential energy
    double t,told; // current time 
    nbody_derivs nbody_num;     // numerical integration object


    nbody(globals g)
    {
        this.g=g;
        X_array=new double[18];
        System.arraycopy(g.X_array, 0, X_array, 0, 18);
        // Initial center of mass
        M=g.m1+g.m2+g.m3;
	    cmx=(g.m1*X_array[0]+g.m2*X_array[6]+g.m3*X_array[12])/M;
	    cmy=(g.m1*X_array[1]+g.m2*X_array[7]+g.m3*X_array[13])/M;
	    cmz=(g.m1*X_array[2]+g.m2*X_array[8]+g.m3*X_array[14])/M;
	    // Initialize integrator
        nbody_num=new nbody_derivs(g);
   
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
            System.arraycopy(nbody_num.step(t, X_array, 18, t-told),0,X_array,0,18);
  		    told=t;
        }        
    }

    public void propagate()
    {
        // Integrate trajectory
        System.arraycopy(nbody_num.step(t, X_array, 18, g.t_step),0,X_array,0,18);
        compute_energy();
        // System.out.println(E+" "+T+" "+U);
        // center of mass
        M=g.m1+g.m2+g.m3;
	    cmx=(g.m1*X_array[0]+g.m2*X_array[6]+g.m3*X_array[12])/M;
	    cmy=(g.m1*X_array[1]+g.m2*X_array[7]+g.m3*X_array[13])/M;
	    cmz=(g.m1*X_array[2]+g.m2*X_array[8]+g.m3*X_array[14])/M;
    }
    
    void compute_energy()
    {
        // Energy
        v1squared=X_array[3]*X_array[3]+X_array[4]*X_array[4]+X_array[5]*X_array[5];
        v2squared=X_array[9]*X_array[9]+X_array[10]*X_array[10]+X_array[11]*X_array[11];
        v3squared=X_array[15]*X_array[15]+X_array[16]*X_array[16]+X_array[17]*X_array[17];
        T=.5*(g.m1*v1squared+g.m2*v2squared+g.m3*v3squared);
        r12=Math.sqrt((X_array[6]-X_array[0])*(X_array[6]-X_array[0])+(X_array[7]-X_array[1])*(X_array[7]-X_array[1])+(X_array[8]-X_array[2])*(X_array[8]-X_array[2]));
        r13=Math.sqrt((X_array[12]-X_array[0])*(X_array[12]-X_array[0])+(X_array[13]-X_array[1])*(X_array[13]-X_array[1])+(X_array[14]-X_array[2])*(X_array[14]-X_array[2]));
        r23=Math.sqrt((X_array[12]-X_array[6])*(X_array[12]-X_array[6])+(X_array[13]-X_array[7])*(X_array[13]-X_array[7])+(X_array[14]-X_array[8])*(X_array[14]-X_array[8]));
        U=g.m1*g.m2/r12+g.m2*g.m3/r23+g.m1*g.m3/r13;
        E=T-U;
    }

 	
	//{{DECLARE_CONTROLS
	//}}
}
