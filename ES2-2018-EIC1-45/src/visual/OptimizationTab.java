package visual;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.commons.mail.EmailException;

import core.Objective;
import core.Path;
import core.Problem;
import core.TimeMultiplier;
import core.User;
import core.Variable;
import email.EMail_Tools;
import jMetal.OptimizationProcess;
import xml.ConfigXML;
import xml.ProblemXML;

/**
 * @author Markiyan Pyekh, Tiago Almeida, Paulo Pina
 *
 */
public class OptimizationTab extends JPanel {

	// *********************DEFINES******************************************

	private final int variables_border_width = 805;
	private final int variables_border_height = 350;

	private final int tools_border_width = 162;
	private final int tools_border_height = 230;

	private final Color general_color = new Color(255, 242, 211);
	// ******************************INSTANCES*********************************************
	private static final long serialVersionUID = 4683732155570118854L;

	// ***************************GENERAL_FIELDS********************************************
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private TitledBorder variables_area_border = BorderFactory.createTitledBorder(blackline, "Variables");
	private TitledBorder tools_area_border = BorderFactory.createTitledBorder(blackline, "Tools");
	private TitledBorder restrictions_area_border = BorderFactory.createTitledBorder(blackline, "Settings");
	private TitledBorder objectives_area_border = BorderFactory.createTitledBorder(blackline, "Objectives");

	private Image newfile_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/new.png"));
	private Image save_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/save.png"));
	private Image correct_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/correct.png"));
	private Image import_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/import.png"));
	private Image run_icon = Toolkit.getDefaultToolkit().createImage(LauncherPanel.class.getResource("/icons/run.png"));
	private Image info_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/info.png"));
	private Image about_background = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/aboutframe.png"));
	private Image new_background = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/newproblem_background.png"));
	private Image new_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/plus.png"));

	private JPanel tools_panel = new JPanel();
	private JPanel variables_panel = new JPanel();
	private JPanel restrictions_panel = new JPanel();
	private JPanel objectives_panel = new JPanel();

	private ActionListener action_listener;
	private FocusListener focus_listener;
	// ***************************GENERAL_FIELDS********************************************

	// ***************************SETTINGS_FIELDS********************************************
	private JCheckBox restrictions_jarcheck = new JCheckBox();
	private JLabel restrictions_useexternaljar_label = new JLabel("Use external JAR file?");
	private JLabel restrictions_externaljarpath_label = new JLabel("JAR Path:");
	private JTextField restrictions_externaljarpath_field = new JTextField();
	private JButton restrictions_choosejarpath_button = new JButton("...");
	private JComboBox<String> algo_name_field;
	private JComboBox<String> settings_time_combobox;
	private JLabel settings_algo_label = new JLabel("Algorithm");
	private JLabel settings_max_time_label = new JLabel("Max run time");
	private JSpinner settings_time_spinner;

	// ***************************SETTINGS_FIELDS********************************************

	// ***************************PROBLEM_FIELDS********************************************
	private JButton problem_about_save_button = new JButton("  OK");
	private JButton new_problem_about_save_button = new JButton("  Create");
	private JLabel problem_name_label = new JLabel("Problem Name");
	private JLabel problem_description_label = new JLabel("Description");
	private JTextField problem_name_field = new JTextField();
	private JTextArea problem_description_area = new JTextArea();
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
	private JFrame about_frame = new JFrame("About");
	private JFrame problem_creation_frame = new JFrame("New problem");
	private File file_problem;
	// ***************************PROBLEM_FIELDS********************************************

	// ***************************TOOLS_FIELDS********************************************
	private JButton tools_import_button = new JButton(" LOAD");
	private JButton tools_export_button = new JButton(" SAVE");
	private JButton tools_newproblem_button = new JButton(" CREATE");
	private JButton tools_run_button = new JButton(" RUN");
	private JButton tools_about_button = new JButton(" ABOUT");
	// ***************************TOOLS_FIELDS********************************************

	// ****************************OBJECTIVES_FIELDS*****************************************
	private JLabel objectives_evaluate_label = new JLabel("Objectives type:");
	private JComboBox<String> objectives_possible_types = new JComboBox<String>(variable_types);
	private JTable objectives_table;
	private String[] objectives_column_names = { "Name", "Type", "Used" };
	private Object[][] objectives_data = {};
	private JTextField objectives_newobjective_field = new JTextField();
	private JButton objectives_addobjective_button = new JButton("Add");
	private JButton objectives_delete_button = new JButton("Delete");
	private JLabel objectives_addobjective_label = new JLabel("Name");
	// ****************************OBJECTIVES_FIELDS*****************************************

	// ****************************RESOURCES**************************************************

	private final String thankyou_message = "Thank you for using this optimization platform. "
			+ "You will be informed by e-mail about the optimization's progress "
			+ "periodically and when the process is finished as well, whether it was successful or when errors occur. ";

	// ******************************INSTANCES_END******************************************

	Window window;

