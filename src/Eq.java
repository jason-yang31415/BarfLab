import java.util.ArrayList;


public class Eq {

	Thing left;
	Thing right;
	ArrayList<Value> vars = new ArrayList<Value>();
	String mml;
	
	boolean syntax = true;
	
	public Eq (Thing left, Thing right){
		this.left = left;
		this.right = right;
		
		getSolve();
	}
	
	public void getSolve(){
		if (left != null && right != null){
			if (left.getSolve() != null)
				vars.addAll(left.getSolve());
			if (right.getSolve() != null)
				vars.addAll(right.getSolve());
			
			mml = MMLConverter.convert(left, right);
			System.out.println(mml);
		}
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
	
	public void setSyntax(boolean syntax){
		this.syntax = syntax;
	}
	
}
