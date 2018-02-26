package cm_awt;

/*
 *  File        :   rk8.java
 *  Author      :   Dave Gaylor
 *  Date        :   3-14-2001
 *  Change      :   4-3-2001
 *                  different naming of variables
 *  Description :   8th Order Runge-Kutta Integrator for Fixed Step Propagation
 *                  Values from Erwin Fehlberg, NASA TR R-287
 *                  
 *                  
 */
 
abstract public class rk8 
{
    
    // Methods to be implemented in subclasses
    // The derivative dx/dt at a given value of t and x.
    abstract public double[] derivs(double x[], double t);
        
    
    //  t:  current time
    //  x:  values at current time
    //  h:  difference between new time and current time
    public double[] step(double t, double x[], int neqns, double h)
    {
        int n = neqns;
	    double f[][] = new double[13][n];
	    double xt[][] = new double[13][n];

	    double x8th[] = new double[n];
	    double xtmp[] = new double[n];
	    double sum[] = new double[n];


	    final double [] a = { 0.0, 2.0/27.0, 1.0/9.0, 1.0/6.0, 5.0/12.0, 0.5,
	    5.0/6.0, 1.0/6.0, 2.0/3.0, 1.0/3.0, 1.0, 0.0, 1.0 };

	    final double [] c = {41.0/840.0, 0.0, 0.0, 0.0, 0.0, 34.0/105.0, 9.0/35.0,
	    9.0/35.0, 9.0/280.0, 9.0/280.0, 41.0/840.0, 0.0, 0.0};

	    final double [] chat = {0.0, 0.0, 0.0, 0.0, 0.0, 34.0/105.0, 9.0/35.0,
	    9.0/35.0, 9.0/280.0, 9.0/280.0, 0.0, 41.0/840.0, 41.0/840.0};

	    double b[][] = new double [13][12];

	    for (int i = 0; i < 13; i++)
	    {
		    for (int j = 0; j < 12; j++)
		    {
			    b[i][j] = 0.0;
		    }
	    }

	    b[1][0] = 2.0/27.0;
	    b[2][0] = 1.0/36.0;
	    b[2][1] = 1.0/12.0;
	    b[3][0] = 1.0/24.0;
	    b[3][2] = 1.0/8.0;
	    b[4][0] = 5.0/12.0;
	    b[4][2] = -25.0/16.0;
	    b[4][3] = 25.0/16.0;
	    b[5][0] = 1.0/20.0;
	    b[5][3] = 0.25;
	    b[5][4] = 0.2;
	    b[6][0] = -25.0/108.0;
	    b[6][3] = 125.0/108.0;
	    b[6][4] = -65.0/27.0;
	    b[6][5] = 125.0/54.0;
	    b[7][0] = 31.0/300.0;
	    b[7][4] = 61.0/225.0;
	    b[7][5] = -2.0/9.0;
	    b[7][6] = 13.0/900.0;
	    b[8][0] = 2.0;
	    b[8][3] = -53.0/6.0;
	    b[8][4] = 704.0/45.0;
	    b[8][5] = -107.0/9.0;
	    b[8][6] = 67.0/90.0;
	    b[8][7] = 3.0;
	    b[9][0] = -91.0/108.0;
	    b[9][3] = 23.0/108.0;
	    b[9][4] = -976.0/135.0;
	    b[9][5] = 311.0/54.0;
	    b[9][6] = -19.0/60.0;
	    b[9][7] = 17.0/6.0;
	    b[9][8] = -1.0/12.0;
	    b[10][0] = 2383.0/4100.0;
	    b[10][3] = -341.0/164.0;
	    b[10][4] = 4496.0/1025.0;
	    b[10][5] = -301.0/82.0;
	    b[10][6] = 2133.0/4100.0;
	    b[10][7] = 45.0/82.0;
	    b[10][8] = 45.0/164.0;
	    b[10][9] = 18.0/41.0;
	    b[11][0] = 3.0/205.0;
	    b[11][5] = -6.0/41.0;
	    b[11][6] = -3.0/205.0;
	    b[11][7] = -3.0/41.0;
	    b[11][8] = 3.0/41.0;
	    b[11][9] = 6.0/41.0;
	    b[12][0] = -1777.0/4100.0;
	    b[12][3] = -341.0/164.0;
	    b[12][4] = 4496.0/1025.0;
	    b[12][5] = -289.0/82.0;
	    b[12][6] = 2193.0/4100.0;
	    b[12][7] = 51.0/82.0;
	    b[12][8] = 33.0/164.0;
	    b[12][9] = 12.0/41.0;
	    b[12][11] = 1.0;

	    double teval[] = new double [13];
	    for (int i = 0; i < 13; i++)  // find times for function evals
	    {
		    teval[i] = t + a[i] * h;
	    }

        // build f matrix
	    f[0] = derivs( x, t);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * b[1][0] * f[0][i];
	    }
	    f[1] = derivs( xtmp, teval[1]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[2][0]*f[0][i] + b[2][1]*f[1][i]);
	    }
	    f[2] = derivs( xtmp, teval[2]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[3][0]*f[0][i] + b[3][2]*f[2][i]);
	    }
	    f[3] = derivs( xtmp, teval[3]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[4][0]*f[0][i] + b[4][2]*f[2][i] + b[4][3]*f[3][i]);
	    }
	    f[4] = derivs( xtmp, teval[4]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[5][0]*f[0][i] + b[5][3]*f[3][i] + b[5][4]*f[4][i]);
	    }
	    f[5] = derivs( xtmp, teval[5]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[6][0]*f[0][i] + b[6][3]*f[3][i] + b[6][4]*f[4][i] + b[6][5]*f[5][i]);
	    }
	    f[6] = derivs( xtmp, teval[6]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[7][0]*f[0][i] + b[7][4]*f[4][i] + b[7][5]*f[5][i] + b[7][6]*f[6][i]);
	    }
	    f[7] = derivs( xtmp, teval[7]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[8][0]*f[0][i] + b[8][3]*f[3][i] + b[8][4]*f[4][i] + b[8][5]*f[5][i] + b[8][6]*f[6][i] + b[8][7]*f[7][i]);
	    }
	    f[8] = derivs( xtmp, teval[8]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[9][0]*f[0][i] + b[9][3]*f[3][i] + b[9][4]*f[4][i] + b[9][5]*f[5][i] + b[9][6]*f[6][i] + b[9][7]*f[7][i]
		    + b[9][8]*f[8][i]);
	    }
	    f[9] = derivs( xtmp, teval[9]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[10][0]*f[0][i] + b[10][3]*f[3][i] + b[10][4]*f[4][i] + b[10][5]*f[5][i] + b[10][6]*f[6][i] + b[10][7]*f[7][i]
		    + b[10][8]*f[8][i] + b[10][9]*f[9][i]);
	    }
	    f[10] = derivs( xtmp, teval[10]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[11][0]*f[0][i] + b[11][5]*f[5][i] + b[11][6]*f[6][i] + b[11][7]*f[7][i]
		    + b[11][8]*f[8][i] + b[11][9]*f[9][i]);
	    }
	    f[11] = derivs( xtmp, teval[11]);

	    for (int i = 0; i < n; i++)
	    {
		    xtmp[i] = x[i] + h * (b[12][0]*f[0][i] + b[12][3]*f[3][i] + b[12][4]*f[4][i] + b[12][5]*f[5][i] + b[12][6]*f[6][i] + b[12][7]*f[7][i]
		    + b[12][8]*f[8][i] + b[12][9]*f[9][i] + f[11][i]);
	    }
	    f[12] = derivs( xtmp, teval[12]);

	    // construct solutions
	    for (int i = 0; i < n; i++)
	    {
		    x8th[i] = x[i] + h*(chat[5]*f[5][i] + chat[6]*f[6][i] + chat[7]*f[7][i] + chat[8]*f[8][i] + chat[9]*f[9][i] + chat[11]*f[11][i] + chat[12]*f[12][i]);
	    }
	    return x8th;
	}

}

