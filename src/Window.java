import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import net.sourceforge.jeuclid.DOMBuilder;
import net.sourceforge.jeuclid.MathMLParserSupport;
import net.sourceforge.jeuclid.MutableLayoutContext;
import net.sourceforge.jeuclid.context.LayoutContextImpl;
import net.sourceforge.jeuclid.context.Parameter;
import net.sourceforge.jeuclid.converter.Converter;
import net.sourceforge.jeuclid.elements.generic.DocumentElement;
import net.sourceforge.jeuclid.layout.JEuclidView;
import net.sourceforge.jeuclid.parser.Parser;

public class Window extends JFrame {
	
	String path;
	
	static Handler handler;
	static ItemHandler itemHandler;
	
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
	
	private static JPanel varPanel;
	static ButtonGroup buttonGroup;
	
	private static JTextField answerText;
	
	private static Panel activePanel;
	private JTextPane equationTP;
	//private SOMETHING equationSOMETHING;
	
	public static JPopupMenu rmenu;
	
	private JPanel errorPanel;
	private static RenderPanel renderPanel;
	
	private int currentId = 0;
	
	//fonts
	static Font fontNormal;
	private static String fontString = "Century Gothic";
	
	public Window(){
		super("BarfLab");
		setLayout(new BorderLayout());
		
		handler = new Handler();
		itemHandler = new ItemHandler();
		
		fontNormal = new Font(fontString, Font.PLAIN, 14);
		
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
		varPanel.setBorder(BorderFactory.createEmptyBorder());
		varUpdate();
		
		//                                                  ANSWER
		//====================================================================================================
		answerText = new JTextField();
		answerText.setEditable(false);
		answerText.setBackground(Color.WHITE);
		answerText.setHorizontalAlignment(JTextField.CENTER);
		Font font = new Font(fontString, Font.BOLD,18);
		answerText.setFont(font);
		varPanel.add(answerText, BorderLayout.SOUTH);
		

		//                                                  RIGHT-CLICK MENU
		//====================================================================================================	
		rmenu = new JPopupMenu();
        
        /*JMenuItem hSplit = new JMenuItem("Split Horizontally");
        hSplit.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
            	splitHorizontal();
            }
            
        });

        rmenu.add(hSplit);

        JMenuItem vSplit = new JMenuItem("Split Vertically");
        vSplit.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                splitVertical();
            }
        });

        rmenu.add(vSplit);*/
		
		//                                                  EQUATION
		//====================================================================================================
		/*equationPanel = new JPanel();
		equationPanel.setLayout(new GridLayout(1, 1));
		//add(equationPanel, BorderLayout.CENTER);
		
		equationTP = new JTextPane();
		equationTP.getDocument().addDocumentListener(new DocListener());
		//equationTP.setPreferredSize(new Dimension(500, 500));
		equationPanel.add(equationTP);*/
		
		Panel panel = new Panel(currentId);
		
		//                                                  ERRORS
		//====================================================================================================
		errorPanel = new JPanel();
		errorPanel.setLayout(new GridLayout(1, 1));
		//add(errorPanel, BorderLayout.SOUTH);
		
		JTextPane text = new JTextPane();
		text.setPreferredSize(new Dimension(500, 500));
		errorPanel.add(text);
		
		Dimension minimumSize = new Dimension(100, 50);
		//equationPanel.setMinimumSize(minimumSize);
		errorPanel.setMinimumSize(minimumSize);
		
		renderPanel = new RenderPanel();
		renderPanel.setMinimumSize(new Dimension(250, 50));
		renderPanel.setBackground(Color.WHITE);
		
		JSplitPane error_render = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, errorPanel, renderPanel);
		error_render.setOneTouchExpandable(true);
		
		//JSplitPane equation_error = new JSplitPane(JSplitPane.VERTICAL_SPLIT, equationPanel, errorPanel);
		JSplitPane equation_error = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel.getPanel(), error_render);
		equation_error.setDividerLocation(500 + equation_error.getInsets().top);
		equation_error.setOneTouchExpandable(true);
		
		JSplitPane var_eqer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, varPanel, equation_error);
		var_eqer.setDividerLocation(200 + var_eqer.getInsets().left);
		equation_error.setOneTouchExpandable(true);
		equation_error.setBorder(BorderFactory.createEmptyBorder());
		
		add(var_eqer);
	}
	
	public static void update(){
		render();
		varUpdate();
	}
	
	public static void render(){
		try {
			Document doc = MathMLParserSupport.parseString(activePanel.getEQ().mml);
			MutableLayoutContext params = new LayoutContextImpl(LayoutContextImpl.getDefaultLayoutContext());
			params.setParameter(Parameter.MATHSIZE, 25f);
			BufferedImage bi = Converter.getInstance().render(doc, params);
			renderPanel.setImage(bi);
		} catch (SAXException | ParserConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void varUpdate() {
		Panel p = activePanel;
		varPanel.removeAll();
		buttonGroup = new ButtonGroup();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		if (p != null) {
			if (p.getEQ() != null){
				if (p.getEQ().getVars() != null){
					for (Value v : p.getEQ().getVars()){
						JPanel subp = new JPanel();
						JRadioButton button = new JRadioButton(v.getName(), false);
						button.setFont(fontNormal);
						button.addItemListener(itemHandler);
						JTextField textfield = new JTextField(10);
						textfield.getDocument().addDocumentListener(new DocListener());
						textfield.setFont(fontNormal);
						subp.add(button);
						subp.add(textfield);
						panel.add(subp);
						buttonGroup.add(button);
					}
				}
			}	
		}
		varPanel.add(panel);
		if (answerText != null){
			varPanel.add(answerText, BorderLayout.SOUTH);
		}
		varPanel.validate();
		varPanel.repaint();
	}
	
	public static void varButtons(){
		if (activePanel.getEQ().syntax){
			int counter = 0;
			int i = 0;
			System.out.println();
			for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
	            AbstractButton button = buttons.nextElement();
	            
	            if (button.isSelected())
	                i = counter;
	            else {
	            	try {
	            		float v = Float.parseFloat(((JTextField) button.getParent().getComponent(1)).getText());
	            		activePanel.getEQ().getVars().get(counter).setValue(v);
	            	} catch (NumberFormatException e){
	            		System.err.println("Var input not a number");
	            	}
	            }
	        	
	        	counter++;
	        }
			activePanel.setSolve(activePanel.getEQ().getVars().get(i));
		}
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
			activePanel.textpane.setText("");
			while ((line = reader.readLine()) != null){
				activePanel.textpane.setText(activePanel.textpane.getText() + line + "\n");
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
	
	public static void setActivePanel(Panel panel){
		activePanel = panel;
	}
	
	public static void setAnswer(float answer){
		answerText.setText(String.format("%f", answer));
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
		JOptionPane.showMessageDialog(null, "PROJECT BARFLAB\nBy Jason Yang and Roop Pal (not really)\n\n"
				+ "This product includes software developed by JEulid (http://www.sourceforge.net/projects/jeuclid/).",
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
			else if (event.getSource() instanceof JRadioButton){
				varButtons();
			}
		}
		
	}
	
	private static class DocListener implements DocumentListener {
		 
	    public void insertUpdate(DocumentEvent e) {
	    	varButtons();
	    }
	    public void removeUpdate(DocumentEvent e) {
	    	varButtons();
	    }
	    public void changedUpdate(DocumentEvent e) {
	    	varButtons();
	    }
	    
	}
	
}
