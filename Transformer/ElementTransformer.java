package Y2U.Transformer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Model;

public class ElementTransformer
{		
	protected Model model;
	
	
	public ElementTransformer(Model model) {
		super();
		this.model = model;
	}
	
	
	
	public void transform(Document yakinduDoc) {
		Element rootEle = yakinduDoc.getDocumentElement();

		//transform variable declarations
		Element modelEle = (Element)rootEle.getElementsByTagName("sgraph:Statechart").item(0);
		if (modelEle != null) {
			String declaration = modelEle.getAttribute("specification");

			VariableTransformer variableTransformer = new VariableTransformer(model, declaration);
			variableTransformer.transform();			
		}
		
		//add event stack declarations
		if(model.getEventStack().getEventNum() > 0) {
			model.addDeclaration("const int TotalEventNumber = " + model.getEventStack().getEventNum() + ";");		
			model.addDeclaration("typedef struct { int validEvents[TotalEventNumber]; int validEventNumber; } EventStack;");
			model.addDeclaration("EventStack eventStack;");
			model.addDeclaration("void empty(){ eventStack.validEventNumber = 0; }");
			model.addDeclaration("bool isEventValid(int event) { int i = 0; for(i=0; i< eventStack.validEventNumber; i++) { if(eventStack.validEvents[i] == event){return true;} } return false; }");
			model.addDeclaration("void push(int event) { if(! isEventValid(event)) { eventStack.validEvents[eventStack.validEventNumber] = event; eventStack.validEventNumber++; } }");
		}
		


		//transform automata 
		NodeList automataEleList = rootEle.getElementsByTagName("regions");
		if(automataEleList != null) {       	     	

			for(int i = 0; i < automataEleList.getLength(); i++) {

				Element automataEle = (Element)automataEleList.item(i);

				AutomataTransformer automataTransformer = new AutomataTransformer(model, automataEle);
				automataTransformer.transform();        		        		
			}  
		}

		
		//transform positions of state/transiton
	}
       


	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}	
}