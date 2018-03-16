package xml;

import java.io.File;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.Config;
import core.Path;
import core.User;

public class ConfigXML {

	private static ConfigXML instance;
	public static Config config;

	//	public static void main(String[] args) {
	//		Config conf = new Config();
	//		conf.setAdmin_name("Tiago Almeida");
	//		conf.setAdmin_mail("tiago.almeida@gmail.com");
	//
	//		ArrayList<Path> paths = new ArrayList<>();
	//		paths.add(new Path("problemXMLfolder", "C:/Users/Admin/Desktop/testXML/"));
	//		paths.add(new Path("documents", "C:/Users/Admin/"));
	//		paths.add(new Path("desktop", "C:/Users/Admin/Desktop/"));
	//
	//		conf.setPaths(paths);
	//
	//
	//		ArrayList<User> users = new ArrayList<>();
	//		users.add(new User("Tiago Almeida", "tiago.almeida@gmail.com"));
	//		users.add(new User("Markiyan Pyekh", "markiyan.pyekh@gmail.com"));
	//
	//		conf.setUsers(users);
	//
	//		File f = new File("C:/Users/Admin/Desktop/testConfigXML/Config.xml");
	//
	////		writeXML(conf, f);
	//		Config config = readXML(f);
	//		System.out.println(config.getAdmin_mail());
	//		//		Problem ret = readXML(f);
	//		//		System.out.println(ret.getVariables().size());
	//	}

	public static void writeXML(Config config, File file){
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element problemRootElement = doc.createElement("Config");
			doc.appendChild(problemRootElement);

			problemRootElement.appendChild(putAdmin(doc, config.getAdmin_name(), 
					config.getAdmin_mail()));

			for(User user : config.getUsers()){
				problemRootElement.appendChild(putUser(doc, user.getUsername(),
						user.getEmailAddr()));
			}

			for(Path path : config.getPaths()){
				problemRootElement.appendChild(putPath(doc, path.getName(),
						path.getUrl()));
			}


			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource source = new DOMSource(doc);
			Result output = new StreamResult(file);
			transformer.transform(source, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readXML(File file){
		config = new Config();
		if(file.getFreeSpace() != 0){
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(file);
				doc.getDocumentElement().normalize();


				NodeList variableList = doc.getElementsByTagName("Path");

				for (int temp = 0; temp < variableList.getLength(); temp++) {
					Node nNode = variableList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Path variable = new Path();
						Element eElement = (Element) nNode;
						variable.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
						variable.setUrl(eElement.getElementsByTagName("url").item(0).getTextContent());
						config.getPaths().add(variable);
					}
				}

				NodeList aboutProblem = doc.getElementsByTagName("Admin");
				for (int temp = 0; temp < aboutProblem.getLength(); temp++) {
					Node nNode = aboutProblem.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						config.setAdmin_name(eElement.getElementsByTagName("name").item(0).getTextContent());
						config.setAdmin_mail(eElement.getElementsByTagName("mail").item(0).getTextContent());
					}
				}


				NodeList aboutUser = doc.getElementsByTagName("User");
				for (int temp = 0; temp < aboutUser.getLength(); temp++) {
					Node nNode = aboutUser.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						User u = new User();
						u.setUsername(eElement.getElementsByTagName("name").item(0).getTextContent());
						u.setEmailAddr(eElement.getElementsByTagName("mail").item(0).getTextContent());
						config.getUsers().add(u);
					}
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(null,
					"File no longer exists! \nConfig has not been saved!",
					"File error",
					JOptionPane.ERROR_MESSAGE);
		}
	}


	private static Node putUser(Document doc, String name, String mail) {
		Element variable = doc.createElement("User");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "mail", mail));
		return variable;
	}

	private static Node putPath(Document doc, String name, String url) {
		Element variable = doc.createElement("Path");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "url", url));
		return variable;
	}

	private static Node putAdmin(Document doc, String name, String mail) {
		Element variable = doc.createElement("Admin");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "mail", mail));
		return variable;
	}

	private static Node putNodeElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}


	public static synchronized ConfigXML getInstance() {
		if (instance == null) {
			instance = new ConfigXML();
		}
		return instance;
	}
}