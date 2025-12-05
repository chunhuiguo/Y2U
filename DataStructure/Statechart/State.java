package Y2U.DataStructure.Statechart;

import java.util.ArrayList;
import java.util.List;

public class State {
	
	private String id;
	private String name;	
	private String action;
	private List<String> incomingTransitionList;
	private List<String> outgoingTransitionList;
	private boolean initial;
	
	public State() {
		id = "";
		name = "";	
		action = "";
		incomingTransitionList = new ArrayList<String> ();
		outgoingTransitionList = new ArrayList<String> ();
		initial = false;
	}
	
	
	/*
	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	
	public void adjustPosition(double factor) {
		position.adjust(factor);
	}
	*/

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<String> getIncomingTransitionList() {
		return incomingTransitionList;
	}
	public void setIncomingTransitionList(List<String> incomingTransitionList) {
		this.incomingTransitionList = incomingTransitionList;
	}
	public List<String> getOutgoingTransitionList() {
		return outgoingTransitionList;
	}
	public void setOutgoingTransitionList(List<String> outgoingTransitionList) {
		this.outgoingTransitionList = outgoingTransitionList;
	}
	public boolean isInitial() {
		return initial;
	}
	public void setInitial(boolean initial) {
		this.initial = initial;
	}		
}
