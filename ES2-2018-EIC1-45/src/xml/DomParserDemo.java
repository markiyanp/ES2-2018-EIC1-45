package xml;

import java.io.File;

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

public class DomParserDemo {

	public static void main(String[] args) {
//			    	 createXML();
		readXML();
	}

	private static void createXML(){
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder icBuilder;
		try {
			icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element problemRootElement = doc.createElement("Problem");
			doc.appendChild(problemRootElement);


			// append child elements to root element
			problemRootElement.appendChild(putVariable(doc, "var1", "integer", "10", "false"));
			problemRootElement.appendChild(putVariable(doc, "var2", "integer", "20",  "true"));
			problemRootElement.appendChild(putVariable(doc, "var3", "double", "30",  "true"));

			// output DOM XML to console 
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource source = new DOMSource(doc);
			Result output = new StreamResult(new File("C:/Users/Admin/Desktop/testXML/outputt.xml"));
			StreamResult console = new StreamResult(System.out);
			transformer.transform(source, output);
			transformer.transform(source, console);



			System.out.println("\nXML DOM Created Successfully..");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private static void readXML(){
		try {

			File fXmlFile = new File("C:/Users/Admin/Desktop/testXML/outputt.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Variable");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

//					System.out.println("Staff id : " + eElement.getAttribute("Name"));
					System.out.println("Name : " + eElement.getElementsByTagName("Name").item(0).getTextContent());
					System.out.println("Type : " + eElement.getElementsByTagName("Type").item(0).getTextContent());
					System.out.println("Restrict : " + eElement.getElementsByTagName("Restrict").item(0).getTextContent());
					System.out.println("Used : " + eElement.getElementsByTagName("Used").item(0).getTextContent());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private static Node putVariable(Document doc, String id, String type, String restrict, String used) {
		Element company = doc.createElement("Variable");
//		company.setAttribute("Name", id);
		company.appendChild(getVariableElements(doc, company, "Name", id));
		company.appendChild(getVariableElements(doc, company, "Type", type));
		company.appendChild(getVariableElements(doc, company, "Restrict", restrict));
		company.appendChild(getVariableElements(doc, company, "Used", used));
		return company;
	}

	// utility method to create text node
	private static Node getVariableElements(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}