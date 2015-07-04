import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class MMLConverter {

	public static String[] opsArray = {"ADD", "SUB", "MULT", "DIV", "POW"};
	public static ArrayList<String> ops = new ArrayList<String>(Arrays.asList(opsArray));
	
	public static String convert(String e){
		String s = "EQ ";
		
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(new InputSource(new ByteArrayInputStream(e.getBytes("utf-8"))));
			if (doc.hasChildNodes())
				s += readNodeList(doc.getChildNodes());
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return s;
	}
	
	public static String readNodeList(NodeList nl){
		String s = "";
		for (int i = 0; i < nl.getLength(); i++){
			Node n = nl.item(i);
			s += readNode(n);
		}
		s = s.replaceAll("(\\r|\\n)", "");
		return s;
	}
	
	public static String readNode(Node n){
		//TODO add parentheses for mfrac and msup
		
		String s = "";
		switch (n.getNodeType()){
		case Node.ELEMENT_NODE:
			if (n.getNodeType() == Node.ELEMENT_NODE){
				switch (n.getNodeName()){
				case "mfrac":
					s += readNode(n.getChildNodes().item(1));
					s += "/";
					s += readNode(n.getChildNodes().item(3));
					break;
				case "msup":
					s += readNode(n.getChildNodes().item(1));
					s += "^";
					s += readNode(n.getChildNodes().item(3));
					break;
				default:
					s += readNodeList(n.getChildNodes());
					break;
				}
			}
			break;
		case Node.TEXT_NODE:
			s += n.getNodeValue();
			break;
		}
		return s;
	}
	
	public static String convert(Thing left, Thing right){
		String s = "";
		s += "<?xml version=\"1.0\"?>\n<!DOCTYPE math PUBLIC \"-//W3C//DTD MathML 3.0//EN\" \"http://www.w3.org/Math/DTD/mathml3/mathml3.dtd\">\n";
		s += "<math mode=\"display\">\n";
		s += "<mrow>\n";
		s += convertExpression(left);
		s += "<mo>=</mo>\n";
		s += convertExpression(right);
		s += "</mrow>\n</math>";
		
		return s;
	}
	
	public static String convertExpression(Thing e){
		String s = "";
		if (e instanceof Value){
			Value v = (Value) e;
			if (v.var)
				s += "<mi>" + v.name + "</mi>\n";
			else
				s += "<mn>" + format(e.calculate()) + "</mn>\n";
		}
		else {
			Operation o = (Operation) e;
			boolean p = false;
			if (o.parent != null){
				int o1 = ops.indexOf(((Operation) o.parent).type.toString());
				int o2 = ops.indexOf(o.type.toString());
				if (o1 > o2 && ((Operation) o.parent).type != OpType.DIV)
					p = true;
			}
			if (p)
				s += "<mo>(</mo>\n";
			
			switch (((Operation) e).type){
			case ADD:
				s += convertExpression(o.v1) + "\n<mo>+</mo>\n" + convertExpression(o.v2) + "\n";
				break;
			case SUB:
				s += convertExpression(o.v1) + "\n<mo>-</mo>\n" + convertExpression(o.v2) + "\n";
				break;
			case MULT:
				s += convertExpression(o.v1) + "\n<mo>*</mo>\n" + convertExpression(o.v2) + "\n";
				break;
			case DIV:
				s += "<mfrac linethickness=\"1\">\n<mrow>" + convertExpression(o.v1) + "</mrow>\n<mrow>" + convertExpression(o.v2) + "</mrow>\n</mfrac>";
				break;
			case POW:
				s += "<msup>\n" + convertExpression(o.v1) + "\n" + convertExpression(o.v2) + "</msup>\n";
				break;
			case ROOT:
				break;
			case LOG:
				break;
			}
			
			if (p)
				s += "<mo>)</mo>";
		}
		
		return s;
	}
	
	public static String format(float d){
	    if (d == (long) d)
	        return String.format("%d",(long) d);
	    else
	        return String.format("%s",d);
	}
	
}
