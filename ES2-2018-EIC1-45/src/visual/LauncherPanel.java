package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import core.User;
import xml.ConfigXML;

/**
 * The launcher of this program with some methods to manage the users.
 * 
 * @authors Tiago Almeida, Markiyan Pyekh
 */

public class LauncherPanel extends JPanel {

	private static final String USER_LOGGED_MSG = "Your user has logged in!";
	private static final String USER_PERMISSION_TO_MODIFY_MSG = "Permission to modify accept!";
	private static final String USER_REGISTERED_MSG = "An user has been registered in your name!";
	private static final String USER_HAS_BEEN_DELETED_MSG = "Your user has been deleted!";
	private static final String USER_HAS_BEEN_MODIFIED_MSG = "Your user has been modified!";
	
	//TODO: This needs to be displayed in a YES/NO dialog when the user hits the "Create User" button!!!
	private final String USER_HITS_REGISTER_LEGAL_MSG = "ATTENTION: We need your complete consent to use your e-mail address. "
			+ "It will only be used for the following ends: "
			+ "\n -General warnings to the system's Administrator about the optimization process; "
			+ "\n -Reception of messages with information pertinent to the optimization process "
			+ "(start of process, current status, errors, etc); "
			+ "\n -Sending help messages to the system's Administrator. "
			+ "\n\n The system may ask for your e-mail address's password for authentication purposes. "
			+ "Your password will never be saved anywhere or shared with anyone. "
			+ "\n Proceed with the registration process?";

	private static final long serialVersionUID = 1L;

	private final Color general_color = new Color(255, 242, 211);

	private Image background = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/launcher_bg.jpg"));

	private Image key_icon = Toolkit.getDefaultToolkit().createImage(LauncherPanel.class.getResource("/icons/key.png"));
	private Image delete_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/delete.png"));
	private Image settings_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/settings.png"));
	private Image save_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/save.png"));
	private Image register_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/social.png"));
	private Image back_icon = Toolkit.getDefaultToolkit()
			.createImage(LauncherPanel.class.getResource("/icons/reply.png"));

	private Window window;
	private Launcher launch;

	private File file;

	private final int border_width = 325;
	private final int border_height = 315;

	private ActionListener listener;

	private Border blackline = BorderFactory.createLineBorder(Color.BLACK);
	private TitledBorder user_area_border = BorderFactory.createTitledBorder(blackline, "Login Area");
	private TitledBorder user_create_border = BorderFactory.createTitledBorder(blackline, "Create User");
	private TitledBorder user_modification_border = BorderFactory.createTitledBorder(blackline, "Modify User");

	// ***************************USER_FIELDS************************************************
	private JPanel user_panel;
	private JLabel user_users_label = new JLabel("Users");
	private JComboBox<String> user_list_field;
	private JLabel user_passwd_label = new JLabel("Password");
	private JPasswordField user_passwd_field = new JPasswordField();
	private JButton user_login_button = new JButton("  Login");
	private JButton user_signup_button = new JButton("  Create User");
	private JButton user_delete_button = new JButton("  Delete User");
	private JButton user_modify_button = new JButton("  Modify");
	// ***************************USER_FIELDS************************************************

	// *************************CREATE_USER_FIELDS*************************************
	private JPanel create_user_panel;
	private JLabel create_user_name_label = new JLabel("Name");
	private JLabel create_user_email_label = new JLabel("Email");
	private JLabel create_user_password_label = new JLabel("Password");
	private JLabel create_user_retypePass_label = new JLabel("Retype password");
	private JTextField create_user_name_field = new JTextField();
	private JTextField create_user_email_field = new JTextField();
	private JPasswordField create_user_passwd_field = new JPasswordField();
	private JPasswordField create_user_retypePass_field = new JPasswordField();
	private JButton create_user_create_button = new JButton("  Create");
	private JButton create_user_back_button = new JButton("  Back");

	// *************************CREATE_USER_FIELDS*************************************

