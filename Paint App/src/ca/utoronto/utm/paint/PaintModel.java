package ca.utoronto.utm.paint;

import java.util.ArrayList;
import java.util.Observable;

import ca.utoronto.utm.paint.DrawingCommands.DrawingCommand;

/**
 * Keeps hold of all the shapes created by the user
 *
 */
public class PaintModel extends Observable {
	private ArrayList<DrawingCommand> commands = new ArrayList<DrawingCommand>();
	private ArrayList<DrawingCommand> redoCommands = new ArrayList<DrawingCommand>();
	public void addCommand(DrawingCommand s) {
		this.commands.add(s);
		this.setChanged(); // when new shape is added a repaint happens
		this.notifyObservers();
		redoCommands = new ArrayList<DrawingCommand>(); // list of potential redos created 
	}
	
	public ArrayList<DrawingCommand> getCommands() {
		return commands;
	}
	public void undo(){
		redoCommands.add(commands.get(commands.size()-1));
		commands.remove(commands.size()-1);
		this.setChanged(); // when new shape is added a repaint happens
		this.notifyObservers();
	}
	public void redo(){
		if (redoCommands.size()!=0){
			commands.add(redoCommands.get(redoCommands.size()-1));
			redoCommands.remove(redoCommands.size()-1);
			this.setChanged(); // when new shape is added a repaint happens
			this.notifyObservers();
		}
	}
	public void clear(){
		commands = new ArrayList<DrawingCommand>();
		this.setChanged(); // when new shape is added a repaint happens
		this.notifyObservers();
	}
}
