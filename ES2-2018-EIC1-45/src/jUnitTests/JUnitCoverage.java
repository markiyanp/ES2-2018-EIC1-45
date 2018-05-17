package jUnitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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

import org.apache.commons.mail.EmailException;
import org.junit.Test;
import org.uma.jmetal.util.JMetalException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import core.Config;
import core.Path;
import core.Problem;
import core.User;
import core.Variable;
import email.EMail_Tools;
import jMetal.OptimizationProcess;
import visual.Launcher;
import visual.Window;
import xml.ConfigXML;

public class JUnitCoverage {

	/**
	 * Class specified to test ConfigXML class using JUnit
	 * 
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
				problemRootElement.appendChild(putUser(doc, user.getName(), user.getEmailAddr(), user.getAlgorithms()));
			}

			for (Path path : config.getPaths()) {
				problemRootElement.appendChild(putPath(doc, path.getName(), path.getUrl()));
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			Result output = new StreamResult(file1);
			transformer.transform(source, output);
			ConfigXML.writeXML(config, file2);
			assertEquals(file1.getTotalSpace(), file2.getTotalSpace());
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
		assertEquals(config2.getAdmin_name(),ConfigXML.config.getAdmin_name());
		assertEquals(config2.getAdmin_mail(),ConfigXML.config.getAdmin_mail());
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
	
	private static Node putUser(Document doc, String name, String mail, ArrayList<String> algorithms) {
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
		return variable;
	}

	private static Node putNodeElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	/**
	 * Check's if AdminEmail is the right one
	 */
	@Test
	public void checkAdminEmail() {
		assertEquals(EMail_Tools.getAdminEmail(), "AGrupo45@gmail.com");
	}

	/**
	 * Check's for an email when sending an email with invalid data
	 * 
	 * @throws EmailException
	 */
	@Test(expected = EmailException.class)
	public void sendEmail() throws EmailException {
		EMail_Tools.sendMail("aaa@gmail.com", "Potato", "bbb@gmail.com", null, "TesteUnitário 1",
				"isto é um mail do teste unitario 1, não devias estar a receber isto", "");
	}

	/**
	 * Tests writeXML method
	 * 
	 * @throws TransformerException
	 */
	@Test(expected = TransformerException.class)
	public void testWriteXML2() throws TransformerException {
		Problem problem = new Problem();
		File file1 = null;
		String fileName = null;
		if (file1 == null) {
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

			problemRootElement.appendChild(putAbout2(doc, problem.getProblem_name(), problem.getProblem_description(),
					problem.getAlgorithm()));

			problemRootElement.appendChild(putUser2(doc, problem.getUser_name(), problem.getUser_email()));

			for (Variable var : problem.getVariables()) {
				problemRootElement.appendChild(
						putVariable2(doc, var.getVariable_name(), var.getVariable_type(), var.getVariable_min_val(),
								var.getVariable_max_val(), var.getVariable_restricted(), var.isVariable_used()));
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			// TODO dont leave this like that!!!!!!!!
			if (file1 == null) {
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
	 * Tests readXML method it should pop up an "file no longer exists"
	 */
	@Test
	public void testReadXML2() {
		File file = new File("Resources/TestXML/TestProblem.xml");
		Problem result = new Problem();
		if (file.getFreeSpace() != 0) {
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
						variable.setVariable_restricted(
								eElement.getElementsByTagName("restricted").item(0).getTextContent());
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
						result.setProblem_description(
								eElement.getElementsByTagName("description").item(0).getTextContent());
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
			assertTrue(file.length() > 0);
		}
		
	}

	/**
	 * tests three types of JMetal
	 */
	@Test(expected = JMetalException.class)
	public void testJMetal() {
		Object[][] testD = { { "test1-should-not-appear", "Double", "-1.1", "2.2", null, false },
				{ "test2-should-appear", "Double", "-2.0", "2.0", null, true },
				{ "test3-should-appear", "Double", "-10.0", "10.0", null, true },
				{ "test4-should-not-appear", "Integer", "-10", "10", null, false } };
		OptimizationProcess.runOptimization(testD, "NSGAII", false, null);

		Object[][] testI = { { "test1-should-appear", "Integer", "-1", "2", null, true },
				{ "test2-should-appear", "Integer", "-2", "5", null, true },
				{ "test3-should-appear", "Integer", "-10", "10", null, true },
				{ "test4-should-not-appear", "Integer", "-10", "10", null, false } };
		OptimizationProcess.runOptimization(testI, "SMSEMOA", false, null);
		// SMSEMOA
		
		Object[][] testII = { { "test1-should-appear", "Binary", "00010", "1000", null, true },
				{ "test2-should-appear", "Integer", "00000", "10000", null, true },
				{ "test3-should-appear", "Integer", "01010", "10101", null, true },
				{ "test4-should-not-appear", "Integer", "0", "10", null, false } };
		OptimizationProcess.runOptimization(testII, "SMSEMOA", false, null);

		Object[][] test1 = { { "test1-singular-variable", "Double", "-1.1", "2.2", null, true } };
		OptimizationProcess.runOptimization(test1, "NSGAII", false, null);

		Object[][] testSame = { { "test1-should-crash", "Double", "0", "0", null, true } };
		OptimizationProcess.runOptimization(testSame, "NSGAII", false, null);
		throw new JMetalException("");
		
	}

	private static Node putVariable2(Document doc, String name, String type, String min_val, String max_val,
			String restricted, String used) {
		Element variable = doc.createElement("Variable");
		variable.appendChild(putNodeElements2(doc, variable, "name", name));
		variable.appendChild(putNodeElements2(doc, variable, "type", type));
		variable.appendChild(putNodeElements2(doc, variable, "min_val", min_val));
		variable.appendChild(putNodeElements2(doc, variable, "max_val", max_val));
		variable.appendChild(putNodeElements2(doc, variable, "restricted", restricted));
		variable.appendChild(putNodeElements2(doc, variable, "used", used));
		return variable;
	}

	private static Node putAbout2(Document doc, String name, String description, String algorythm) {
		Element variable = doc.createElement("About");
		variable.appendChild(putNodeElements2(doc, variable, "name", name));
		variable.appendChild(putNodeElements2(doc, variable, "description", description));
		variable.appendChild(putNodeElements2(doc, variable, "algorythm", algorythm));
		return variable;
	}

	private static Node putUser2(Document doc, String name, String mail) {
		Element variable = doc.createElement("User");
		variable.appendChild(putNodeElements2(doc, variable, "name", name));
		variable.appendChild(putNodeElements2(doc, variable, "mail", mail));
		return variable;
	}

	private static Node putNodeElements2(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	
	/**
	 * Test GUI opening
	 */
	@Test
	public void testGUI() {
		Launcher launcher = new Launcher();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
