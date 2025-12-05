package Y2U.DataStructure;

import java.util.ArrayList;

//implement Yakindu EVENT semantics (such as multiple raise, multiple acceptance, and simultaneous events) with EventStack class
/*
public class Event extends Automata {
	
	//event.eventName = EA
	//event.name = EAEvent // which is the event automata name
	private String eventName; //chan eventName
	
	public Event(String name, int eventIndex) {
		super(name + "Event");
		
		eventName = name;
		
		name = name + "Event";
		
		State state = new State("init_" + this.name, "");	
		state.setPosition(0, 0);
		addState(state);
		
		Transition  transition = new Transition("tran_" + this.name, "init_" + this.name, "init_" + this.name);
		transition.setUpdate("push(" + eventIndex + ")");
		//transition.setSynchronisation(eventName + "!");
		transition.setExpressionPosition(100, 0);
		transition.addPosition(100, -50);
		transition.addPosition(100, 50);
		addTransition(transition);
		
		initialStateID = "init_" + this.name;
	}
	
	

	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	
}
*/
