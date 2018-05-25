package jMetal.binaryProblems;

import java.util.BitSet;

import org.uma.jmetal.solution.BinarySolution;

public class OneZeroMax {

	/**
	 * Returns a solution
	 * @param solution
	 * @return solution
	 */
	public static double[] OneZeroMaxSolution(BinarySolution solution) {
	    int counterOnes;
	    int counterZeroes;
	    counterOnes = 0;
	    counterZeroes = 0;

	    BitSet bitset = solution.getVariableValue(0) ;
	    for (int i = 0; i < bitset.length(); i++) {
	      if (bitset.get(i)) {
	        counterOnes++;
	      } else {
	        counterZeroes++;
	      }
	    }
	    double[] returnSolution = new double[2];
	    returnSolution[0] = -1.0 * counterOnes;
	    returnSolution[1] = -1.0 * counterZeroes;
	    // OneZeroMax is a maximization problem: multiply by -1 to minimize
	    return returnSolution;
	}
}
