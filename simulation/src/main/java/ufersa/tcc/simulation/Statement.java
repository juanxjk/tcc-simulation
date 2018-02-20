package ufersa.tcc.simulation;

public class Statement {
	public float ti;
	public float tf;
	public float dX;
	public float dY;
	public float dV;
	public float dV_;

	public Statement(float ti, float tf, float dX, float dY, float dV, float dV_) {
		this.ti = ti;
		this.tf = tf;
		this.dX = dX;
		this.dY = dY;
		this.dV = dV;
		this.dV_ = dV_;
	}

	public boolean isActive(float t_) {
		if (t_ >= this.ti && t_ <= tf)
			return true;
		return false;
	}

}