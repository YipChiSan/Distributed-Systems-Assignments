
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;

public class OperationFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1960761537120707078L;
	private JTextField word;
	private JTextArea meaning;
	private JTextArea output;
	
	
	private PrintStream printStream ;
	private JButton buttonSubmit = new JButton("Submit");
	private JButton buttonClear = new JButton("Clear");
	
	
	
	public OperationFrame(String hostname, Integer port){
		super("Dictionary");
		
		setLayout(new GridBagLayout());
		GridBagConstraints firstLineConstraint = new GridBagConstraints();
		JLabel typeLabel = new JLabel("select what to do:");
		firstLineConstraint.gridx = 0;
		firstLineConstraint.gridy = 0;
		firstLineConstraint.insets = new Insets(10,10,0,10);
        firstLineConstraint.anchor = GridBagConstraints.CENTER;
        add(typeLabel,firstLineConstraint);
        
        firstLineConstraint.gridx = 1;
		String[] dictOperation = {"","Add","Delete","Show"};
		JComboBox<String> operationList = new JComboBox<String>(dictOperation);
	    add(operationList,firstLineConstraint);
		setSize(400,100);
		setVisible(true);
		
		
		
				
		word = new JTextField(20);
				
		meaning = new JTextArea(50,3);
		output = new JTextArea(50,10);
		output.setEditable(false);
		meaning.setLineWrap(true);
		meaning.setWrapStyleWord(true);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
				
		printStream = new PrintStream(new CustomOutputStream(output));
		PrintStream standardOut = System.out;
		System.setOut(printStream);
		System.setErr(printStream);
				
		InputRestriction wordFilter = new InputRestriction();
		wordFilter.setMaxLength(15);
		wordFilter.setTextArea(output);
		((AbstractDocument) word.getDocument()).setDocumentFilter(wordFilter);
				
		InputRestriction meaningFilter = new InputRestriction();
		meaningFilter.setMaxLength(80);
		meaningFilter.setTextArea(output);
		((AbstractDocument) meaning.getDocument()).setDocumentFilter(meaningFilter);
				
		JLabel outputLabel = new JLabel("Output:");
		GridBagConstraints labelConstraint = new GridBagConstraints();
		labelConstraint.gridx = 0;
		labelConstraint.gridy = 1;
		labelConstraint.insets = new Insets(10,10,0,10);
		labelConstraint.anchor = GridBagConstraints.WEST;
		add(outputLabel,labelConstraint);
		        
		        GridBagConstraints outputConstraint = new GridBagConstraints();
		        outputConstraint.gridx = 0;
		        outputConstraint.gridy = 2;
		        outputConstraint.gridwidth = GridBagConstraints.REMAINDER;
		        outputConstraint.gridheight = 3;
		        outputConstraint.fill = GridBagConstraints.BOTH;
		        outputConstraint.insets = new Insets(0,10,10,10);
		        outputConstraint.weightx = 0;
		        outputConstraint.weighty = 1;
		        
		        add(new JScrollPane(output),outputConstraint);
		        
		        GridBagConstraints buttomConstraint = new GridBagConstraints();
		        buttomConstraint.gridy = 5;
		        buttomConstraint.gridx = 0;
		        buttomConstraint.weightx = 1;
		        buttomConstraint.insets = new Insets(10,10,10,10);
		        buttomConstraint.fill = GridBagConstraints.BOTH;
		        
		        add(buttonSubmit,buttomConstraint);
		        
		        buttomConstraint.gridx = 1;
		        
		        buttomConstraint.insets = new Insets(10,10,10,10);
		        add(buttonClear,buttomConstraint);
		        
		        GridBagConstraints wordMeaningConstraint = new GridBagConstraints();
		        
		        JLabel wordLabel = new JLabel("Word (maximum size: 15):");
		        GridBagConstraints wordMeaningLabelConstraint = new GridBagConstraints();
		        wordMeaningLabelConstraint.gridx = 0;
		        wordMeaningLabelConstraint.gridy = 6;
		        wordMeaningLabelConstraint.insets = new Insets(0,10,0,10);
		        wordMeaningLabelConstraint.anchor = GridBagConstraints.WEST;
		        add(wordLabel,wordMeaningLabelConstraint);
		        
		        wordMeaningConstraint.gridy = 7;
		        wordMeaningConstraint.gridx = 0;
		        wordMeaningConstraint.gridwidth = GridBagConstraints.REMAINDER;
		        wordMeaningConstraint.fill = GridBagConstraints.BOTH;
		        wordMeaningConstraint.weightx = 0;
		        
		        wordMeaningConstraint.insets = new Insets(0,10,10,10);
		        add(word,wordMeaningConstraint);
		        
		        JLabel meaningLabel = new JLabel("Meaning (maximum size: 80):");
		        wordMeaningLabelConstraint.gridy = 8;
		        
		        wordMeaningConstraint.gridy = 9;
		        wordMeaningConstraint.gridwidth = 2;
		        wordMeaningConstraint.weighty = 1;
		        wordMeaningConstraint.gridheight = 1;
		        
		        add(meaning,wordMeaningConstraint);
		        add(new JScrollPane(meaning),wordMeaningConstraint);
		        add(meaningLabel,wordMeaningLabelConstraint);
		        meaningLabel.setVisible(true);
		        meaning.setVisible(true);
		        setSize(400, 700);
		        
		        buttonSubmit.addActionListener(new ActionListener() {		
		            public void actionPerformed(ActionEvent evt) {
		                Socket socket;
						try {
						   	
						   String operation = (String) operationList.getSelectedItem();
		            	   String wordToServer = word.getText();
		            	   if (wordToServer.length() == 0){
		            		 System.out.println("Please enter a not empty word");
		            	} else {
		            	switch (operation){
		            	case "Add":
		            		
		            		String meaningToServer = meaning.getText();
		            		if (meaningToServer.length()!=0){
		            			socket = new Socket(hostname,port);
		            			sendToSocket(socket,"+",wordToServer,meaningToServer);
		            		} else{
		            			System.out.println("Please enter a not empty meaning");
		            		}
		        			break;	
		        			
		            	case "Delete":
		            		socket = new Socket(hostname,port);
		            		sendToSocket(socket, "-", wordToServer);
		            		break;
		            	
		            	case "Show":
		            		socket = new Socket(hostname,port);
		            		sendToSocket(socket,"=",wordToServer);
		            		break;
		            	default:
		            		standardOut.println("Select what you want to do");
		            		System.out.println("Select what you want to do");
		            		
		            	}
		            	}		
		            } catch (NumberFormatException e1) {
		            	System.out.println("Bad port format. Please close the window and enter a number.\n");
		    			
		    		} catch (UnknownHostException e11) {
		    			System.out.println("Cannot find the specified host. Please close the window and check host name...\n");
		    			
		    		} catch (IOException e111) {
		    			
		    			System.out.println(" >Cannot connect with server...");
		    			System.out.println(" >Please check if port number corresponds to port number in server...");
		    			System.out.println(" >Possibly server is not operating. Please try again later...");
		    			System.out.println(" >If you think your server name and port number is correct, you can wait until the server is active...");
		    			
		    		} catch(IllegalArgumentException e) {
		    			System.out.println("Please close the window and enter a number between 0 and 65535 for the port.\n");
		    			
		    		} 
		        	}});
		         
		        // adds event handler for button Clear
		        buttonClear.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		                // clears the text area
		                try {
		                    output.getDocument().remove(0,
		                            output.getDocument().getLength());
		                   
		                    standardOut.println("Text area cleared");
		                } catch (BadLocationException ex) {
		                    ex.printStackTrace();
		                }
		            }
		        });
		        setLocationRelativeTo(null);
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			};
		
		
	
	private void sendToSocket(Socket socket, String type, String word){
		String toServer = type + ":" + word ;
		Asker ask = new Asker();
		try {
			System.out.println(ask.request(socket, toServer));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendToSocket(Socket socket, String type, String word, String meaning){
		String toServer = type + ":" + word + ":" + meaning;
		Asker ask = new Asker();
		try {
			System.out.println(ask.request(socket, toServer));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
}
