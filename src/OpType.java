
public enum OpType {

	ADD("SUB"),
	SUB("ADD"),
	MULT("DIV"),
	DIV("MULT"),
	POW("ROOT"),
	ROOT("POW"),
	LOG("POW");
	
	final String inverse;
	
	OpType(String inverse){
		this.inverse = inverse;
	}
	
	public String getInverse(){
		return inverse;
	}
	
}
