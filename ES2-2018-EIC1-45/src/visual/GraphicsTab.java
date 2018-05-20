package visual;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import graphics.Individual_Graphic;

/**
 * @author Tiago Almeida
 *
 */
public class GraphicsTab extends JPanel{

	private static final long serialVersionUID = 6741926429964865025L; 
	private JPanel pnlGraph = new JPanel();
	private Individual_Graphic g = new Individual_Graphic();
	private ArrayList<XYChart> charts;
	private int pointer = 0;
	private Button prev;
	private Button next;
	private ArrayList<JPanel> list = new ArrayList<>();

	/**
	 * The constructor
	 */
	public GraphicsTab() {
		
		charts = g.getCharts();
		
		pnlGraph.setLayout(null);
		pnlGraph.setBackground(Color.LIGHT_GRAY);
		setBackground(Color.LIGHT_GRAY);
		pnlGraph.setBounds(0, 0, 860, 612);
		
		ActionListener listener = new MyListener();
		prev = new Button("Prev");
		prev.addActionListener(listener);
		next = new Button("Next");
		next.addActionListener(listener);
		
		prev.setBounds(50, 580, 130, 25);
		next.setBounds(200, 580, 130, 25);
		add(prev);
		add(next);
		add(pnlGraph);
		createPanels();
	}

	/**
	 * Set a panel with graphic
	 */
	private void setCurrentPanel() {
		if(list.size() != 0 && pointer >= 0 && pointer < charts.size()) {
			prev.setEnabled(true);
			next.setEnabled(true);
			remove(pnlGraph);
			this.pnlGraph = this.list.get(pointer);
			this.pnlGraph.setBounds(0, 0, 838, 570);
			add(pnlGraph);
			repaint();
		} else {
//			chart_Panel = new XChartPanel<>(this.charts.get(0));
			if(pointer < 0)
				prev.setEnabled(false);
			if(pointer > charts.size() - 1)
				next.setEnabled(false);
		}
	}
	
	/**
	 * Create the panels
	 */
	private void createPanels() {
		for (int i = 0; i < this.charts.size(); i++) {
			JPanel pan = new JPanel();
			pan.setBounds(0, 0, 838, 570);
			list.add(new XChartPanel<>(this.charts.get(i)));
		}
	}

	/**
	 * The listener
	 */
	private class MyListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Button b = (Button) e.getSource();
			switch(b.getLabel()){
			case "Prev":
				if(pointer > -1) 
					pointer--;
				setCurrentPanel();
				break;
			case "Next":
				if(pointer < charts.size()) 
					pointer++;
				setCurrentPanel();
				break;
			default:
				throw new IllegalStateException("Unknown component");
			}
		}
	}

	//			private void graphic_Chooser() {
	//				graphsList = new JComboBox<Object>();
	//				graphsList.setBounds(650, 570, 130, 25);
	//			}

}
