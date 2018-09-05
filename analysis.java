 

package joeyPackage;
import java.awt.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Main extends lexer
{
	private JFrame frame;
	private JButton open, lexer, syntax, semantic, clear;
	private String[] codeStr;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Main window = new Main();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	public Main()
	{
		initialize();
	}
	private void initialize()
	{
		frame = new JFrame("JAVA COMPILER");
		frame.setSize(615, 500);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(158, 68, 400, 350);
frame.add(scrollPane);
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Arial",Font.BOLD,12));
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		JLabel label= new JLabel("JAVA COMPILER SIMULATOR");
		label.setForeground(Color.PINK);
		label.setFont(new Font("Lucida Handwriting",Font.BOLD,26));
		label.setBounds(105, 0, 500, 60);
		frame.add(label);

		open = new JButton("OPEN");
		clear = new JButton("CLEAR");
		lexer = new JButton("LEXER");
		syntax = new JButton("SYNTAX");
		semantic = new JButton("SEMANTIC");
		
		clear.setEnabled(false);
		lexer.setEnabled(false);
		syntax.setEnabled(false);
		semantic.setEnabled(false);
		
		open.setBackground(Color.CYAN);
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setAcceptAllFileFilterUsed(false);
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		        fileChooser.addChoosableFileFilter(filter);
		        if(fileChooser.showDialog(fileChooser, "open")==JFileChooser.APPROVE_OPTION)
		        {
		        	File sourceCode = new File("C:/Users/Daniel Penjan/Desktop/joey.txt");
		        	Scanner in = null;
		        	try {
		        		in = new Scanner(sourceCode);
		        	} catch (FileNotFoundException e) {
		        		e.printStackTrace();
		        	}
		        	int line = 0;
		        	while(in.hasNextLine())
		        	{
		        		line++;
	in.nextLine();
		        	}
		        	try {
		        		in = new Scanner(sourceCode);
		        	} catch (FileNotFoundException e) {
		        		e.printStackTrace();
		        	}
		        	codeStr = new String[line];
		        	for(int ctr=0; ctr<line; ctr++)
		        		codeStr[ctr] = in.nextLine();
				
		        	for(int ctr=0; ctr<codeStr.length; ctr++)
		        		textArea.append(codeStr[ctr] + "\n");
				
		        	open.setEnabled(false);
		        	clear.setEnabled(true);
		        	lexer.setEnabled(true);
		        	syntax.setEnabled(true);
		        }
		        else
		        {
		        	JOptionPane.showMessageDialog(null, "No File Chosen!");
		        }
			}
		});
		open.setBounds(29, 69, 95, 23);
		frame.add(open);
		clear.setBackground(Color.CYAN);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				textArea.setText("");
				open.setEnabled(true);
				clear.setEnabled(false);
				lexer.setEnabled(false);
				syntax.setEnabled(false);
				semantic.setEnabled(false);
			}
		});
		clear.setBounds(29, 283, 95, 23);
		frame.add(clear);
		lexer.setBackground(Color.CYAN);
		lexer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				for(int ctr=0; ctr<codeStr.length; ctr++)
textArea.append(Lex(codeStr[ctr]) + "\n");
				
				lexer.setEnabled(false);
			}
		});
		lexer.setBounds(29, 121, 95, 23);
		frame.add(lexer);
		syntax.setBackground(Color.CYAN);
		syntax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				analyzeSyntax(Lex(codeStr[0]));
				textArea.append(syntaxResult + "\n");
				syntax.setEnabled(false);
				semantic.setEnabled(true);
			}
		});
		syntax.setBounds(29, 178, 95, 23);
		frame.add(syntax);
		semantic.setBackground(Color.CYAN);
		semantic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				textArea.append(semanticResult + "\n");
				semantic.setEnabled(false);
			}
		});
		semantic.setBounds(29, 230, 95, 23);
		frame.add(semantic);
	}
}

package joeyPackage;
import java.util.*;

public class lexer
	{
	static String result;
	static String syntaxResult;
	static String semanticResult = "";
	static ArrayList<String> operatorList = new ArrayList<String>();
	static ArrayList<String> tokenList = new ArrayList<String>();
	static String [] x = new String[2];
	public static enum Type
	{
		identifier, operator, separator, numeric_literal,



