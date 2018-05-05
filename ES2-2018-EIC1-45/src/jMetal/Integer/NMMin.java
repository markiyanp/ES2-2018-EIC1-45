package jMetal.Integer;

public class NMMin {

	public static double[] NMMinSolution(double[] fx, int[] x) {
		int valueN = 100;
		int valueM = -100;
	    fx[0] = 0;
	    fx[1] = 0;
	    for (int i = 0; i < x.length; i++) {
	      int value = x[i];
	      fx[0] += Math.abs(valueN - value) ;
	      fx[1] += Math.abs(valueM - value) ;
	    }
	    double[] returnSolution = new double[2];
	    returnSolution[0] = fx[0];
	    returnSolution[1] = fx[1];
	    return returnSolution;
	}
}
