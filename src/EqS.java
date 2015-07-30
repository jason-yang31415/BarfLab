
public class EqS {

	Thing left;
	Thing right;
	float answer = 0;
	
	/*public static void main (String[] args){
		new EqS();
	}*/
	
	public EqS (Eq eq){
		left = eq.getLeft();
		right = eq.getRight();
	}
	
	/*public EqS(){
		//setup
		// log (9, x) = 2
		Thing l9 = new Value(9f, false);
		Thing lx = new Value(0f, true);
		Thing lOp1 = new Operation(OpType.LOG, l9, lx);
		lOp1.setParent(null);
		left = lOp1;
		
		Thing r2 = new Value(2f, false);
		r2.setParent(null);
		right = r2;
		
		solve();
	}*/
	
	public EqS (Thing left, Thing right){
		this.left = left;
		this.right = right;
		
		solve();
	}
	
	public void solve(){
		Thing eval;
		Thing var;
		if (left.containsSolve()){
			eval = right;
			var = left;
		}
		else {
			eval = left;
			var = right;
		}
		
		if (isIsolated(var)){
			System.out.println(eval.calculate());
			answer = eval.calculate();
		}
		else {
			Operation varOp = (Operation) var;
			
			boolean position = true;
			if (varOp.v1.containsSolve())
				position = true;
			else 
				position = false;
			
			if (position || (varOp.type == OpType.ADD || varOp.type == OpType.MULT)){
				Thing newEval;
				if (varOp.type == OpType.LOG)
					newEval = new Operation(varOp.inverse, varOp.v2, eval);
				else
					newEval = new Operation(varOp.inverse, eval, varOp.v2);
				newEval.setParent(null);
				eval = newEval;
				var = varOp.v1;
			}
			else {
				Thing newEval;
				if (varOp.type == OpType.SUB || varOp.type == OpType.DIV)
					newEval = new Operation(varOp.type, varOp.v1, eval);
				else if (varOp.type == OpType.POW)
					newEval = new Operation(OpType.LOG, eval, varOp.v1);
				else if (varOp.type == OpType.LOG)
					newEval = new Operation(OpType.ROOT, varOp.v1, eval);
				else
					newEval = new Operation(varOp.inverse, varOp.v1, eval);
				newEval.setParent(null);
				eval = newEval;
				var = varOp.v2;
			}
			
			if (left.containsSolve()){
				right = eval;
				left = var;
			}
			else {
				left = eval;
				right = var;
			}
			
			solve();
		}
	}
	
	public boolean isIsolated(Thing var){
		if (var instanceof Value)
			return true;
		else
			return false;
	}
	
	public float getAnswer(){
		return answer;
	}
	
}
