import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTMLDocument;

public class Panel {

	String eqString;
	Eq eq;
	EqS eqs;
	JPanel panel;
	JTextPane textpane;
	float answer;
	
	int id;
	JSplitPane parent;
	
	
	public Panel (int id){
		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		
		this.id = id;
		
		textpane = new JTextPane();
		textpane.setFont(Window.fontNormal);
		
		textpane.getDocument().addDocumentListener(new DocListener());
		textpane.addMouseListener(new MouseHandler());
		//equationTP.setPreferredSize(new Dimension(500, 500));
		textpane.setComponentPopupMenu(Window.rmenu);
		textpane.setBorder(BorderFactory.createEmptyBorder());
		
		JScrollPane scrollPane = new JScrollPane(textpane);
		TextLineNumber tln = new TextLineNumber(textpane);
		tln.setBackground(Color.decode("#F5F5F5"));
		scrollPane.setRowHeaderView(tln);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		panel.add(scrollPane);
	}
	
	public void update(){
		
		eqString = textpane.getText();
		eq = new EqFile().importEqFromString(eqString);
		if (eq != null && eq.syntax){
			solve();
			Window.update();
		}
		
		setActivePanel();
	}
	
	public void solve(){
		eqs = new EqS(eq);
		System.out.println(eqs.getAnswer());
		answer = eqs.getAnswer();
		Window.setAnswer(answer);
	}
	
	public void setActivePanel(){
		Window.setActivePanel(this);
	}
	
	public JPanel getPanel(){
		return panel;
	}
	
	public Eq getEQ(){
		return eq;
	}
	
	public float getAnswer(){
		return answer;
	}
	
	public void setSolve(Value v){
		for (Value value : eq.vars){
			if (value.equals(v)){
				value.setSolve(true);
			}
			else
				value.setSolve(false);
		}
		solve();
	}
	
	public class MouseHandler implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			setActivePanel();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private class DocListener implements DocumentListener {
		 
	    public void insertUpdate(DocumentEvent e) {
	        update();
	    }
	    public void removeUpdate(DocumentEvent e) {
	        update();
	    }
	    public void changedUpdate(DocumentEvent e) {
	        update();
	    }
	}
	
}
