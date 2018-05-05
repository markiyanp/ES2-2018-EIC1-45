package jMetal.Binary;

import java.util.BitSet;

public class OneZeroMax {

	public static void main(String[] args) {
	    int counterOnes;
	    int counterZeroes;
	    counterOnes = 0;
	    counterZeroes = 0;    
	    for (int i = 0; i < args[0].length(); i++) {
	      if (args[0].charAt(i)=='1') {
	        counterOnes++;
	      } else {
	        counterZeroes++;
	      }
	    }
	    // OneZeroMax is a maximization problem: multiply by -1 to minimize
		System.out.print("" + (-1.0 * counterOnes) + " " + (-1.0 * counterZeroes));
	}
}
