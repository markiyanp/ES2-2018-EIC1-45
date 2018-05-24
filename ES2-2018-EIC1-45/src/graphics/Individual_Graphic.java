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

import visual.Window;

/**
 * @author Tiago Almeida
 *
 */
public class Individual_Graphic {

	private static XYSeries series;
	private ArrayList<XYChart> charts = new ArrayList<XYChart>();

	/**
	 * Returns the graphics
	 * 
	 * @return charts
	 */
	public ArrayList<XYChart> getCharts(String path, ArrayList<String> names) {
		ArrayList<ArrayList<String>> results = readFile(path);
		ArrayList<String> results_column = new ArrayList<String>();
		
		int count;
		count = results.get(0).size();
		
		ArrayList<Integer> xData = new ArrayList<Integer>();
		ArrayList<Double> yData = new ArrayList<Double>();

		for (int i = 0; i < count; i++) {
			results_column.clear();
			for (ArrayList<String> s : results) {
				results_column.add(s.get(i));
			}
			XYChart chart = new XYChartBuilder().title("Graphic " + i).xAxisTitle("Total").yAxisTitle("Weights")
					.build();
			yData.clear();
			xData.clear();
			for (int j = 0; j < results_column.size(); j++) {
				xData.add(j);
				yData.add(Double.valueOf(results_column.get(j)));
				if (xData.size() == results_column.size()) {
					Random rand = new Random();
					float r = rand.nextFloat();
					float g = rand.nextFloat();
					float b = rand.nextFloat();
					Color randomColor = new Color(r, g, b);
					
					series = (XYSeries) chart.addSeries(names.get(i), xData, yData).setLineColor(randomColor);
					series.setMarker(SeriesMarkers.NONE);
					charts.add(chart);
				}
			}
		}
		return charts;
	}

	/**
	 * Read a file
	 * 
	 * @param fileName
	 * @return row_list
	 */
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
		} catch (FileNotFoundException ex) {
		}
		return row_list;
	}

}
