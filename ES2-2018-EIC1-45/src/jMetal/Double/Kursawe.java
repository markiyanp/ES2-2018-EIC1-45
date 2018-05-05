package jMetal.Double;

public class Kursawe {

	public static void main(String[] args) {
		double aux, xi, xj;
	    double[] fx = new double[2];
	    double[] x = new double[args.length];
	    for (int i = 0; i < args.length; i++) {
		      x[i] = Double.parseDouble(args[i]) ;
		}
	    fx[0] = 0.0;
	    for (int var = 0; var < args.length - 1; var++) {
	      xi = x[var] * x[var];
	      xj = x[var + 1] * x[var + 1];
	      aux = (-0.2) * Math.sqrt(xi + xj);
	      fx[0] += (-10.0) * Math.exp(aux);
	    }
	    fx[1] = 0.0;
	    for (int var = 0; var < args.length; var++) {
	      fx[1] += Math.pow(Math.abs(x[var]), 0.8) +
	        5.0 * Math.sin(Math.pow(x[var], 3.0));
	    }
	    System.out.print("" + fx[0] + " " + fx[1]);
	}
}