	// *************************MODIFY_USER_FIELDS*************************************
	private JPanel modify_user_panel;
	private JLabel modify_user_collection_label = new JLabel("Users");
	private JLabel modify_user_name_label = new JLabel("Username");
	private JLabel modify_user_email_label = new JLabel("Email");
	private JTextField modify_user_name_field = new JTextField();
	private JTextField modify_user_email_field = new JTextField();
	private JButton modify_user_save_button = new JButton("  Save");
	private JButton modify_user_back_button = new JButton("  Back");

	// *************************CREATE_USER_FIELDS*************************************

	/**
	 * The constructor
	 * @param launch
	 * @param file_config
	 */
	public LauncherPanel(Launcher launch, File file_config) {
		this.launch = launch;
		this.file = file_config;
		new_action_listener();
		setLayout(null);
		init_user_panel();
		init_modify_panel();
		init_register_panel();

		add(user_panel);
	}

	private String getCurrentEmail() {
		String s = (String) user_list_field.getSelectedItem();
		return s.substring(s.indexOf("[") + 1, s.indexOf("]"));
	}

	/**
	 * Load users into the users combobox
	 */
	private void loadUsers() {
		ArrayList<User> users = ConfigXML.config.getUsers();
		String[] items = new String[users.size() + 1];
		for (User u : users) {
			String ret = "";
			ret += u.getName();
			ret += "  [";
			ret += u.getEmailAddr();
			ret += "]";
			items[users.indexOf(u)] = ret;
		}

		User admin_instance = ConfigXML.config.getAdmin();
		String admin_string = "";
		admin_string += admin_instance.getName();
		admin_string += "  [";
		admin_string += admin_instance.getEmailAddr();
		admin_string += "]";
		admin_string += " [ADMIN]";
		items[items.length - 1] = admin_string;
		user_list_field = new JComboBox<String>(items);
	}

	/**
	 * The main panel
	 */
	private void init_user_panel() {
		user_panel = new JPanel();
		user_panel.setBorder(user_area_border);
		user_panel.setBounds(36, 147, border_width, border_height);
		user_panel.setOpaque(false);
		user_panel.setLayout(null);

		loadUsers();

		user_users_label.setBounds(15, 20, 50, 25);
		user_list_field.setBounds(15, 45, 290, 28);
		user_passwd_label.setBounds(15, 75, 220, 25);
		user_passwd_field.setBounds(15, 100, 290, 28);
		user_signup_button.setBounds(15, 190, 290, 28);
		user_login_button.setBounds(15, 150, 290, 28);
		user_delete_button.setBounds(15, 270, 290, 28);
		user_modify_button.setBounds(15, 230, 290, 28);

		user_login_button.setIcon(new ImageIcon(key_icon));
		user_signup_button.setIcon(new ImageIcon(register_icon));
		user_modify_button.setIcon(new ImageIcon(settings_icon));
		user_delete_button.setIcon(new ImageIcon(delete_icon));

		user_login_button.addActionListener(listener);
		user_signup_button.addActionListener(listener);
		user_modify_button.addActionListener(listener);
		user_delete_button.addActionListener(listener);

		user_login_button.setBackground(general_color);
		user_delete_button.setBackground(general_color);
		user_modify_button.setBackground(general_color);
		user_signup_button.setBackground(general_color);

		user_panel.add(user_login_button);
		user_panel.add(user_delete_button);
		user_panel.add(user_modify_button);
		user_panel.add(user_passwd_label);
		user_panel.add(user_passwd_field);
		user_panel.add(user_list_field);
		user_panel.add(user_users_label);
		user_panel.add(user_signup_button);

	}

