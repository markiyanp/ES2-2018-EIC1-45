package graphics;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

public class Graphic {

	public Graphic() {
		getChart();   
	}

	public XYChart getChart() {
		XYChart chart = new XYChartBuilder().title("Graphic").xAxisTitle("Rules").yAxisTitle("Weights").build();
		ArrayList<ArrayList<String>> results = readFile("Resources/Results/AntiSpamFilterProblem.NSGAII.rs");
		List<Integer> xData = new ArrayList<Integer>();
		List<Double> yData = new ArrayList<Double>();
		for (int i = 0; i < results.size(); i++) {
			yData.clear();
			for (int j = 0; j < results.get(i).size(); j++) {
				xData.add(j);
				yData.add(Double.valueOf(results.get(i).get(j)));
			}
			chart.addSeries(Integer.toString(i), xData, yData);
			xData.clear();
		}
		chart.getStyler().setChartTitleVisible(true);
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		return chart;
	}

	public static ArrayList<ArrayList<String>> readFile(String fileName) {
		ArrayList<ArrayList<String>> row_list = new ArrayList<>();
		try {
			Scanner scan = new Scanner(new FileReader(fileName)); 
			String line = "";
			while (scan.hasNextLine()) {
				line = scan.nextLine();
				String[] arr = line.split(" ");
				ArrayList<String> list_line = new ArrayList<>();
				for (int i = 0; i < arr.length; i++) {
					list_line.add(arr[i]);
				}
				row_list.add(list_line);
			}
			scan.close();
		}
		catch (FileNotFoundException ex) {
		}
		return row_list;
	}

}