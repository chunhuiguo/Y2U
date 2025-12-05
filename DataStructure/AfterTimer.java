package Y2U.DataStructure;

public class AfterTimer extends Timer {

	public AfterTimer(String name, int time, String timeUnit) {

		super(name + "Timer", time, timeUnit);
		
		declarationList.add("clock t;");
		
		State state = new State("init_" + this.name, "");	
		state.setInvariant("t <=" + time);
		state.setPosition(0, 0);
		addState(state);
		
		state = new State("state_" + this.name, "");
		state.setPosition(200, 0);
		addState(state);
		
		Transition  transition = new Transition("tran_" + this.name, "init_" + this.name, "state_" + this.name);
		transition.setSynchronisation(name + "!");
		transition.setGuard("t == " + time);
		transition.setUpdate("t := " + 0);
		transition.setExpressionPosition(100, 100);
		//transition.addPosition(100, 100);
		addTransition(transition);
		
		initialStateID = "init_" + this.name;
	}
}
