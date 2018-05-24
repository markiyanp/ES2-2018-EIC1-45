package visual;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import xml.ConfigXML;

/**
 * @author Markiyan Pyekh
 *
 */
public class Launcher extends JFrame {

	private static final long serialVersionUID = 1L;
	private final String TITLE = "ES2-2018-EIC1-G45";

	// **********************************DIMENSIONS*************************************
	private final int WIDTH = 400;
	private final int HEIGHT = 521;
	// **********************************DIMENSIONS*************************************

	// **********************************INSTANCES**************************************
	private JPanel main;
	private File file;
	// **********************************INSTANCES**************************************
	
	/**
	 * The constructor
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public Launcher() throws IOException, URISyntaxException {
		ConfigXML.currentDirectory = System.getProperty("user.dir");
		file = new File(ConfigXML.currentDirectory + "/" + "config.xml");
		ConfigXML.readXML(file);
		setTitle(TITLE);
		main = new LauncherPanel(this, file);
		add(main);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * The main
	 * 
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		new Launcher();
	}
	
}