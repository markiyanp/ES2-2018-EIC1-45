package visual;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.User;

public class Window extends JFrame{
	
	private static final long serialVersionUID = -1172067089622609226L;
	
	private final String TITLE = "ES2-2018-EIC1-G45";

	//**********************************DIMENSIONS*************************************
	private final int WIDTH = 1140;
	private final int HEIGHT = 699;
	private final int LEFT_PANEL_WIDTH = 270;
	private final int TOP_PANEL_HEIGHT = 70;
	private final int BOTT_PANEL_HEIGHT = 30;
	//**********************************DIMENSIONS*************************************
	
	//**********************************INSTANCES**************************************
	private TopPanel top_panel;
	private LeftPanel left_panel;
	private RightPanel right_panel;
	private Bott_Panel bott_panel;
	private MainPanel main_panel;
	private static User user;
	//**********************************INSTANCES**************************************
	
	//**********************************CONFIG*****************************************
	//**********************************CONFIG*****************************************
	
	public Window(User user) {
		//INITIALIZE
		this.user = user;
		this.top_panel = new TopPanel(this, TOP_PANEL_HEIGHT);
		this.bott_panel = new Bott_Panel(this,BOTT_PANEL_HEIGHT);
		this.right_panel = new RightPanel(this);
		this.left_panel = new LeftPanel(this,LEFT_PANEL_WIDTH);
		this.main_panel = new MainPanel(this);
		
		//FRAME CONFIG
		add(main_panel, BorderLayout.CENTER);
		setTitle(TITLE + " - " + user.getName());
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	

	public JPanel getMain_panel() {
		return main_panel;
	}

	public void setMain_panel(MainPanel main_panel) {
		this.main_panel = main_panel;
	}

	public TopPanel getTop_panel() {
		return top_panel;
	}

	public JPanel getLeft_panel() {
		return left_panel;
	}

	public RightPanel getRight_panel() {
		return right_panel;
	}

	public JPanel getBott_panel() {
		return bott_panel;
	}

	public static User getUser() {
		return user;
	}


	public static void setUser(User user) {
		Window.user = user;
	}

}