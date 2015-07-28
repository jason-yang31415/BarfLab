import java.io.BufferedReader;
<<<<<<< HEAD
import java.io.FileInputStream;
import java.io.FileNotFoundException;
=======
>>>>>>> master
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqFile {

	Eq eq;
	
	boolean syntax = true;
	
	String[] numeralsArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
	ArrayList<String> numerals = new ArrayList<String>(Arrays.asList(numeralsArray));
	String[] opsArray = {"+", "-", "*", "/", "^"};
	ArrayList<String> ops = new ArrayList<String>(Arrays.asList(opsArray));
	
<<<<<<< HEAD
	String eqString;
	Map<String, List<String>> vars = new HashMap<String, List<String>>();
	
	/*public static void main(String[] args){
=======
	public static void main(String[] args){
>>>>>>> master
		try {
			new EqFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
<<<<<<< HEAD
	/*public Eq importEqFromFile(String path) throws IOException{
		//InputStream i = getClass().getClassLoader().getResourceAsStream("eq/test.eq");
		InputStream i = new FileInputStream(path);
=======
	public EqFile() throws IOException{
		InputStream i = getClass().getResourceAsStream("eq/test_complicated.eq");
>>>>>>> master
		BufferedReader reader = new BufferedReader(new InputStreamReader(i));
		String line;
		while ((line = reader.readLine()) != null){
			//parse
			if (line.startsWith("EQ ")){
				String equation = line.split("EQ ")[1];
				equation = equation.replaceAll("\\s", "");
				String[] line1 = equation.split("=");
				String leftString = line1[0];
				String rightString = line1[1];
				
				Thing left = parseExpression(leftString);
				Thing right = parseExpression(rightString);
				//parseExpression(leftString);
				//parseExpression(rightString);
				
				EqS eqs = new EqS(left, right);
				System.out.println(eqs.getAnswer());
			}
		}
		reader.close();
<<<<<<< HEAD
		System.err.println("No equation found");
		return null;
	}*/
	
	public Eq importEqFromString(String eqString) {
		//InputStream i = getClass().getClassLoader().getResourceAsStream("eq/test.eq");
		syntax = true;
		String eqLineString = null;
		String[] lines = eqString.split(System.getProperty("line.separator"));
		for (String line : lines){
			//parse
			if (line.startsWith("EQ ")){
				eqLineString = line;
			}
			else if (line.startsWith("def")){
				String[] vdefSegs = line.split(" ");
				if (vdefSegs.length >= 3){
					ArrayList<String> array = new ArrayList<String>();
					for (int i = 2; i < vdefSegs.length; i++){
						array.add(vdefSegs[i]);
					}
					vars.put(vdefSegs[1], array);
				}
				else {
					System.err.println("Expected variable name and def");
					syntax = false;
				}
			}
			else if (line.startsWith("//")){
				
			}
			else if (line.startsWith("")){
				
			}
			else {
				System.err.println("Unexpected '" + line + "'");
				syntax = false;
			}
		}
		
		if ((eq = parseFile(eqLineString)) != null){
			if(!syntax) {
				System.err.println("Syntax error");
				eq.setSyntax(false);
			}
		}
		else {
			syntax = false;
			eq = new Eq(null, null);
			eq.setSyntax(false);
		}
		return eq;
	}
	
	public Eq parseFile(String eqString){
		if (eqString != null && eqString.split("EQ ").length > 1){
			String equation = eqString.split("EQ ")[1];
			Eq eq = parseEquation(equation);
			
			return eq;
		}
		System.err.println("No eq found");
		syntax = false;
		return null;
	}
	
	public Eq parseEquation(String equation){
		equation = equation.replaceAll("\\s", "");
		if (equation.contains("=") && equation.split("=").length > 1){
			String[] line1 = equation.split("=");
			String leftString = line1[0];
			String rightString = line1[1];
			
			Thing left = parseExpression(leftString);
			Thing right = parseExpression(rightString);
			
			Eq eq = new Eq(left, right);
			return eq;
		}
		else {
			return null;
		}
=======
>>>>>>> master
	}
	
	public Thing parseExpression(String e){
		/*int rPpos = e.indexOf(")");
		if (rPpos >= 0){
			String rPstring = e.substring(0, rPpos);
			int lPpos = rPstring.lastIndexOf("(");
			if (lPpos >= 0){
				String subString = rPstring.substring(lPpos + 1, rPpos);
				subString = subString.replaceAll("\\s", "");
				String v1string = subString.substring(0, 1);
				float v1float;
				boolean v1var;
				try {
					v1float = Float.parseFloat(v1string);
					v1var = false;
				} catch (NumberFormatException n){
					v1float = 0f;
					v1var = true;
				}
				System.out.println(v1var);
			}
			else {
				System.err.println("Syntax error: Expected '('\n:(\n");
			}
		}
		else {
			System.out.println(e);
		}*/
		
		Thing thing = null;
		
		e = e.replaceAll("\\s", "");
		String[] stringArray = e.split("");
		ArrayList<String> string = new ArrayList<String>(Arrays.asList(stringArray));
		int parentheses = 0;
		int opOrder = -1;
		int opPos = -1;
		String op = "";
		
		for (String s : string){
			if (s.equals("(") || s.equals("["))
				parentheses++;
			
			if (s.equals(")") || s.equals("]"))
				parentheses--;
			
			//If character is outside parentheses
			if (parentheses == 0){
				if (!s.equals("(") && !s.equals(")")){
					//If character is an op
					if (ops.contains(s)){
						if (opOrder >= 0 && opPos >= 0){
							if (opOrder > ops.indexOf(s)){
								opOrder = ops.indexOf(s);
								opPos = string.indexOf(s);
								op = s;
							}
						}
						else {
							opOrder = ops.indexOf(s);
							opPos = string.indexOf(s);
							op = s;
						}
					}
				}
			}
		}
		

		//Check if string e contains any ops outside parentheses
		if (opOrder >= 0 && opPos >= 0){
			String leftString = e.substring(0, opPos);
			String rightString = e.substring(opPos + 1, e.length());
			System.out.println(leftString);
			System.out.println(rightString);
			System.out.println();
			
			if (leftString.length() > 0 && rightString.length() > 0){
				OpType type;
				switch(op){
				case "+":
					type = OpType.ADD;
					break;
				case "-":
					type = OpType.SUB;
					break;
				case "*":
					type = OpType.MULT;
					break;
				case "/":
					type = OpType.DIV;
					break;
				case "^":
					type = OpType.POW;
					break;
				default:
					type = OpType.ADD;
					break;
				}
				thing = new Operation(type, parseExpression(leftString), parseExpression(rightString));
			}
			else {
				System.err.println("Expected value");
				thing = new Value(0, null, false, false);
				syntax = false;
			}
		}
		//Does not contain any ops outside parentheses
		else {
			if (e.startsWith("[") && e.endsWith("]")){
				String[] params = e.substring(1, e.length() - 1).split(",");
				switch (params[0]){
				case "log":
					if (params.length == 3)
						thing = new Operation(OpType.LOG, parseExpression(params[1]), parseExpression(params[2]));
					else {
						System.err.println("Extra / insufficient parameters");
						thing = new Value(0, null, false, false);
						syntax = false;
					}
					break;
				case "sqrt":
					if (params.length == 2)
						thing = new Operation(OpType.ROOT, parseExpression(params[1]), new Value(2, null, false, false));
					else {
						System.err.println("Extra / insufficient parameters");
						thing = new Value(0, null, false, false);
						syntax = false;
					}
					break;
				case "root":
					if (params.length == 3)
						thing = new Operation(OpType.ROOT, parseExpression(params[1]), parseExpression(params[2]));
					else {
						System.err.println("Extra / insufficient parameters");
						thing = new Value(0, null, false, false);
						syntax = false;
					}
					break;
				default:
					System.err.println("No such function");
					thing = new Value(0, null, false, false);
					syntax = false;
					break;
				}
			}
			else {
				boolean containsOp = false;
				for (String s : string){
					//Check if contains ops inside parentheses
					if (ops.contains(s)){
						containsOp = true;
					}
				}
				
				//Check if contains ops inside parentheses
				if (containsOp){
					//remove outer parentheses
					String stringNew = e.substring(1, e.length() - 1);
					thing = parseExpression(stringNew);
				}
				else {
					//Parse e as constant or var
					if (isAlpha(e)){
						if (vars.containsKey(e)){
							List<String> list = vars.get(e);
							if (list.get(0).equals("k")){
								if (list.size() >= 2){
									String s = "";
									for (int i = 1; i < list.size(); i++){
										s += list.get(i);
									}
									String[] array = s.split("");
									boolean containsVar = false;
									for (String c : array){
										if (isAlpha(c)){
											containsVar = true;
										}
									}
									if (!containsVar){
										thing = parseExpression(s);
									}
									else {
										System.err.println("Cannot have variable in constant");
										thing = new Value(0, null, false, false);
										syntax = false;
									}
								}
								else {
									System.err.println("No constant definition");
									thing = new Value(0, null, false, false);
									syntax = false;
								}
							}
							else if (list.get(0).equals("eq")){
								if (list.size() >= 2){
									String s = "";
									for (int i = 1; i < list.size(); i++){
										s += list.get(i);
									}
									Eq equation = null;
									if (s.startsWith("\\")){
										String path = s.substring(1, s.length());
										if (path.endsWith(".eq")){
											String eqString = "";
											try {
												InputStream i = new FileInputStream(path);
												BufferedReader reader = new BufferedReader(new InputStreamReader(i));
												String line;
												while ((line = reader.readLine()) != null){
													eqString += line + "\n";
												}
												reader.close();
											} catch (FileNotFoundException e1) {
												// TODO Auto-generated catch block
												System.err.println("File at '" + path + "' not found\njava.io.FileNotFoundException");
												syntax = false;
											} catch (IOException ioe) {
												// TODO Auto-generated catch block
												System.err.println("Something screwed up\njava.io.IOException");
												syntax = false;
											}
											equation = new EqFile().importEqFromString(eqString);
										}
									}
									else {
										equation = new EqFile().parseEquation(s);
									}
									
									if (equation != null && equation.syntax){
										EqS eqs = new EqS(equation);
										float answer = eqs.getAnswer();
										thing = new Value(answer, null, false, false);
									}
									else {
										System.err.println("No eq found in var def");
										thing = new Value(0, null, false, false);
										syntax = false;
									}
								}
								else
									syntax = false;
							}
							else if (list.get(0).equals("v")){
								thing = new Value(0, e, true, true);
							}
							else {
								System.err.println("Unexpected '" + list.get(0) + "'");
								syntax = false;
							}
							
						}
						else {
							thing = new Value(0, e, true, true);
						}
						
					}
					else {
						try {
							thing = new Value(Float.parseFloat(e), null, false, false);
						}
						catch (NumberFormatException err){
							System.err.println("Unexpected '" + e + "'\njava.lang.NumberFormatException");
							thing = new Value(0, null, false, false);
							syntax = false;
						}
					}
				}
			}
		}
		return thing;
		
		/*e = e.replaceAll("\\s", "");
		String[] stringArray = e.split("");
		for (String s : stringArray){
			if (isAlpha(s) && isUpper(s)){
				Thing var = new Value(0f, false);
				int varPos = e.indexOf(s);
			}
		}*/
	}
	
	public boolean isAlpha(String s){
		char[] chars = s.toCharArray();
		for (char c : chars){
			if (!Character.isLetter(c)){
				return false;
			}
		}
		return true;
	}
	
	public boolean isUpper(String s){
		char[] chars = s.toCharArray();
		for (char c : chars){
			if (!Character.isUpperCase(c)){
				return false;
			}
		}
		return true;
	}
	
	public boolean getSyntax(){
		return syntax;
	}
	
}