	/**
	 * The user creation panel
	 */
	private void init_register_panel() {
		create_user_panel = new JPanel();
		create_user_panel.setBorder(user_create_border);
		create_user_panel.setBounds(36, 147, border_width, border_height);
		create_user_panel.setOpaque(false);
		create_user_panel.setLayout(null);

		create_user_name_label.setBounds(15, 20, 50, 25);
		create_user_email_label.setBounds(15, 75, 290, 25);
		create_user_password_label.setBounds(15, 130, 220, 25);
		create_user_retypePass_label.setBounds(15, 185, 290, 25);

		create_user_name_field.setBounds(15, 45, 290, 28);
		create_user_email_field.setBounds(15, 100, 290, 28);
		create_user_passwd_field.setBounds(15, 155, 290, 28);
		create_user_retypePass_field.setBounds(15, 210, 290, 28);

		create_user_create_button.setBounds(170, 270, 135, 28);
		create_user_back_button.setBounds(15, 270, 135, 28);

		create_user_create_button.setIcon(new ImageIcon(register_icon));
		create_user_back_button.setIcon(new ImageIcon(back_icon));

		create_user_create_button.addActionListener(listener);
		create_user_back_button.addActionListener(listener);

		create_user_create_button.setBackground(general_color);
		create_user_back_button.setBackground(general_color);

		create_user_panel.add(create_user_name_label);
		create_user_panel.add(create_user_email_label);
		create_user_panel.add(create_user_password_label);
		create_user_panel.add(create_user_retypePass_label);
		create_user_panel.add(create_user_name_field);
		create_user_panel.add(create_user_email_field);
		create_user_panel.add(create_user_passwd_field);
		create_user_panel.add(create_user_retypePass_field);
		create_user_panel.add(create_user_create_button);
		create_user_panel.add(create_user_back_button);

		// getUserLogged();
	}

	/**
	 * The user modification panel
	 */
	private void init_modify_panel() {
		modify_user_panel = new JPanel();
		modify_user_panel.setBorder(user_modification_border);
		modify_user_panel.setBounds(36, 147, border_width, border_height);
		modify_user_panel.setOpaque(false);
		modify_user_panel.setLayout(null);

		modify_user_name_label.setBounds(15, 20, 60, 25);
		modify_user_email_label.setBounds(15, 75, 290, 25);

		modify_user_name_field.setBounds(15, 45, 290, 28);
		modify_user_email_field.setBounds(15, 100, 290, 28);

		modify_user_save_button.setBounds(170, 270, 135, 28);
		modify_user_back_button.setBounds(15, 270, 135, 28);

		modify_user_save_button.setIcon(new ImageIcon(save_icon));
		modify_user_back_button.setIcon(new ImageIcon(back_icon));

		modify_user_save_button.addActionListener(listener);
		modify_user_back_button.addActionListener(listener);

		modify_user_save_button.setBackground(general_color);
		modify_user_back_button.setBackground(general_color);

		modify_user_panel.add(modify_user_collection_label);
		modify_user_panel.add(modify_user_name_label);
		modify_user_panel.add(modify_user_email_label);
		modify_user_panel.add(modify_user_name_field);
		modify_user_panel.add(modify_user_email_field);
		modify_user_panel.add(modify_user_save_button);
		modify_user_panel.add(modify_user_back_button);
	}

