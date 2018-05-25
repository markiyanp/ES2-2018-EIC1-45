package jMetal.exampleProblems;

public class AntiSpamFilterProblem_JAR {

	public static void main(String[] args) {
		double[] t = { 0, 0 };
		double[] x = new double[args.length];

		for (int i = 0; i < x.length; i++) {
			x[i] = Double.parseDouble(args[i]);
		}

		double[] s = AntiSpamSolution(t, x);
		System.out.print(s[0] + " " + s[1]);

	}

	private static double[] AntiSpamSolution(double[] t, double[] x) {
		for (int var = 0; var < x.length; var++) {
			if (x[var] > 3)
				t[0] += 1; // FP
		}
		
		for (int var = 0; var < x.length; var++) {
			if (x[var] < -3)
				t[1] += 1; // FN
		}
		return t;
	}

}
