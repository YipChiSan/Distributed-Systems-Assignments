
import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

class InputRestriction extends DocumentFilter {
	private int maxLength;
	
	private JTextArea textArea;
	
	protected void setMaxLength(int i){
		this.maxLength = i;
	}
	
	protected void setTextArea(JTextArea textArea){
		this.textArea = textArea;
	}
	
	private boolean isEnglishWord(String text){
		for (int n = text.length(); n > 0; n--) {//an inserted string may be more than a single character i.e a copy and paste of 'aaa123d', also we iterate from the back as super.XX implementation will put last insterted string first and so on thus 'aa123d' would be 'daa', but because we iterate from the back its 'aad' like we want
            char c = text.charAt(n - 1);//get a single character of the string
            if (!Character.isAlphabetic(c) && !Character.isWhitespace(c)){
            	return false;
            }
        }
		return true ;
	}
	
	private boolean lessThanMaxLength(String text){
		return text.length() <= this.maxLength;
	}
	
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) {
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);
		Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();

        try {
			sb.append(doc.getText(0, doc.getLength()));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			System.out.println("Fail to read input");
		}
        sb.insert(offset, string);
        if (isEnglishWord(sb.toString()) && lessThanMaxLength(sb.toString())){
        	try {
				super.insertString(fb, offset, string, attr);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				System.out.println("Fail to read input");
			}
        }else if(!isEnglishWord(sb.toString())){
        	System.out.println("The input must be plain English text. It can only contain English character and white space.");
        }
        	else{
        		System.out.println("The input word should be not more than 15 characters and the input meanings should be not more than 80 characters.");
        }
        	
        }
	
	
	 public void replace(FilterBypass fb, int offset, int length, String text,
             AttributeSet attrs) {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder(2);
        try {
		 sb.append(doc.getText(0, doc.getLength()));
	}   catch (BadLocationException e) {
		 System.out.println("Fail to read input");
	}
        sb.replace(offset, offset + length, text);
       
        if (isEnglishWord(sb.toString()) 
        		&& lessThanMaxLength(sb.toString())) {
       
             try {
			   super.replace(fb, offset, length, text, attrs);
			} catch (BadLocationException e) {
					// TODO Auto-generated catch block
				System.out.println("Fail to read input");
			}
              } else if(!isEnglishWord(sb.toString())){
               	System.out.println("The input must be plain English text. It can only contain English character and white space.");
              } else{
            	  System.out.println("The input word should be not more than 20 characters and the input meanings should be not more than 80 characters.");
              }
       } 
	 
	 public void remove(FilterBypass fb, int offset, int length, String text,
             AttributeSet attrs) {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder(2);
        try {
		 sb.append(doc.getText(0, doc.getLength()));
	}   catch (BadLocationException e) {
		 System.out.println("Fail to read input");
	}
        sb.delete(offset, offset + length);
       
        if (isEnglishWord(sb.toString()) 
        		&& lessThanMaxLength(sb.toString())) {
             try {
			   super.replace(fb, offset, length, text, attrs);
			} catch (BadLocationException e) {
					// TODO Auto-generated catch block
				System.out.println("Fail to read input");
			}
              } else if(!isEnglishWord(sb.toString())){
               	System.out.println("The input must be plain English text. It can only contain English character and white space.");
              } else{
              	  System.out.println("The input word should be not more than 20 characters and the input meanings should be not more than 80 characters.");
                }
           
       }  
   
}
