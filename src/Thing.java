import java.util.ArrayList;


public abstract class Thing {

	public abstract float calculate();
	
	public abstract void setParent(Thing parent);
	
	public abstract boolean containsVar();
	
	public abstract boolean containsSolve();
	
	public abstract ArrayList<Value> getSolve();
	
}
