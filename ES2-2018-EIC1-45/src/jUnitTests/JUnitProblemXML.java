package jUnitTests;


import java.io.File;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.Problem;
import core.Variable;

public class JUnitProblemXML {

	/**
	 * Class specified to test ProblemXML class using JUnit
	 * @author afgos-iscteiulpt
	 */
	
	/**
	 * Tests writeXML method
	 * @throws TransformerException 
	 */
	@Test(expected=TransformerException.class)
	public void testWriteXML() throws TransformerException {
		Problem problem= new Problem();
		File file1 = null;
		String fileName = null;
		if(file1==null){
			String extension = ".xml";
			DateTimeFormatter timeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
			String time = timeStamp.format(java.time.LocalDateTime.now());
			fileName = time + extension;
		}

		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
	
			try {
				icBuilder = icFactory.newDocumentBuilder();
		
			Document doc = icBuilder.newDocument();
			Element problemRootElement = doc.createElement("Problem");
			doc.appendChild(problemRootElement);

			problemRootElement.appendChild(putAbout(doc, problem.getProblem_name(), 
					problem.getProblem_description(),
					problem.getAlgorithm()));

			problemRootElement.appendChild(putUser(doc, problem.getUser_name(),
					problem.getUser_email()));

			for(Variable var : problem.getVariables()){
				problemRootElement.appendChild(putVariable(doc, var.getVariable_name(),
						var.getVariable_type(),
						var.getVariable_min_val(),
						var.getVariable_max_val(),
						var.getVariable_restricted(),
						var.isVariable_used()));
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource source = new DOMSource(doc);
			//TODO dont leave this like that!!!!!!!!
			if(file1 == null){
				System.out.println(fileName);
				File f = new File("C:/Users/Admin/Desktop/testXML/" + fileName);
				Result output = new StreamResult(f);
				transformer.transform(source, output);
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * Tests readXML method
	 * it should pop up an "file no longer exists" 
	 */
	@Test(expected=NullPointerException.class)
	public void testReadXML() {
		File file = new File("Resources/TestXML/TestProblem");
		Problem result = new Problem();
		if(file.getFreeSpace() != 0){
			try {

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(file);
				doc.getDocumentElement().normalize();


				NodeList variableList = doc.getElementsByTagName("Variable");

				for (int temp = 0; temp < variableList.getLength(); temp++) {
					Node nNode = variableList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Variable variable = new Variable();
						Element eElement = (Element) nNode;

						variable.setVariable_name(eElement.getElementsByTagName("name").item(0).getTextContent());
						variable.setVariable_type(eElement.getElementsByTagName("type").item(0).getTextContent());
						variable.setVariable_min_val(eElement.getElementsByTagName("min_val").item(0).getTextContent());
						variable.setVariable_max_val(eElement.getElementsByTagName("max_val").item(0).getTextContent());
						variable.setVariable_restricted(eElement.getElementsByTagName("restricted").item(0).getTextContent());
						variable.setVariable_used(eElement.getElementsByTagName("used").item(0).getTextContent());
						result.getVariables().add(variable);
					}
				}

				NodeList aboutProblem = doc.getElementsByTagName("About");

				for (int temp = 0; temp < aboutProblem.getLength(); temp++) {
					Node nNode = aboutProblem.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						result.setProblem_name(eElement.getElementsByTagName("name").item(0).getTextContent());
						result.setProblem_description(eElement.getElementsByTagName("description").item(0).getTextContent());
						result.setAlgorithm(eElement.getElementsByTagName("algorythm").item(0).getTextContent());
					}
				}


				NodeList aboutUser = doc.getElementsByTagName("User");

				for (int temp = 0; temp < aboutUser.getLength(); temp++) {
					Node nNode = aboutUser.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						result.setUser_name(eElement.getElementsByTagName("name").item(0).getTextContent());
						result.setUser_email(eElement.getElementsByTagName("mail").item(0).getTextContent());
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
			throw new NullPointerException();
		}
	}
	
	private static Node putVariable(Document doc, String name, String type, String min_val, String max_val, String restricted, String used) {
		Element variable = doc.createElement("Variable");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "type", type));
		variable.appendChild(putNodeElements(doc, variable, "min_val", min_val));
		variable.appendChild(putNodeElements(doc, variable, "max_val", max_val));
		variable.appendChild(putNodeElements(doc, variable, "restricted", restricted));
		variable.appendChild(putNodeElements(doc, variable, "used", used));
		return variable;
	}

	private static Node putAbout(Document doc, String name, String description, String algorythm) {
		Element variable = doc.createElement("About");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "description", description));
		variable.appendChild(putNodeElements(doc, variable, "algorythm", algorythm));
		return variable;
	}

	private static Node putUser(Document doc, String name, String mail) {
		Element variable = doc.createElement("User");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "mail", mail));
		return variable;
	}

	private static Node putNodeElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

}
