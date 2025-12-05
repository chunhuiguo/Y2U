package Y2U.Transformer;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Automata;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.Position;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Transition;

public class Synchronizer {
	protected Model model;

	public Synchronizer(Model model) {
		super();
		this.model = model;
	}

	public void synchronizeAutomata() {
		
		this.setAutomataPriorities();
		
		this.addSynchronyDeclarations();
		
		this.addSelfloopTransition();
		
		this.addSynchronyGuardAndUpdate();
		
		this.addMaxStepSelfloop();

	}

	private void setAutomataPriorities() {
		List<Automata> automataList = model.getAutomataList();

		Automata automata = null;
		for (int i = 0; i < automataList.size(); i++) {
			automata = automataList.get(i);
			automata.setPriority(i + 1);
		}
	}

	private void addSynchronyDeclarations() {
		int automataNum = model.getAutomataList().size();
		
		model.addDeclaration("const int MAXSTEP = 32767;");

		String allSynVariable = "";
		String allSynCheck = "";
		for (int i = 1; i <= automataNum; i++) {
			model.addDeclaration("int synT" + i + " = 0;");

			if (i < automataNum) {
				allSynVariable = allSynVariable + "int synT" + i + ",";

				if (i < automataNum - 1) {
					allSynCheck = allSynCheck + "synT" + i + "==synT"
							+ Integer.toString(i + 1) + " && ";
				} else {
					allSynCheck = allSynCheck + "synT" + i + "==synT"
							+ Integer.toString(i + 1);
				}
			} else {
				allSynVariable = allSynVariable + "int synT" + i;
			}
		}

		model.addDeclaration("bool checkHighest(" + allSynVariable
				+ ") {     return (" + allSynCheck + "); }");

		model.addDeclaration("bool checkLower(int synT, int synThigher) {     return synT<synThigher; }");

		model.addDeclaration("bool checkLowestMAXSTEP(int synT) {     return synT==MAXSTEP; }");

		model.addDeclaration("void update(int& synT, int& synThigher) {     synT = synT+1;     if(synThigher == MAXSTEP)         synThigher=0; }");

		model.addDeclaration("void updateLowestMAXSTEP(int& synT) {     synT = 0; }");
	}

	private void addSelfloopTransition() {
		List<Automata> automataList = model.getAutomataList();

		for (int i = 0; i < automataList.size(); i++) {
			Automata automata = automataList.get(i);
			List<State> stateList = automata.getStateList();
			List<Transition> transitionList = automata.getTransitionList();

			for (int j = 0; j < stateList.size(); j++) {
				String allGuards = ""; // guards of all outgoing transitions of
										// the state
				State state = stateList.get(j);
				
				int outgoingTransitionNum = 0;

				for (int k = 0; k < transitionList.size(); k++) {
					Transition transition = transitionList.get(k);

					if (transition.getSource().equals(state.getId())) {
						outgoingTransitionNum++;
						
						if (!transition.getGuard().equals("")) {
							if (allGuards.equals("")) {
								allGuards = transition.getGuard();
							} else {
								allGuards = allGuards + "&&"
										+ transition.getGuard();
							}
						}
					}
				}
				
				if(outgoingTransitionNum != 0) {
					if(allGuards.equals("")) {
						allGuards = "false";
					}
					else {
						allGuards = "!(" + allGuards + ")";
					}
					
				}
				else {
					allGuards = "";
				}

				int x = state.getPosition().getX();
				int y = state.getPosition().getY();
				Transition transition = new Transition();
				transition.setSource(state.getId());
				transition.setTarget(state.getId());
				transition.setGuard(allGuards);			
				transition.addPosition(x - 50, y - 100);
				transition.addPosition(x + 50, y - 100);
				transition.setExpressionPosition(x - 50, y - 100);
				automata.addTransition(transition);
			}
		}
	}

	private void addSynchronyGuardAndUpdate() {
		List<Automata> automataList = model.getAutomataList();		

		for (int i = 0; i < automataList.size(); i++) {
			Automata automata = automataList.get(i);			
			List<Transition> transitionList = automata.getTransitionList();

			String synUpdate = "";
			String synGuard = "";
			
			if(i == 0) {
				
				String allSynVariable = "";
				for(int j = 1; j <= automataList.size(); j++) {
					if(j < automataList.size()) {
						allSynVariable = allSynVariable + "synT" + j + ",";
					}
					else {
						allSynVariable = allSynVariable + "synT" + j;
					}
				}
				synGuard = "checkHighest(" + allSynVariable + ")";
				
				synUpdate = "update(synT" + Integer.toString(i+1) + ",synT" + Integer.toString(automataList.size()) + ")";
			}
			else {
				synGuard = "checkLower(synT" + Integer.toString(i+1) + ",synT" + i + ")";
				synUpdate = "update(synT" + Integer.toString(i+1) + ",synT" + i + ")";
				
				if(i == automataList.size()-1) {
					synUpdate = synUpdate + ",empty()";
				}
			}

			for (int k = 0; k < transitionList.size(); k++) {
				Transition transition = transitionList.get(k);
				
				if(transition.getGuard().equals("")) {
					transition.setGuard(synGuard);
				}
				else {
					transition.setGuard(transition.getGuard() + "&&" + synGuard);
				}
				
				if(transition.getUpdate().equals("")) {
					transition.setUpdate(synUpdate);
				}
				else {
					transition.setUpdate(transition.getUpdate() + "," + synUpdate);
				}
			}

		}
	}
	
	
	//for the highest priority automata, add another selfloop transition to identify if the lowest priority automata executes MAXSTEP
	private void addMaxStepSelfloop() {
		
		Automata automata = model.getAutomataList().get(0);
		int automataNum = model.getAutomataList().size();
		List<State> stateList = automata.getStateList();
		
		String synUpdate = "updateLowestMAXSTEP(synT" + automataNum + ")";
		String synGuard = "checkLowestMAXSTEP(synT" + automataNum + ")";
		
		for (int j = 0; j < stateList.size(); j++) {
			
			State state = stateList.get(j);			

			int x = state.getPosition().getX();
			int y = state.getPosition().getY();
			
			Transition transition = new Transition();
			transition.setSource(state.getId());
			transition.setTarget(state.getId());
			transition.setGuard(synGuard);
			transition.setUpdate(synUpdate);			
			transition.addPosition(x - 50, y + 100);
			transition.addPosition(x + 50, y + 100);
			transition.setExpressionPosition(x - 50, y + 100);
			automata.addTransition(transition);
		}		
		
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
}