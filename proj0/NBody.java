public class NBody {
    /* Get radius from the txt */
    public static double readRadius(String filename){
        In in = new In(filename);
        int number = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /* Get every planets information from the txt */
    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int number = in.readInt();
        Planet[] planets = new Planet[number];
        in.readDouble();
        for (int i = 0; i < number; i++){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            Planet p = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
            planets[i] = p;
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        double radiusDraw = readRadius(filename);
        Planet[] planetsDraw = readPlanets(filename);

        int planetNumber = planetsDraw.length;

        /* Set the scale of the window */
        StdDraw.setScale(-radiusDraw,radiusDraw);

        /* clear the window */
        StdDraw.clear();

        /* Set the image as the background based on scale */
        StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radiusDraw, 2 * radiusDraw);
        StdDraw.show();

        /* Draw every planet */
        for (int i = 0; i < planetNumber; i++){
            planetsDraw[i].draw();
        }

        /* Animation */
        StdDraw.enableDoubleBuffering();

        for (double time = 0; time < T; time += dt){
            double[] xForces = new double[planetNumber];
            double[] yForces = new double[planetNumber];

            /* Store net xForce and net yForce respectively */
            for (int i = 0; i < planetNumber; i++) {
                xForces[i] = planetsDraw[i].calcNetForceExertedByX(planetsDraw);
                yForces[i] = planetsDraw[i].calcNetForceExertedByY(planetsDraw);
            }

            /* Update every planet with its net xForce and net yForce */
            for (int i = 0; i < planetNumber; i++) {
                planetsDraw[i].update(dt, xForces[i], yForces[i]);
            }

            /* Draw background image */
            StdDraw.picture(0, 0, "images/starfield.jpg", 2 * radiusDraw, 2 * radiusDraw);

            /* Draw every planet */
            for (int i = 0; i < planetNumber; i++) {
                planetsDraw[i].draw();
            }

            /* Show the offscreen buffer */
            StdDraw.show();

            /* Pause the animation */
            StdDraw.pause(10);
        }

        /* Print out the information of planets */
        StdOut.printf("%d\n", planetNumber);
        StdOut.printf("%.2e\n", radiusDraw);
        for (int i = 0; i < planetNumber; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planetsDraw[i].xxPos, planetsDraw[i].yyPos, planetsDraw[i].xxVel,
                    planetsDraw[i].yyVel, planetsDraw[i].mass, planetsDraw[i].imgFileName);
        }
    }
}

