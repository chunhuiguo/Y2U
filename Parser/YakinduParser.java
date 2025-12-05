package Y2U.Parser;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Statechart.State;
import Y2U.DataStructure.Statechart.Statechart;
import Y2U.DataStructure.Statechart.Transition;
import Y2U.DataStructure.Statechart.stModel;
import Y2U.Transformer.AutomataTransformer;
import Y2U.Transformer.VariableTransformer;

public class YakinduParser {

	private Document yakinduDoc;
	private stModel stmodel;
	
	public YakinduParser (Document yakinduDoc) {
		super();
		this.yakinduDoc = yakinduDoc;
		
		stmodel = null;
	}
	
	public void parse() {		
		Element rootEle = yakinduDoc.getDocumentElement();		
		//parse statechart model
		Element stmodelEle = (Element)rootEle.getElementsByTagName("sgraph:Statechart").item(0);
		if (stmodelEle != null) {
			stmodel = new stModel();
			
			//parse declaration
			String declaration = stmodelEle.getAttribute("specification").trim();
			stmodel.setDeclaration(declaration);
			
			
			//parse statechart list
			NodeList stEleList = stmodelEle.getElementsByTagName("regions");			
			if(stEleList != null) {
				for(int i = 0; i < stEleList.getLength(); i++) {
					//parse statechart
					Element stEle = (Element)stEleList.item(i);
					
					Statechart st = new Statechart();
					st.setId(stEle.getAttribute("xmi:id"));
					st.setName(stEle.getAttribute("name"));
					st.setPriority(i+1);
					
					stmodel.addStatechart(st);
					
					
					//parse state
					
					
					
					
					//parse transition
					
					
					??
					stateList = new ArrayList<State> ();
					transitionList = new ArrayList<Transition> ();
					initialStateID = "";
					

					

					    		        		
				}  
			}
		}

		
	}
}
