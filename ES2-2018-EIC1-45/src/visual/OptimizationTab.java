package visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.commons.mail.EmailException;

import core.User;
import core.Variable;
import email.EMail_Tools;
import xml.ConfigXML;
import xml.ProblemXML;

public class OptimizationTab extends JPanel {

	// *********************DEFINES******************************************

	private final int problem_border_width = 284;
	private final int problem_border_height = 230;

	private final int user_border_width = 260;
	private final int user_border_height = 230;

	private final int variables_border_width = 805;
	private final int variables_border_height = 350;

	private final int tools_border_width = 230;
	private final int tools_border_height = 150;

	private final int algo_border_width = 230;
	private final int algo_border_height = 70;

	// ******************************INSTANCES*********************************************
	private static final long serialVersionUID = 4683732155570118854L;

	// TODO
	private File file_config = new File("Resources/TestXML/Config.xml");
	private File file_problem = new File("Resources/TestXML/TestProblem.xml");

	// ***************************GENERAL_FIELDS********************************************
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private TitledBorder problem_area_border = BorderFactory.createTitledBorder(blackline, "About the problem");
	private TitledBorder user_area_border = BorderFactory.createTitledBorder(blackline, "User Area");
	private TitledBorder variables_area_border = BorderFactory.createTitledBorder(blackline, "Variables");
	private TitledBorder tools_area_border = BorderFactory.createTitledBorder(blackline, "Tools");
	private TitledBorder algo_area_border = BorderFactory.createTitledBorder(blackline, "Algorithm");

	private JPanel problem_panel = new JPanel();
	private JPanel tools_panel = new JPanel();
	private JPanel variables_panel = new JPanel();
	private JPanel user_panel = new JPanel();
	private JPanel algo_panel = new JPanel();

	private ActionListener action_listener;
	private FocusListener focus_listener;
	// ***************************GENERAL_FIELDS********************************************

	// ***************************PROBLEM_FIELDS********************************************
	private JLabel problem_name_label = new JLabel("Problem Name");
	private JLabel problem_description_label = new JLabel("Description");
	private JTextField problem_name_field = new JTextField();
	private JTextArea problem_description_area = new JTextArea();
	private JButton create_problem_button = new JButton("+");
	private String[] variable_types = { "Integer", "Binary", "Double" };
	private JTable table;
	private JLabel variable_name_label = new JLabel("Name");
	private JLabel variable_type_label = new JLabel("Type");
	private JLabel variable_minval_label = new JLabel("Min Val");
	private JLabel variable_maxval_label = new JLabel("Max Val");
	private JLabel variable_restricted_label = new JLabel("Restricted");
	private JTextField variable_name_field = new JTextField();
	private JComboBox<String> variable_type_field = new JComboBox<String>(variable_types);
	private JTextField variable_minval_field = new JTextField();
	private JTextField variable_maxval_field = new JTextField();
	private JTextField variable_restricted_field = new JTextField();
	private JButton variable_add_button = new JButton("Create");
	private JButton variable_remove_button = new JButton("Remove Selected");
	private JButton variable_selectAll_button = new JButton("Enable All");
	private JButton variable_deselectAll_button = new JButton("Disable All");
	private String[] column_names = { "Name", "Type", "Min Val", "Max Val", "Restricted", "Used" };
	private Object[][] data = {};

	// ***************************PROBLEM_FIELDS********************************************

	// ***************************ALGO_FIELDS********************************************
	private JComboBox<String> algo_name_field;
	// ***************************ALGO
	// FIELDS********************************************

	// ***************************USER_FIELDS********************************************
	private JLabel user_name_label = new JLabel("Name");
	private JComboBox<String> user_name_field;
	private JLabel user_email_label = new JLabel("E-mail");
	private JTextField user_email_field = new JTextField();
	private JButton user_choose_button = new JButton("Choose");
	private JButton user_delete_button = new JButton("Delete");
	private JButton user_create_button = new JButton("Create");
	private JButton user_modify_button = new JButton("Modify");
	// ***************************USER_FIELDS********************************************

