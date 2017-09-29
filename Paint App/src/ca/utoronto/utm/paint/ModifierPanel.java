package ca.utoronto.utm.paint;


import javax.swing.*;

import ca.utoronto.utm.paint.DrawingCommands.BackgroundChange;
import ca.utoronto.utm.paint.DrawingCommands.ColorChange;
import ca.utoronto.utm.paint.DrawingCommands.StrokeChange;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * The Modifier Panel is used by the user to select the colour of the shapes, outline thickness, and fill style.
 * A user can choose from over 1000 colours, between a filled or outlined shape, and between 10 varaitions of thickness.
 * @author csc207 group
 *
 */

public class ModifierPanel extends JPanel implements ActionListener {

	private View view; //so 
	private PaintModel model;
	JRadioButton filledStyle, outlineStyle;
	ButtonGroup options;
	public boolean filled;
	private Color color;
	private JComboBox box;
	private JLabel label;
	
	public ModifierPanel(View view,PaintModel model){
		this.view = view;
		this.model = model;

		this.setLayout(new GridLayout(1,7));
		
		JButton b1 = new JButton("Choose a color");
		b1.addActionListener(this);
		
		JButton b2 = new JButton("Choose a background");
		b2.addActionListener(this);
		
		options = new ButtonGroup(); //used to group FilledStyle & OutlineStyle Buttons together
		
		filledStyle = new JRadioButton("Filled shape"); 
		filledStyle.addActionListener(this);
		options.add(filledStyle);
		
		outlineStyle = new JRadioButton("Outlined shape"); 
		outlineStyle.addActionListener(this);
		options.add(outlineStyle); //only one of FilledStyle or OutlineStyle can be selected at a time
		
		outlineStyle.setSelected(true);
		
		this.add(b1);
		this.add(b2);
		this.add(filledStyle);
		this.add(outlineStyle);
		
		// Used to create a dropdown menu of 10 different line thickness settings for creating new shapes
		
		box = new JComboBox();
		box.setActionCommand("thickness");
	    box.setOpaque(true);

	    label = new JLabel("Line Thickness:");
	    
	    for(int i = 1; i < 11; i++) {
	      box.addItem(i);
	    }
	    
	    box.addActionListener(this);
	    
	    this.add(label);
	    this.add(box); 
	}

	@Override
	/**
	 * Prompts user with choose a color dialog, if user clicks choose color button
	 * Changes either if shapes drawn are filled or not filled depending on radiobuttons.
	 * Changes thickness of shapes when drawn when user changes the thickness in the dropdown menu.
	 */
	public void actionPerformed(ActionEvent ae){ 
		
		if (ae.getActionCommand()=="Choose a color"){
			color = JColorChooser.showDialog(null, "Pick a Color", color); //the desired color is the one selected from the pop-up panel
			ColorChange newColor = new ColorChange(color);
			model.addCommand(newColor); //sets the colour of subsequent shapes to be drawn to the colour chosen
		}
		else if (ae.getActionCommand()=="Choose a background"){
			color = JColorChooser.showDialog(null, "Pick a Color", color); //the desired color is the one selected from the pop-up panel
			BackgroundChange newBackground = new BackgroundChange(color, view.getPaintPanel());
			model.addCommand(newBackground); //sets the background of the paint panel to the colour chosen
		}
		else if(ae.getActionCommand()=="thickness"){
			int thickness = ((int)box.getSelectedItem()); // int value is set to the label of the item just clicked
			StrokeChange newStroke = new StrokeChange(thickness);
			model.addCommand(newStroke); // the latest drawing thickness in PaintPanel is updated
		}
		if (filledStyle.isSelected()){
			this.view.getPaintPanel().setFilled(true); //sets subsequent shapes to be drawn with a colour fill
		}else{
			this.view.getPaintPanel().setFilled(false); //sets subsequent shapes to be drawn with no fill - online an outline
		}
	
}

}
