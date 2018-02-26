package cm_awt;

/*
 *  File        :   hill_derivs.java
 *  Author      :   Tobias Berthold
 *  Date        :   11-4-2001
 *  Change      :
 *  Description :   implements specifics of Hill,
 *                  uses class rk8 for Runge Kutta integration
 */


public class hill_derivs extends rk8
{
    // Constants


    // Variables
    //double dxdt[];
    //double mu;
    double xc,yc,zc,xdot,ydot,zdot;
    double r;
    double rcubed;
    double Ux,Uy;
    globals g;

    public hill_derivs(globals g)
    {
        // Initialize
        this.g=g;
        //dxdt=new double[18];

		//{{INIT_CONTROLS
		//}}

	}

    // Overridden methods
    // vector r1 is x[0], x[1], x[2]
    // vector v1 is x[3], x[4], x[5]
    // vector r2 is x[6], x[7], x[8]
    // vector v2 is x[9], x[10], x[11]
    // vector r3 is x[12], x[13], x[14]
    // vector v3 is x[15], x[16], x[17]

    public double[] derivs(double x[], double t)
    {

        double dxdt[]=new double[18];

        xc=x[0];
        yc=x[1];
        xdot=x[3];
        ydot=x[4];

        r=Math.sqrt(xc*xc+yc*yc);
        rcubed=r*r*r;
        Ux=3*xc-xc/rcubed;
        Uy=-yc/rcubed;

        // Derivatives
        dxdt[0] =x[3];
        dxdt[1] =x[4];
        dxdt[2] =x[5];
        dxdt[3] =2*ydot+Ux;
        dxdt[4] =-2*xdot+Uy;
        dxdt[5] =0;
        return dxdt;
    }

	//{{DECLARE_CONTROLS
	//}}
}
