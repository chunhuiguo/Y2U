package Y2U.Transformer;

import org.w3c.dom.Element;

//import Y2U.DataStructure.Event;
import Y2U.DataStructure.Model;

public class VariableTransformer extends ElementTransformer {
	
	private String variableInfo;

	public VariableTransformer(Model model, String variableInfo) {
		super(model);
		this.variableInfo = variableInfo;
	}
	
	
	
	public void transform() {	

		String[] declarationList = variableInfo.split("\n");

		int beginIndex = 0;
		int endIndex = 0;
		String varName = "";
		String varType = "";
		String interfaceName = "";
		for(int i = 0; i < declarationList.length; i++){

			declarationList[i].trim();
			if(declarationList[i].length() == 0)
				continue;
			if(declarationList[i].startsWith("//"))
				continue;

			if(declarationList[i].contains("interface"))
			{
				beginIndex = 10;
				endIndex = declarationList[i].indexOf(":");

				if(beginIndex < endIndex)
					interfaceName = declarationList[i].substring(beginIndex, endIndex).trim();
				else
					interfaceName = "";
			}

			if(declarationList[i].contains("var"))
			{
				beginIndex = 4;
				endIndex = declarationList[i].indexOf(":"); 
				varName = declarationList[i].substring(beginIndex, endIndex).trim();

				if(interfaceName.length() > 0)
					varName = interfaceName + "_" + varName;

				beginIndex = declarationList[i].indexOf(":") + 1;
				endIndex = declarationList[i].length();
				if(declarationList[i].contains("="))
				{
					endIndex = declarationList[i].indexOf("=");
				}
				varType = declarationList[i].substring(beginIndex, endIndex).trim();

				switch(varType) {
				case "integer": varType = "int"; break;				
				case "boolean": varType = "bool"; break;
				case "string": varType = ""; model.addStringVariable(varName); break;
				case "real": varType = ""; model.addRealVariable(varName); break;
				default: varType = ""; break;
				}

				if(! varType.equals(""))
					model.addDeclaration(varType + " " + varName + ";");
			}

			//Event event = null;
			if(declarationList[i].contains("event"))
			{
				if(declarationList[i].contains("in event"))
					beginIndex = 9;
				else if(declarationList[i].contains("out event"))
					beginIndex = 10;
				else
					beginIndex = 6;

				endIndex = declarationList[i].length();
				varName = declarationList[i].substring(beginIndex, endIndex).trim();
				if(interfaceName.length() > 0)
					varName = interfaceName + "_" + varName;
				//varType = "chan";

				//model.addDeclaration(varType + " " + varName + ";");

				//event stack
				int eventIndex = model.getEventStack().addEventToTable(varName);
				//event automata
				//event = new Event(varName, eventIndex);
				//model.addEvent(event);
				
			}
		}
	}
	
	
	

	public String getVariableInfo() {
		return variableInfo;
	}
	public void setVariableInfo(String variableInfo) {
		this.variableInfo = variableInfo;
	}

}
