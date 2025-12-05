/*
package Y2U;

import Y2U.DataStructure.Automata;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Transition;

public class Layout {
	
	private Model model;
	
	public Layout(Model model) {
		super();
		this.model = model;
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




	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	
	
	
}
*/
