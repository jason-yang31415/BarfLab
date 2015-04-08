import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class EqFile {

	String[] numeralsArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
	ArrayList<String> numerals = new ArrayList<String>(Arrays.asList(numeralsArray));
	String[] opsArray = {"+", "-", "*", "/", "^"};
	ArrayList<String> ops = new ArrayList<String>(Arrays.asList(opsArray));
	
	public static void main(String[] args){
		try {
			new EqFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public EqFile() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("D:/Eclipse/Workspace/EqS/src/eq/test.eq"));
		String line;
		while ((line = reader.readLine()) != null){
			//parse
			if (line.startsWith("EQ ")){
				String equation = line.split("EQ ")[1];
				String[] line1 = equation.split(" = ");
				String leftString = line1[0];
				String rightString = line1[1];
				
				//Thing left = parseExpression(leftString);
				//Thing right = parseExpression(rightString);
				parseExpression(leftString);
				parseExpression(rightString);
			}
		}
		reader.close();
	}
	
	public void parseExpression(String e){
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
		
		Thing thing;
		
		e = e.replaceAll("\\s", "");
		String[] stringArray = e.split("");
		ArrayList<String> string = new ArrayList<String>(Arrays.asList(stringArray));
		int parentheses = 0;
		int opOrder = -1;
		int opPos = -1;
		
		for (String s : string){
			if (s.equals("("))
				parentheses++;
			
			if (s.equals(")"))
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
							}
						}
						else {
							opOrder = ops.indexOf(s);
							opPos = string.indexOf(s);
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
		}
		//Does not contain any ops outside parentheses
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
				
			}
			else {
				//Parse e as constant or var
				if (isUpper(e)){
					thing = new Value(0, true);
				}
				else {
					thing = new Value(Float.parseFloat(e), false);
				}
			}
		}
		
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
	
}