	/**
	 * The constructor
	 * 
	 * @param window
	 */
	public OptimizationTab(Window window) {

		this.window = window;
		tools_run_button.setEnabled(false);
		tools_export_button.setEnabled(false);
		tools_about_button.setEnabled(false);

		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		// ConfigXML.readXML(file_config);
		createActionListener();
		createFocusListener();
		tools_panel();
		variables_panel();
		permissionsToCreateVar();
		settings_panel();
		objectives_panel();
	}

	/**
	 * Set in the problem the user who is logged in
	 */
	public void setProblemOwner() {
		ProblemXML.problem.setUser_name(window.getUser().getName());
		ProblemXML.problem.setUser_email(window.getUser().getEmailAddr());
	}

	/**
	 * Handles all button-user interactions.
	 */
	private void createActionListener() {
		ActionListener lis = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == variable_add_button) {
					restrictionToCreateVar(variable_type_field);
					createFocusListener();

				} else if (e.getSource() == variable_selectAll_button) {
					enableAll();

				} else if (e.getSource() == variable_deselectAll_button) {
					disableAll();

				} else if (e.getSource() == variable_remove_button) {
					removeVariable();

				} else if (e.getSource() == tools_export_button) {
					setProblemOwner();
					ProblemXML.problem.setObjectives(toArrayListObjectives(objectives_data));
					ProblemXML.problem.setVariables(toArrayListVariables(data));
					ProblemXML.problem.setAlgorithm(algo_name_field.getSelectedItem().toString());
					ProblemXML.writeXML(ProblemXML.problem, file_problem);
					JOptionPane.showMessageDialog(window, "File has been successfully saved!");
				} else if (e.getSource() == problem_about_save_button) {
					saveAbout();

				} else if (e.getSource() == tools_run_button) {
					runProcess();

				} else if (e.getSource() == tools_about_button) {
					showAboutWindow();

				} else if (e.getSource() == tools_import_button) {
					importProblem();
				} else if (e.getSource() == tools_newproblem_button) {
					createProblem();

				} else if (e.getSource() == new_problem_about_save_button) {
					saveNewAbout();

				} else if (e.getSource() == objectives_addobjective_button) {
					addObjective();
				} else if (e.getSource() == objectives_delete_button) {
					removeObjective();
				}
			}
			
		};
		this.action_listener = lis;
	}
	
	/**
	 * Begins the optimization process according to the settings set on the GUI.
	 */
	private void runProcess() {
		long timelimit = 0;
		timelimit += (int) settings_time_spinner.getValue();

		timelimit = getMaxTime(timelimit);

		// sendMailAdmin();
		OptimizationProcess op1 = new OptimizationProcess();
		op1.setData(data);
		op1.setObjectives(objectives_data);
		op1.setAlgorithm(((String) algo_name_field.getSelectedItem()).trim());
		op1.setJar(restrictions_jarcheck.isSelected());
		op1.setJarPath(restrictions_externaljarpath_field.getText().trim());
		op1.setProblemName(problem_name_field.getText());
		op1.setTimelimit(timelimit);
		op1.start();
	}

	/**
	 * Returns the max time limit that the user specified.
	 * 
	 * @param timelimit
	 * @return the time limit in miliseconds
	 */
	private long getMaxTime(long timelimit) {
		switch ((String) settings_time_combobox.getSelectedItem()) {
		case "second(s)":
			timelimit *= TimeMultiplier.SECOND.getMultiplier();
			break;
		case "minute(s)":
			timelimit *= TimeMultiplier.MINUTE.getMultiplier();
			break;
		case "hour(s)":
			timelimit *= TimeMultiplier.HOUR.getMultiplier();
			break;
		default:
			throw new IllegalStateException("Something's wrong here! Couldn't parse the correct timelimit!");
		}
		timelimit *= 1000;
		return timelimit;
	}

	/**
	 * Creates a frame with a new about problem
	 */
	private void createProblem() {
		problem_creation_frame.setSize(266, 400);
		problem_creation_frame.setLocationRelativeTo(null);
		JPanel main_about_panel = new JPanel();
		main_about_panel.setLayout(null);
		JPanel content_about_panel = new JPanel();
		content_about_panel.setBounds(10, 80, 230, 270);
		content_about_panel.setOpaque(false);
		content_about_panel.setLayout(null);

		new_problem_about_save_button.setIcon(new ImageIcon(newfile_icon));

		new_problem_about_save_button.setBackground(general_color);

		new_problem_about_save_button.setBounds(65, 240, 100, 25);

		problem_name_label.setBounds(10, 0, 100, 20);
		problem_name_field.setBounds(10, 20, 210, 25);

		problem_description_label.setBounds(10, 55, 100, 20);
		problem_description_area.setBounds(10, 75, 210, 145);

		new_problem_about_save_button.addActionListener(action_listener);

		content_about_panel.add(problem_name_label);
		content_about_panel.add(problem_name_field);
		content_about_panel.add(problem_description_label);
		content_about_panel.add(problem_description_area);
		content_about_panel.add(new_problem_about_save_button);

		main_about_panel.add(content_about_panel);
		add(main_about_panel);

		JLabel picLabel = new JLabel(new ImageIcon(new_background));
		picLabel.setBounds(0, 0, 250, 400);
		main_about_panel.add(picLabel);

		problem_creation_frame.add(main_about_panel);
		problem_creation_frame.setVisible(true);
	}

	/**
	 * Save a new problem in a specify directory
	 * 
	 * @param prob
	 */
	private void saveNewProblem(Problem prob) {
		String extension = ".xml";
		DateTimeFormatter timeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
		String time = timeStamp.format(java.time.LocalDateTime.now());
		String path = "";
		JFileChooser chooser;
		HashMap<String, Path> paths = ConfigXML.config.getPaths();
		if (paths.containsKey("lastJarPath")) {
			path = paths.get("lastJarPath").getUrl();
		}
		if (path != "") {
			File folder = new File(path);
			chooser = new JFileChooser(folder);
		} else {
			chooser = new JFileChooser();
		}

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showSaveDialog(window);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File dir = chooser.getSelectedFile();
			File new_file = new File(
					dir.getAbsolutePath() + "/" + problem_name_field.getText() + "_" + time + extension);
			this.file_problem = new_file;
			ProblemXML.writeXML(prob, this.file_problem);
			loadProblem(file_problem);
		}
	}

	/**
	 * Returns a list of variables
	 * 
	 * @param data
	 * @return
	 */
	private ArrayList<Variable> toArrayListVariables(Object[][] data) {
		ArrayList<Variable> ret = new ArrayList<>();
		for (int i = 0; i < data.length; i++) {
			String a = "";
			if ((boolean) data[i][5] == true) {
				a = "true";
			} else {
				a = "false";
			}
			ret.add(new Variable((String) data[i][0], (String) data[i][1], (String) data[i][2], (String) data[i][3],
					(String) data[i][4], a));
		}
		return ret;
	}

	/**
	 * Returns a list of objectives
	 * 
	 * @param data
	 * @return
	 */
	private ArrayList<Objective> toArrayListObjectives(Object[][] data) {
		ArrayList<Objective> ret = new ArrayList<>();
		for (int i = 0; i < data.length; i++) {
			String a = "";
			if ((boolean) data[i][2] == true) {
				a = "true";
			} else {
				a = "false";
			}
			ret.add(new Objective((String) data[i][0], (String) data[i][1], a));
		}
		return ret;
	}

	/**
	 * Import a problem in specify directory
	 */
	private void importProblem() {
		String path = "";
		JFileChooser chooser;
		HashMap<String, Path> paths = ConfigXML.config.getPaths();
		if (paths.containsKey("lastJarPath")) {
			path = paths.get("lastJarPath").getUrl();
		}
		if (path != "") {
			File folder = new File(path);
			chooser = new JFileChooser(folder);
		} else {
			chooser = new JFileChooser();
		}

		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(window);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			loadProblem(chooser.getSelectedFile());
			Path pa = ConfigXML.config.getPaths().get("lastJarPath");
			pa.setUrl(chooser.getSelectedFile().getParent());
			ConfigXML.config.getPaths().remove("lastJarPath");
			ConfigXML.config.getPaths().put("lastJarPath", pa);
			ConfigXML.writeXML(ConfigXML.config, window.getConfigPath());
			file_problem = chooser.getSelectedFile();
		}
	}

	/**
	 * Show a about problem
	 */
	private void showAboutWindow() {
		about_frame.setSize(266, 400);
		about_frame.setLocationRelativeTo(null);
		JPanel main_about_panel = new JPanel();
		main_about_panel.setLayout(null);
		JPanel content_about_panel = new JPanel();
		content_about_panel.setBounds(10, 80, 230, 270);
		content_about_panel.setOpaque(false);
		content_about_panel.setLayout(null);

		problem_about_save_button.setIcon(new ImageIcon(correct_icon));

		problem_about_save_button.setBackground(general_color);

		problem_about_save_button.setBounds(65, 240, 100, 25);

		problem_name_label.setBounds(10, 0, 100, 20);
		problem_name_field.setBounds(10, 20, 210, 25);

		problem_description_label.setBounds(10, 55, 100, 20);
		problem_description_area.setBounds(10, 75, 210, 145);

		problem_about_save_button.addActionListener(action_listener);

		content_about_panel.add(problem_name_label);
		content_about_panel.add(problem_name_field);
		content_about_panel.add(problem_description_label);
		content_about_panel.add(problem_description_area);
		content_about_panel.add(problem_about_save_button);

		main_about_panel.add(content_about_panel);
		add(main_about_panel);

		JLabel picLabel = new JLabel(new ImageIcon(about_background));
		picLabel.setBounds(0, 0, 250, 400);
		main_about_panel.add(picLabel);

		about_frame.add(main_about_panel);
		about_frame.setVisible(true);
	}

	/**
	 * Save the about problem with name and description
	 */
	private void saveAbout() {
		if (!problem_name_field.getText().isEmpty()) {
			ProblemXML.problem.setProblem_name(problem_name_field.getText());
			ProblemXML.problem.setProblem_description(problem_description_area.getText());
			about_frame.dispose();
		} else {
			messageDialog("<html><font color=RED > The Problem Name can't be empty </font></html>");
			problem_name_field.requestFocus();
		}
	}

	/**
	 * Save a new about problem with name and description
	 */
	private void saveNewAbout() {
		if (!problem_name_field.getText().isEmpty()) {
			Problem new_problem = new Problem();
			new_problem.setProblem_name(problem_name_field.getText());
			new_problem.setProblem_description(problem_description_area.getText());
			saveNewProblem(new_problem);
			problem_creation_frame.dispose();
		} else {
			messageDialog("<html><font color=RED > The Problem Name can't be empty </font></html>");
			problem_name_field.requestFocus();
		}
	}

	/**
	 * Create a new variable
	 */
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

		DefaultTableModel model = tableModel(this.data, this.column_names);
		table.setModel(model);
		table.repaint();
	}

	/**
	 * Disable all variables
	 */
	private void disableAll() {
		for (int i = 0; i < this.data.length; i++) {
			this.data[i][table.getColumnCount() - 1] = false;
		}
		DefaultTableModel model = tableModel(this.data, this.column_names);
		table.setModel(model);
		table.repaint();

	}

	/**
	 * Enable all variables
	 */
	private void enableAll() {
		for (int i = 0; i < this.data.length; i++) {
			this.data[i][table.getColumnCount() - 1] = true;
		}
		DefaultTableModel model = tableModel(this.data, this.column_names);
		table.setModel(model);
		table.repaint();
	}

	/**
	 * Remove a variable
	 */
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
			DefaultTableModel model = tableModel(this.data, this.column_names);
			table.setModel(model);
			table.repaint();
		}
	}

	/**
	 * Send an email to the administrator
	 */
	private void sendMailAdmin() {
		User u = new User("default", "group45.dummy.user.1@gmail.com");
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String[] admin = { ConfigXML.config.getAdmin_mail() };

			EMail_Tools.sendMail("group45.optimization.bot@gmail.com", "******", u.getEmailAddr(), admin, // cc
																											// to
																											// admin
					"Otimização em curso: " + // need to say what it is
							problem_name_field.getText() + // get the problem's
															// name
							" " + dateFormat.format(date), // and the current
															// date:time
					thankyou_message, ""); // no attachment YET, it needs to be
											// an XML
		} catch (EmailException e1) {
			e1.printStackTrace();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Objectives panel
	 */
	private void objectives_panel() {
		objectives_panel.setBorder(objectives_area_border);
		objectives_panel.setBounds(433, 10, 385, 230);
		objectives_panel.setOpaque(false);
		objectives_panel.setLayout(null);

		DefaultTableModel model = tableModel(this.objectives_data, this.objectives_column_names);
		this.objectives_table = new JTable(model);

		this.objectives_table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int rowAtPoint = table.rowAtPoint(e.getPoint());
				int columnAtPoint = table.columnAtPoint(e.getPoint());
				if (columnAtPoint == table.getColumnCount() - 1) {
					objectives_data[rowAtPoint][columnAtPoint] = !(boolean) objectives_table.getValueAt(rowAtPoint,
							columnAtPoint);
					DefaultTableModel model = tableModel(objectives_data, objectives_column_names);
					objectives_table.setModel(model);
					objectives_table.repaint();
				}
			}
		});

		this.objectives_table.setRowHeight(20);
		this.objectives_table.setRowSelectionAllowed(true);
		this.objectives_table.setColumnSelectionAllowed(false);
		this.objectives_table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.objectives_table.setPreferredScrollableViewportSize(objectives_table.getPreferredSize());

		JTableHeader th = this.objectives_table.getTableHeader();
		th.setBackground(Color.darkGray);
		th.setForeground(Color.white);

		JScrollPane sp = new JScrollPane(this.objectives_table);
		sp.setBounds(16, 95, 352, 120);

		objectives_newobjective_field.setBounds(16, 65, 215, 25);
		objectives_addobjective_button.setBounds(235, 65, 58, 25);

		objectives_addobjective_label.setBounds(16, 45, 100, 20);

		objectives_evaluate_label.setBounds(15, 22, 100, 20);
		objectives_possible_types.setBounds(120, 22, 245, 25);

		objectives_delete_button.setBounds(297, 65, 70, 25);

		objectives_addobjective_button.setBackground(general_color);
		objectives_addobjective_button.addActionListener(action_listener);

		objectives_delete_button.setBackground(general_color);
		objectives_delete_button.addActionListener(action_listener);

		objectives_panel.add(objectives_delete_button);
		objectives_panel.add(objectives_possible_types);
		objectives_panel.add(sp);
		objectives_panel.add(objectives_evaluate_label);
		objectives_panel.add(objectives_addobjective_label);
		objectives_panel.add(objectives_evaluate_label);
		objectives_panel.add(objectives_newobjective_field);
		objectives_panel.add(objectives_addobjective_button);
		add(objectives_panel);
	}

	/**
	 * Adds a new objective in the objectives table
	 */
	private void addObjective() {
		if (objectives_newobjective_field.getText() != "") {
			Object[][] mod = new Object[this.objectives_data.length + 1][objectives_table.getColumnCount()];
			if (this.objectives_data.length > 0) {
				for (int i = 0; i < this.objectives_data.length; i++) {
					mod[i] = this.objectives_data[i];
				}
			}

			Object[] row = new Object[objectives_table.getColumnCount()];

			row[0] = objectives_newobjective_field.getText();
			row[1] = objectives_possible_types.getSelectedItem().toString();
			row[2] = false;

			mod[mod.length - 1] = row;

			this.objectives_data = mod;

			DefaultTableModel model = tableModel(this.objectives_data, this.objectives_column_names);
			objectives_table.setModel(model);
			objectives_table.repaint();
			objectives_newobjective_field.setText("");
		}
	}

	/**
	 * Remove a objective in the objectives table
	 */
	private void removeObjective() {
		if (this.objectives_data.length != 0 && objectives_table.getSelectedRow() >= 0) {
			Object[][] mod = new Object[this.objectives_data.length - 1][objectives_table.getColumnCount()];
			int k = 0;
			for (int i = 0; i < objectives_data.length; i++) {
				if (i != objectives_table.getSelectedRow()) {
					mod[k] = objectives_data[i];
					k++;
				}
			}
			this.objectives_data = mod;
			DefaultTableModel model = tableModel(this.objectives_data, this.objectives_column_names);
			objectives_table.setModel(model);
			objectives_table.repaint();
		}

	}

	/**
	 * Settings panel
	 */
	private void settings_panel() {
		restrictions_panel.setBorder(restrictions_area_border);
		restrictions_panel.setBounds(190, 10, 230, 230);
		restrictions_panel.setOpaque(false);
		restrictions_panel.setLayout(null);

		User user_logged = window.getUser();
		String[] algorithms = new String[user_logged.getAlgorithms().size()];
		for (String s : user_logged.getAlgorithms()) {
			algorithms[user_logged.getAlgorithms().indexOf(s)] = s;
		}
		this.algo_name_field = new JComboBox<String>(algorithms);

		String[] values = { TimeMultiplier.SECOND.getName(), TimeMultiplier.MINUTE.getName(),
				TimeMultiplier.HOUR.getName() };
		this.settings_time_combobox = new JComboBox<String>(values);

		SpinnerModel model = new SpinnerNumberModel(10, 0, 60, 1);
		this.settings_time_spinner = new JSpinner(model);

		algo_name_field.setBounds(17, 40, 195, 25);
		settings_algo_label.setBounds(17, 20, 100, 20);
		settings_time_spinner.setBounds(17, 100, 100, 25);
		settings_max_time_label.setBounds(17, 75, 100, 25);
		settings_time_combobox.setBounds(125, 100, 85, 25);

		restrictions_useexternaljar_label.setBounds(17, 140, 180, 20);
		restrictions_jarcheck.setBounds(150, 140, 20, 20);
		restrictions_jarcheck.setOpaque(false);
		restrictions_externaljarpath_label.setBounds(17, 163, 180, 20);
		restrictions_externaljarpath_field.setBounds(17, 185, 170, 25);
		restrictions_choosejarpath_button.setBounds(190, 185, 20, 25);

		restrictions_panel.add(restrictions_jarcheck);
		restrictions_panel.add(restrictions_useexternaljar_label);
		restrictions_panel.add(restrictions_externaljarpath_label);
		restrictions_panel.add(restrictions_externaljarpath_field);
		restrictions_panel.add(restrictions_choosejarpath_button);
		restrictions_panel.add(settings_time_spinner);
		restrictions_panel.add(settings_time_combobox);
		restrictions_panel.add(settings_max_time_label);
		restrictions_panel.add(algo_name_field);
		restrictions_panel.add(settings_algo_label);
		add(restrictions_panel);
	}

	/**
	 * Tools panel
	 */
	private void tools_panel() {
		tools_panel.setBorder(tools_area_border);
		tools_panel.setBounds(15, 10, tools_border_width, tools_border_height);
		tools_panel.setOpaque(false);
		tools_panel.setLayout(null);

		tools_run_button.setBackground(new Color(187, 216, 162));
		tools_newproblem_button.setBackground(general_color);
		tools_import_button.setBackground(general_color);
		tools_export_button.setBackground(general_color);
		tools_about_button.setBackground(general_color);

		tools_newproblem_button.setIcon(new ImageIcon(newfile_icon));
		tools_import_button.setIcon(new ImageIcon(import_icon));
		tools_export_button.setIcon(new ImageIcon(save_icon));
		tools_run_button.setIcon(new ImageIcon(run_icon));
		tools_about_button.setIcon(new ImageIcon(info_icon));

		tools_newproblem_button.setBounds(15, 145, 130, 30);
		tools_import_button.setBounds(15, 105, 130, 30);
		tools_export_button.setBounds(15, 65, 130, 30);
		tools_run_button.setBounds(15, 25, 130, 30);
		tools_about_button.setBounds(15, 185, 130, 30);

		tools_run_button.addActionListener(action_listener);
		tools_about_button.addActionListener(action_listener);
		tools_export_button.addActionListener(action_listener);
		tools_import_button.addActionListener(action_listener);
		tools_newproblem_button.addActionListener(action_listener);

		tools_panel.add(tools_run_button);
		tools_panel.add(tools_newproblem_button);
		tools_panel.add(tools_import_button);
		tools_panel.add(tools_export_button);
		tools_panel.add(tools_about_button);
		add(tools_panel);
	}

	/**
	 * Variables panel
	 */
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
		variable_add_button.setIcon(new ImageIcon(new_icon));
		variable_add_button.setBackground(general_color);
		variable_remove_button.setBackground(general_color);
		variable_remove_button.setBounds(10, 315, 180, 25);
		variable_remove_button.addActionListener(action_listener);

		variable_selectAll_button.setBounds(510, 315, 130, 25);
		variable_selectAll_button.addActionListener(action_listener);
		variable_selectAll_button.setBackground(general_color);
		variable_deselectAll_button.setBounds(650, 315, 130, 25);
		variable_deselectAll_button.addActionListener(action_listener);
		variable_deselectAll_button.setBackground(general_color);

		createActionListenerForCombobox(variable_type_field);
		DefaultTableModel model = tableModel(this.data, this.column_names);
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

	/**
	 * Table with the variables
	 * 
	 * @param data_matrix
	 * @param columns
	 * @return model
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
	 * Loads the problem that is recorded
	 * 
	 * @param file
	 */
	private void loadProblem(File file) {
		ProblemXML.readXML(file);

		Object[][] objs = new Object[ProblemXML.problem.getObjectives().size()][3];
		for (Objective obj : ProblemXML.problem.getObjectives()) {
			objs[ProblemXML.problem.getObjectives().indexOf(obj)][0] = obj.getName();
			objs[ProblemXML.problem.getObjectives().indexOf(obj)][1] = obj.getType();
			if (obj.isUsed().contains("true")) {
				objs[ProblemXML.problem.getObjectives().indexOf(obj)][2] = new Boolean(true);
			} else {
				objs[ProblemXML.problem.getObjectives().indexOf(obj)][2] = new Boolean(false);
			}
		}
		this.objectives_data = objs;
		this.objectives_table.setModel(tableModel(this.objectives_data, this.objectives_column_names));

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
		this.table.setModel(tableModel(this.data, this.column_names));

		this.problem_name_field.setText(ProblemXML.problem.getProblem_name());
		this.problem_description_area.setText(ProblemXML.problem.getProblem_description());

		for (int i = 0; i < this.algo_name_field.getItemCount(); i++) {
			if (ProblemXML.problem.getAlgorithm().contains(this.algo_name_field.getItemAt(i))) {
				this.algo_name_field.setSelectedIndex(i);
			}
		}

		tools_run_button.setEnabled(true);
		tools_export_button.setEnabled(true);
		tools_about_button.setEnabled(true);
		tools_import_button.setEnabled(false);
		tools_newproblem_button.setEnabled(false);

	}


	// ***************************RESTRICTIONS_VARIABLES********************************************
	/**
	 * Verify whether the user is allowed to create variables
	 */
	private void permissionsToCreateVar() {
		String denied = "no";
		if (window.getUser().getCreate_var().equals(denied)) {
			variable_name_field.setEnabled(false);
			variable_type_field.setEnabled(false);
			variable_minval_field.setEnabled(false);
			variable_maxval_field.setEnabled(false);
			variable_restricted_field.setEnabled(false);
			variable_add_button.setEnabled(false);
		}
	}

	/**
	 * Restrictions to create a variable
	 */
	private void restrictionToCreateVar(JComboBox<String> cbox) {
		if (variable_name_field.getText().isEmpty() || ((String) variable_type_field.getSelectedItem() != "Binary"
				&& variable_minval_field.getText().isEmpty()) || variable_maxval_field.getText().isEmpty()) {
			messageDialog("<html><font color=RED > Exist empty fields! </font></html>");
		} else {
			boolean check = false;
			for (int i = 0; i < data.length; i++) {
				if (((String) data[i][0]).contains(variable_name_field.getText().trim())) {
					check = true;
				}
			}
			String s = (String) cbox.getSelectedItem();
			switch (s) {
			case "Double":
				if ((!check && isDouble(variable_minval_field.getText()) && isDouble(variable_maxval_field.getText())
						|| (!check && isDouble(variable_minval_field.getText())
								&& isDouble(variable_maxval_field.getText())
								&& isDouble(variable_restricted_field.getText())))) {
					createVariable();
					variable_name_field.setText("");
					variable_minval_field.setText("");
					variable_maxval_field.setText("");
					variable_restricted_field.setText("");
					variable_type_field.setSelectedIndex(0);
				}
				break;

			case "Integer":
				if ((!check && isInteger(variable_minval_field.getText()) && isInteger(variable_maxval_field.getText())
						|| (!check && isInteger(variable_minval_field.getText())
								&& isInteger(variable_maxval_field.getText())
								&& isInteger(variable_restricted_field.getText())))) {
					createVariable();
					variable_name_field.setText("");
					variable_minval_field.setText("");
					variable_maxval_field.setText("");
					variable_restricted_field.setText("");
					variable_type_field.setSelectedIndex(0);
				}
				break;

			case "Binary":
				if ((!check && isInteger(variable_maxval_field.getText())
						&& !variable_maxval_field.getText().contains("-"))) {
					createVariable();
					variable_name_field.setText("");
					variable_minval_field.setText("");
					variable_maxval_field.setText("");
					variable_restricted_field.setText("");
					variable_type_field.setSelectedIndex(0);
				}
				break;
			}
			if (check) {
				messageDialog("<html><font color=RED >Variable already exist!</font></html>");
				variable_name_field.setText("");
				variable_minval_field.setText("");
				variable_maxval_field.setText("");
				variable_restricted_field.setText("");
				variable_type_field.setSelectedIndex(0);
			}
		}
	}

	/**
	 * Listener to enable or disable the fields
	 * 
	 * @param cbox
	 */
	private void createActionListenerForCombobox(JComboBox<String> cbox) {
		ActionListener cbActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String s = (String) cbox.getSelectedItem();
				variable_minval_field.setText("");
				variable_maxval_field.setText("");
				variable_restricted_field.setText("");

				switch (s) {
				case "Double":
					variable_minval_field.setEnabled(true);
					variable_restricted_field.setEnabled(true);
					break;

				case "Integer":
					variable_minval_field.setEnabled(true);
					variable_restricted_field.setEnabled(true);
					break;

				case "Binary":
					variable_minval_field.setEnabled(false);
					variable_restricted_field.setEnabled(false);
					break;
				}
			}
		};
		cbox.addActionListener(cbActionListener);
	}

	/**
	 * The focus listener
	 */
	private void createFocusListener() {
		FocusListener lis = new FocusListener() {
			int show_message = 0;

			@Override
			public void focusGained(FocusEvent e) {
				if (e.getSource() == variable_name_field) {
					analyzierName(e);
				} else {
					analyzierType(e);
				}
				if (e.getSource() == variable_restricted_field) {
					show_message++;
					if (show_message == 1) {
						messageDialog(
								"<html><font color=GREEN > If you want an array you will have to use , </font></html>");
						variable_minval_field.requestFocus();
					}
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (e.getSource() == variable_name_field) {
					analyzierName(e);
				} else {
					analyzierType(e);
				}
			}
		};
		this.focus_listener = lis;
	}

	/**
	 * The restriciton to the name
	 */
	private void analyzierName(FocusEvent event) {
		if (!(variable_name_field.getText().matches("[a-zA-Z0-9_]*"))) {
			variable_name_field.setText("");
			messageDialog(
					"<html><font color=RED > The variable name can only have letters and/or numbers! </font></html>");
			variable_name_field.requestFocus();
		}
	}

	/**
	 * Main method with type restrictions
	 */
	private void analyzierType(FocusEvent event) {
		String type_name = String.valueOf(variable_type_field.getSelectedItem());
		switch (type_name) {

		case "Double":
			variable_minval_field.setEnabled(true);
			variable_restricted_field.setEnabled(true);
			DoubleRestriction(event);
			compareDoubleValues();
			break;

		case "Integer":
			variable_minval_field.setEnabled(true);
			variable_restricted_field.setEnabled(true);
			IntegerRestriction(event);
			compareIntegerValues();
			break;

		case "Binary":
			variable_minval_field.setEnabled(false);
			variable_restricted_field.setEnabled(false);
			BinaryRestriction(event);
			break;
		}
	}

	/**
	 * Verify if it's double
	 * 
	 * @param str
	 * @return
	 */
	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Method with double restrictions
	 * 
	 * @param value
	 */
	private void DoubleRestriction(FocusEvent value) {
		int show_message = 0;
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
					messageDialog(
							"<html><font color=RED > The minium value can only have decimal numbers! </font></html>");
					variable_minval_field.setText("");
					variable_minval_field.requestFocus();
				} else if (value.getSource() == variable_maxval_field) {
					messageDialog(
							"<html><font color=RED > The maximum value can only have decimal numbers! </font></html>");
					variable_maxval_field.setText("");
					variable_maxval_field.requestFocus();
				}
			}
			if (value.getSource() == variable_restricted_field) {
				for (String str : array) {
					if (!isDouble(str) || isInteger(str)) {
						show_message++;
						if (show_message == 1) {
							messageDialog(
									"<html><font color=RED > The restricted value can only have decimal numbers! </font></html>");
							variable_restricted_field.setText("");
							variable_restricted_field.requestFocus();
						}
					}
				}
			}
		}
	}

	/**
	 * Restrictions and comparation of the minimum double with the maximum double
	 */
	private void compareDoubleValues() {
		try {
			int show_message = 0;
			double minval = Double.parseDouble(variable_minval_field.getText());
			double maxval = Double.parseDouble(variable_maxval_field.getText());
			String[] array = variable_restricted_field.getText().split(",");
			double maxval_suggestion = minval + 0.1;
			double restricted = 0;
			DecimalFormat df = new DecimalFormat("0.#");
			if (minval > maxval) {
				variable_maxval_field.setText(df.format(maxval_suggestion));
				messageDialog(
						"<html><font color=RED > The minimum value can not be bigger than the maximum value! </font></html>");
				variable_maxval_field.requestFocus();
			}
			if (minval == maxval) {
				variable_maxval_field.setText(df.format(maxval_suggestion));
				messageDialog("<html><font color=RED > The values can not be equals! </font></html>");
				variable_maxval_field.requestFocus();
			}
			for (String str : array) {
				restricted = Double.parseDouble(str);
				if (minval >= restricted || maxval <= restricted) {
					show_message++;
				}
				if (show_message == 1) {
					messageDialog(
							"<html><font color=RED > The restricted value must be between the minium value and the maximum value! </font></html>");
					variable_restricted_field.setText("");
					variable_restricted_field.requestFocus();
				}
			}

		} catch (NumberFormatException e) {
		}
	}

	/**
	 * Verify if it's integer
	 * 
	 * @param str
	 * @return
	 */
	private boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Method with integer restrictions
	 * 
	 * @param value
	 */
	private void IntegerRestriction(FocusEvent value) {
		int show_message = 0;
		JTextField aux = ((JTextField) value.getComponent());
		String[] array = variable_restricted_field.getText().split(",");
		if (!aux.getText().isEmpty()) {
			if (!isInteger(aux.getText())) {
				if (value.getSource() == variable_minval_field) {
					messageDialog(
							"<html><font color=RED > The minium value can only have integer numbers! </font></html>");
					variable_minval_field.setText("");
					variable_minval_field.requestFocus();
				} else if (value.getSource() == variable_maxval_field) {
					messageDialog(
							"<html><font color=RED > The maximum value can only have integer numbers! </font></html>");
					variable_maxval_field.setText("");
					variable_maxval_field.requestFocus();
				}
			}
			if (value.getSource() == variable_restricted_field) {
				for (String str : array) {
					if (!isInteger(str)) {
						show_message++;
						if (show_message == 1) {
							messageDialog(
									"<html><font color=RED > The restricted value can only have integer numbers! </font></html>");
							variable_restricted_field.setText("");
							variable_restricted_field.requestFocus();
						}
					}
				}
			}
		}
	}

	/**
	 * Restrictions and comparation the minium integer with the maximum integer
	 */
	private void compareIntegerValues() {
		try {
			int minval = Integer.parseInt(variable_minval_field.getText());
			int maxval = Integer.parseInt(variable_maxval_field.getText());
			int maxval_suggestion;
			int show_message = 0;
			String[] array = variable_restricted_field.getText().split(",");
			int restricted;
			if (minval > maxval) {
				maxval_suggestion = minval + 1;
				variable_maxval_field.setText(Integer.toString(maxval_suggestion));
				messageDialog(
						"<html><font color=RED > The minimum value can not be bigger than the maximum value! </font></html>");
				variable_maxval_field.requestFocus();
			}
			if (minval == maxval) {
				maxval_suggestion = minval + 1;
				variable_maxval_field.setText(Integer.toString(maxval_suggestion));
				messageDialog("<html><font color=RED > The values can not be equals! </font></html>");
				variable_maxval_field.requestFocus();
			}
			for (String str : array) {
				restricted = Integer.parseInt(str);
				if (minval >= restricted || maxval <= restricted) {
					show_message++;
					int compare_values = maxval - minval;
					if (compare_values == 1) {
						maxval_suggestion = minval + 2;
						variable_maxval_field.setText(Integer.toString(maxval_suggestion));
					}
					if (show_message == 1) {
						messageDialog(
								"<html><font color=RED > The restricted value must be between the minium value and the maximum value! </font></html>");
						variable_restricted_field.requestFocus();
						variable_restricted_field.setText("");
					}
				}
			}
		} catch (NumberFormatException e) {
		}
	}

	/**
	 * Method with binary restrictions
	 * 
	 * @param value
	 */
	private void BinaryRestriction(FocusEvent value) {
		JTextField aux = ((JTextField) value.getComponent());
		if (!aux.getText().isEmpty()) {
			if (aux.getText().contains(",")) {
				aux.setText(aux.getText().replaceAll(",", "."));
			}
			if (isDouble(aux.getText())) {
				double d = Double.parseDouble(aux.getText());
				int round = (int) Math.round(d);
				aux.setText(Integer.toString(round));
			}
			if (!isInteger(aux.getText()) || aux.getText().contains("-")) {
				messageDialog(
						"<html><font color=RED > The maximum value can only have integer numbers! </font></html>");
				aux.setText("");
				aux.requestFocus();
			}
		}
	}

	/**
	 * Method with message error
	 * 
	 * @param message
	 */
	private void messageDialog(String message) {
		String try_again = "<html><font color=RED > Please try again... </font></html>";
		JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	public Object[][] getData() {
		return data;
	}

	public Object[][] getObjectives_data() {
		return objectives_data;
	}

	public JComboBox<String> getAlgo_name_field() {
		return algo_name_field;
	}
	
	public JTextField getProblem_name_field() {
		return problem_name_field;
	}

}