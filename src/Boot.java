import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Boot {
	
	public static void main(String[] args){
		Eq equation = null;
		try {
			equation = new EqFile().importEq("eq/test_complicated.eq");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Something screwed up :(\nPlease report this bug.");
			e.printStackTrace();
		}
		
		if (equation != null){
			EqS eqs = new EqS(equation);
			System.out.println(eqs.getAnswer());
		}
	}
	
}
