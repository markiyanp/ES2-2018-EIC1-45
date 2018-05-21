package jMetal.integerProblems;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.operator.impl.crossover.IntegerSBXCrossover;
import org.uma.jmetal.operator.impl.mutation.IntegerPolynomialMutation;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import jMetal.AbstractExperiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExperimentsInteger extends AbstractExperiment {
	private static int INDEPENDENT_RUNS = 5;
	private static int maxEvaluations = 500;

	public void execute() throws IOException {
		List<ExperimentProblem<IntegerSolution>> problemList = new ArrayList<>();
		if (isJar()) {
			INDEPENDENT_RUNS = 2;
			maxEvaluations = 250;
			problemList.add(new ExperimentProblem<>(new MyProblemInteger(getLimits_Int(), isJar(), getJarPath(), getProblemName())));
		}
		else { 
			INDEPENDENT_RUNS = 5;
			maxEvaluations = 500;
			problemList.add(new ExperimentProblem<>(new MyProblemInteger(getLimits_Int(), isJar(), null, getProblemName())));
		}
		String experimentBaseDirectory = "experimentBaseDirectory";

		
		List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithmList = configureAlgorithmList(
				problemList, getAlgorithm());

		Experiment<IntegerSolution, List<IntegerSolution>> experiment = new ExperimentBuilder<IntegerSolution, List<IntegerSolution>>(
				"ExperimentsInteger").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(experimentBaseDirectory).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR")
						.setReferenceFrontDirectory(experimentBaseDirectory + "/referenceFronts")
						.setIndicatorList(Arrays.asList(new PISAHypervolume<IntegerSolution>()))
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

		new ExecuteAlgorithms<>(experiment).run();
		new GenerateReferenceParetoFront(experiment).run();
		new ComputeQualityIndicators<>(experiment).run();
		new GenerateLatexTablesWithStatistics(experiment).run();
		new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
	}

	private List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> configureAlgorithmList(
			List<ExperimentProblem<IntegerSolution>> problemList, String algo) {
		List<ExperimentAlgorithm<IntegerSolution, List<IntegerSolution>>> algorithms = new ArrayList<>();

		for (int i = 0; i < problemList.size(); i++) {

			switch (algo) {
			case "NSGAII":
				Algorithm<List<IntegerSolution>> algorithm1 = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
						new IntegerSBXCrossover(0.9, 20.0),
						new IntegerPolynomialMutation(1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
								.setMaxEvaluations(maxEvaluations).setPopulationSize(100).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm1, "NSGAII", problemList.get(i).getTag()));
				break;
			case "SMSEMOA":
				Algorithm<List<IntegerSolution>> algorithm2 = new SMSEMOABuilder<>(problemList.get(i).getProblem(),
						new IntegerSBXCrossover(0.9, 20.0),
						new IntegerPolynomialMutation(1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", problemList.get(i).getTag()));
				break;
			case "MOCell":
				Algorithm<List<IntegerSolution>> algorithm3 = new MOCellBuilder<>(problemList.get(i).getProblem(),
						new IntegerSBXCrossover(0.9, 20.0),
						new IntegerPolynomialMutation(1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
								.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", problemList.get(i).getTag()));
				break;
			case "PAES":
				Algorithm<List<IntegerSolution>> algorithm4 = new PAESBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).setArchiveSize(100).setBiSections(2)
						.setMutationOperator(new IntegerPolynomialMutation(
								1 / problemList.get(i).getProblem().getNumberOfVariables(), 20.0))
						.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm4, "PAES", problemList.get(i).getTag()));
				break;
			case "RandomSearch":
				Algorithm<List<IntegerSolution>> algorithm5 = new RandomSearchBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm5, "RandomSearch", problemList.get(i).getTag()));
				break;
			default:
				throw new IllegalStateException("This has got to stop. How did algorithm parsing succeed until the Experiment?");
			}
		}
		return algorithms;
	}

}
