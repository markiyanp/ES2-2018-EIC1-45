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
import graphics.Objectives_Aux_Graphic;
import graphics.Variables_Aux_Graphic;

/**
 * @author Tiago Almeida
 *
 */
public class GraphicsTab extends JPanel {

	private static final long serialVersionUID = 6741926429964865025L;
	private JPanel pnlGraph = new JPanel();
	private Individual_Graphic g = new Individual_Graphic();
	private ArrayList<XYChart> charts;
	private ArrayList<JPanel> list = new ArrayList<>();
	private JTable table;
	private String[] table_column_names = { "Index", "Name" };
	private Object[][] table_data_objectives = {};
	private Object[][] table_data_variables = {};
	private String path;
	private Window window;
	private ActionListener action_listener;
	private ArrayList<String> variables_name_aux = new ArrayList<String>();
	private ArrayList<String> objectives_name_aux = new ArrayList<String>();

	// ------------------------Panels-------------------------------------
	private Border blackline = BorderFactory.createLineBorder(Color.BLACK, 2);
	private JPanel experiments_panel = new JPanel();
	private JPanel algorithm_panel = new JPanel();
	private JPanel problem_panel = new JPanel();
	private JPanel table_panel = new JPanel();

	// -----------------------Labels--------------------------------------
	private JLabel experiments_label;
	private JLabel algorithm_name_label;
	private JLabel problem_name_label;

	// -----------------------Buttons------------------------------------
	private Button variables_button = new Button("Variables");
	private Button objectives_button = new Button("Objectives");

	/**
	 * The constructor
	 */
	public GraphicsTab(Window window) {
		this.window = window;
		pnlGraph.setLayout(null);
		pnlGraph.setBackground(Color.LIGHT_GRAY);
		setBackground(Color.LIGHT_GRAY);
		pnlGraph.setBounds(0, 0, 860, 400);
		
		createActionListener();
		
		experiments_panel.setBorder(blackline);
		experiments_panel.setBackground(new Color(255, 242, 211));
		experiments_panel.setBounds(20, 10, 165, 30);
		experiments_panel.setOpaque(true);
		experiments_panel.setLayout(null);

		algorithm_panel.setBorder(blackline);
		algorithm_panel.setBackground(new Color(255, 242, 211));
		algorithm_panel.setBounds(205, 10, 125, 30);
		algorithm_panel.setOpaque(true);
		algorithm_panel.setLayout(null);

		problem_panel.setBorder(blackline);
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

		variables_button.addActionListener(action_listener);
		objectives_button.addActionListener(action_listener);

		add(experiments_panel);
		add(algorithm_panel);
		add(problem_panel);
		table_panel();
		add(pnlGraph);
		add(variables_button);
		add(objectives_button);
	}

	private void table_panel() {
		table_panel.setBounds(0, 400, 860, 500);
		table_panel.setOpaque(false);
		table_panel.setLayout(null);

		DefaultTableModel model = tableModel(this.table_data_variables, this.table_column_names);
		this.table = new JTable(model);

		this.table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int rowAtPoint = table.rowAtPoint(e.getPoint());
				remove(pnlGraph);
				pnlGraph = list.get(rowAtPoint);
				pnlGraph.setBounds(0, 50, 838, 330);
				add(pnlGraph);
				repaint();
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
		table_panel.add(sp);
		add(table_panel);
	}

	/**
	 * Set a panel with graphic
	 */

	/**
	 * Create the panels
	 */
	private void createPanels() {
		list.clear();
		for (int i = 0; i < this.charts.size(); i++) {
			JPanel pan = new JPanel();
			pan.setBounds(0, 0, 838, 570);
			list.add(new XChartPanel<>(this.charts.get(i)));
		}
	}

