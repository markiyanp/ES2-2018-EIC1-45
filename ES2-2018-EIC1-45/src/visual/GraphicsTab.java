package visual;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
	private ArrayList<JPanel> list = new ArrayList<>();
	private JTable table;
	private String[] table_column_names = { "Index", "Name" };
	private Object[][] table_data = {};
	
	//------------------------Panels-------------------------------------
	private Border blackline = BorderFactory.createLineBorder(Color.BLACK, 2);
	private JPanel experiments_panel = new JPanel();
	private JPanel algorithm_panel = new JPanel();
	private JPanel problem_panel = new JPanel();
	private JPanel tabel_panel = new JPanel();
	
	//-----------------------Labels--------------------------------------
	private JLabel experiments_int = new JLabel("Experiments Integer");
	private JLabel experiments_double = new JLabel("Experiments Double");
	private JLabel experiments_binary = new JLabel("Experiments Binary");
	private JLabel algorithm_name = new JLabel("RandomSearch");
	private JLabel problem_name = new JLabel("MyProblemDouble");
	
	//-----------------------Buttons------------------------------------
	private Button prev;
	private Button next;
	private Button variables_button = new Button("Variables");
	private Button objectives_button = new Button("Objectives");
	

	/**
	 * The constructor
	 */
	public GraphicsTab() {
		
		charts = g.getCharts();
		
		pnlGraph.setLayout(null);
		pnlGraph.setBackground(Color.LIGHT_GRAY);
		setBackground(Color.LIGHT_GRAY);
		pnlGraph.setBounds(0, 0, 860, 400);
		
		
		experiments_double.setFont(new Font("SansSerif", Font.BOLD, 15));
		experiments_double.setBounds(10, 0, 200, 30);
		
		
		algorithm_name.setFont(new Font("SansSerif", Font.BOLD, 15));
		algorithm_name.setBounds(10, 0, 200, 30);
		
		
		problem_name.setFont(new Font("SansSerif", Font.BOLD, 15));
		problem_name.setBounds(10, 0, 200, 30);
		
		experiments_panel.setBorder(blackline);
		experiments_panel.add(experiments_double);
		experiments_panel.setBackground(new Color(255, 242, 211));
		experiments_panel.setBounds(20, 10, 165, 30);
		experiments_panel.setOpaque(true);
		experiments_panel.setLayout(null);
		
		algorithm_panel.setBorder(blackline);
		algorithm_panel.add(algorithm_name);
		algorithm_panel.setBackground(new Color(255, 242, 211));
		algorithm_panel.setBounds(205, 10, 125, 30);
		algorithm_panel.setOpaque(true);
		algorithm_panel.setLayout(null);
		
		problem_panel.setBorder(blackline);
		problem_panel.add(problem_name);
		problem_panel.setBackground(new Color(255, 242, 211));
		problem_panel.setBounds(350, 10, 145, 30);
		problem_panel.setOpaque(true);
		problem_panel.setLayout(null);
		
		variables_button.setBackground(new Color(187, 216, 162));
		variables_button.setFont(new Font("SansSerif", Font.BOLD, 15));
		variables_button.setBounds(600, 10, 100, 30);
		objectives_button.setFont(new Font("SansSerif", Font.BOLD, 15));
		objectives_button.setBackground(new Color(200, 100, 100));
		objectives_button.setBounds(720, 10, 100, 30);
		
		ActionListener listener = new MyListener();
		prev = new Button("Prev");
		prev.addActionListener(listener);
		next = new Button("Next");
		next.addActionListener(listener);
		
		
		prev.setBackground(Color.YELLOW);
		prev.setFont(new Font("SansSerif", Font.BOLD, 15));
		prev.setBounds(230, 580, 130, 25);
		
		next.setBackground(Color.YELLOW);
		next.setFont(new Font("SansSerif", Font.BOLD, 15));
		next.setBounds(430, 580, 130, 25);
		
		add(prev);
		add(next);
		add(experiments_panel);
		add(algorithm_panel);
		add(problem_panel);
		objectives_panel();
		add(pnlGraph);
		add(variables_button);
		add(objectives_button);
		createPanels();
	}

	private void objectives_panel() {
		tabel_panel.setBounds(0, 380, 860, 500);
		tabel_panel.setOpaque(false);
		tabel_panel.setLayout(null);

		DefaultTableModel model = tableModel(this.table_data, this.table_column_names);
		this.table = new JTable(model);

		this.table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int rowAtPoint = table.rowAtPoint(e.getPoint());
				int columnAtPoint = table.columnAtPoint(e.getPoint());
				if (columnAtPoint == table.getColumnCount() - 1) {
					table_data[rowAtPoint][columnAtPoint] = !(boolean) table.getValueAt(rowAtPoint,
							columnAtPoint);
					DefaultTableModel model = tableModel(table_data, table_column_names);
					table.setModel(model);
					table.repaint();
				}
			}
		});

		this.table.setRowHeight(20);
		this.table.setRowSelectionAllowed(true);
		this.table.setColumnSelectionAllowed(false);
		this.table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.table.setPreferredScrollableViewportSize(table.getPreferredSize());

		JTableHeader th = this.table.getTableHeader();
		th.setBackground(Color.darkGray);
		th.setForeground(Color.white);

		JScrollPane sp = new JScrollPane(this.table);
		sp.setBounds(220, 0, 352, 190);

		tabel_panel.add(sp);
		add(tabel_panel);
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
			this.pnlGraph.setBounds(0, 50, 838, 330);
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
	
	private DefaultTableModel tableModel(Object[][] data_matrix, String[] columns) {
		DefaultTableModel model = new DefaultTableModel(data_matrix, columns) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == table.getColumnCount() - 1) {
					return true;
				}
				return false;
			}
		};

		return model;
	}

	//			private void graphic_Chooser() {
	//				graphsList = new JComboBox<Object>();
	//				graphsList.setBounds(650, 570, 130, 25);
	//			}
	
	
}
