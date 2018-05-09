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
import email.EMail_Tools;
import xml.ConfigXML;

public class LauncherPanel extends JPanel {

	private static final String USER_LOGGED_MSG = "Your user has logged in!";
	private static final String USER_PERMISSION_TO_MODIFY_MSG = "Permission to modify accept!";
	private static final String USER_REGISTERED_MSG = "An user has been registered in your name!";
	private static final String USER_HAS_BEEN_DELETED_MSG = "Your user has been deleted!";
	private static final String USER_HAS_BEEN_MODIFIED_MSG = "Your user has been modified!";

	private static final long serialVersionUID = 1L;

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

	private String[] array;
	
	public LauncherPanel(Launcher launch, File file) {
		this.launch = launch;
		this.file = file;
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

	private void loadUsers() {
		ArrayList<User> users = ConfigXML.config.getUsers();
		String[] items = new String[users.size() + 1];
		for (User u : users) {
			String ret = "";
			ret += u.getUsername();
			ret += "  [";
			ret += u.getEmailAddr();
			ret += "]";
			items[users.indexOf(u)] = ret;
		}

		User admin_instance = ConfigXML.config.getAdmin();
		String admin_string = "";
		admin_string += admin_instance.getUsername();
		admin_string += "  [";
		admin_string += admin_instance.getEmailAddr();
		admin_string += "]";
		admin_string += " [ADMIN]";
		items[items.length - 1] = admin_string;
		user_list_field = new JComboBox<String>(items);
	}

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

		user_panel.add(user_login_button);
		user_panel.add(user_delete_button);
		user_panel.add(user_modify_button);
		user_panel.add(user_passwd_label);
		user_panel.add(user_passwd_field);
		user_panel.add(user_list_field);
		user_panel.add(user_users_label);
		user_panel.add(user_signup_button);
		
	}

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
	}

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

		modify_user_panel.add(modify_user_collection_label);
		modify_user_panel.add(modify_user_name_label);
		modify_user_panel.add(modify_user_email_label);
		modify_user_panel.add(modify_user_name_field);
		modify_user_panel.add(modify_user_email_field);
		modify_user_panel.add(modify_user_save_button);
		modify_user_panel.add(modify_user_back_button);
	}

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

			private void loginUser() {
				String passwd = String.valueOf(user_passwd_field.getPassword());
				boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd, USER_LOGGED_MSG);

				if (check) {
					launch.setVisible(false);
					new Window();
				} else {
					messageDialog("<html><font color=RED > The credentials are wrong! </font></html>");
					System.out.println("WARNING: Incorrect credentials!");
				}
			}

			private void createUser() {
				String passwd = String.valueOf(create_user_passwd_field.getPassword());
				String repeat = String.valueOf(create_user_retypePass_field.getPassword());

				if (!passwd.equals(repeat)) {
					messageDialog("<html><font color=RED > The retype password is wrong! </font></html>");
					System.out.println("Passwords don't match.");
					return;
				}

//				boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd, USER_REGISTERED_MSG);
//
//				if (check) {
					User new_user = new User(create_user_name_field.getText(), create_user_email_field.getText());
					ArrayList<User> users = ConfigXML.config.getUsers();
					ArrayList<User> new_users = new ArrayList<User>();
					int i = 0;
					for (User u : users) {
						new_users.add(i, u);
						i++;
					}
					new_users.add(i, new_user);

					ConfigXML.config.getUsers().clear();
					ConfigXML.config.setUsers(new_users);
					ConfigXML.writeXML(ConfigXML.config, file);

					String new_item = "";
					new_item += new_user.getUsername();
					new_item += "  [";
					new_item += new_user.getEmailAddr();
					new_item += "]";
					user_list_field.addItem(new_item);
//				} else {
//					messageDialog("<html><font color=RED > The credentials are wrong! </font></html>");
//					System.out.println("WARNING: Incorrect credentials!");
//				}
			}
			
			private void loginModifyUser() {
				String passwd = String.valueOf(user_passwd_field.getPassword());
				boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd, USER_PERMISSION_TO_MODIFY_MSG);
				
				if (check) {
					array = user_list_field.getSelectedItem().toString().split(" ");
					
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
				} else {
					add(user_panel);
					messageDialog("<html><font color=RED > The credentials are wrong! </font></html>");
					System.out.println("WARNING: Incorrect credentials!");
				}
			}

			private void modifyUser() {
//				String passwd = String.valueOf(user_passwd_field.getPassword());
//				boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd, USER_HAS_BEEN_MODIFIED_MSG);
//
//				if (check) {
					User modify_user = null;
					ArrayList<User> users = ConfigXML.config.getUsers();
					ArrayList<User> new_users = new ArrayList<User>();
					int i = 0;
					for (User u : users) {
						if (user_list_field.getSelectedItem() == u) {
							users.remove(i);
						}
						new_users.add(i, u);
						i++;
					}
					modify_user = new User(modify_user_name_field.getText(), modify_user_email_field.getText());
					new_users.add(i, modify_user);

					ConfigXML.config.getUsers().clear();
					ConfigXML.config.setUsers(new_users);
					ConfigXML.writeXML(ConfigXML.config, file);

					user_list_field.removeItem(user_list_field.getSelectedItem());

					String new_item = "";
					new_item += modify_user.getUsername();
					new_item += "  [";
					new_item += modify_user.getEmailAddr();
					new_item += "]";
					user_list_field.addItem(new_item);
//				} else {
//					messageDialog("<html><font color=RED > The credentials are wrong! </font></html>");
//					System.out.println("WARNING: Incorrect credentials!");
//				}
			}

			private void deleteUser() {
//				String passwd = String.valueOf(user_passwd_field.getPassword());
//				boolean check = EMail_Tools.checkAuth(getCurrentEmail(), passwd, USER_HAS_BEEN_DELETED_MSG);
//
//				if (check) {
					file = new File("Resources/config.xml");
					ArrayList<User> users = ConfigXML.config.getUsers();
					ArrayList<User> new_users = new ArrayList<User>();
					for (User u : users) {
						if (!u.getEmailAddr().equals(getCurrentEmail())) {
							new_users.add(u);
						}
					}
					ConfigXML.config.getUsers().clear();
					ConfigXML.config.setUsers(new_users);
					ConfigXML.writeXML(ConfigXML.config, file);

					user_list_field.removeItem(user_list_field.getSelectedItem());
//				} else {
//					messageDialog("<html><font color=RED > The credentials are wrong! </font></html>");
//					System.out.println("WARNING: Incorrect credentials!");
//				}

			}
		};
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
	
	private void messageDialog(String message) {
		String try_again = "<html><font color=RED > Please try again... </font></html>";
		JOptionPane.showMessageDialog(null, try_again + "\n" + message, "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}
}