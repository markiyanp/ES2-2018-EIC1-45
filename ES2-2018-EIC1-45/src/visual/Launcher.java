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
		String curDir = System.getProperty("user.dir");
		file = new File(curDir + "/" + "config.xml");
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

<<<<<<< HEAD
=======
//	public File readFromJARFile() throws IOException {
//		InputStream inputStream = Launcher.class.getResourceAsStream("/config.xml");
//		File return_file = new File("Resources/config.xml");
//		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
//			FileOutputStream outputStream = new FileOutputStream(return_file);
//			int read = 0;
//			byte[] bytes = new byte[1024];
//			while ((read = inputStream.read(bytes)) != -1) {
//				outputStream.write(bytes, 0, read);
//			}
//			outputStream.close();
//		} catch (IOException e) {
//		}
//
//		return return_file;
//	}
//	
//	public File readFromJarFile(){
//		File file = null;
//	    String resource = "/Resources/config.xml";
//	    URL res = getClass().getResource(resource);
//	    if (res.toString().startsWith("jar:")) {
//	        try {
//	            InputStream input = getClass().getResourceAsStream(resource);
//	            file = File.createTempFile("tempfile", ".tmp");
//	            OutputStream out = new FileOutputStream(file);
//	            int read;
//	            byte[] bytes = new byte[1024];
//
//	            while ((read = input.read(bytes)) != -1) {
//	                out.write(bytes, 0, read);
//	            }
//	            file.deleteOnExit();
//	        } catch (IOException ex) {
//	        }
//	    } else {
//	        //this will probably work in your IDE, but not from a JAR
//	        file = new File(res.getFile());
//	    }
//
//	    if (file != null && !file.exists()) {
//	        throw new RuntimeException("Error: File " + file + " not found!");
//	    }
//	    return file;
//	}

	/**
	 * The main
	 * 
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException
	 */
>>>>>>> branch 'master' of https://github.com/markiyan-iscteiulpt/ES2-2018-EIC1-45.git
	public static void main(String[] args) throws IOException, URISyntaxException {
		new Launcher();
	}
}