package Y2U;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Automata;
import Y2U.DataStructure.Event;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Timer;
import Y2U.DataStructure.Transition;

public class backup {

	private Document yakinduDoc;	
	private Model model;
	
	
	
	public ElementTransformer()
	{		
		model = new Model();
	}
	
	
	
	//
	public void read() {
		yakinduDoc = parse(yakinduFilePath);
		
		Element rootEle = yakinduDoc.getDocumentElement();	
        
        //read automata 
        NodeList automataEleList = rootEle.getElementsByTagName("regions");
        if(automataEleList != null) { 
        	
        	Element automataEle = null;
        	Element stateEle = null;
        	        	
        	Automata automata = null;
        	State state = null;
        	Transition transition = null;
        	Event event = null;
        	Timer timer = null;
        	
        	String automataName = "";
        	String stateName = "";
        	String stateID = "";
        	String stateType = "";        	
        	
        	for(int i = 0; i < automataEleList.getLength(); i++) {
        		
        		automataEle = (Element)automataEleList.item(i);
        		automataName = automataEle.getAttribute("name").trim();
        		automataName = automataName.replace(' ', '_');
        		automataName = automataName.replace('.', '_');
        		automataName = automataName.replace('-', '_');
        		
        		automata = new Automata(automataName);
        		model.addAutomata(automata);
        		
        		
        		NodeList stateEleList = automataEle.getElementsByTagName("vertices");
        		if(stateEleList != null) {          			
        			
        			for(int j = 0; j < stateEleList.getLength(); j++) {        	
        				
        				//read states
        				stateEle = (Element)stateEleList.item(j);
        				stateType = stateEle.getAttribute("xsi:type").trim();
        				stateID = stateEle.getAttribute("xmi:id").trim();
        				
        				state = automata.findState(stateID);
        				//new state
        				if(state == null)
        				{
        					state = new State(stateID);
        					automata.addState(state);
        				}
        				
        				//initial state
        				if(stateType.equals("sgraph:Entry"))
        				{
        					stateName = "init_" + automataName;
        					stateName = stateName.replace(' ', '_');
        					stateName = stateName.replace('.', '_');
        					stateName = stateName.replace('-', '_');
        					state.setName(stateName);        					
        					
        					//set initial state
        					automata.setInitialStateID(stateID);
        				}
        				//simple state
        				else if(stateType.equals("sgraph:State"))
        				{
        					stateName = stateEle.getAttribute("name").trim() + "_" + automataName;
        					stateName = stateName.replace(' ', '_');
        					stateName = stateName.replace('.', '_');
        					stateName = stateName.replace('-', '_');
        					state.setName(stateName);
        					
        				}
        				
        				//state incoming transitions
        				String incomingTransitions = stateEle.getAttribute("incomingTransitions").trim();
        				if(! incomingTransitions.equals(""))
        				{
        					String[] incomingTransitionList = incomingTransitions.split(" ");
            				
            				for(int k = 0; k < incomingTransitionList.length; k++)
            				{
            					transition = automata.findTransition(incomingTransitionList[k]);
            					if(transition == null)
            					{
            						transition = new Transition(incomingTransitionList[k]);
            						automata.addTransition(transition);
            					}
            					
            					transition.setTarget(stateID);
            				}
        				}
        				
        				
        				
        				//state outgoing transitions
        				NodeList outgoingTransitionEleList = stateEle.getElementsByTagName("outgoingTransitions");
        				if(outgoingTransitionEleList != null)
        				{
        					Element outgoingTransitionEle = null;
        					String transitionID = "";
        					
        					for(int k = 0; k < outgoingTransitionEleList.getLength(); k++)
        					{
        						outgoingTransitionEle = (Element)outgoingTransitionEleList.item(k);
        						transitionID = outgoingTransitionEle.getAttribute("xmi:id").trim();
        						
        						transition = automata.findTransition(transitionID);
        						if(transition == null)
        						{
        							transition = new Transition(transitionID);
            						automata.addTransition(transition);
        						}
        						transition.setSource(stateID);
        						transition.setTarget(outgoingTransitionEle.getAttribute("target").trim());
        						
        						
        						//outgoing transition specification
        						String outgoingTransitionSpecification = outgoingTransitionEle.getAttribute("specification").trim();
        						if(! outgoingTransitionSpecification.equals(""))
        						{
        							outgoingTransitionSpecification = outgoingTransitionSpecification.replace("[", "");
        							outgoingTransitionSpecification = outgoingTransitionSpecification.replace("]", "");
        							outgoingTransitionSpecification = outgoingTransitionSpecification.replace("/", "");		
        							outgoingTransitionSpecification = outgoingTransitionSpecification.replace('.', '_');
        							outgoingTransitionSpecification = outgoingTransitionSpecification.replace("always", "");
            						outgoingTransitionSpecification.trim();
                    				
            						
            						//events            						
            						String eventName = "";
            						for(int l = 0; l < model.getEventList().size(); l++)
            						{
            							eventName = model.getEventList().get(l).getName();
            							eventName = eventName.substring(0, eventName.length() - 5);
            							if(outgoingTransitionSpecification.contains(eventName))
            							{
            								transition.setSynchronisation(eventName + "?");
            								
            								outgoingTransitionSpecification = outgoingTransitionSpecification.replace(eventName, "").trim();
            							}
            						}            						
            						
            						
            						
            						//timer
            						
            						
            						
            						
            						//general specifications            						
            						transition.setGuard(outgoingTransitionSpecification);
        						}        						
        					}
        				}
        				
        				
        				
        				//state specification
        				String stateSpecification = stateEle.getAttribute("specification").trim();
        				if(! stateSpecification.equals(""))
        				{
        					String[] stateSpecificationList = stateSpecification.split("\n");
            		        
            				String entryString = "";
            				String exitString = "";     
            				boolean entry = false;
            				boolean exit = false;
            		        for(int k = 0; k < stateSpecificationList.length; k++)
            		        {
            		        	        		        	      		        	
            		        	if(stateSpecificationList[k].contains("entry"))
            		        	{
            		        		entry = true;
            		        		exit = false;
            		        	}
            		        	else if(stateSpecificationList[k].contains("exit"))
            		        	{
            		        		entry = false;
            		        		exit = true;
            		        	}
            		        	else
            		        	{
            		        		if(entry)
            		        			entryString = entryString + stateSpecificationList[k].trim();
            		        		
            		        		if(exit)
            		        			exitString = exitString + stateSpecificationList[k].trim();
            		        	}           		        	
            		        }
            		        
            		        entryString = entryString.replace("=", ":=");
            		        entryString = entryString.replace(';', ',');
            		        entryString = entryString.replace('.', '_');
            		        exitString = exitString.replace("=", ":=");
            		        exitString = exitString.replace(';', ',');
            		        exitString = exitString.replace('.', '_');
        		        	
        		        	
        		        	if(entryString.length() > 0)
        		        	{
        		        		List<Transition> inTransitionList = automata.findIncomingTransition(stateID);
        		        		
        		        		for(int l = 0; l < inTransitionList.size(); l++)
        		        		{
        		        			transition = inTransitionList.get(l);
        		        			
        		        			if(transition.getUpdate().length() == 0)
        		        				transition.setUpdate(entryString);
        		        			else
        		        				transition.setUpdate(transition.getUpdate() + ", " + entryString);
        		        		}
        		        	}
        		        	
        		        	if(exitString.length() > 0)
        		        	{
        		        		List<Transition> outTransitionList = automata.findOutgoingTransition(stateID);
        		        		
        		        		for(int l = 0; l < outTransitionList.size(); l++)
        		        		{
        		        			transition = outTransitionList.get(l);
        		        			
        		        			if(transition.getUpdate().length() == 0)
        		        				transition.setUpdate(exitString);
        		        			else
        		        				transition.setUpdate(exitString + ", " + transition.getUpdate());
        		        		}
        		        	}
        				}        			
        			}
        			
        		}        		
        	}  
        }        
	}    
        
        
        
        
    public void setLocations()
    {
    	Automata automata = null;  
    	State state = null;
    	Transition transition = null;
    	
    	for(int i = 0; i < model.getAutomataList().size(); i++)
    	{
    		automata = model.getAutomataList().get(i);
    		
    		for(int j = 0; j < automata.getStateList().size(); j++)
    		{
    			state = automata.getStateList().get(j);
    			
    			state.setX(100*j);
    			state.setY(100*j);
    		}
    		
    		
    		for(int j = 0; j < automata.getTransitionList().size(); j++)
    		{
    			transition = automata.getTransitionList().get(j);
    			
    			transition.setX((automata.findState(transition.getSource()).getX() + automata.findState(transition.getTarget()).getX()) / 2);
    			transition.setY((automata.findState(transition.getSource()).getY() + automata.findState(transition.getTarget()).getY()) / 3 +  automata.findState(transition.getSource()).getY());
    		}
    	}
    }
    
    
    
    
    
