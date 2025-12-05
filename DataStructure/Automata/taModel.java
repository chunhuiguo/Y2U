package Y2U.DataStructure.Automata;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class taModel {
	
	//all timers have the same timeUnit
	private Hashtable<String, Integer> timeUnitList; //enum TimeUnit { s, ms, us,ns };
	private String timeUnit;
	
	private List<Statechart> automataList;	
	//private List<Event> eventList;	
	private List<Timer> timerList;
	private List<String> declarationList;
	private List<String> sysList;
	private Hashtable<String, StringVariable> stringVariableList;
	private Hashtable<String, RealVariable> realVariableList;
	
	//just store reference for all states/transitions in Yakindu model
	//reference just for state/transition point settings
	private List<State> allYstates;
	private List<Transition> allYtransitions;
	
	private EventStack eventStack;
	

	
	public taModel() {
		automataList = new ArrayList<Statechart> ();
		//eventList = new ArrayList<Event>();
		timerList = new ArrayList<Timer>();
		declarationList = new ArrayList<String> ();
		sysList = new ArrayList<String> ();	
		stringVariableList = new Hashtable<String, StringVariable>();
		realVariableList = new Hashtable<String, RealVariable>();
		
		timeUnit = "s";
		
		timeUnitList = new Hashtable<String, Integer>();
		timeUnitList.put("s", 0);
		timeUnitList.put("", 0);
		timeUnitList.put("ms", -3);
		timeUnitList.put("us", -6);
		timeUnitList.put("ns", -9);
		
		allYstates = new ArrayList<State>();
		allYtransitions = new ArrayList<Transition>();
		
		eventStack = new EventStack();
	}
	
	
	public void addYstate(State state) {
		allYstates.add(state);
	}
	
	public void addYtransition(Transition transition) {
		allYtransitions.add(transition);
	}
	
	public Transition findTransition(String transitionID) {		
		
		for(int i = 0; i < allYtransitions.size(); i++) {
			if(allYtransitions.get(i).getId().equals(transitionID))
				return allYtransitions.get(i);
		}
		
		return null;
	}
	
	public State findState(String stateID) {	
		
		for(int i = 0; i < allYstates.size(); i++) {
			if(allYstates.get(i).getId().equals(stateID))
				return allYstates.get(i);
		}
		
		return null;
	}
	
	
	public void addDeclaration(String declaration) {
		declarationList.add(declaration);
	}
	
	public void addAutomata(Statechart automata) {
		automataList.add(automata);
		
		sysList.add(automata.getName());
	}
	
	/*
	public void addEvent(Event event) {	
		eventList.add(event);		
		sysList.add(event.getName());
	}
	
	public Event findEvent(String eventName) {
		for(int i = 0; i < eventList.size(); i++) {
			Event event = eventList.get(i);
			
			if(event.getEventName().equals(eventName))
				return event;
		}
		return null;
	}
	*/
	
	//add timer and update all timers' time value based on timeUnit
	//all timers have the same timeUnit
	public void addTimer(String timerName, Timer timer) {
		String newTimeUnit = timer.getTimeUnit();
		
		if(timeUnitList.get(timeUnit) - timeUnitList.get(newTimeUnit) < 0) {
			timer.updateTimeByUnit(timeUnit, timeUnitList);
		}
			
		if(timeUnitList.get(timeUnit) - timeUnitList.get(newTimeUnit) > 0) {
			for(int i = 0; i < timerList.size(); i++) {
				timerList.get(i).updateTimeByUnit(newTimeUnit, timeUnitList);
				
				timeUnit = newTimeUnit;
			}
		}
			
		timerList.add(timer);
		
		sysList.add(timer.getName());
	}
	
	//find the every timer with the timerName (e.g. every5s)
	//every timer contains a self loop transition, all every timers with same time value can share one
	public Timer findTimer(String timerName) {
		for(Timer timer : timerList) {
			if(timer.getTimerName().equals(timerName))
				return timer;
		}

		return null;
	}
	
	//find counts of the after timer with same time value (e.g. after5s_0, after5s_1) 
	//each after timers with same time value has its own timer (NOT share)
	//the new timerName will be after5s_[count+1]
	public int findTimerCount(String timerName) {
		
		int count = 0;
		
		for(Timer timer : timerList) {
			if(timer.getTimerName().startsWith(timerName))
				count++;
		}

		return count;
	}
	
	public void addSys(String sys) {
		sysList.add(sys);
	}
	
	public void addStringVariable(String strName) {
		stringVariableList.put(strName, new StringVariable(strName));		
	}
	
	
	public StringVariable findStringVariable(String strName) {
		return stringVariableList.get(strName);
	}
	
	
	
	public void addRealVariable(String name) {
		realVariableList.put(name, new RealVariable(name));
	}
	
	
	public RealVariable findRealVariable(String name) {
		return realVariableList.get(name);
	}
	
	
	
	
	
	public List<Statechart> getAutomataList() {
		return automataList;
	}
	public void setAutomataList(List<Statechart> automataList) {
		this.automataList = automataList;
	}
	/*
	public List<Event> getEventList() {
		return eventList;
	}
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	*/
	public List<Timer> getTimerList() {
		return timerList;
	}
	public void setTimerList(List<Timer> timerList) {
		this.timerList = timerList;
	}
	public List<String> getDeclarationList() {
		return declarationList;
	}
	public void setDeclarationList(List<String> declarationList) {
		this.declarationList = declarationList;
	}	
	public List<String> getSysList() {
		return sysList;
	}
	public void setSysList(List<String> sysList) {
		this.sysList = sysList;
	}
	public Hashtable<String, StringVariable> getStringVariableList() {
		return stringVariableList;
	}
	public void setStringVariableList(Hashtable<String, StringVariable> stringVariableList) {
		this.stringVariableList = stringVariableList;
	}
	public Hashtable<String, RealVariable> getRealVariableList() {
		return realVariableList;
	}
	public void setRealVariableList(Hashtable<String, RealVariable> realVariableList) {
		this.realVariableList = realVariableList;
	}

	public Hashtable<String, Integer> getTimeUnitList() {
		return timeUnitList;
	}

	public void setTimeUnitList(Hashtable<String, Integer> timeUnitList) {
		this.timeUnitList = timeUnitList;
	}

	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}


	public List<State> getAllYstates() {
		return allYstates;
	}


	public void setAllYstates(List<State> allYstates) {
		this.allYstates = allYstates;
	}


	public List<Transition> getAllYtransitions() {
		return allYtransitions;
	}


	public void setAllYtransitions(List<Transition> allYtransitions) {
		this.allYtransitions = allYtransitions;
	}


	public EventStack getEventStack() {
		return eventStack;
	}


	public void setEventStack(EventStack eventStack) {
		this.eventStack = eventStack;
	}
	
}
