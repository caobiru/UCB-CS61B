public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double g = 6.67e-11;

    /* Construct a planet */
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /* Copy a planet */
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /* Calculate the distance between two planets */
    public double calcDistance(Planet p){
        double r = Math. sqrt((this.xxPos - p.xxPos) * (this.xxPos - p.xxPos) + (this.yyPos - p.yyPos) * (this.yyPos - p.yyPos));
        return r;
    }

    /* Calculate the force on one planet */
    public double calcForceExertedBy(Planet p){
        double f = g * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));
        return f;
    }

    /* Calculate the single force on x axis */
    public double calcForceExertedByX(Planet p){
        double dx = p.xxPos - this.xxPos;
        double fx = this.calcForceExertedBy(p) * dx / this.calcDistance(p);
        return fx;
    }

    /* Calculate the single force on y axis */
    public double calcForceExertedByY(Planet p){
        double dy = p.yyPos - this.yyPos;
        double fy = this.calcForceExertedBy(p) * dy / this.calcDistance(p);
        return fy;
    }

    /* Calculate the total force on x axis */
    public double calcNetForceExertedByX(Planet[] allPlanets){
        double netFx = 0;
        for (int i = 0; i < allPlanets.length; i++){
            if (!this.equals(allPlanets[i])){
                netFx += this.calcForceExertedByX(allPlanets[i]);
            }
        }
        return netFx;
    }

    /* Calculate the total force on y axis */
    public double calcNetForceExertedByY(Planet[] allPlanets){
        double netFy = 0;
        for (int i = 0; i < allPlanets.length; i++){
            if (!this.equals(allPlanets[i])){
                netFy += this.calcForceExertedByY(allPlanets[i]);
            }
        }
        return netFy;
    }

    /* update the position and velocity when a force exerted */
    public void update(double dt, double fX, double fY){
        double aNet, aNetx, aNety;
        aNetx = fX / this.mass;
        aNety = fY / this.mass;
        aNet = Math.sqrt(aNetx * aNetx + aNety * aNety);
        this.xxVel += aNetx * dt;
        this.yyVel += aNety * dt;
        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }

    /* Draw the planet at its location */
    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, this.imgFileName);
    }
}