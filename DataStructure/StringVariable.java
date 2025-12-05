package Y2U.DataStructure;

import java.util.Hashtable;

public class StringVariable {

	private String name;		
	//use int to represent string value
	private Hashtable<String, Integer> dictionary;
	
	public StringVariable(String name) {
		super();
		this.name = name;
		
		dictionary = new Hashtable<String, Integer>();
	}
	
	
	//add a dictionary record of string value, and return its corresponding int
	public int addStringValue(String strValue) {
		
		dictionary.put(strValue, new Integer(dictionary.size()+1));
		
		return dictionary.size();
	}
	
	//find the int value of corresponding string value
	public Integer findStringIntValue(String strValue) {
		return dictionary.get(strValue);
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Hashtable<String, Integer> getDictionary() {
		return dictionary;
	}
	public void setDictionary(Hashtable<String, Integer> dictionary) {
		this.dictionary = dictionary;
	}	
}
