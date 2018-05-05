package jMetal.Double;

public class Kursawe {

	public static double[] kurzaseSolution(double[] fx, double[] x) {
		double aux, xi, xj;
	    for (int i = 0; i < x.length; i++) {
		      x[i] = x[i] ;
		}
	    fx[0] = 0.0;
	    for (int var = 0; var < x.length - 1; var++) {
	      xi = x[var] * x[var];
	      xj = x[var + 1] * x[var + 1];
	      aux = (-0.2) * Math.sqrt(xi + xj);
	      fx[0] += (-10.0) * Math.exp(aux);
	    }
	    fx[1] = 0.0;
	    for (int var = 0; var < x.length; var++) {
	      fx[1] += Math.pow(Math.abs(x[var]), 0.8) +
	        5.0 * Math.sin(Math.pow(x[var], 3.0));
	    }
	    double[] returnArray = new double[2];
	    returnArray[0] = fx[0];
	    returnArray[1] = fx[1];
	    return returnArray;
	}
}
