
public class Value extends Thing {

	float value;
	boolean var;
	boolean solve;
	
	Thing parent;
	
	public Value(float value, boolean var){
		this.value = value;
		this.var = var;
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
	
	public float calculate(){
		return value;
	}
	
}
