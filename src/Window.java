import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Window extends JFrame {
	
	private JButton button;
	
	public Window(){
		super("Title Bar");
		setLayout(new FlowLayout());
		
		button = new JButton("Calculate");
		add(button);
		
		Handler handler = new Handler();
		button.addActionListener(handler);
	}
	
	public void doStuff(String path){
		Eq equation = null;
		try {
			path = path.replace('\\', '/');
            System.out.println("opening " + path);
			equation = new EqFile().importEq(path);
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
	
	private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == button){
				/*final JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("EQ Equations", "eq");
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(Window.this);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            //This is where a real application would open the file.
		            doStuff(file.getPath());
		        } else {
		            System.out.println("screwed up");
		        }*/
				FileDialog fc = new java.awt.FileDialog((java.awt.Frame) null);
				fc.setVisible(true);
				String path = fc.getDirectory() + fc.getFile();
				doStuff(path);
			}
		}
		
	}
	
	private class ItemHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			
		}
		
	}
	
}
