class Body {
  float x, y, mass; 
  float vx, vy; 
  final static float G = 750;
  Body(float x, float y, float mass, float vx, float vy) {
    this.x = x;
    this.y = y;
    this.mass = mass;
    this.vx = vx;
    this.vy = vy;
  }

  void interact(Body b, float t) {
    final float deltaX  = b.x - this.x;
    final float deltaY = b.y - this.y;
    final float dist2 = pow(deltaX, 2)+pow(deltaY, 2);
    final float dist = sqrt(dist2);
    final float force = G * b.mass * this.mass / dist2;
    final float accelX = force * deltaX / (dist*mass);
    final float accelY = force * deltaY / (dist*mass);
    this.vx += accelX * t;
    this.vy += accelY * t;
    this.x += vx * t;
    this.y += vy * t;
  }


  void draw() {
    fill(255, 255, 0, 200);
    ellipse(this.x, this.y, this.mass, this.mass);
  }
}

float t=0.0;

class Statement {
  float ti; 
  float tf; 
  float dX; 
  float dY; 
  float dV; 
  float dV_;

  Statement(float ti, float tf, float dX, float dY, float dV, float dV_) {
    this.ti= ti; 
    this.tf = tf; 
    this.dX = dX; 
    this.dY =dY; 
    this.dV = dV; 
    this.dV_ = dV_;
  }

  boolean isActive(float t_) {
    if (t_>=this.ti && t_<=tf) return true;
    return false;
  }
}

class Script {
  final ArrayList<Statement> statements = new ArrayList<Statement>();
  Script(ArrayList<Statement> statements) {
  };
  void process(Body b, float dt) {
    float vx = b.vx;
    float vy = b.vy;

    float mv = sqrt(pow(vx, 2)+pow(vy, 2));
    if (mv > 0) {
      vx/=mv;
      vy/=mv;
    }
    float vx_ = -vy;
    float vy_ = vx;

    for (Statement s : statements) {
      if (!s.isActive(t)) continue;
      b.vx += dt*(vx+vx_)/b.mass;
      b.vy += dt*(vy+vy_)/b.mass;
    }
  }

  void addStatement(float ti, float tf, float dX, float dY, float dV, float dV_) {
    this.statements.add(new Statement( ti, tf, dX, dY, dV, dV_);
  }
}

class Rocket {
  Body b; 
  Script script;
  Rocket(float x, float y, float mass, float vx, float vy) {
    this.b = new Body( x, y, mass, vx, vy);
  }
}


float dt=0.1;
Body b;
Body earth;

void setup() {
  size(800, 600);
  b = new Body(width/2, height/2, 100, 0, 0);
  earth = new Body(width/2, height/2+200, 10, 20, 0);
}
void draw() {

  background(0);
  text(t, 50, 50);
  b.draw();
  earth.interact(b, dt);
  earth.draw();
  t+=dt;
}