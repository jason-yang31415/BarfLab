import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class EqFile {

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
				String left = line1[0];
				String right = line1[1];
				
				parseExpression(left);
				parseExpression(right);
			}
		}
		reader.close();
	}
	
	public void parseExpression(String e){
		int rPpos = e.indexOf(")");
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
		}
		
		e = e.replaceAll("\\s", "");
		String[] stringArray = e.split("");
		for (String s : stringArray){
			if (isAlpha(s) && isUpper(s)){
				Thing var = new Value(0f, false);
				int varPos = e.indexOf(s);
				
			}
		}
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
