package visual;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.User;

public class Window extends JFrame {

	private static final long serialVersionUID = -1172067089622609226L;

	private final String TITLE = "ES2-2018-EIC1-G45";

	// **********************************DIMENSIONS*************************************
	private final int WIDTH = 1140;
	private final int HEIGHT = 699;
	private final int LEFT_PANEL_WIDTH = 270;
	private final int TOP_PANEL_HEIGHT = 70;
	private final int BOTT_PANEL_HEIGHT = 30;
	// **********************************DIMENSIONS*************************************

	// **********************************INSTANCES**************************************
	private TopPanel top_panel;
	private LeftPanel left_panel;
	private RightPanel right_panel;
	private Bott_Panel bott_panel;
	private MainPanel main_panel;
	private User user;
	private File file;
	// **********************************INSTANCES**************************************

	// **********************************CONFIG*****************************************
	// **********************************CONFIG*****************************************

	/**
	 * The constructor of the main Window.
	 * 
	 * @param user
	 * @param config_file
	 */
	public Window(User user, File config_file) {
		// INITIALIZE
		this.user = user;
		this.file = config_file;
		this.top_panel = new TopPanel(this, TOP_PANEL_HEIGHT);
		this.bott_panel = new Bott_Panel(this, BOTT_PANEL_HEIGHT);
		this.right_panel = new RightPanel(this);
		this.left_panel = new LeftPanel(this, LEFT_PANEL_WIDTH);
		this.main_panel = new MainPanel(this);

		// FRAME CONFIG
		add(main_panel, BorderLayout.CENTER);
		setTitle(TITLE + " - " + user.getName());
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Returns the main Panel.
	 * 
	 * @return
	 */
	public JPanel getMain_panel() {
		return main_panel;
	}

	/**
	 * Sets the main Panel.
	 * 
	 * @param main_panel
	 */
	public void setMain_panel(MainPanel main_panel) {
		this.main_panel = main_panel;
	}

	/**
	 * Returns the top Panel.
	 * 
	 * @return
	 */
	public TopPanel getTop_panel() {
		return top_panel;
	}

	/**
	 * Returns the left Panel.
	 * 
	 * @return
	 */
	public JPanel getLeft_panel() {
		return left_panel;
	}

	/**
	 * Returns the right Panel.
	 * 
	 * @return
	 */
	public RightPanel getRight_panel() {
		return right_panel;
	}

	/**
	 * Returns the Bottom Panel.
	 * 
	 * @return
	 */
	public JPanel getBott_panel() {
		return bott_panel;
	}

	/**
	 * Returns logged User instance.
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the User that is logged in.
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the Path to config file.
	 * 
	 * @return
	 */
	public File getConfigPath() {
		return file;
	}
}