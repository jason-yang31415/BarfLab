import java.util.ArrayList;
import java.util.Arrays;


public class MMLConverter {

	public static String[] opsArray = {"ADD", "SUB", "MULT", "DIV", "POW"};
	public static ArrayList<String> ops = new ArrayList<String>(Arrays.asList(opsArray));
	
	public static String convert(Thing left, Thing right){
		String s = "";
		s += "<?xml version=\"1.0\"?>\n<!DOCTYPE math PUBLIC \"-//W3C//DTD MathML 2.0//EN\" \"http://www.w3.org/TR/MathML2/dtd/mathml2.dtd\">\n";
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
