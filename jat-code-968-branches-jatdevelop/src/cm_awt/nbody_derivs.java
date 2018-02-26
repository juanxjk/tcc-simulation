package cm_awt;

/*
 *  File        :   nbody_derivs.java
 *  Author      :   Tobias Berthold
 *  Date        :   9-14-2001
 *  Change      :   
 *  Description :   implements specifics of n-body problem,
 *                  uses class rk8 for Runge Kutta integration
 */



public class nbody_derivs extends rk8 
{
    // Constants
    public final static double gamma_0 = 5.380999999999994E-6;
    

    // Variables
    //double dxdt[];
    double m1,m2,m3;
    double r1,r2,r3;
    double r12,r23,r13;
    double r12cubed,r23cubed,r13cubed;
    globals g;
    
    public nbody_derivs(globals g)
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

        m1=g.m1;
        m2=g.m2;
        m3=g.m3;


        r12=Math.sqrt((x[6]-x[0])*(x[6]-x[0])+(x[7]-x[1])*(x[7]-x[1])+(x[8]-x[2])*(x[8]-x[2]));
        r13=Math.sqrt((x[12]-x[0])*(x[12]-x[0])+(x[13]-x[1])*(x[13]-x[1])+(x[14]-x[2])*(x[14]-x[2]));
        r23=Math.sqrt((x[12]-x[6])*(x[12]-x[6])+(x[13]-x[7])*(x[13]-x[7])+(x[14]-x[8])*(x[14]-x[8]));
        r12cubed=r12*r12*r12;
        r13cubed=r13*r13*r13;
        r23cubed=r23*r23*r23;

        // Derivatives
        dxdt[0] =x[3];
        dxdt[1] =x[4];
        dxdt[2] =x[5];
        dxdt[3] =m2/r12cubed*(x[6]-x[0])+m3/r13cubed*(x[12]-x[0]);
        dxdt[4] =m2/r12cubed*(x[7]-x[1])+m3/r13cubed*(x[13]-x[1]);
        dxdt[5] =m2/r12cubed*(x[8]-x[2])+m3/r13cubed*(x[14]-x[2]);
        dxdt[6] =x[9];
        dxdt[7] =x[10];
        dxdt[8] =x[11];
        dxdt[9] =-m1/r12cubed*(x[6]-x[0])+m3/r23cubed*(x[12]-x[6]);
        dxdt[10]=-m1/r12cubed*(x[7]-x[1])+m3/r23cubed*(x[13]-x[7]);
        dxdt[11]=-m1/r12cubed*(x[8]-x[2])+m3/r23cubed*(x[14]-x[8]);
        dxdt[12]=x[15];
        dxdt[13]=x[16];
        dxdt[14]=x[17];
        dxdt[15]=-m1/r13cubed*(x[12]-x[0])-m2/r23cubed*(x[12]-x[6]);
        dxdt[16]=-m1/r13cubed*(x[13]-x[1])-m2/r23cubed*(x[13]-x[7]);
        dxdt[17]=-m1/r13cubed*(x[14]-x[2])-m2/r23cubed*(x[14]-x[8]);
        return dxdt;
    }

	//{{DECLARE_CONTROLS
	//}}
}
