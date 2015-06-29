import java.util.ArrayList;


public class Operation extends Thing {

	OpType type;
	OpType inverse;
	Thing v1;
	Thing v2;
	
	Thing parent;
	
	public Operation(OpType type, Thing v1, Thing v2){
		this.type = type;
		this.v1 = v1;
		this.v2 = v2;
		
		v1.setParent(this);
		v2.setParent(this);
		setChildren(v1, v2);
		
		String inverseString = type.getInverse();
		switch (inverseString){
		case "ADD":
			inverse = OpType.ADD;
			break;
		case "SUB":
			inverse = OpType.SUB;
			break;
		case "MULT":
			inverse = OpType.MULT;
			break;
		case "DIV":
			inverse = OpType.DIV;
			break;
		case "POW":
			inverse = OpType.POW;
			break;
		case "ROOT":
			inverse = OpType.ROOT;
			break;
		case "LOG":
			inverse = OpType.LOG;
		}
	}
	
	public void setParent(Thing parent){
		this.parent = parent;
	}
	
	public void setChildren(Thing c1, Thing c2){
		v1 = c1;
		v2 = c2;
	}
	
	public boolean containsVar(){
		if (v1.containsVar() || v2.containsVar()){
			return true;
		}
		return false;
	}
	
	public boolean containsSolve(){
		if (v1.containsSolve() || v2.containsSolve()){
			return true;
		}
		return false;
	}
	
	public ArrayList<Value> getSolve(){
		ArrayList<Value> list = new ArrayList<Value>();
		if (v1.getSolve() != null){
			list.addAll(v1.getSolve());
		}
		if (v2.getSolve() != null){
			list.addAll(v2.getSolve());
		}
		return list;
	}
	
	public float calculate(){
		switch (type){
		case ADD:
			return v1.calculate() + v2.calculate();
		case SUB:
			return v1.calculate() - v2.calculate();
		case MULT:
			return v1.calculate() * v2.calculate();
		case DIV:
			return v1.calculate() / v2.calculate();
		case POW:
			return (float) Math.pow(v1.calculate(), v2.calculate());
		case ROOT:
			return (float) Math.pow(v1.calculate(), 1 / v2.calculate());
		case LOG:
			return (float) (Math.log(v1.calculate()) / Math.log(v2.calculate()));
		default:
			return v1.calculate() + v2.calculate();	
		}
	}
	
}