    public void countStateTransitionNum()
    {
    	int stateNum = 0;
		int transitionNum = 0;
		int automataNum = 0;
		
		//automata
		for(int i = 0; i < model.getAutomataList().size(); i++)
		{    	
			automataNum = automataNum + 1;
			stateNum = stateNum + model.getAutomataList().get(i).getStateList().size();
			transitionNum = transitionNum + model.getAutomataList().get(i).getTransitionList().size();
		}
		
		
		//event
		for(int i = 0; i < model.getEventList().size(); i++)
		{    			
			automataNum = automataNum + 1;
			stateNum = stateNum + model.getEventList().get(i).getStateList().size();
			transitionNum = transitionNum + model.getEventList().get(i).getTransitionList().size();
		}
		
		
		
		//timer
		for(int i = 0; i < model.getTimerList().size(); i++)
		{    			
			automataNum = automataNum + 1;
			stateNum = stateNum + model.getTimerList().get(i).getStateList().size();
			transitionNum = transitionNum + model.getTimerList().get(i).getTransitionList().size();
		} 
		
		
		System.out.println("Automata Num: " + automataNum);
		System.out.println("State Num: " + stateNum);
		System.out.println("Transition Num: " + transitionNum);
    }
    
	
	//
	      
	

	
	public Document getYakinduDoc() {
		return yakinduDoc;
	}
	public void setYakinduDoc(Document yakinduDoc) {
		this.yakinduDoc = yakinduDoc;
	}
	
}