	/**
	 * The listener
	 */
	private void new_action_listener() {
		listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == user_signup_button) {
					removeAll();
					add(create_user_panel);
					repaint();
				} else if (e.getSource() == create_user_back_button) {
					clean_create_users_fields();
					removeAll();
					add(user_panel);
					repaint();
				} else if (e.getSource() == modify_user_back_button) {
					removeAll();
					add(user_panel);
					repaint();
				} else if (e.getSource() == user_modify_button) {
					if (user_list_field.getSelectedItem().toString().contains("[ADMIN]")) {
						try {
							java.awt.Desktop.getDesktop().edit(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						removeAll();
						loginModifyUser();
					}
					repaint();
				} else if (e.getSource() == user_delete_button) {
					deleteUser();
				} else if (e.getSource() == modify_user_save_button) {
					modifyUser();
				} else if (e.getSource() == create_user_create_button) {
					createUser();
				} else if (e.getSource() == user_login_button) {
					loginUser();
				}
			}

			/**
			 * Verify authentication
			 */
			private void loginUser() {
				// String passwd = String.valueOf(user_passwd_field.getPassword());
				// boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd,
				// USER_LOGGED_MSG);
				//
				// if (check) {
				launch.dispose();
				new Window(getUserLogged(), file);
				// } else {
				// messageDialog("<html><font color=RED > The credentials are wrong!
				// </font></html>");
				// System.out.println("WARNING: Incorrect credentials!");
				// }
				// String passwd =
				// String.valueOf(user_passwd_field.getPassword());
				// boolean check = EMail_Tools.checkAuth(getCurrentEmail(),
				// passwd, USER_LOGGED_MSG);
				//
			}

			/**
			 * Method for creating users
			 */
			private void createUser() {
				// String passwd = String.valueOf(create_user_passwd_field.getPassword());
				// String repeat = String.valueOf(create_user_retypePass_field.getPassword());
				//
				// if (!passwd.equals(repeat)) {
				// messageDialog("<html><font color=RED > The retype password is wrong!
				// </font></html>");
				// System.out.println("Passwords don't match.");
				// return;
				// }

				// boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd,
				// USER_REGISTERED_MSG);
				//
				// if (check) {
				int verify = 0;
				ArrayList<User> users = ConfigXML.config.getUsers();
				String[] array = create_user_name_field.getText().split(" ");
				if (!(array.length == 2)) {
					messageDialog(
							"<html><font color=RED > The field name must have first and last name! </font></html>");
					create_user_name_field.setText("");
					create_user_name_field.requestFocus();
				} else {
					for (int i = 0; i < users.size(); i++) {
						if (users.get(i).getName().equals(create_user_name_field.getText())) {
							messageDialog("<html><font color=RED > This user already exists! </font></html>");
							verify = 1;
							create_user_name_field.setText("");
							create_user_name_field.requestFocus();
						}
						if (users.get(i).getEmailAddr().equals(create_user_email_field.getText())) {
							messageDialog("<html><font color=RED > This email already exists! </font></html>");
							verify = 1;
							create_user_email_field.setText("");
							create_user_email_field.requestFocus();
						}
					}
					if (verify == 0) {
						ArrayList<String> algorithms = new ArrayList<String>();
						algorithms.add("SPEA2");
						algorithms.add("SMPSO");
						algorithms.add("PAES");
						algorithms.add("IBEA");
						algorithms.add("NSGAII");
						algorithms.add("SMSEMOA");
						algorithms.add("GDE3");
						algorithms.add("MOCell");
						algorithms.add("MOEAD");
						algorithms.add("RandomSearch");
						algorithms.add("MOCH");

						User new_user = new User(create_user_name_field.getText(), create_user_email_field.getText(),
								algorithms, "yes");
						users.add(new_user);

						ConfigXML.config.setUsers(users);
						ConfigXML.writeXML(ConfigXML.config, file);

						String new_item = "";
						new_item += new_user.getName();
						new_item += "  [";
						new_item += new_user.getEmailAddr();
						new_item += "]";
						user_list_field.addItem(new_item);

						JOptionPane.showMessageDialog(null,
								create_user_name_field.getText() + " was successfully created!", "SUCESS",
								JOptionPane.INFORMATION_MESSAGE);
						clean_create_users_fields();

					}
				}
				// } else {
				// messageDialog("<html><font color=RED > The credentials are
				// wrong!</font></html>");
				// System.out.println("WARNING: Incorrect credentials!");
				// }
			}

			/**
			 * Verify authentication to modify users
			 */
			private void loginModifyUser() {
				// String passwd = String.valueOf(user_passwd_field.getPassword());
				// boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd,
				// USER_PERMISSION_TO_MODIFY_MSG);
				//
				// if (check) {
				String[] array = user_list_field.getSelectedItem().toString().split(" ");

				String name_user = "";
				name_user += array[0];
				name_user += " ";
				name_user += array[1];

				String email_user = "";
				email_user += array[3];
				email_user = email_user.replace("[", "");
				email_user = email_user.replace("]", "");

				modify_user_name_field.setText(name_user);
				modify_user_email_field.setText(email_user);

				add(modify_user_panel);
				// } else {
				// add(user_panel);
				// messageDialog("<html><font color=RED > The credentials are wrong!
				// </font></html>");
				// System.out.println("WARNING: Incorrect credentials!");
				// }
			}

			/**
			 * Method to modify users
			 */
			private void modifyUser() {
				// String passwd = String.valueOf(user_passwd_field.getPassword());
				// boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd,
				// USER_HAS_BEEN_MODIFIED_MSG);

				// if (check) {
				String old_name = "";
				String[] array = modify_user_name_field.getText().split(" ");
				if (!(array.length == 2)) {
					messageDialog(
							"<html><font color=RED > The field name must have first and last name! </font></html>");
					modify_user_name_field.setText("");
					modify_user_name_field.requestFocus();
				} else {
					int verify = 0;
					ArrayList<User> users = ConfigXML.config.getUsers();
					for (int i = 0; i < users.size(); i++) {
						if (users.get(i).getName().equals(modify_user_name_field.getText())
								&& !((String) user_list_field.getSelectedItem()).contains(users.get(i).getName())) {
							messageDialog("<html><font color=RED > This user already exists! </font></html>");
							verify = 1;
							modify_user_name_field.setText("");
							modify_user_name_field.requestFocus();
						}
						if (users.get(i).getEmailAddr().equals(modify_user_email_field.getText())
								&& !((String) user_list_field.getSelectedItem())
										.contains(users.get(i).getEmailAddr())) {
							messageDialog("<html><font color=RED > This email already exists! </font></html>");
							verify = 1;
							modify_user_email_field.setText("");
							modify_user_email_field.requestFocus();
						}
					}
					if (verify == 0) {
						for (int i = 0; i < users.size(); i++) {
							if (((String) user_list_field.getSelectedItem()).contains(users.get(i).getName())) {
								old_name = users.get(i).getName();
								users.get(i).setName(modify_user_name_field.getText());
								users.get(i).setEmailAddr(modify_user_email_field.getText());

								String new_item = "";
								new_item += users.get(i).getName();
								new_item += "  [";
								new_item += users.get(i).getEmailAddr();
								new_item += "]";
								user_list_field.addItem(new_item);
								
								modify_user_name_field.setText(users.get(i).getName());
								modify_user_email_field.setText(users.get(i).getEmailAddr());
							}
						}
						ConfigXML.writeXML(ConfigXML.config, file);
						user_list_field.removeItem(user_list_field.getSelectedItem());
						JOptionPane.showMessageDialog(null, old_name + " was successfully modified!", "SUCESS",
								JOptionPane.INFORMATION_MESSAGE);
						clean_create_users_fields();
						removeAll();
						add(user_panel);
						repaint();
					}
				}
				// } else {
				// messageDialog("<html><font color=RED > The credentials are
				// wrong!</font></html>");
				// System.out.println("WARNING: Incorrect credentials!");
				// }
			}

			/**
			 * Method to delete users
			 */
			private void deleteUser() {

				// String passwd = String.valueOf(user_passwd_field.getPassword());
				// boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd,
				// USER_HAS_BEEN_DELETED_MSG);
				//
				// if (check) {
				String old_name = "";
				ArrayList<User> users = ConfigXML.config.getUsers();
				for (int i = 0; i < users.size(); i++) {
					if (((String) user_list_field.getSelectedItem()).contains(users.get(i).getName())) {
						old_name = users.get(i).getName();
						users.remove(i);
					}
				}
				ConfigXML.config.setUsers(users);
				ConfigXML.writeXML(ConfigXML.config, file);

				user_list_field.removeItem(user_list_field.getSelectedItem());
				JOptionPane.showMessageDialog(null, old_name + " was successfully deleted!", "SUCESS",
						JOptionPane.INFORMATION_MESSAGE);

				// } else {
				// messageDialog("<html><font color=RED > The credentials are
				// wrong!</font></html>");
				// System.out.println("WARNING: Incorrect credentials!");
				// }

			}
		};
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

