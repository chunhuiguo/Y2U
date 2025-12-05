package Y2U.Transformer;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Automata;
//import Y2U.DataStructure.Event;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Transition;

public class TransitionTransformer extends ElementTransformer {

	private Element stateEle;
	private Automata automata;
	private State state;

	public TransitionTransformer(Model model, Element stateEle, Automata automata, State state) {
		super(model);
		this.stateEle = stateEle;
		this.automata = automata;
		this.state = state;
	}	








	public void transform() {	

		//state incoming transitions
		String incomingTransitions = stateEle.getAttribute("incomingTransitions").trim();
		if(! incomingTransitions.equals(""))
		{
			String[] incomingTransitionList = incomingTransitions.split(" ");

			for(int i = 0; i < incomingTransitionList.length; i++)
			{
				transformIncomingTransition(incomingTransitionList[i]);
			}
		}

		//state outgoing transitions
		NodeList outgoingTransitionEleList = stateEle.getElementsByTagName("outgoingTransitions");
		if(outgoingTransitionEleList != null)
		{
			for(int i = 0; i < outgoingTransitionEleList.getLength(); i++)
			{			
				transformOutgoingTransition((Element)outgoingTransitionEleList.item(i), i);
			}
		}
	}



	private void transformIncomingTransition(String incomingTransitionID) {

		Transition transition = null;

		transition = automata.findTransition(incomingTransitionID);
		if(transition == null)
		{
			transition = new Transition(incomingTransitionID);
			automata.addTransition(transition);
			model.addYtransition(transition); //add reference of all transitions in Yakindu model
		}

		transition.setTarget(state.getId());

	}


	private void transformOutgoingTransition(Element outgoingTransitionEle, int tranPriority) {

		String transitionID = "";
		Transition transition = null;		

		transitionID = outgoingTransitionEle.getAttribute("xmi:id").trim();

		transition = automata.findTransition(transitionID);
		if(transition == null)
		{
			transition = new Transition(transitionID);
			automata.addTransition(transition);
			model.addYtransition(transition); //add reference of all transitions in Yakindu model
		}
		transition.setSource(state.getId());
		transition.setTarget(outgoingTransitionEle.getAttribute("target").trim());
		transition.setPriority(tranPriority);
		
		transformTransitionAction(outgoingTransitionEle, transition);
	}


	//outgoing transition specification
	private String transformTransitionAction(Element outgoingTransitionEle, Transition transition) {

		String guardStr = "";
		String outgoingTransitionSpecification = outgoingTransitionEle.getAttribute("specification").trim();
		if(! outgoingTransitionSpecification.equals(""))
		{			
			String updateStr = "";
			String[] specifications = outgoingTransitionSpecification.split("/");
			
			//length is 1
			if(specifications.length > 0) {
				guardStr = specifications[0].trim();
			}
			//length is 2
			if(specifications.length > 1 ) {
				updateStr = specifications[1].trim();
			}			
			
			if(! guardStr.equals("")) {
				
				guardStr = guardStr.replace("[", "");
				guardStr = guardStr.replace("]", "");				
				guardStr = guardStr.replace('.', '_');
				//guardStr = guardStr.replace(" ", "");
				guardStr = guardStr.replace("always", "");
				guardStr.trim();
				
				//replace event guard
				Hashtable<String, Integer> eventTable = model.getEventStack().getEventTable();
				Enumeration<String> eventNames = eventTable.keys();
				String eventName = "";
				int eventIndex = 0;
				while(eventNames.hasMoreElements()) {
					eventName = eventNames.nextElement();
					if(guardStr.contains(eventName)) {
						eventIndex = eventTable.get(eventName);					
		    	    	guardStr = guardStr.replace(eventName, "isEventValid(" + eventIndex + ")");
		    	      }	
				}
								
				/*
				Set set = model.getEventStack().getEventTable().entrySet();
	    	    Iterator it = set.iterator();	    	    
	    	    while (it.hasNext()) {
	    	      Map.Entry entry = (Map.Entry) it.next();
	    	      
	    	      String eventName = (String) entry.getKey();
	    	      int eventIndex =  ((Integer) entry.getValue()).intValue();
	    	      
	    	      if(guardStr.contains(eventName)) {
	    	    	  guardStr = guardStr.replace(eventName, "isEventValid(" + eventIndex + ")");
	    	      }	    	      	      
	    	    }
	    	    */
	    	    
				//timer




	    	    List<Transition> higherPriorityTransitionList = automata.findHigherPriorityTransition(transition.getSource(), transition.getPriority());
	    	    String additionalGuard = ""; //add guard conditions according to transition priority
	    	    for(int i = 0; i < higherPriorityTransitionList.size(); i++) {
	    	    	additionalGuard = additionalGuard + " && !(" + higherPriorityTransitionList.get(i).getOriginalGuard() + ")";
	    	    }
				
	    	    transition.setOriginalGuard(guardStr);
				transition.setGuard(guardStr + additionalGuard);
			}
			
			
			if(! updateStr.equals("")) {
				
				updateStr = updateStr.replace('.', '_');
				
				//transition update
				String update = "";
				if(! transition.getUpdate().equals("")) {
					update = transition.getUpdate() + ",";
				}
				
				String[] updateStrArray = updateStr.split(";");
				for(int i = 0; i < updateStrArray.length; i++) {
					
					String str = updateStrArray[i].trim();
					
					//raise event
					if(str.contains("raise")) {
						str = str.substring(6, str.length()).trim();
						update = update + "push(" + model.getEventStack().findEventIndex(str) + "),";
					}
					else {
						update = update + str + ",";
					}
				}
				
				//remove last comma (,)
				update = update.substring(0, update.length()-1);
				transition.setUpdate(update);
			}			
		}
		
		return guardStr;
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////
	private String[] splitGuards(String guardStr) {
		return guardStr.split("&&|\\\\|!|(|)");
	}





	public Element getStateEle() {
		return stateEle;
	}
	public void setStateEle(Element stateEle) {
		this.stateEle = stateEle;
	}
	public Automata getAutomata() {
		return automata;
	}
	public void setAutomata(Automata automata) {
		this.automata = automata;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
}
