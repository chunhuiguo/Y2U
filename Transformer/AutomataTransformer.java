package Y2U.Transformer;

import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Automata;
//import Y2U.DataStructure.Event;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Timer;
import Y2U.DataStructure.Transition;

public class AutomataTransformer extends ElementTransformer {


	private Element automataEle;
	private Automata automata;


	public AutomataTransformer(Model model, Element automataEle) {
		super(model);
		this.automataEle = automataEle;

		automata = null;
	}





	public void transform() {	

		Element stateEle = null;		
		String automataName = "";		     	


		automataName = automataEle.getAttribute("name").trim();
		automataName = automataName.replace(' ', '_');
		automataName = automataName.replace('.', '_');
		automataName = automataName.replace('-', '_');

		automata = new Automata(automataName);
		model.addAutomata(automata);


		NodeList stateEleList = automataEle.getElementsByTagName("vertices");
		if(stateEleList != null) {          			

			for(int i = 0; i < stateEleList.getLength(); i++) {        	

				//transform states
				stateEle = (Element)stateEleList.item(i);

				StateTransformer stateTransformer = new StateTransformer(model, stateEle, automata);
				stateTransformer.transform();
			}

		}        		

	}



	public Element getAutomataEle() {
		return automataEle;
	}
	public void setAutomataEle(Element automataEle) {
		this.automataEle = automataEle;
	}
	public Automata getAutomata() {
		return automata;
	}
	public void setAutomata(Automata automata) {
		this.automata = automata;
	}

}
