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
  
  String toString(){
   return "VelX" + this.vx; 
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
    this.ti  =  ti; 
    this.tf  =  tf; 
    this.dX  =  dX; 
    this.dY  =  dY; 
    this.dV  =  dV; 
    this.dV_ =  dV_;
  }

  boolean isActive(float t_) {
    if (t_>=this.ti && t_<=tf) return true;
    return false;
  }
}

class Script {
  final ArrayList<Statement> statements = new ArrayList<Statement>();
  Script() {
  };
  Script(ArrayList<Statement> statements) {
  };
  void process(Body b, float dt) {
    float vx = b.vx ;
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
      float dvx = s.dX + (vx * s.dV + vx_ * s.dV_); 
      float dvy = s.dY + (vy * s.dV + vy_ * s.dV_); 
      b.vx += dt*(dvx)/b.mass;
      b.vy += dt*(dvy)/b.mass;
    }
  }

  void addStatement(float ti, float tf, float dX, float dY, float dV, float dV_) {
    this.statements.add(new Statement(ti, tf, dX, dY, dV, dV_));
  }
}

class Rocket {
  Body b; 
  Script script;
  Rocket(float x, float y, float mass, float vx, float vy) {
    this.b = new Body( x, y, mass, vx, vy);
    this.script = new Script();
  }
  void interact(Body b, float t) {
    this.b.interact(b, t);
  }
  void draw() {
    b.draw();
  }

  void addStatement(Statement s) {
    this.script.addStatement( s.ti, s.tf, s.dX, s.dY, s.dV, s.dV_);
  }

  void simulate(World w, float dt) {
    for (Body body : w.bodies) {
      this.b.interact(body, dt);
    }
    this.script.process(this.b, dt);
    this.b.draw();
  }
}

class World {

  ArrayList<Body> bodies = new ArrayList<Body>();

  void addBody(Body b) {
    bodies.add(new Body(b.x, b.y, b.mass, b.vx, b.vy));
  }
}


float dt=0.1;
Body b;
Rocket r1;
World w1;
void setup() {
  size(800, 600);
  b = new Body(width/2, height/2, 100, 0, 0);
  r1 = new Rocket(width/2, height/2+200, 10, 20, 0);
  w1 = new World();
  w1.addBody(b);
}


void draw() {

  background(0);
  text(t, 50, 50);
  b.draw();
  //r1.interact(b, dt);
  //r1.draw();
  r1.addStatement(new Statement(10, 11, 0, 0, 0.1, 0));
  r1.simulate(w1, dt );
  t+=dt;
}