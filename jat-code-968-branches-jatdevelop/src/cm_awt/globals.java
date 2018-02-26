package cm_awt;

/*
 *  File        :   globals.java
 *  Author      :   Tobias Berthold
 *  Date        :   9-19-2001
 *  Description :   Contains global variables
 *  Change      :   
 *
 */

class globals implements java.io.Serializable
{
    // Constants

    // Variables
    public char mode;           // N=nbody, C=CRTBP, H=Hill
    public char origin_display; // origin of display: I=IRF, C=CM, 1=body 1, etc.
    public double zoom;         // Zoom factor
    public double t_final;      // final time
    public double t_step;       // time step
    public double m1,m2,m3;     // masses
    public double mu;     // CRTBP parameter
    public double X_array[]=    // intitial r1_x, r1_y, r1_z, v1_x, v1_y, v1_z, etc.
    { 
        10.0,    // r1_x
        0,
        0.,
        0,     // v1_x
        1.0,
        0.,
        -5.,     // r2_x
        8.66025,
        0.,
        -.866025,     // v2_x
        -.5,
        0.,
        -5.,    // r3_x
        -8.66025,
        0.,
        .866025,     // v3_x
        -.5,
        0.,
    };


    public globals()
    {
        mode='P';
        zoom=8.;
        t_final=3.;      // final time
        t_step=0.01;       // time step
        m1=30.;
        m2=30.;
        m3=30.;
        origin_display='I';
        mu=0.2;
		//{{INIT_CONTROLS
		//}}
	}
    
	//{{DECLARE_CONTROLS
	//}}
}