	// ***************************TOOLS_FIELDS********************************************
	private JButton tools_import_button = new JButton("LOAD");
	private JButton tools_export_button = new JButton("SAVE");
	private JButton tools_reset_button = new JButton("Reset fields");
	private JButton tools_run_button = new JButton("RUN");
	// ***************************TOOLS_FIELDS********************************************

	// ******************************INSTANCES_END******************************************

	public OptimizationTab() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		ConfigXML.readXML(file_config);
		ProblemXML.readXML(file_problem);

		createActionListener();
		createFocusListener();
		problem_panel();
		user_panel();
		tools_panel();
		variables_panel();
		algorithm_panel();

		// TODO remove this!!!!
		loadProblem();
	}

	private void user_panel() {
		user_panel.setBorder(user_area_border);
		user_panel.setBounds(15, 10, user_border_width, user_border_height);
		user_panel.setOpaque(false);
		user_panel.setLayout(null);

		String[] usernames = new String[ConfigXML.config.getUsers().size()];
		for (User u : ConfigXML.config.getUsers()) {
			usernames[ConfigXML.config.getUsers().indexOf(u)] = u.getUsername();
		}
		user_name_field = new JComboBox<String>(usernames);

		for (User u : ConfigXML.config.getUsers()) {
			if (u.getUsername().contains(user_name_field.getSelectedItem().toString())) {
				user_email_field.setText(u.getEmailAddr());
			}
		}

		user_name_field.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				for (User u : ConfigXML.config.getUsers()) {
					if (u.getUsername().contains(user_name_field.getSelectedItem().toString())) {
						user_email_field.setText(u.getEmailAddr());
					}
				}
			}
		});

		user_name_label.setBounds(15, 20, 50, 25);
		user_name_field.setBounds(15, 43, 230, 25);
		user_email_label.setBounds(15, 72, 230, 25);
		user_email_field.setBounds(15, 95, 230, 25);

		user_choose_button.setBounds(15, 135, 230, 23);
		user_delete_button.setBounds(15, 195, 110, 23);
		user_create_button.setBounds(135, 195, 110, 23);
		user_modify_button.setBounds(15, 165, 230, 23);

		user_panel.add(user_choose_button);
		user_panel.add(user_delete_button);
		user_panel.add(user_create_button);
		user_panel.add(user_modify_button);

		user_panel.add(user_email_label);
		user_panel.add(user_email_field);
		user_panel.add(user_name_field);
		user_panel.add(user_name_label);
		add(user_panel);
	}

	private void createActionListener() {
		ActionListener lis = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == variable_add_button) {
					createVariable();
				} else if (e.getSource() == variable_selectAll_button) {
					enableAll();
				} else if (e.getSource() == variable_deselectAll_button) {
					disableAll();
				} else if (e.getSource() == variable_remove_button) {
					removeVariable();
				} else if (e.getSource() == tools_run_button) {
					sendMailAdmin();
				}
			}
		};
		this.action_listener = lis;
	}

	private void createVariable() {
		Object[][] mod = new Object[this.data.length + 1][table.getColumnCount()];
		if (this.data.length > 0) {
			for (int i = 0; i < this.data.length; i++) {
				mod[i] = this.data[i];
			}
		}

		Object[] row = new Object[table.getColumnCount()];

		row[0] = variable_name_field.getText();
		row[1] = variable_type_field.getSelectedItem();
		row[2] = variable_minval_field.getText();
		row[3] = variable_maxval_field.getText();
		row[4] = variable_restricted_field.getText();
		row[5] = false;

		mod[mod.length - 1] = row;

		this.data = mod;

		DefaultTableModel model = tableModel();
		table.setModel(model);
		table.repaint();
	}

	private void disableAll() {
		for (int i = 0; i < this.data.length; i++) {
			this.data[i][table.getColumnCount() - 1] = false;
		}
		DefaultTableModel model = tableModel();
		table.setModel(model);
		table.repaint();

	}

	private void enableAll() {
		for (int i = 0; i < this.data.length; i++) {
			this.data[i][table.getColumnCount() - 1] = true;
		}
		DefaultTableModel model = tableModel();
		table.setModel(model);
		table.repaint();
	}

	private void removeVariable() {
		if (this.data.length != 0 && table.getSelectedRow() >= 0) {
			Object[][] mod = new Object[this.data.length - 1][this.table.getColumnCount()];
			int k = 0;
			for (int i = 0; i < this.data.length; i++) {
				if (i != table.getSelectedRow()) {
					mod[k] = this.data[i];
					k++;
				}
			}
			this.data = mod;
			DefaultTableModel model = tableModel();
			table.setModel(model);
			table.repaint();
		}
	}

	private void sendMailAdmin() {
		User u = new User("default", "group45.dummy.user.1@gmail.com");
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String[] admin = { EMail_Tools.getAdminEmail() };

			EMail_Tools.sendMail("group45.optimization.bot@gmail.com", "******", u.getEmailAddr(), admin, // cc to admin
					"Otimização em curso: " + // need to say what it is
							problem_name_field.getText() + // get the problem's name
							" " + dateFormat.format(date), // and the current date:time
					"Muito obrigado por usar esta plataforma de otimização. "
							+ "Será informado por email sobre o progresso do processo de otimização, "
							+ "quando o processo de otimização tiver atingido 25%, 50%, 75% do total "
							+ "do tempo estimado, " // this train might need to be moved to its own String TODO
							+ "e também quando o processo tiver terminado, "
							+ "com sucesso ou devido à ocorrência de erros.",
					""); // no attachment YET, it needs to be an XML
		} catch (EmailException e1) {
			e1.printStackTrace();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}

	private void tools_panel() {
		tools_panel.setBorder(tools_area_border);
		tools_panel.setBounds(590, 10, tools_border_width, tools_border_height);
		tools_panel.setOpaque(false);
		tools_panel.setLayout(null);

		tools_reset_button.setBounds(15, 65, 200, 30);
		tools_import_button.setBounds(115, 25, 95, 30);
		tools_export_button.setBounds(15, 25, 95, 30);
		tools_run_button.setBounds(15, 105, 200, 30);

		tools_run_button.addActionListener(action_listener);

		tools_panel.add(tools_run_button);
		tools_panel.add(tools_reset_button);
		tools_panel.add(tools_import_button);
		tools_panel.add(tools_export_button);
		add(tools_panel);
	}

	private void algorithm_panel() {
		algo_panel.setBorder(algo_area_border);
		algo_panel.setBounds(590, 170, algo_border_width, algo_border_height);
		algo_panel.setOpaque(false);
		algo_panel.setLayout(null);

		String[] algorithms = new String[ConfigXML.config.getAlgorithms().size()];
		for (String s : ConfigXML.config.getAlgorithms()) {
			algorithms[ConfigXML.config.getAlgorithms().indexOf(s)] = s;
		}

		this.algo_name_field = new JComboBox<String>(algorithms);

		algo_name_field.setBounds(17, 25, 195, 25);

		algo_panel.add(algo_name_field);
		add(algo_panel);
	}

	private void problem_panel() {
		problem_panel.setBorder(problem_area_border);
		problem_panel.setBounds(290, 10, problem_border_width, problem_border_height);
		problem_panel.setOpaque(false);
		problem_panel.setLayout(null);

		problem_name_label.setBounds(15, 25, 100, 20);
		problem_name_field.setBounds(15, 45, 200, 25);
		create_problem_button.setBounds(220, 45, 45, 25);
		create_problem_button.addActionListener(action_listener);

		problem_description_label.setBounds(15, 80, 100, 20);
		problem_description_area.setBounds(15, 100, 250, 115);

		problem_panel.add(create_problem_button);
		problem_panel.add(problem_description_area);
		problem_panel.add(problem_name_field);
		problem_panel.add(problem_name_label);
		problem_panel.add(problem_description_label);
		add(problem_panel);
	}

	private void variables_panel() {
		variables_panel.setBorder(variables_area_border);
		variables_panel.setBounds(15, 250, variables_border_width, variables_border_height);
		variables_panel.setOpaque(false);
		variables_panel.setLayout(null);

		variable_name_label.setBounds(10, 20, 50, 20);
		variable_type_label.setBounds(175, 20, 50, 20);
		variable_minval_label.setBounds(318, 20, 50, 20);
		variable_maxval_label.setBounds(435, 20, 50, 20);
		variable_restricted_label.setBounds(555, 20, 90, 20);

		variable_name_field.setBounds(10, 40, 150, 25);
		variable_name_field.addFocusListener(focus_listener);
		variable_type_field.setBounds(175, 40, 125, 25);
		variable_minval_field.setBounds(318, 40, 100, 25);
		variable_minval_field.addFocusListener(focus_listener);
		variable_maxval_field.setBounds(435, 40, 100, 25);
		variable_maxval_field.addFocusListener(focus_listener);
		variable_restricted_field.setBounds(555, 40, 125, 25);
		variable_restricted_field.addFocusListener(focus_listener);

		variable_add_button.setBounds(690, 40, 105, 25);
		variable_add_button.addActionListener(action_listener);
		variable_remove_button.setBounds(10, 315, 180, 25);
		variable_remove_button.addActionListener(action_listener);

		variable_selectAll_button.setBounds(510, 315, 130, 25);
		variable_selectAll_button.addActionListener(action_listener);
		variable_deselectAll_button.setBounds(650, 315, 130, 25);
		variable_deselectAll_button.addActionListener(action_listener);

		DefaultTableModel model = tableModel();
		this.table = new JTable(model);

		this.table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int rowAtPoint = table.rowAtPoint(e.getPoint());
				int columnAtPoint = table.columnAtPoint(e.getPoint());
				if (columnAtPoint == table.getColumnCount() - 1) {
					data[rowAtPoint][columnAtPoint] = table.getValueAt(rowAtPoint, columnAtPoint);
				}
			}
		});

		table.setRowHeight(20);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		JTableHeader th = this.table.getTableHeader();
		th.setBackground(Color.darkGray);
		th.setForeground(Color.white);

		JScrollPane sp = new JScrollPane(this.table);
		sp.setBounds(10, 77, 785, 230);

		variables_panel.add(sp);
		variables_panel.add(variable_name_field);
		variables_panel.add(variable_type_field);
		variables_panel.add(variable_minval_field);
		variables_panel.add(variable_maxval_field);
		variables_panel.add(variable_restricted_field);
		variables_panel.add(variable_name_label);
		variables_panel.add(variable_type_label);
		variables_panel.add(variable_minval_label);
		variables_panel.add(variable_maxval_label);
		variables_panel.add(variable_restricted_label);
		variables_panel.add(variable_add_button);
		variables_panel.add(variable_remove_button);
		variables_panel.add(variable_selectAll_button);
		variables_panel.add(variable_deselectAll_button);
		add(variables_panel);
	}

	private DefaultTableModel tableModel() {
		DefaultTableModel model = new DefaultTableModel(data, column_names) {
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

	private void loadProblem() {
		Object[][] vars = new Object[ProblemXML.problem.getVariables().size()][6];
		for (Variable var : ProblemXML.problem.getVariables()) {
			vars[ProblemXML.problem.getVariables().indexOf(var)][0] = var.getVariable_name();
			vars[ProblemXML.problem.getVariables().indexOf(var)][1] = var.getVariable_type();
			vars[ProblemXML.problem.getVariables().indexOf(var)][2] = var.getVariable_min_val();
			vars[ProblemXML.problem.getVariables().indexOf(var)][3] = var.getVariable_max_val();
			vars[ProblemXML.problem.getVariables().indexOf(var)][4] = var.getVariable_restricted();
			if (var.isVariable_used().contains("true")) {
				vars[ProblemXML.problem.getVariables().indexOf(var)][5] = new Boolean(true);
			} else {
				vars[ProblemXML.problem.getVariables().indexOf(var)][5] = new Boolean(false);
			}
		}
		this.data = vars;
		this.table.setModel(tableModel());

		this.problem_name_field.setText(ProblemXML.problem.getProblem_name());
		// TODO make text NOT overflow (begin new line)
		this.problem_description_area.setText(ProblemXML.problem.getProblem_description());

		for (int i = 0; i < this.algo_name_field.getItemCount(); i++) {
			if (ProblemXML.problem.getAlgorithm().contains(this.algo_name_field.getItemAt(i))) {
				this.algo_name_field.setSelectedIndex(i);
			}
		}

	}

	// ***************************RESTRICTIONS_VARIABLES********************************************

	private void createFocusListener() {
		FocusListener lis = new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (e.getSource() == variable_name_field) {
					analyzierName();
				} else {
					analyzierType(e);
				}
			}
		};
		this.focus_listener = lis;
	}

	private void analyzierName() {
		if (!(variable_name_field.getText().matches("[a-zA-Z0-9_]*"))) {
			variable_name_field.setText("");
			String try_again = "<html><font color=RED > Please try again... </font></html>";
			String message = "<html><font color=RED > The variable name can only have letters and/or numbers! </font></html>";
			JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
			variable_name_field.requestFocus();
		}
	}

	private void analyzierType(FocusEvent event) {
		String type_name = String.valueOf(variable_type_field.getSelectedItem());
		switch (type_name) {

		case "Double":
			DoubleRestriction(event);
			compareDoubleValues();
			break;

		case "Integer":
			IntegerRestriction(event);
			compareIntegerValues();
			break;

		case "Binary":
			binaryRestriction(event);
			compareBinaryValues();
			break;
		}
	}

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private void DoubleRestriction(FocusEvent value) {
		String try_again = "<html><font color=RED > Please try again... </font></html>";
		String message = null;
		JTextField aux = ((JTextField) value.getComponent());
		String[] array = variable_restricted_field.getText().split(",");
		if (!aux.getText().isEmpty()) {
			if (aux.getText().contains(",") && aux != variable_restricted_field) {
				aux.setText(aux.getText().replaceAll(",", "."));
			}
			if (isInteger(aux.getText())) {
				int number_inserted = Integer.parseInt(aux.getText());
				double minval_suggestion = (double) number_inserted;
				aux.setText(Double.toString(minval_suggestion));
			}
			if (!isDouble(aux.getText())) {
				if (value.getSource() == variable_minval_field) {
					message = "<html><font color=RED > The minium value can only have decimal numbers! </font></html>";
					JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
					variable_minval_field.setText("");
					variable_minval_field.requestFocus();
				} else if (value.getSource() == variable_maxval_field) {
					message = "<html><font color=RED > The maximum value can only have decimal numbers! </font></html>";
					JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
					variable_maxval_field.setText("");
					variable_maxval_field.requestFocus();
				}
			}
			if (value.getSource() == variable_restricted_field) {
				for (String str : array) {
					if (!isDouble(str) || isInteger(str)) {
						message = "<html><font color=RED > The restricted value can only have decimal numbers! </font></html>";
						JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR",
								JOptionPane.ERROR_MESSAGE);
						variable_restricted_field.setText("");
						variable_restricted_field.requestFocus();
					}
				}
			}
		}
	}

	private void compareDoubleValues() {
		try {
			double minval = Double.parseDouble(variable_minval_field.getText());
			double maxval = Double.parseDouble(variable_maxval_field.getText());
			String[] array = variable_restricted_field.getText().split(",");
			double maxval_suggestion = minval + 0.1;
			double aux;
			DecimalFormat df = new DecimalFormat("0.#");
			if (minval > maxval) {
				variable_maxval_field.setText(df.format(maxval_suggestion));
				String try_again = "<html><font color=RED > Please try again... </font></html>";
				String message = "<html><font color=RED > The minimum value can not be bigger than the maximum value! </font></html>";
				JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
				variable_maxval_field.requestFocus();
			}
			if (minval == maxval) {
				variable_maxval_field.setText(df.format(maxval_suggestion));
				String try_again = "<html><font color=RED > Please try again... </font></html>";
				String message = "<html><font color=RED > The values can not be equals! </font></html>";
				JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
				variable_maxval_field.requestFocus();
			}
			for (String str : array) {
				aux = Double.parseDouble(str);
				if (minval >= aux || maxval <= aux) {
					String try_again = "<html><font color=RED > Please try again... </font></html>";
					String message = "<html><font color=RED > The restricted value must be between the minium value and the maximum value! </font></html>";
					JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
					variable_restricted_field.setText("");
					variable_restricted_field.requestFocus();
				}
			}
		} catch (NumberFormatException e) {
		}
	}

	private boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private boolean isArrayInt(String[] array) {
		for (int i = 1; i < array.length; i++) {
			if (isInteger(array[i])) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private void IntegerRestriction(FocusEvent value) {
		String try_again = "<html><font color=RED > Please try again... </font></html>";
		String message;
		JTextField aux = ((JTextField) value.getComponent());
		String[] array = variable_restricted_field.getText().split(",");
		if (!aux.getText().isEmpty()) {
			if (!isInteger(aux.getText())) {
				if (value.getSource() == variable_minval_field) {
					message = "<html><font color=RED > The minium value can only have integer numbers! </font></html>";
					JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
					variable_minval_field.setText("");
					variable_minval_field.requestFocus();
				} else if (value.getSource() == variable_maxval_field) {
					message = "<html><font color=RED > The maximum value can only have integer numbers! </font></html>";
					JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
					variable_maxval_field.setText("");
					variable_maxval_field.requestFocus();
				} if (value.getSource() == variable_restricted_field) {
					for (String str : array) {
						if (!isInteger(str) || isDouble(str)) {
							message = "<html><font color=RED > The restricted value can only have integer numbers! </font></html>";
							JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR",
									JOptionPane.ERROR_MESSAGE);
							variable_restricted_field.setText("");
							variable_restricted_field.requestFocus();
						}
					}
				}
			}
		}
	}

	private void compareIntegerValues() {
		try {
			int minval = Integer.parseInt(variable_minval_field.getText());
			int maxval = Integer.parseInt(variable_maxval_field.getText());
			int maxval_suggestion;
			String[] array = variable_restricted_field.getText().split(",");
			int aux;
			String message;
			String try_again = "<html><font color=RED > Please try again... </font></html>";
			if (isArrayInt(array)) {
				for (int i = 0; i < array.length; i++) {
					aux = Integer.parseInt(array[i]);
					if (minval >= aux || maxval <= aux) {
						variable_restricted_field.setText("");
						try_again = "<html><font color=RED > Please try again... </font></html>";
						message = "<html><font color=RED > The array of restricted value must be between the minium value and the maximum value! </font></html>";
						JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			if (minval > maxval) {
				maxval_suggestion = minval + 1;
				variable_maxval_field.setText(Integer.toString(maxval_suggestion));
				message = "<html><font color=RED > The minimum value can not be bigger than the maximum value! </font></html>";
				JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
				variable_maxval_field.requestFocus();
			}
			if (minval == maxval) {
				maxval_suggestion = minval + 1;
				variable_maxval_field.setText(Integer.toString(maxval_suggestion));
				message = "<html><font color=RED > The values can not be equals! </font></html>";
				JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
				variable_maxval_field.requestFocus();
			}
			int restricted = Integer.parseInt(variable_restricted_field.getText());
			if ((minval >= restricted || maxval <= restricted) && !isArrayInt(array)) {
				int compare_values = maxval - minval;
				if (compare_values == 1) {
					maxval_suggestion = minval + 2;
					variable_maxval_field.setText(Integer.toString(maxval_suggestion));
				}
				variable_restricted_field.setText("");
				message = "<html><font color=RED > The restricted value must be between the minium value and the maximum value! </font></html>";
				JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
				variable_restricted_field.requestFocus();
			}
		} catch (NumberFormatException e) {
		}
	}

	private boolean isBinary(String str) {
		if (str.matches("[0-1]*")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isArrayBinary(String[] array) {
		for (int i = 1; i < array.length; i++) {
			if (isBinary(array[i])) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private void binaryRestriction(FocusEvent value) {
		String try_again = "<html><font color=RED > Please try again... </font></html>";
		String message;
		JTextField aux = ((JTextField) value.getComponent());
		String[] array = variable_restricted_field.getText().split(",");
		if (!aux.getText().isEmpty()) {
			if (!(isBinary(aux.getText()) || isArrayBinary(array))) {
				if (value.getSource() == variable_minval_field) {
					message = "<html><font color=RED > The minium value can only have binary numbers! </font></html>";
					JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
					variable_minval_field.setText("");
					variable_minval_field.requestFocus();
				} else if (value.getSource() == variable_maxval_field) {
					message = "<html><font color=RED > The maximum value can only have binary numbers! </font></html>";
					JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
					variable_maxval_field.setText("");
					variable_maxval_field.requestFocus();
				} else if (value.getSource() == variable_restricted_field) {
					message = "<html><font color=RED > The restricted value can only have binary numbers! </font></html>";
					JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
					variable_restricted_field.setText("");
					variable_restricted_field.requestFocus();
				}
			}
		}
	}

	public int binaryToInteger(String binary) {
		char[] numbers = binary.toCharArray();
		int result = 0;
		for (int i = numbers.length - 1; i >= 0; i--)
			if (numbers[i] == '1')
				result += Math.pow(2, (numbers.length - i - 1));
		return result;
	}

	private void compareBinaryValues() {
		int minval = 0;
		int maxval = 0;
		int restricted = 0;
		String[] array = variable_restricted_field.getText().split(",");
		int aux;
		String message;
		String try_again = "<html><font color=RED > Please try again... </font></html>";
		if (!variable_minval_field.getText().isEmpty() && !variable_maxval_field.getText().isEmpty()) {
			minval = binaryToInteger(variable_minval_field.getText());
			maxval = binaryToInteger(variable_maxval_field.getText());
			if (minval >= maxval && isBinary(variable_minval_field.getText())
					&& isBinary(variable_maxval_field.getText()) && isBinary(variable_restricted_field.getText())) {
				variable_maxval_field.setText("");
				message = "<html><font color=RED > The minimum value can not be bigger than the maximum value! </font></html>";
				JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
				variable_maxval_field.requestFocus();
			}
			if (!variable_restricted_field.getText().isEmpty()) {
				restricted = binaryToInteger(variable_restricted_field.getText());
				if (((minval >= restricted || maxval <= restricted) && (!isArrayBinary(array)
						&& isBinary(variable_minval_field.getText()) && isBinary(variable_maxval_field.getText())
						&& isBinary(variable_restricted_field.getText())))) {
					variable_restricted_field.setText("");
					message = "<html><font color=RED > The restricted value must be between the minium value and the maximum value! </font></html>";
					JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
					variable_restricted_field.requestFocus();
				}
				if (isArrayBinary(array)) {
					for (int i = 0; i < array.length; i++) {
						aux = binaryToInteger(array[i]);
						if (minval >= aux || maxval <= aux) {
							variable_restricted_field.setText("");
							try_again = "<html><font color=RED > Please try again... </font></html>";
							message = "<html><font color=RED > The array of restricted value must be between the minium value and the maximum value! </font></html>";
							JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		}
	}

}