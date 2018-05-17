package xml;

import java.io.File;
import java.util.ArrayList;

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

	
	/**
	 * Writes "config" in "file" with xml format
	 * @param config
	 * @param file
	 */
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
				problemRootElement.appendChild(putUser(doc, user.getName(),
						user.getEmailAddr(), user.getAlgorithms(), user.getCreate_var()));
			}

			for(String path_name : config.getPaths().keySet()){
				problemRootElement.appendChild(putPath(doc, path_name,
						config.getPaths().get(path_name).getUrl()));
			}
			
			problemRootElement.appendChild(putLimitTime(doc, config.getTime()));


			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource source = new DOMSource(doc);
			Result output = new StreamResult(file);
			transformer.transform(source, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * reads xml File and saves it in config object
	 * @param file
	 */
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
						config.getPaths().put(eElement.getElementsByTagName("name").item(0).getTextContent(), variable);
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
						u.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
						u.setEmailAddr(eElement.getElementsByTagName("mail").item(0).getTextContent());
						String[] algorithms = eElement.getElementsByTagName("algorithms").item(0).getTextContent().split(",");
						ArrayList<String> array = new ArrayList<String>();
						for(String algo : algorithms) {
							array.add(algo);
						}
						u.setAlgorithms(array);
						u.setCreate_var(eElement.getElementsByTagName("createVars").item(0).getTextContent());
						config.getUsers().add(u);
					}
				}
				
				NodeList aboutTime = doc.getElementsByTagName("TimeOut");
				for (int temp = 0; temp < aboutTime.getLength(); temp++) {
					Node nNode = aboutTime.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						config.setTime(eElement.getElementsByTagName("time").item(0).getTextContent());
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

	/**
	 * Creates Node with user "name" and "email" and puts it in "doc"
	 * @param doc
	 * @param name
	 * @param mail
	 * @return Node
	 */
	private static Node putUser(Document doc, String name, String mail, ArrayList<String> algorithms, String create_var) {
		Element variable = doc.createElement("User");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "mail", mail));
		String algo = "";
		for(int i = 0; i < algorithms.size(); i++) {
			if(i != algorithms.size() - 1) {
				algo += algorithms.get(i);
				algo += ",";
			} else {
				algo += algorithms.get(i);
			}
		}
		variable.appendChild(putNodeElements(doc, variable, "algorithms", algo));
		variable.appendChild(putNodeElements(doc, variable, "createVars", create_var));
		return variable;
	}

	/**
	 * Creates Node with path "name" and "url" and puts it in doc
	 * @param doc
	 * @param name
	 * @param url
	 * @return Node
	 */
	private static Node putPath(Document doc, String name, String url) {
		Element variable = doc.createElement("Path");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "url", url));
		return variable;
	}
	
	/**
	 * Cretes Node with Admin "name" and "mail" and puts it in "doc"
	 * @param doc
	 * @param name
	 * @param mail
	 * @return Node
	 */
	private static Node putAdmin(Document doc, String name, String mail) {
		Element variable = doc.createElement("Admin");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "mail", mail));
		return variable;
	}
	
	/**
	 * Creates Node with limit time "time"
	 * @param doc
	 * @param name
	 * @return Node
	 */
	private static Node putLimitTime(Document doc, String time) {
		Element variable = doc.createElement("TimeOut");
		variable.appendChild(putNodeElements(doc, variable, "time", time));
		return variable;
	}

	/**
	 * Creates Node with Elements "name" and "value" and puts it in "doc"
	 * @param doc
	 * @param element
	 * @param name
	 * @param value
	 * @return Node
	 */
	private static Node putNodeElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	/**
	 * Getter to ConfigXML instance
	 * @return ConfigXML instance
	 */
	public static synchronized ConfigXML getInstance() {
		if (instance == null) {
			instance = new ConfigXML();
		}
		return instance;
	}
}