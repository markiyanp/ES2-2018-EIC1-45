package jMetal.binaryProblems;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.algorithm.multiobjective.mochc.MOCHCBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.paes.PAESBuilder;
import org.uma.jmetal.algorithm.multiobjective.randomsearch.RandomSearchBuilder;
import org.uma.jmetal.algorithm.multiobjective.smsemoa.SMSEMOABuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.impl.crossover.HUXCrossover;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.RandomSelection;
import org.uma.jmetal.operator.impl.selection.RankingAndCrowdingSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
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

public class ExperimentsBinary extends AbstractExperiment {
	private static int INDEPENDENT_RUNS = 5;
	private static int maxEvaluations = 500;

	public void execute() throws IOException {
		List<ExperimentProblem<BinarySolution>> problemList = new ArrayList<>();
		if (isJar()) {
			INDEPENDENT_RUNS = 2;
			maxEvaluations = 250;
			problemList.add(new ExperimentProblem<>(new MyProblemBinary(getLimits_Binary(), getNumber_of_objectives(),
					getNumber_of_variables(), isJar(), getJarPath(), getProblemName(), getTimelimit())));
		} else {
			INDEPENDENT_RUNS = 5;
			maxEvaluations = 500;
			problemList.add(new ExperimentProblem<>(new MyProblemBinary(getLimits_Binary(), getNumber_of_objectives(),
					getNumber_of_variables(), isJar(), null, getProblemName(), getTimelimit())));
		}
		String experimentBaseDirectory = "experimentBaseDirectory";

		List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithmList = configureAlgorithmList(
				problemList, getAlgorithm());

		Experiment<BinarySolution, List<BinarySolution>> experiment = new ExperimentBuilder<BinarySolution, List<BinarySolution>>(
				"ExperimentsBinary").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(experimentBaseDirectory).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR")
						.setReferenceFrontDirectory(experimentBaseDirectory + "/referenceFronts")
						.setIndicatorList(Arrays.asList(new PISAHypervolume<BinarySolution>()))
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

		new ExecuteAlgorithms<>(experiment).run();
		new GenerateReferenceParetoFront(experiment).run();
		new ComputeQualityIndicators<>(experiment).run();
		new GenerateLatexTablesWithStatistics(experiment).run();
		new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();
	}

	private List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> configureAlgorithmList(
			List<ExperimentProblem<BinarySolution>> problemList, String algo) {
		List<ExperimentAlgorithm<BinarySolution, List<BinarySolution>>> algorithms = new ArrayList<>();

		for (int i = 0; i < problemList.size(); i++) {

			switch (algo) {
			case "NSGAII":
				Algorithm<List<BinarySolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(
								1.0 / ((MyProblemBinary) problemList.get(i).getProblem()).getNumberOfBits(0)))
										.setMaxEvaluations(maxEvaluations).setPopulationSize(100).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", problemList.get(i).getTag()));
				break;
			case "SMSEMOA":
				Algorithm<List<BinarySolution>> algorithm2 = new SMSEMOABuilder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(
								1.0 / ((MyProblemBinary) problemList.get(i).getProblem()).getNumberOfBits(0)))
										.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm2, "SMSEMOA", problemList.get(i).getTag()));
				break;
			case "MOCell":
				Algorithm<List<BinarySolution>> algorithm3 = new MOCellBuilder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(
								1.0 / ((MyProblemBinary) problemList.get(i).getProblem()).getNumberOfBits(0)))
										.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm3, "MOCell", problemList.get(i).getTag()));
				break;
			case "MOCH":
				Algorithm<List<BinarySolution>> algorithm4 = new MOCHCBuilder(
						(BinaryProblem) problemList.get(i).getProblem()).setMaxEvaluations(maxEvaluations)
								.setCrossover(new HUXCrossover(1.0))
								.setNewGenerationSelection(new RankingAndCrowdingSelection<BinarySolution>(100))
								.setCataclysmicMutation(new BitFlipMutation(0.35))
								.setParentSelection(new RandomSelection<BinarySolution>())
								.setEvaluator(new SequentialSolutionListEvaluator<BinarySolution>()).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm4, "MOCH", problemList.get(i).getTag()));
				break;
			case "PAES":
				Algorithm<List<BinarySolution>> algorithm5 = new PAESBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).setArchiveSize(100).setBiSections(2)
						.setMutationOperator(new BitFlipMutation(
								1.0 / ((MyProblemBinary) problemList.get(i).getProblem()).getNumberOfBits(0)))
						.build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm5, "PAES", problemList.get(i).getTag()));
				break;
			case "RandomSearch":
				Algorithm<List<BinarySolution>> algorithm6 = new RandomSearchBuilder<>(problemList.get(i).getProblem())
						.setMaxEvaluations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm6, "RandomSearch", problemList.get(i).getTag()));
				break;
			case "SPEA2":
				Algorithm<List<BinarySolution>> algorithm7 = new SPEA2Builder<>(problemList.get(i).getProblem(),
						new SinglePointCrossover(1.0),
						new BitFlipMutation(
								1.0 / ((MyProblemBinary) problemList.get(i).getProblem()).getNumberOfBits(0)))
										.setMaxIterations(maxEvaluations).build();
				algorithms.add(new ExperimentAlgorithm<>(algorithm7, "SPEA2", problemList.get(i).getTag()));
				break;
			default:
				throw new IllegalStateException(
						"This has got to stop. How did algorithm parsing succeed until the Experiment?");
			}

		}
		return algorithms;
	}

}
