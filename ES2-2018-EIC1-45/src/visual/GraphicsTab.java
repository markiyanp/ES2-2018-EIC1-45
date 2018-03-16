package visual;

import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import graphics.Graphic;

public class GraphicsTab extends JPanel{
	 
	private static final long serialVersionUID = 6741926429964865025L; 
	JPanel pnlChart = new JPanel();
	private Graphic graph = new Graphic();
	private XYChart chart;
	
	public GraphicsTab() {
		
		graphics.Graphic g= new Graphic();
		pnlChart.setBounds(0, 0, 840, 800);
		chart = g.getChart();
		XChartPanel<XYChart> cp = new XChartPanel<>(this.graph.getChart());
		cp.setBounds(0, 0, 838, 612);
		pnlChart.add(cp);
		add(pnlChart);
	}

	public XYChart getChart() {
		return chart;
	}

	public void setChart(XYChart chart) {
		this.chart = chart;
	}

}
