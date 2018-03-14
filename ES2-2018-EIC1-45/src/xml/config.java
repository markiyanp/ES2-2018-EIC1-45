package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Tiago Almeida
 * This class manage the config.xml document
 */
public class config {

	private static String name = "Tiago Almeida";
	private static String email = "tfpaa@iscte-iul.pt";
	private static String jmetal_path = "jmetal";
	private static String jar_path = "jar";

	/**
	 * Write in config.xml document
	 */
	private static void writeXML() {
		try {
			File inputFile = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();         

			Node xml = doc.getFirstChild();
			//Create Node Admin
			Node admin = doc.createElement("Administrador");
			Node name_Admin = doc.createElement("Name");
			name_Admin.setTextContent(name);
			admin.appendChild(name_Admin);
			Node email_Admin = doc.createElement("Email");
			email_Admin.setTextContent(email);
			admin.appendChild(email_Admin);
			xml.appendChild(admin);

			//Create Node Paths
			Node paths = doc.createElement("Paths");
			Node jmetal = doc.createElement("JMetal_Path");
			jmetal.setTextContent(jmetal_path);
			paths.appendChild(jmetal);
			Node jar = doc.createElement("Jar_Path");
			jar.setTextContent(jar_path);
			paths.appendChild(jar);
			xml.appendChild(paths);

			// Save XML document
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new FileOutputStream("config.xml"));
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);

		} catch (Exception e) { e.printStackTrace(); }
	}

	/**
	 * Update in config.xml document with different information 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public static void updateXML() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		String filePath = "config.xml";
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList admins = doc.getElementsByTagName("Administrador");
			NodeList paths = doc.getElementsByTagName("Paths");
			Element admin_name = null;
			Element admin_email = null;
			Element path_jmetal = null;
			Element path_jar = null;
			for(int i = 0; i < admins.getLength(); i++) {
				admin_name = (Element) admins.item(i);
				Node update_Name = admin_name.getElementsByTagName("Name").item(0).getFirstChild();
				update_Name.setNodeValue(name);

				admin_email = (Element) admins.item(i);
				Node update_Email = admin_email.getElementsByTagName("Email").item(0).getFirstChild();
				update_Email.setNodeValue(email);

				path_jmetal = (Element) paths.item(i);
				Node update_JMetal = path_jmetal.getElementsByTagName("JMetal_Path").item(0).getFirstChild();
				update_JMetal.setNodeValue(jmetal_path);

				path_jar = (Element) paths.item(i);
				Node update_Jar = path_jar.getElementsByTagName("Jar_Path").item(0).getFirstChild();
				update_Jar.setNodeValue(jar_path);

				doc.getDocumentElement().normalize();
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("config.xml"));
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.transform(source, result);
			}
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Read config.xml document and decided if write or update
	 * @throws TransformerException
	 */
	public static void readXML() throws TransformerException {
		String filePath = "config.xml";
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList admin = doc.getElementsByTagName("Administrador");
			if(admin.getLength() == 0) {
				writeXML();
			} else {
				updateXML();
			}
		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void main (String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		readXML();
	}

}