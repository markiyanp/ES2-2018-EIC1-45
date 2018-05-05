package jMetal.Integer;

public class NMMin {

	public static void main(String[] args) {
		int valueN = 100;
		int valueM = -100;
		int approximationToN;
	    int approximationToM ;
	    approximationToN = 0;
	    approximationToM = 0;
	    for (int i = 0; i < args.length; i++) {
	      int value = Integer.parseInt(args[i]);//solution.getVariableValue(i) ;
	      approximationToN += Math.abs(valueN - value) ;
	      approximationToM += Math.abs(valueM - value) ;
	    }
	    System.out.print("" + approximationToN + " " + approximationToM);
	}
}
