package graphics;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class Individual_Graphic {

	private static XYSeries series;

	public Individual_Graphic() {
		getCharts();
	}
	
	public ArrayList<XYChart> getCharts() {
		
		ArrayList<XYChart> charts = new ArrayList<XYChart>();

		ArrayList<ArrayList<String>> results = readFile("Resources/Results/AntiSpamFilterProblem.NSGAII.rs");
		ArrayList<Integer> xData = new ArrayList<Integer>();
		ArrayList<Double> yData = new ArrayList<Double>();

		for (int i = 0; i < results.size(); i++) {
			XYChart chart = new XYChartBuilder().title("Graphic " + i).xAxisTitle("Rules").yAxisTitle("Weights").build();
			yData.clear();
			xData.clear();
			for (int j = 0; j < results.get(i).size(); j++) {
				xData.add(j);
				yData.add(Double.valueOf(results.get(i).get(j)));
				if(xData.size() == results.get(i).size()) {
					Random rand = new Random();
					float r = rand.nextFloat();
					float g = rand.nextFloat();
					float b = rand.nextFloat();
					Color randomColor = new Color(r, g, b);
					
					series = (XYSeries) chart.addSeries("Solution " + i, xData, yData).setLineColor(randomColor);
					series.setMarker(SeriesMarkers.NONE);  
					charts.add(chart);
				}
			}	
		}
//		new SwingWrapper<XYChart>(charts).displayChartMatrix();
		return charts;
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
