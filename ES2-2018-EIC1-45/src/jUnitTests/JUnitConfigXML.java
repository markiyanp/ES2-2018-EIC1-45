package jUnitTests;

import static org.junit.Assert.*;

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

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.Config;
import core.Path;
import core.User;
import xml.ConfigXML;

public class JUnitConfigXML {

	/**
	 * Class specified to test ConfigXML class using JUnit
	 * @author afgos-iscteiulpt
	 */
	
	/**
	 * Tests WriteXML method
	 */
	@Test
	public void testWriteXML() {
		Config config = new Config();
		File file1 = new File("test-file1.xml");
		File file2 = new File("test-file2.xml");

		try {
			DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder icBuilder;
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element problemRootElement = doc.createElement("Config");
			doc.appendChild(problemRootElement);

			problemRootElement.appendChild(putAdmin(doc, config.getAdmin_name(), config.getAdmin_mail()));

			for (User user : config.getUsers()) {
				problemRootElement.appendChild(putUser(doc, user.getUsername(), user.getEmailAddr()));
			}

			for (Path path : config.getPaths()) {
				problemRootElement.appendChild(putPath(doc, path.getName(), path.getUrl()));
			}

			for (String s : config.getAlgorithms()) {
				problemRootElement.appendChild(putAlgorithm(doc, s));
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			Result output = new StreamResult(file1);
			transformer.transform(source, output);
			ConfigXML.writeXML(config, file2);
			assertEquals(file1.getTotalSpace(), file2.getTotalSpace());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Tests ReadXML Method
	 */
	
	@Test
	public void testReadXML() {
		File file = new File("Resources/TestXML/Config.xml");
		ConfigXML.readXML(file);
		Config config2 = ConfigXML.config;
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
						config2.getPaths().add(variable);
					}
				}

				NodeList aboutProblem = doc.getElementsByTagName("Admin");
				for (int temp = 0; temp < aboutProblem.getLength(); temp++) {
					Node nNode = aboutProblem.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						config2.setAdmin_name(eElement.getElementsByTagName("name").item(0).getTextContent());
						config2.setAdmin_mail(eElement.getElementsByTagName("mail").item(0).getTextContent());
					}
				}
				
				NodeList algorithms = doc.getElementsByTagName("Algorithm");
				for (int temp = 0; temp < algorithms.getLength(); temp++) {
					Node nNode = algorithms.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						config2.getAlgorithms().add(eElement.getElementsByTagName("algo").item(0).getTextContent());
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
						config2.getUsers().add(u);
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
		assertEquals(config2.getUsers(),ConfigXML.config.getUsers());
		assertEquals(config2.getPaths(),ConfigXML.config.getPaths());
		assertEquals(config2.getAlgorithms(),ConfigXML.config.getAlgorithms());
		assertEquals(config2.getAdmin_name(),ConfigXML.config.getAdmin_name());
		assertEquals(config2.getAdmin_mail(),ConfigXML.config.getAdmin_mail());
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

	private static Node putAlgorithm(Document doc, String name) {
		Element variable = doc.createElement("Algorithm");
		variable.appendChild(putNodeElements(doc, variable, "algo", name));
		return variable;
	}

	private static Node putNodeElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

}
