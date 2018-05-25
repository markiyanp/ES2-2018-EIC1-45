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

import core.Objective;
import core.Problem;
import core.Variable;

public class ProblemXML {

	public static Problem problem;

	private static ProblemXML instance;

	/**
	 * Writes in XML file all info about user's problem
	 * 
	 * @param problem
	 * @param file
	 */
	public static void writeXML(Problem problem, File file) {
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element problemRootElement = doc.createElement("Problem");
			doc.appendChild(problemRootElement);

			problemRootElement.appendChild(
					putAbout(doc, problem.getProblem_name(), problem.getProblem_description(), problem.getAlgorithm()));

			problemRootElement.appendChild(putUser(doc, problem.getUser_name(), problem.getUser_email()));

			for (Variable var : problem.getVariables()) {
				problemRootElement.appendChild(
						putVariable(doc, var.getVariable_name(), var.getVariable_type(), var.getVariable_min_val(),
								var.getVariable_max_val(), var.getVariable_restricted(), var.isVariable_used()));
			}
			
			for(Objective obj : problem.getObjectives()){
				problemRootElement.appendChild(putObjective(doc, obj.getName(), obj.getType(), obj.isUsed()));
			}

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			if (file == null) {
				JOptionPane.showMessageDialog(null, "File no longer exists! \nConfig has not been saved!", "File error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				Result output = new StreamResult(file);
				transformer.transform(source, output);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads XML file and saves it into "Problem" object
	 * 
	 * @param file
	 */
	public static void readXML(File file) {
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
				
				NodeList objectiveList = doc.getElementsByTagName("Objective");

				for (int temp = 0; temp < objectiveList.getLength(); temp++) {
					Node nNode = objectiveList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Objective objective = new Objective();
						Element eElement = (Element) nNode;

						objective.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
						objective.setType(eElement.getElementsByTagName("type").item(0).getTextContent());
						objective.setUsed(eElement.getElementsByTagName("used").item(0).getTextContent());
						result.getObjectives().add(objective);
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
		} else {
			JOptionPane.showMessageDialog(null, "File no longer exists! \nConfig has not been saved!", "File error",
					JOptionPane.ERROR_MESSAGE);
		}
		problem = result;
	}

	/**
	 * Creates Node with variable name, type, minimum value, maximum value and puts it in "doc"
	 * 
	 * @param doc
	 * @param name
	 * @param type
	 * @param min_val
	 * @param max_val
	 * @param restricted
	 * @param used
	 * @return variable
	 */
	private static Node putVariable(Document doc, String name, String type, String min_val, String max_val,
			String restricted, String used) {
		Element variable = doc.createElement("Variable");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "type", type));
		variable.appendChild(putNodeElements(doc, variable, "min_val", min_val));
		variable.appendChild(putNodeElements(doc, variable, "max_val", max_val));
		variable.appendChild(putNodeElements(doc, variable, "restricted", restricted));
		variable.appendChild(putNodeElements(doc, variable, "used", used));
		return variable;
	}

	/**
	 * Creates Node with problem name, description, algorithm and puts it in "doc"
	 * 
	 * @param doc
	 * @param name
	 * @param description
	 * @param algorythm
	 * @return variable
	 */
	private static Node putAbout(Document doc, String name, String description, String algorythm) {
		Element variable = doc.createElement("About");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "description", description));
		variable.appendChild(putNodeElements(doc, variable, "algorythm", algorythm));
		return variable;
	}

	/**
	 * Creates Node with user name, email and puts it in "doc"
	 * 
	 * @param doc
	 * @param name
	 * @param mail
	 * @return variable
	 */
	private static Node putUser(Document doc, String name, String mail) {
		Element variable = doc.createElement("User");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "mail", mail));
		return variable;
	}
	
	/**
	 * Creates Node with objective name, type, if it used and puts it in "doc"
	 * 
	 * @param doc
	 * @param name
	 * @param type
	 * @param used
	 * @return variable
	 */
	private static Node putObjective(Document doc, String name, String type, String used) {
		Element variable = doc.createElement("Objective");
		variable.appendChild(putNodeElements(doc, variable, "name", name));
		variable.appendChild(putNodeElements(doc, variable, "type", type));
		variable.appendChild(putNodeElements(doc, variable, "used", used));
		return variable;
	}

	/**
	 * Creates Node with Elements "name" and "value" and puts it in "doc"
	 * 
	 * @param doc
	 * @param element
	 * @param name
	 * @param value
	 * @return node
	 */
	private static Node putNodeElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	/**
	 * Getter to ProblemXML instance
	 * 
	 * @return instance
	 */
	public static synchronized ProblemXML getInstance() {
		if (instance == null) {
			instance = new ProblemXML();
		}
		return instance;
	}
}