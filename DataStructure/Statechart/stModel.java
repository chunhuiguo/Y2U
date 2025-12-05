package Y2U.DataStructure.Statechart;

import java.util.ArrayList;
import java.util.List;

public class stModel {
		
	private List<Statechart> stList;	
	private String declaration;
	private int stNum;
	
	public stModel() {
		stList = new ArrayList<Statechart> ();		
		declaration = "";
		stNum = 0;
	}
	
	public void addStatechart(Statechart st) {
		stList.add(st);
	}

	
	
	
	public List<Statechart> getStList() {
		return stList;
	}
	public void setStList(List<Statechart> stList) {
		this.stList = stList;
	}
	public String getDeclaration() {
		return declaration;
	}
	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}
	public int getStNum() {
		return stNum;
	}
	public void setStNum(int stNum) {
		this.stNum = stNum;
	}	
}
