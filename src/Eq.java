import java.util.ArrayList;


public class Eq {

	Thing left;
	Thing right;
	ArrayList<Value> vars = new ArrayList<Value>();
	
	public Eq (Thing left, Thing right){
		this.left = left;
		this.right = right;
		
		getSolve();
	}
	
	public void getSolve(){
		if (left.getSolve() != null)
			vars.addAll(left.getSolve());
		if (right.getSolve() != null)
			vars.addAll(right.getSolve());
	}
	
	public Thing getLeft(){
		return left;
	}
	
	public Thing getRight(){
		return right;
	}
	
	public ArrayList<Value> getVars(){
		return vars;
	}
	
}
