package visual;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import xml.ConfigXML;

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

	public Launcher() throws IOException {
//		file = readFromJARFile();
		file = new File("Resources/config.xml");
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

	public File readFromJARFile() throws IOException {
		InputStream inputStream = Launcher.class.getResourceAsStream("/config.xml");
		File return_file = new File("conf.xml");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			FileOutputStream outputStream = new FileOutputStream(return_file);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
		} catch (IOException e) {
		}

		return return_file;
	}

	public static void main(String[] args) throws IOException {
		new Launcher();
	}

	public URL getResource(String resource) {

		URL url;

		// Try with the Thread Context Loader.
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			url = classLoader.getResource(resource);
			if (url != null) {
				return url;
			}
		}

		// Let's now try with the classloader that loaded this class.
		classLoader = this.getClass().getClassLoader();
		if (classLoader != null) {
			url = classLoader.getResource(resource);
			if (url != null) {
				return url;
			}
		}

		// Last ditch attempt. Get the resource from the classpath.
		return ClassLoader.getSystemResource(resource);
	}
}