	private void clean_create_users_fields() {
		create_user_name_field.setText("");
		create_user_email_field.setText("");
		create_user_passwd_field.setText("");
		create_user_retypePass_field.setText("");
	}

	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		int h = background.getHeight(null);
		int w = background.getWidth(null);

		if (w > this.getWidth()) {
			background = background.getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT);
			h = background.getHeight(null);
		}

		if (h > this.getHeight()) {
			background = background.getScaledInstance(-1, getHeight(), Image.SCALE_DEFAULT);
		}
		int x = (getWidth() - background.getWidth(null)) / 2;
		int y = (getHeight() - background.getHeight(null)) / 2;

		page.drawImage(background, x, y, null);
		repaint();
	}

	/**
	 * @return
	 */
	public Window getWindow() {
		return window;
	}

	/**
	 * @param window
	 */
	public void setWindow(Window window) {
		this.window = window;
	}

	/*
	 * public void createUsersTest() { Config cfg = new Config();
	 * cfg.setAdmin_mail("mphna@gmail.com"); cfg.setAdmin_name("Markiyan");
	 * 
	 * ArrayList<String> algorithms = new ArrayList<String>();
	 * algorithms.add("SPEA2"); algorithms.add("SMPSO"); algorithms.add("PAES");
	 * algorithms.add("IBEA"); algorithms.add("NSGAII"); algorithms.add("SMSEMOA");
	 * algorithms.add("GDE3"); algorithms.add("MOCell"); algorithms.add("MOEAD");
	 * algorithms.add("RandomSearch"); algorithms.add("MOCH");
	 * 
	 * String create_var = "yes";
	 * 
	 * User u = new User("Tiago Almeida", "tiago@gmail.com", algorithms,
	 * create_var); User u1 = new User("Paulo Pina", "paulo@gmail.com", algorithms,
	 * create_var); User u2 = new User("Andre Godinho", "andre@gmail.com",
	 * algorithms, create_var);
	 * 
	 * ArrayList<User> users = new ArrayList<>(); users.add(u); users.add(u1);
	 * users.add(u2);
	 * 
	 * Path p = new Path("ProblemsPath", "C:/");
	 * 
	 * ArrayList<Path> paths = new ArrayList<>(); paths.add(p);
	 * 
	 * cfg.setTime("5"); cfg.setUsers(users); // cfg.setPaths(paths);
	 * 
	 * ConfigXML.writeXML(cfg, new File("Resources/config.xml")); }
	 */

	/**
	 * Returns the user who is logged in
	 * @return User
	 */
	public User getUserLogged() {

		ArrayList<User> users = ConfigXML.config.getUsers();
		String[] array = user_list_field.getSelectedItem().toString().split(" ");

		String create_var = "";
		String name_user = "";
		name_user += array[0];
		name_user += " ";
		name_user += array[1];

		String email_user = "";
		email_user += array[3];
		email_user = email_user.replace("[", "");
		email_user = email_user.replace("]", "");

		String algo = "";
		for (User user : users) {
			if (user.getName().equals(name_user) && user.getEmailAddr().equals(email_user)) {
				algo += user.getAlgorithms();
				create_var = user.getCreate_var();
			}
		}
		algo = algo.replace("[", "");
		algo = algo.replace("]", "");

		String[] algorithms = algo.split(",");
		ArrayList<String> array_algo = new ArrayList<String>();
		for (String s : algorithms) {
			array_algo.add(s);
		}

		User user_logged = new User(name_user, email_user, array_algo, create_var);
		return user_logged;
	}

}