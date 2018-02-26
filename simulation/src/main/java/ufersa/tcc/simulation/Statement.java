package ufersa.tcc.simulation;

public class Statement {
	public double ti;
	public double tf;
	public double dX;
	public double dY;
	public double dV;
	public double dV_;

	public Statement(double ti, double tf, double dX, double dY, double dV, double dV_) {
		this.ti = ti;
		this.tf = tf;
		this.dX = dX;
		this.dY = dY;
		this.dV = dV;
		this.dV_ = dV_;
	}

	public boolean isActive(double t_) {
		if (t_ >= this.ti && t_ <= tf)
			return true;
		return false;
	}

}