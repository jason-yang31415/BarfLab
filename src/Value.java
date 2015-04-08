
public class Value extends Thing {

	float value;
	boolean var;
	
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
	
	public boolean containsVar(){
		return var;
	}
	
	public float calculate(){
		return value;
	}
	
}
