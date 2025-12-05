package Y2U.DataStructure;

import java.util.ArrayList;
import java.util.List;

public class Automata {
	
	protected String name;	
	protected List<State> stateList;
	protected List<Transition> transitionList;
	protected String initialStateID;
	protected List<String> declarationList;
	private int priority;
	
	
	public Automata(String name) {
		this.name = name;
		stateList = new ArrayList<State> ();
		transitionList = new ArrayList<Transition> ();
		declarationList = new ArrayList<String> ();
		
		priority = 0;
	}
	
	
	
	public void addState(State state) {
		stateList.add(state);
	}
	
	public void addTransition(Transition transition) {
		transitionList.add(transition);
	}
	
	public Transition findTransition(String transitionID) {		
		
		for(int i = 0; i < transitionList.size(); i++) {
			if(transitionList.get(i).getId().equals(transitionID))
				return transitionList.get(i);
		}
		
		return null;
	}
	
	public State findState(String stateID) {	
		
		for(int i = 0; i < stateList.size(); i++) {
			if(stateList.get(i).getId().equals(stateID))
				return stateList.get(i);
		}
		
		return null;
	}
	
	public List<Transition> findIncomingTransition(String stateID)
	{		
		
		List<Transition> incomingTransitionList = new ArrayList<Transition>();
		Transition transition = null;
		
		for(int i = 0; i < transitionList.size(); i++)
		{
			transition = transitionList.get(i);
			
			if(transition.getTarget().equals(stateID))
				incomingTransitionList.add(transition);
		}
		
		return incomingTransitionList;
	}
	
	public List<Transition> findOutgoingTransition(String stateID)
	{		
		
		List<Transition> incomingTransitionList = new ArrayList<Transition>();
		Transition transition = null;
		
		for(int i = 0; i < transitionList.size(); i++)
		{
			transition = transitionList.get(i);
			
			if(transition.getSource().equals(stateID))
				incomingTransitionList.add(transition);
		}
		
		return incomingTransitionList;
	}
	
	//find transitions from state "stateID" and have higher priorities than "tranPriority"
	public List<Transition> findHigherPriorityTransition(String stateID, int tranPriority)
	{		
		
		List<Transition> higherPriorityTransitionList = new ArrayList<Transition>();
		Transition transition = null;
		
		for(int i = 0; i < transitionList.size(); i++)
		{
			transition = transitionList.get(i);
			
			if(transition.getSource().equals(stateID) && transition.getPriority() < tranPriority)
				higherPriorityTransitionList.add(transition);
		}
		
		return higherPriorityTransitionList;
	}
	
	public void addDeclaration(String declaration) {
		declarationList.add(declaration);
	}
	
	
	
		
	//
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<State> getStateList() {
		return stateList;
	}
	public void setStateList(List<State> stateList) {
		this.stateList = stateList;
	}
	public List<Transition> getTransitionList() {
		return transitionList;
	}
	public void setTransitionList(List<Transition> transitionList) {
		this.transitionList = transitionList;
	}
	public String getInitialStateID() {
		return initialStateID;
	}
	public void setInitialStateID(String initialStateID) {
		this.initialStateID = initialStateID;
	}
	public List<String> getDeclarationList() {
		return declarationList;
	}
	public void setDeclarationList(List<String> declarationList) {
		this.declarationList = declarationList;
	}



	public int getPriority() {
		return priority;
	}



	public void setPriority(int priority) {
		this.priority = priority;
	}


	
}
