package visual;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

import xml.ConfigXML;

public class Launcher extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private final String TITLE = "ES2-2018-EIC1-G45";
	
	//**********************************DIMENSIONS*************************************
	private final int WIDTH = 400;
	private final int HEIGHT = 521;
	//**********************************DIMENSIONS*************************************

	//**********************************INSTANCES**************************************
	private JPanel main;
	private ClassLoader classLoader = getClass().getClassLoader();
	private File file = new File(classLoader.getResource("config.xml").getFile());
	//**********************************INSTANCES**************************************
	
	public Launcher() {
		ConfigXML.readXML(file);
		setTitle(TITLE);
		main = new LauncherPanel(file);
		add(main);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		
//		Config cfg = new Config();
//		cfg.setAdmin_mail("mphna@gmail.com");
//		cfg.setAdmin_name("Markiyan");
//		
//		User u = new User("Tiago", "tiago@gmail.com");
//		User u1 = new User("Paulo", "paulo@gmail.com");
//		User u2 = new User("Andre", "andre@gmail.com");
//		
//		ArrayList<User> users = new ArrayList<>();
//		users.add(u);
//		users.add(u1);
//		users.add(u2);
//	
//		Path p = new Path("ProblemsPath", "C:/");
//		
//		ArrayList<Path> paths = new ArrayList<>();
//		paths.add(p);
//		
//		ArrayList<String> algorithms = new ArrayList<>();
//		algorithms.add("NSGAII");
//		
//		
//		cfg.setUsers(users);
//		cfg.setPaths(paths);
//		cfg.setAlgorithms(algorithms);
//		
//		ConfigXML.writeXML(cfg, new File("Resources/config.xml"));
		
		
		new Launcher();
	}
}