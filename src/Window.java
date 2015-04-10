import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

public class Window extends JFrame {
	
	String path;
	
	private JMenuBar menu;
	
	private JMenu fileMenu;
	private JMenuItem fileMenuExit;
	private JMenuItem fileMenuImport;
	
	private JMenu viewMenu;
	private JCheckBoxMenuItem viewMenuImport;
	
	private JMenu helpMenu;
	private JMenuItem helpMenuAbout;
	
	JToolBar importBar;
	private JButton browseButton;
	private JTextField addressTF;
	private JButton importButton;
	
	private JPanel varPanel;
	
	private JTextField answerText;
	
	private JPanel equationPanel;
	private JTextPane equationTP;
	//private SOMETHING equationSOMETHING;
	
	private JPanel errorPanel;
	
	public Window(){
		super("BarfLab");
		setLayout(new BorderLayout());
		
		Handler handler = new Handler();
		ItemHandler itemHandler = new ItemHandler();
		
		//                                                  MENU
		//====================================================================================================
		
		menu = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menu.add(fileMenu);
        
        fileMenuImport = new JMenuItem("Import");
        fileMenuImport.setMnemonic(KeyEvent.VK_I);
        fileMenuImport.setToolTipText("Import");
        fileMenu.add(fileMenuImport);
        
        fileMenu.addSeparator();
        
        fileMenuExit = new JMenuItem("Exit");
        fileMenuExit.setMnemonic(KeyEvent.VK_E);
        fileMenuExit.setToolTipText("Exit");
        fileMenu.add(fileMenuExit);
        
        fileMenuExit.addActionListener(handler);
        fileMenuImport.addActionListener(handler);
        
        viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        menu.add(viewMenu);
        
        viewMenuImport = new JCheckBoxMenuItem("Show Import Bar");
        viewMenuImport.setMnemonic(KeyEvent.VK_I);
        viewMenuImport.setDisplayedMnemonicIndex(5);
        viewMenuImport.setSelected(true);
        viewMenu.add(viewMenuImport);
        
        viewMenuImport.addItemListener(itemHandler);
        
        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menu.add(helpMenu);
        
        helpMenuAbout = new JMenuItem("About");
        helpMenuAbout.setMnemonic(KeyEvent.VK_A);
        helpMenu.add(helpMenuAbout);
        
        helpMenuAbout.addActionListener(handler);
        
        setJMenuBar(menu);

		//                                                  IMPORTS
		//====================================================================================================
        importBar = new JToolBar();
        importBar.setFloatable(false);
        add(importBar, BorderLayout.NORTH);
		
		////NOTE: TEMP
		//importBar.setBorder(BorderFactory.createEtchedBorder());
		//importPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		browseButton = new JButton("Browse");
		importBar.add(browseButton);
		
		addressTF = new JTextField(100);
		importBar.add(addressTF);
		
		importButton = new JButton("Import");
		importBar.add(importButton);
		
		browseButton.addActionListener(handler);
		importButton.addActionListener(handler);
		
		//                                                  VARIABLES
		//====================================================================================================
		varPanel = new JPanel();
		varPanel.setLayout(new BorderLayout());
		
		//                                                  ANSWER
		//====================================================================================================
		answerText = new JTextField();
		answerText.setEditable(false);
		varPanel.add(answerText, BorderLayout.SOUTH);
		
		//                                                  EQUATION
		//====================================================================================================
		equationPanel = new JPanel();
		equationPanel.setLayout(new GridLayout(1, 1));
		//add(equationPanel, BorderLayout.CENTER);
		
		equationTP = new JTextPane();
		equationTP.getDocument().addDocumentListener(new DocListener());
		//equationTP.setPreferredSize(new Dimension(500, 500));
		equationPanel.add(equationTP);
		
		//                                                  ERRORS
		//====================================================================================================
		errorPanel = new JPanel();
		errorPanel.setLayout(new GridLayout(1, 1));
		//add(errorPanel, BorderLayout.SOUTH);
		
		JTextPane text = new JTextPane();
		text.setPreferredSize(new Dimension(500, 500));
		errorPanel.add(text);
		
		Dimension minimumSize = new Dimension(100, 50);
		equationPanel.setMinimumSize(minimumSize);
		errorPanel.setMinimumSize(minimumSize);
		
		JSplitPane equation_error = new JSplitPane(JSplitPane.VERTICAL_SPLIT, equationPanel, errorPanel);
		equation_error.setDividerLocation(500 + equation_error.getInsets().top);
		equation_error.setOneTouchExpandable(true);
		
		JSplitPane var_eqer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, varPanel, equation_error);
		var_eqer.setDividerLocation(200 + var_eqer.getInsets().left);
		equation_error.setOneTouchExpandable(true);
		
		add(var_eqer);
	}
	
	public void importFile(String path){
		/*try {
			path = path.replace('\\', '/');
            System.out.println("opening " + path);
			equation = new EqFile().importEq(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Something screwed up :(\nPlease report this bug.");
			e.printStackTrace();
		}*/
		try {
			InputStream i = new FileInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(i));
			String line;
			equationTP.setText("");
			while ((line = reader.readLine()) != null){
				equationTP.setText(equationTP.getText() + line + "\n");
			}
			reader.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(){
		Eq equation = new EqFile().importEqFromString(equationTP.getText());
		if (equation != null){
			EqS eqs = new EqS(equation);
			System.out.println(eqs.getAnswer());
			answerText.setText(String.format("%f", eqs.getAnswer()));
		}
	}
	
	public void browse(){
		FileDialog fc = new java.awt.FileDialog((java.awt.Frame) null);
		fc.setVisible(true);
		String browsePath = fc.getDirectory() + fc.getFile();
		if (fc.getFile() != null){
			addressTF.setText(browsePath);
		}
	}
	
	public void about(){
		JOptionPane.showMessageDialog(null, "PROJECT BARFLAB\nBy Jason Yang and Roop Pal (not really)",
				"About", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private class Handler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == fileMenuExit){
				System.exit(0);
			}
			if (event.getSource() == fileMenuImport){
				browse();
			}
			if (event.getSource() == helpMenuAbout){
				about();
			}
			if (event.getSource() == browseButton){
				browse();
			}
			if (event.getSource() == importButton){
				path = addressTF.getText();
				importFile(path);
			}
		}
		
	}
	
	private class ItemHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getSource() == viewMenuImport){
				if (viewMenuImport.isSelected()) {
					importBar.setVisible(true);
	            } else {
	            	importBar.setVisible(false);
	            }
			}
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
