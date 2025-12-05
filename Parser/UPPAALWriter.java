package Y2U.Parser;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import Y2U.DataStructure.Automata;
//import Y2U.DataStructure.Event;
import Y2U.DataStructure.Model;
import Y2U.DataStructure.Position;
import Y2U.DataStructure.RealVariable;
import Y2U.DataStructure.State;
import Y2U.DataStructure.StringVariable;
import Y2U.DataStructure.Timer;
import Y2U.DataStructure.Transition;

public class UPPAALWriter {
	
	private String uppaalFilePath;
	private Document uppaalDoc;	
	private Model model;
	
	
	
	public UPPAALWriter(String uppaalFilePath, Model model) {
		super();
		this.uppaalFilePath = uppaalFilePath;
		this.model = model;		
		
		uppaalDoc = null;
	}
	
	
	
	public void write()
	{   	     
		uppaalDoc = createDoc();  

		writeModel();    		

		writeDoc();
		
		writeEventStackDictionary();
    }  
	
	
	private Document createDoc() { 
    	Document document = null; 
    	try { 
    		//DOM new Document instance 
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder(); 
    		//create new document 
    		document = docBuilder.newDocument(); 
    	} catch (ParserConfigurationException e) { 
    		e.printStackTrace();  
    	}
    	return document; 
    }

	
	// write the content into xml file
	private void writeDoc()
	{
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			DOMImplementation domImpl = uppaalDoc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("doctype",
			    "-//Uppaal Team//DTD Flat System 1.1//EN",
			    "http://www.it.uu.se/research/group/darts/uppaal/flat-1_2.dtd");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			
			uppaalDoc.setXmlStandalone(true);
			DOMSource source = new DOMSource(uppaalDoc);
			PrintWriter pw = new PrintWriter(uppaalFilePath, "utf-8"); 
			StreamResult result = new StreamResult(pw);
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
		}
		catch (TransformerException tfe) {
    		tfe.printStackTrace();
    	}
    	catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
	}
	
	
	private void writeModel() {
		
		//root (nta) elements    		
		Element rootEle = uppaalDoc.createElement("nta");
		uppaalDoc.appendChild(rootEle);		
	
		//declaration elements
		//golbal declarations
		Element declaratonEle = uppaalDoc.createElement("declaration");
		rootEle.appendChild(declaratonEle);
		
		String declaratons = "";
		for(int i = 0; i < model.getDeclarationList().size(); i++)
		{
			declaratons = declaratons + model.getDeclarationList().get(i) + "\n";
		}		
		List<StringVariable> stringVariableList = new ArrayList<StringVariable>(model.getStringVariableList().values());		
		for(StringVariable stringVariable : stringVariableList) {
			declaratons = declaratons + "int " + stringVariable.getName() + "\n";
		}
		List<RealVariable> realVariableList = new ArrayList<RealVariable>(model.getRealVariableList().values());		
		for(RealVariable realVariable : realVariableList) {
			declaratons = declaratons + "int " + realVariable.getIntegerPartName() + "\n";
			declaratons = declaratons + "int " + realVariable.getFractionPartName() + "\n";
		}
		declaratonEle.appendChild(uppaalDoc.createTextNode(declaratons));
		
		
		
		//template (automata) elements
		Automata automata = null;
		for(int i = 0; i < model.getAutomataList().size(); i++)
		{    			
			automata = model.getAutomataList().get(i);    			
			writeAutomata(rootEle, automata);
		}
		
		/*
		//template (event) elements	
		Event event = null;
		for(int i = 0; i < model.getEventList().size(); i++)
		{    			
			event = model.getEventList().get(i);    			
			writeAutomata(rootEle, event);
		}
		*/
		
		
		//template (timer) elements
		for(Timer timer : model.getTimerList()) {
			writeAutomata(rootEle, timer);
		}		
		
		
		//system element
		Element systemEle = uppaalDoc.createElement("system");
		rootEle.appendChild(systemEle);
		String systemStr = "system";
		for(int i = 0; i < model.getSysList().size(); i++)
		{
			if(i==0)
				systemStr = systemStr + " " + model.getSysList().get(i);  
			else
				systemStr = systemStr + ", " + model.getSysList().get(i); 
		}
		systemStr = systemStr + ";";
		systemEle.appendChild(uppaalDoc.createTextNode(systemStr));
		
		
		//query element
		Element queryEle = uppaalDoc.createElement("queries");
		rootEle.appendChild(queryEle); 
	}   
    
    
    private void writeAutomata(Element rootEle, Automata automata)
    {		
		Element automataEle = uppaalDoc.createElement("template");
		rootEle.appendChild(automataEle);
		
		//automata basic info
		Element automataNameEle = uppaalDoc.createElement("name");   		
		automataNameEle.appendChild(uppaalDoc.createTextNode(automata.getName()));
		automataEle.appendChild(automataNameEle);
		
		
		
		//declaration elements
		//automata declarations
		Element declaratonEle = uppaalDoc.createElement("declaration");
		automataEle.appendChild(declaratonEle);

		String declaratons = "";
		for(int i = 0; i < automata.getDeclarationList().size(); i++)
		{
			declaratons = declaratons + automata.getDeclarationList().get(i) + "\n";
		}
		declaratonEle.appendChild(uppaalDoc.createTextNode(declaratons));
		
		
		
		//location (state) elements
		State state = null;
		for(int j = 0; j < automata.getStateList().size(); j++)
		{
			state = automata.getStateList().get(j);			
			writeStates(automataEle, state);
		}
		
		
		
		//initial state element
		Element initStateEle = uppaalDoc.createElement("init");
		initStateEle.setAttribute("ref", automata.getInitialStateID());
		automataEle.appendChild(initStateEle);
		
		
		
		//transition elements
		Transition transition = null;
		for(int j = 0; j < automata.getTransitionList().size(); j++)
		{
			transition = automata.getTransitionList().get(j);			
			writeTransitions(automataEle, transition);
		}
    }
    
    
    
    private void writeStates(Element automataEle, State state)
    {    				
    	//state basic info
    	Element stateEle = uppaalDoc.createElement("location");
    	stateEle.setAttribute("id", state.getId());
    	stateEle.setAttribute("x", String.valueOf(state.getPosition().getX() ));
    	stateEle.setAttribute("y", String.valueOf(state.getPosition().getY() ));
    	automataEle.appendChild(stateEle);

    	//state name
    	Element stateNameEle = uppaalDoc.createElement("name");
    	stateNameEle.setAttribute("x", String.valueOf(state.getPosition().getX() -8));
    	stateNameEle.setAttribute("y", String.valueOf(state.getPosition().getY() -8));
    	stateNameEle.appendChild(uppaalDoc.createTextNode(state.getName()));
    	stateEle.appendChild(stateNameEle);


    	//state invariant
    	Element invariantEle = uppaalDoc.createElement("label");
    	invariantEle.setAttribute("kind", "invariant");
    	invariantEle.setAttribute("x", String.valueOf(state.getPosition().getX() -18));
    	invariantEle.setAttribute("y", String.valueOf(state.getPosition().getY() +10));
    	invariantEle.appendChild(uppaalDoc.createTextNode(state.getInvariant()));    	
    }
    
    
    private void writeTransitions(Element automataEle, Transition transition)
    {   				
    	//transition basic info
    	Element transitionEle = uppaalDoc.createElement("transition");        			
    	automataEle.appendChild(transitionEle);


    	//transition source
    	Element transitionSourceEle = uppaalDoc.createElement("source");
    	transitionSourceEle.setAttribute("ref", transition.getSource());    				
    	transitionEle.appendChild(transitionSourceEle);


    	//transition target
    	Element transitionTargetEle = uppaalDoc.createElement("target");
    	transitionTargetEle.setAttribute("ref", transition.getTarget());    				
    	transitionEle.appendChild(transitionTargetEle);


    	//transition guard
    	Element transitionGuardEle = uppaalDoc.createElement("label");
    	transitionGuardEle.setAttribute("kind", "guard");
    	//+10  ????????
    	transitionGuardEle.setAttribute("x", String.valueOf(transition.getExpressionPosition().getX() ));
    	transitionGuardEle.setAttribute("y", String.valueOf(transition.getExpressionPosition().getY() ));
    	transitionGuardEle.appendChild(uppaalDoc.createTextNode(transition.getGuard()));    				
    	transitionEle.appendChild(transitionGuardEle);    	


    	//transition assignment
    	Element transitionAssignmentEle = uppaalDoc.createElement("label");
    	transitionAssignmentEle.setAttribute("kind", "assignment");
    	transitionAssignmentEle.setAttribute("x", String.valueOf(transition.getExpressionPosition().getX() ));
    	transitionAssignmentEle.setAttribute("y", String.valueOf(transition.getExpressionPosition().getY() +15));
    	transitionAssignmentEle.appendChild(uppaalDoc.createTextNode(transition.getUpdate()));    				
    	transitionEle.appendChild(transitionAssignmentEle);
    	
    	//transition synchronisation
    	Element transitionSynEle = uppaalDoc.createElement("label");
    	transitionSynEle.setAttribute("kind", "synchronisation");
    	transitionSynEle.setAttribute("x", String.valueOf(transition.getExpressionPosition().getX() ));
    	transitionSynEle.setAttribute("y", String.valueOf(transition.getExpressionPosition().getY() +30));
    	transitionSynEle.appendChild(uppaalDoc.createTextNode(transition.getSynchronisation()));    				
    	transitionEle.appendChild(transitionSynEle);


    	//transition nail (positions)
    	List<Position> nailList = transition.getPositionList();
    	
    	Element transitionNailEle = null;
    	for(int i = 0; i < nailList.size(); i++) {
    		
    		transitionNailEle = uppaalDoc.createElement("nail");
        	transitionNailEle.setAttribute("x", String.valueOf(nailList.get(i).getX() ));
        	transitionNailEle.setAttribute("y", String.valueOf(nailList.get(i).getY() )); 				
        	transitionEle.appendChild(transitionNailEle); 
    	}    	   			
    }

    
    private void writeEventStackDictionary() {
    	
    	String path = uppaalFilePath.substring(0, uppaalFilePath.length()-4) + "_EventDictionary.txt";
    	
    	try {            
    		FileWriter writer = new FileWriter(path, false);
        	writer.write("eventName    eventIndex");
        	writer.write("\r\n");   // write new line
        	
        	Set set = model.getEventStack().getEventTable().entrySet();
    	    Iterator it = set.iterator();
    	    
    	    while (it.hasNext()) {
    	      Map.Entry entry = (Map.Entry) it.next();
    	      
    	      writer.write(entry.getKey() + "    " + entry.getValue());
    	      writer.write("\r\n");   // write new line	      
    	    }
    	    
    	    writer.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    

	public String getUppaalFilePath() {
		return uppaalFilePath;
	}
	public void setUppaalFilePath(String uppaalFilePath) {
		this.uppaalFilePath = uppaalFilePath;
	}
	public Document getUppaalDoc() {
		return uppaalDoc;
	}
	public void setUppaalDoc(Document uppaalDoc) {
		this.uppaalDoc = uppaalDoc;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
}