	/**
	 * The listener
	 */
	public void createActionListener() {
		ActionListener lis = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == variables_button) {
					objectives_button.setEnabled(true);
					variables_button.setEnabled(false);
					setDoneVariables();
					charts.clear();

				} else if (e.getSource() == objectives_button) {
					variables_button.setEnabled(true);
					objectives_button.setEnabled(false);
					setDoneObjectives();
					charts.clear();
				}
			}
		};
		this.action_listener = lis;
	}

	/**
	 * Reads the Objectives data from the gui and creates the chart source info.
	 * @return Variables_Aux_Graphic
	 */
	public Variables_Aux_Graphic processVariables() {
		String algorithm_name = window.getRight_panel().getOpt_tab().getAlgo_name_field().getSelectedItem().toString()
				.trim();
		
		algorithm_name_label = new JLabel(algorithm_name);
		algorithm_name_label.setFont(new Font("SansSerif", Font.BOLD, 15));
		algorithm_name_label.setBounds(10, 0, 200, 30);
		algorithm_panel.add(algorithm_name_label);
		
		String problem_name = window.getRight_panel().getOpt_tab().getProblem_name_field().getText();

		problem_name_label = new JLabel(problem_name);
		problem_name_label.setFont(new Font("SansSerif", Font.BOLD, 15));
		problem_name_label.setBounds(10, 0, 200, 30);
		problem_panel.add(problem_name_label);
		
		Object[][] variables = window.getRight_panel().getOpt_tab().getData();
		for (int i = 0; i < variables.length; i++) {
			String variable_name = "";
			if (variables[i][1].equals("Integer") && variables[i][5].equals(Boolean.TRUE)) {
				experiments_label = new JLabel("ExperimentsInteger");
				experiments_label.setFont(new Font("SansSerif", Font.BOLD, 15));
				experiments_label.setBounds(10, 0, 200, 30);
				experiments_panel.add(experiments_label);
				variable_name = variables[i][0].toString();
				switch (algorithm_name) {
				case "NSGAII":
					path = "experimentBaseDirectory/ExperimentsInteger/data/NSGAII/" + problem_name
							+ "/BEST_HV_VAR.tsv";
					break;
				case "SMSEMOA":
					path = "experimentBaseDirectory/ExperimentsInteger/data/SMSEMOA/" + problem_name
							+ "/BEST_HV_VAR.tsv";
					break;
				case "MOCell":
					path = "experimentBaseDirectory/ExperimentsInteger/data/MOCell/" + problem_name
							+ "/BEST_HV_VAR.tsv";
					break;
				case "PAES":
					path = "experimentBaseDirectory/ExperimentsInteger/data/PAES/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "RandomSearch":
					path = "experimentBaseDirectory/ExperimentsInteger/data/RandomSearch/" + problem_name
							+ "/BEST_HV_VAR.tsv";
					break;
				}
			}
			if (variables[i][1].equals("Double") && variables[i][5].equals(Boolean.TRUE)) {
				experiments_label = new JLabel("ExperimentsDouble");
				experiments_label.setFont(new Font("SansSerif", Font.BOLD, 15));
				experiments_label.setBounds(10, 0, 200, 30);
				experiments_panel.add(experiments_label);
				switch (algorithm_name) {
				case "NSGAII":
					path = "experimentBaseDirectory/ExperimentsDouble/data/NSGAII/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "SMSEMOA":
					path = "experimentBaseDirectory/ExperimentsDouble/data/SMSEMOA/" + problem_name
							+ "/BEST_HV_VAR.tsv";
					break;
				case "GDE3":
					path = "experimentBaseDirectory/ExperimentsDouble/data/GDE3/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "IBEA":
					path = "experimentBaseDirectory/ExperimentsDouble/data/IBEA/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "MOCell":
					path = "experimentBaseDirectory/ExperimentsDouble/data/MOCell/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "MOEAD":
					path = "experimentBaseDirectory/ExperimentsDouble/data/MOEAD/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "PAES":
					path = "experimentBaseDirectory/ExperimentsDouble/data/PAES/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "RandomSearch":
					path = "experimentBaseDirectory/ExperimentsDouble/data/RandomSearch/" + problem_name
							+ "/BEST_HV_VAR.tsv";
					break;
				}
			}
			if (variables[i][1].equals("Binary") && variables[i][5].equals(Boolean.TRUE)) {
				experiments_label = new JLabel("ExperimentsBinary");
				experiments_label.setFont(new Font("SansSerif", Font.BOLD, 15));
				experiments_label.setBounds(10, 0, 200, 30);
				experiments_panel.add(experiments_label);
				switch (algorithm_name) {
				case "NSGAII":
					path = "experimentBaseDirectory/ExperimentsBinary/data/NSGAII/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "SMSEMOA":
					path = "experimentBaseDirectory/ExperimentsBinary/data/SMSEMOA/" + problem_name
							+ "/BEST_HV_VAR.tsv";
					break;
				case "MOCell":
					path = "experimentBaseDirectory/ExperimentsBinary/data/MOCell/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "MOCH":
					path = "experimentBaseDirectory/ExperimentsBinary/data/MOCH/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "PAES":
					path = "experimentBaseDirectory/ExperimentsBinary/data/PAES/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				case "RandomSearch":
					path = "experimentBaseDirectory/ExperimentsBinary/data/RandomSearch/" + problem_name
							+ "/BEST_HV_VAR.tsv";
					break;
				case "SPEA2":
					path = "experimentBaseDirectory/ExperimentsBinary/data/SPEA2/" + problem_name + "/BEST_HV_VAR.tsv";
					break;
				}
			}
			variables_name_aux.add(variable_name);
			variable_name = "";
		}
		return new Variables_Aux_Graphic(path, variables_name_aux);
	}

	/**
	 * Reads the Objectives data from the gui and creates the chart source info.
	 * @return Objectives_Aux_Graphic
	 */
	public Objectives_Aux_Graphic processObjectives() {
		String algorithm_name = window.getRight_panel().getOpt_tab().getAlgo_name_field().getSelectedItem().toString()
				.trim();
		
		algorithm_name_label = new JLabel(algorithm_name);
		algorithm_name_label.setFont(new Font("SansSerif", Font.BOLD, 15));
		algorithm_name_label.setBounds(10, 0, 200, 30);
		algorithm_panel.add(algorithm_name_label);
		
		String problem_name = window.getRight_panel().getOpt_tab().getProblem_name_field().getText();

		problem_name_label = new JLabel(problem_name);
		problem_name_label.setFont(new Font("SansSerif", Font.BOLD, 15));
		problem_name_label.setBounds(10, 0, 200, 30);
		problem_panel.add(problem_name_label);
		
		Object[][] objectives = window.getRight_panel().getOpt_tab().getObjectives_data();
		for (int i = 0; i < objectives.length; i++) {
			String objective_name = "";
			if (objectives[i][1].equals("Integer") && objectives[i][2].equals(Boolean.TRUE)) {
				experiments_label = new JLabel("ExperimentsInteger");
				experiments_label.setFont(new Font("SansSerif", Font.BOLD, 15));
				experiments_label.setBounds(10, 0, 200, 30);
				experiments_panel.add(experiments_label);
				objective_name = objectives[i][0].toString();
				switch (algorithm_name) {
				case "NSGAII":
					path = "experimentBaseDirectory/ExperimentsInteger/data/NSGAII/" + problem_name
							+ "/BEST_HV_FUN.tsv";
					break;
				case "SMSEMOA":
					path = "experimentBaseDirectory/ExperimentsInteger/data/SMSEMOA/" + problem_name
							+ "/BEST_HV_FUN.tsv";
					break;
				case "MOCell":
					path = "experimentBaseDirectory/ExperimentsInteger/data/MOCell/" + problem_name
							+ "/BEST_HV_FUN.tsv";
					break;
				case "PAES":
					path = "experimentBaseDirectory/ExperimentsInteger/data/PAES/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "RandomSearch":
					path = "experimentBaseDirectory/ExperimentsInteger/data/RandomSearch/" + problem_name
							+ "/BEST_HV_FUN.tsv";
					break;
				}
			}
			if (objectives[i][1].equals("Double") && objectives[i][2].equals(Boolean.TRUE)) {
				experiments_label = new JLabel("ExperimentsDouble");
				experiments_label.setFont(new Font("SansSerif", Font.BOLD, 15));
				experiments_label.setBounds(10, 0, 200, 30);
				experiments_panel.add(experiments_label);
				switch (algorithm_name) {
				case "NSGAII":
					path = "experimentBaseDirectory/ExperimentsDouble/data/NSGAII/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "SMSEMOA":
					path = "experimentBaseDirectory/ExperimentsDouble/data/SMSEMOA/" + problem_name
							+ "/BEST_HV_FUN.tsv";
					break;
				case "GDE3":
					path = "experimentBaseDirectory/ExperimentsDouble/data/GDE3/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "IBEA":
					path = "experimentBaseDirectory/ExperimentsDouble/data/IBEA/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "MOCell":
					path = "experimentBaseDirectory/ExperimentsDouble/data/MOCell/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "MOEAD":
					path = "experimentBaseDirectory/ExperimentsDouble/data/MOEAD/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "PAES":
					path = "experimentBaseDirectory/ExperimentsDouble/data/PAES/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "RandomSearch":
					path = "experimentBaseDirectory/ExperimentsDouble/data/RandomSearch/" + problem_name
							+ "/BEST_HV_FUN.tsv";
					break;
				}
			}
			if (objectives[i][1].equals("Binary") && objectives[i][2].equals(Boolean.TRUE)) {
				experiments_label = new JLabel("ExperimentsBinary");
				experiments_label.setFont(new Font("SansSerif", Font.BOLD, 15));
				experiments_label.setBounds(10, 0, 200, 30);
				experiments_panel.add(experiments_label);
				switch (algorithm_name) {
				case "NSGAII":
					path = "experimentBaseDirectory/ExperimentsBinary/data/NSGAII/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "SMSEMOA":
					path = "experimentBaseDirectory/ExperimentsBinary/data/SMSEMOA/" + problem_name
							+ "/BEST_HV_FUN.tsv";
					break;
				case "MOCell":
					path = "experimentBaseDirectory/ExperimentsBinary/data/MOCell/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "MOCH":
					path = "experimentBaseDirectory/ExperimentsBinary/data/MOCH/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "PAES":
					path = "experimentBaseDirectory/ExperimentsBinary/data/PAES/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				case "RandomSearch":
					path = "experimentBaseDirectory/ExperimentsBinary/data/RandomSearch/" + problem_name
							+ "/BEST_HV_FUN.tsv";
					break;
				case "SPEA2":
					path = "experimentBaseDirectory/ExperimentsBinary/data/SPEA2/" + problem_name + "/BEST_HV_FUN.tsv";
					break;
				}
			}
			objectives_name_aux.add(objective_name);
			objective_name = "";
		}
		return new Objectives_Aux_Graphic(path, objectives_name_aux);
	}

	/**
	 * Creates the default table model.
	 * @param data_matrix
	 * @param columns
	 * @return
	 */
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

	/**
	 * Sets the objectives chart.
	 */
	public void setDoneObjectives() {
		processObjectives();
		Objectives_Aux_Graphic aux = processObjectives();
		charts = g.getCharts(aux.getPath(), aux.getObjectives_name());
		convertToMatrix_Objectives(charts, aux);
		DefaultTableModel model = tableModel(table_data_objectives, table_column_names);
		table.setModel(model);
		table.repaint();
		createPanels();
	}

	/**
	 * Sets the variables chart.
	 */
	public void setDoneVariables() {
		processVariables();
		Variables_Aux_Graphic aux = processVariables();
		charts = g.getCharts(aux.getPath(), aux.getVariables_name());
		convertToMatrix_Variables(charts, aux);
		DefaultTableModel model = tableModel(table_data_variables, table_column_names);
		table.setModel(model);
		table.repaint();
		createPanels();
	}

	/**
	 * Converts the Objectives charts to Object matrix.
	 * @param charts
	 * @param aux
	 */
	private void convertToMatrix_Objectives(ArrayList<XYChart> charts, Objectives_Aux_Graphic aux) {
		Object[][] ret = new Object[charts.size()][2];
		for (int i = 0; i < charts.size(); i++) {
			ret[i][0] = i;
			ret[i][1] = aux.getObjectives_name().get(i);
		}
		this.table_data_objectives = ret;
	}

	/**
	 * Converts the variables charts to Object matrix.
	 * @param charts
	 * @param aux
	 */
	private void convertToMatrix_Variables(ArrayList<XYChart> charts, Variables_Aux_Graphic aux) {
		Object[][] ret = new Object[charts.size()][2];
		for (int i = 0; i < charts.size(); i++) {
			ret[i][0] = i;
			ret[i][1] = aux.getVariables_name().get(i);
		}
		this.table_data_variables = ret;
	}

}
