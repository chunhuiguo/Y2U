package Y2U.Transformer;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Y2U.DataStructure.Model;
import Y2U.DataStructure.State;
import Y2U.DataStructure.Transition;

public class PositionSetter {

	private Model model;

	public PositionSetter(Model model) {
		super();
		this.model = model;
	}

	public void setPositions(Document yakinduDoc) {

		Element rootEle = yakinduDoc.getDocumentElement();

		Element diagramEle = (Element) rootEle.getElementsByTagName("notation:Diagram").item(0);
		if (diagramEle != null) {
			
			setStatePositions(diagramEle);
			
			setTransitionPositions(diagramEle);
			
			//adjustOpsitions();
		}
	}

	private void setStatePositions(Element diagramEle) {

		NodeList automataPositionEleList = diagramEle.getElementsByTagName("children");
		
		if(automataPositionEleList != null) {
			
			List<State> stateList = model.getAllYstates();
			for (int i = 0; i < automataPositionEleList.getLength(); i++) {
				
				Element automataPositionEle = (Element) automataPositionEleList.item(i);
				
				//due Yakindu file format, there are too many "children" elements
				//check if the gotten element is "Region" position
				if(! automataPositionEle.getAttribute("type").equals("Region")) {
					continue;
				}
				
				NodeList automataCompartmentPositionEleList = automataPositionEle.getElementsByTagName("children");
				for(int j = 0; j < automataCompartmentPositionEleList.getLength(); j++) {
					
					Element automataCompartmentPositionEle = (Element) automataCompartmentPositionEleList.item(j);
					
					if(! automataCompartmentPositionEle.getAttribute("type").equals("RegionCompartment")) {
						continue;
					}
					
					NodeList statePositionEleList = automataCompartmentPositionEle.getElementsByTagName("children");
					for(int k = 0; k < statePositionEleList.getLength(); k++) {
						
						Element statePositionEle = (Element) statePositionEleList.item(k);
						
						if(statePositionEle.getAttribute("type").equals("State") || statePositionEle.getAttribute("type").equals("Entry")) {
							
							String stateID = statePositionEle.getAttribute("element");
							State state = null;
							for (int l = 0; l < stateList.size(); l++) {
								if (stateList.get(l).getId().equals(stateID)) {
									state = stateList.get(l);
									break;
								}
							}
							
							if (state != null) {
								
								//state position	
								NodeList statePositionLayoutEleList = statePositionEle.getElementsByTagName("layoutConstraint");
								
								if(statePositionLayoutEleList != null) {
									for(int m = 0; m < statePositionLayoutEleList.getLength(); m++) {
										Element statePositionLayoutEle = (Element) statePositionLayoutEleList.item(m);										
										
										if(statePositionLayoutEle.getParentNode().equals(statePositionEle)) {								
											
											int x = Integer.parseInt(statePositionLayoutEle.getAttribute("x").trim());
											int y = Integer.parseInt(statePositionLayoutEle.getAttribute("y").trim());
											
											state.setPosition(x, y);
										}
									}
								}			
								
							}						
						}
					}
				}
				
			}
		}
		
	}

	private void setTransitionPositions(Element diagramEle) {

		NodeList transitionPositionEleList = diagramEle.getElementsByTagName("edges");

		if (transitionPositionEleList != null) {

			String transitionID = "";			
			List<Transition> transitionList = model.getAllYtransitions();
			Transition transition = null;
			String nailPosition = "";
			String[] nailPositions;
			String nailPositionStr = "";
			String[] nailPositionValues;
			for (int i = 0; i < transitionPositionEleList.getLength(); i++) {
				

				Element transitionPositionEle = (Element) transitionPositionEleList
						.item(i);
				transitionID = transitionPositionEle.getAttribute("element");

				transition = null;
				for (int j = 0; j < transitionList.size(); j++) {
					if (transitionList.get(j).getId().equals(transitionID)) {
						transition = transitionList.get(j);
						break;
					}
				}

				if (transition != null) {
					
					// expression position of the transition
					Element transitionExpressionPositionEle = (Element) transitionPositionEle
							.getElementsByTagName("children").item(0);
					Element transitionExpressionPositionLayoutEle = (Element) transitionExpressionPositionEle
							.getElementsByTagName("layoutConstraint").item(0);
					
					String xStr  = transitionExpressionPositionLayoutEle.getAttribute("x").trim();
					int x = 0;
					if(! xStr.equals("")) {
						x = Integer.parseInt(transitionExpressionPositionLayoutEle
								.getAttribute("x").trim());
					}
					String yStr  = transitionExpressionPositionLayoutEle.getAttribute("y").trim();
					int y = 0;
					if(! yStr.equals("")) {
						y = Integer.parseInt(transitionExpressionPositionLayoutEle
								.getAttribute("y").trim());
					}				

					transition.setExpressionPosition(x, y);
					
					

					// nail positions of the transition
					Element transitionNailPositionEle = (Element) transitionPositionEle
							.getElementsByTagName("bendpoints").item(0);
					nailPosition = transitionNailPositionEle
							.getAttribute("points");
					nailPositions = nailPosition.split("\\$");

					// if nailPositions.length=2, then the transition has no nails
					// the two positions are the start point and end point, then the nail at the mid-point
					if (nailPositions.length > 2) {

						for (int k = 1; k < nailPositions.length - 1; k++) {
							nailPositionStr = nailPositions[k];
							nailPositionStr= nailPositionStr.replace("[", "");
							nailPositionStr = nailPositionStr.replace("]", "");

							nailPositionValues = nailPositionStr.split(",");

							// the nail position in Yakindu is
							// "[6, 4, -76, -1]$[29, 17, -53, 12]$[65, 8, -17, 3]"
							// transform the position into x and y according to
							// manual transformation experience of simple/small
							// Yakindu model
							x = Integer.parseInt(nailPositionValues[1].trim())
									+ Integer.parseInt(nailPositionValues[0]
											.trim());
							y = Integer.parseInt(nailPositionValues[3].trim())
									- Integer.parseInt(nailPositionValues[2]
											.trim());
							transition.addPosition(x, y);
						}						
					}
					/*
					else {
						State source = transition.getSource();
						State target = transition.getTarget();
					}
					*/
					
					
					
					//????
					if(transition.getPositionList().size() > 0) {
						transition.setExpressionPosition(transition.getPositionList().get(0).getX(), transition.getPositionList().get(0).getY());
					}									
				}
			}
		}
	}
	
	
	public void adjustOpsitions() {
		List<State> stateList = model.getAllYstates();
		List<Transition> transitionList = model.getAllYtransitions();
		
		for(int i = 0; i < stateList.size(); i++) {
			//stateList.get(i).adjustPosition(1.2);			
		}
		
		for(int i = 0; i < transitionList.size(); i++) {
			transitionList.get(i).adjustPosition(1.2);
		}
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
}
