import java.util.ArrayList;


public class Value extends Thing {

	float value;
	String name;
	boolean var;
	boolean solve;
	
	Thing parent;
	
	public Value(float value, String name, boolean var, boolean solve){
		this.value = value;
		this.name = name;
		this.var = var;
		if (var)
			this.solve = solve;
		else
			this.solve = false;
		
	}
	
	public void setParent(Thing parent){
		this.parent = parent;
	}
	
	public void setValue(float value){
		this.value = value;
	}
	
	public void setSolve(boolean solve){
		this.solve = solve;
	}
	
	public boolean containsVar(){
		return var;
	}
	
	public boolean containsSolve(){
		return solve;
	}
	
	public ArrayList<Value> getSolve(){
		if (solve){
			ArrayList<Value> list = new ArrayList<Value>();
			list.add(this);
			return list;
		}
		else
			return null;
	}
	
	public float calculate(){
		return value;
	}
	
	public String getName(){
		return name;
	}
	
}
