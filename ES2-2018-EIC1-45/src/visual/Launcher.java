package visual;

import java.io.File;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import core.User;
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
	private File file;
	//**********************************INSTANCES**************************************
	
	public Launcher(){
		file = new File("Resources/config.xml");
		ConfigXML.readXML(file);
		setTitle(TITLE);
		main = new LauncherPanel(this,file);
		add(main);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		
		new Launcher();
	}
	
	public URL getResource(String resource){

	    URL url ;

	    //Try with the Thread Context Loader. 
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    if(classLoader != null){
	        url = classLoader.getResource(resource);
	        if(url != null){
	            return url;
	        }
	    }

	    //Let's now try with the classloader that loaded this class.
	    classLoader = this.getClass().getClassLoader();
	    if(classLoader != null){
	        url = classLoader.getResource(resource);
	        if(url != null){
	            return url;
	        }
	    }

	    //Last ditch attempt. Get the resource from the classpath.
	    return ClassLoader.getSystemResource(resource);
	}
}