package cm_awt;

/*
 *  File        :   crtbp_derivs.java
 *  Author      :   Tobias Berthold
 *  Date        :   10-8-2001
 *  Change      :
 *  Description :   implements specifics of crtbp problem,
 *                  uses class rk8 for Runge Kutta integration
 */


public class crtbp_derivs extends rk8
{
    // Constants


    // Variables
    //double dxdt[];
    //double mu;
    double xc,yc,zc,xdot,ydot,zdot;
    double r1,r2;
    double r1cubed,r2cubed;
    double fac1,fac2;
    globals g;

    public crtbp_derivs(globals g)
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

        //mu=0.01;
        xc=x[0];
        yc=x[1];
        zc=x[2];
        xdot=x[3];
        ydot=x[4];
        zdot=x[5];

        r1=Math.sqrt((xc+g.mu)*((xc+g.mu))+yc*yc+zc*zc);
        r2=Math.sqrt((xc-1+g.mu)*(xc-1+g.mu)+yc*yc+zc*zc);
        r1cubed=r1*r1*r1;
        r2cubed=r2*r2*r2;
        fac1=-(1-g.mu)/r1cubed;
        fac2=-g.mu/r2cubed;

        // Derivatives
        dxdt[0] =x[3];
        dxdt[1] =x[4];
        dxdt[2] =x[5];
        dxdt[3] =fac1*(xc+g.mu)+fac2*(xc-1+g.mu)+2*ydot+xc;
        dxdt[4] =fac1*(yc)+fac2*(yc)-2*xdot+yc;
        dxdt[5] =fac1*(zc)+fac2*(zc);
        return dxdt;
    }

	//{{DECLARE_CONTROLS
	//}}
}
