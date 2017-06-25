import java.awt.*;

import BreezyGUI.*;

public class BreezyExample extends GBFrame 
{
	private Label first;
	private Button butt;
	private TextField firstField; 
	private TextArea outputArea;
	
   	public BreezyExample()
   	{
   		first = addLabel("Label name",1,1,1,1);
   		butt = addButton("button",3,1,1,1);
   		firstField = addTextField("a/b",1,2,1,1);
   		outputArea = addTextArea("",4,1,5,5);
   	}

   	public void buttonClicked (Button buttonObj)
   	{
   		if(buttonObj == butt) {
   			
   		}
   	}
   	
   	public static void main(String[] args)
	{
		Frame frm = new BreezyExample();    
		frm.setSize (400, 400);  
		frm.setVisible (true);           
	}
      	
 }