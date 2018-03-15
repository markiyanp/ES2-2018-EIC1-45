package visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
import email.EMail_Tools;

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


	//***************************GENERAL FIELDS********************************************
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


	private ActionListener listener;
	//***************************GENERAL FIELDS********************************************

	//***************************PROBLEM FIELDS********************************************
	private JLabel problem_name_label = new JLabel("Problem Name");
	private JLabel problem_description_label = new JLabel("Description");
	private JTextField problem_name_field = new JTextField();
	private JTextArea problem_description_area = new JTextArea();
	private JButton create_problem_button = new JButton("+");
	private String[] variable_types = { "String", "Integer", "Boolean", "Double", "Long" };
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

	//***************************PROBLEM FIELDS********************************************
	
	
	//***************************ALGO FIELDS********************************************
	private JComboBox<String> algo_name_field = new JComboBox<String>();
	//***************************ALGO FIELDS********************************************


	//***************************USER FIELDS********************************************
	private JLabel user_name_label = new JLabel("Name");
	private JComboBox<String> user_name_field = new JComboBox<String>();
	private JLabel user_email_label = new JLabel("E-mail");
	private JTextField user_email_field = new JTextField();
	private JButton user_choose_button = new JButton("Choose");
	private JButton user_delete_button = new JButton("Delete");
	private JButton user_create_button = new JButton("Create");
	private JButton user_modify_button = new JButton("Modify");
	//***************************USER FIELDS********************************************
	
	
	
	//***************************TOOLS FIELDS********************************************
	private JButton tools_import_button = new JButton("LOAD");
	private JButton tools_export_button = new JButton("SAVE");
	private JButton tools_reset_button = new JButton("Reset fields");
	private JButton tools_run_button = new JButton("RUN");
	//***************************TOOLS FIELDS********************************************



	// ******************************INSTANCES_END******************************************

	public OptimizationTab() {
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);

		createActionListener();
		problem_panel();
		user_panel();
		tools_panel();
		variables_panel();
		algorithm_panel();
	}


	private void user_panel() {
		user_panel.setBorder(user_area_border);
		user_panel.setBounds(15, 10, user_border_width, user_border_height);
		user_panel.setOpaque(false);
		user_panel.setLayout(null);
		
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

	private void createActionListener(){
		ActionListener lis = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == variable_add_button){
					createVariable();
				}else if(e.getSource() == variable_selectAll_button){
					enableAll();
				}else if(e.getSource() == variable_deselectAll_button){
					disableAll();
				}else if(e.getSource() == variable_remove_button){
					removeVariable();
				}else if(e.getSource() == tools_run_button){
					sendMailAdmin();
				}
			}
		};
		this.listener = lis;
	}


	private void createVariable() {
		Object[][] mod = new Object[this.data.length+1][table.getColumnCount()];
		if(this.data.length>0){
			for(int i = 0; i < this.data.length; i++){
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

		mod[mod.length-1] = row;

		this.data = mod;

		DefaultTableModel model = tableModel();
		table.setModel(model);
		table.repaint();
	}

	private void disableAll() {
		for(int i = 0; i < this.data.length; i++){
			this.data[i][table.getColumnCount()-1] = false;
		}
		DefaultTableModel model = tableModel();
		table.setModel(model);
		table.repaint();


	}

	private void enableAll() {
		for(int i = 0; i < this.data.length; i++){
			this.data[i][table.getColumnCount()-1] = true;
		}
		DefaultTableModel model = tableModel();
		table.setModel(model);
		table.repaint();
	}

	private void removeVariable() {
		if(this.data.length != 0 && table.getSelectedRow() >= 0){
			Object[][] mod = new Object[this.data.length-1][this.table.getColumnCount()];
			int k = 0;
			for(int i = 0; i < this.data.length; i++){
				if(i != table.getSelectedRow()){
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
	
	
	private void sendMailAdmin(){
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String[] admin = { EMail_Tools.getAdminEmail() };
			
			EMail_Tools.sendMail("group45.optimization.bot@gmail.com", //optimization bot
					"******", //password goes here. do not commit a version with password PLEASE
					User.getEmailAddr(), //send email to user
					admin, //cc to admin
					"Otimização em curso: " + //need to say what it is
					problem_name_field.getText() + //get the problem's name
					" "+
					dateFormat.format(date), //and the current date:time
					"Muito obrigado por usar esta plataforma de otimização. "
					+ "Será informado por email sobre o progresso do processo de otimização, "
					+ "quando o processo de otimização tiver atingido 25%, 50%, 75% do total "
					+ "do tempo estimado, " //this train might need to be moved to its own String TODO
					+ "e também quando o processo tiver terminado, "
					+ "com sucesso ou devido à ocorrência de erros.", 
					""); //no attachment YET, it needs to be an XML
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
		
		tools_run_button.addActionListener(listener);
		
		tools_panel.add(tools_run_button);
		tools_panel.add(tools_reset_button);
		tools_panel.add(tools_import_button);
		tools_panel.add(tools_export_button);
		add(tools_panel);
	}
	
	private void algorithm_panel() {
		algo_panel.setBorder(algo_area_border);
		algo_panel.setBounds(590,170, algo_border_width, algo_border_height);
		algo_panel.setOpaque(false);
		algo_panel.setLayout(null);
		
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
		create_problem_button.addActionListener(listener);

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
		variable_type_field.setBounds(175, 40, 125, 25);
		variable_minval_field.setBounds(318, 40, 100, 25);
		variable_maxval_field.setBounds(435, 40, 100, 25);
		variable_restricted_field.setBounds(555, 40, 125, 25);

		variable_add_button.setBounds(690, 40, 105, 25);
		variable_add_button.addActionListener(listener);
		variable_remove_button.setBounds(10, 315, 180, 25);
		variable_remove_button.addActionListener(listener);

		variable_selectAll_button.setBounds(510, 315, 130, 25);
		variable_selectAll_button.addActionListener(listener);
		variable_deselectAll_button.setBounds(650, 315, 130, 25);
		variable_deselectAll_button.addActionListener(listener);

		DefaultTableModel model =  tableModel();
		this.table = new JTable(model);

		this.table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable)e.getSource();

				int rowAtPoint = table.rowAtPoint(e.getPoint());
				int columnAtPoint = table.columnAtPoint(e.getPoint());
				if(columnAtPoint == table.getColumnCount()-1){
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

	private DefaultTableModel tableModel(){
		DefaultTableModel model =  new DefaultTableModel(data,column_names) {
			private static final long serialVersionUID = 1L;
			@Override 
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
			@Override
			public boolean isCellEditable(int row, int column){  
				if(column == table.getColumnCount()-1){
					return true;
				}
				return false;  
			} 
		};


		return model;
	}

}