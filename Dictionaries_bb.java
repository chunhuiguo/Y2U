package Y2U;

import java.util.Hashtable;

import Y2U.DataStructure.StringVariable;

public class Dictionaries_bb {
	
	private Hashtable<String, StringVariable> dics;

	public Dictionaries_bb() {
		super();
		dics = new Hashtable<String, StringVariable>();
	}
	
	
	public StringVariable addDictionary(String dictionaryName) {
		StringVariable dictionary = new StringVariable(dictionaryName);
		dics.put(dictionaryName, dictionary);
		
		return dictionary;
	}
	
	public StringVariable findDictionary(String dictionaryName) {
		return dics.get(dictionaryName);
	}
	

	public Hashtable<String, StringVariable> getDics() {
		return dics;
	}
	public void setDics(Hashtable<String, StringVariable> dics) {
		this.dics = dics;
	}	
}
