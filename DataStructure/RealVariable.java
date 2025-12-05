package Y2U.DataStructure;

public class RealVariable {
	
	private String name;
	//use to integer to represent a real number (int part and fraction part)
	private String integerPartName; //realName_integer
	private String fractionPartName; //realName_fraction
	private int integerValue; 
	private int fractionValue; 
	
	public RealVariable(String name) {
		super();
		this.name = name;
		
		integerPartName = name + "_integer";
		fractionPartName = name + "_fraction";
		
		integerValue = 0;
		fractionValue = 0;
	}
	
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntegerPartName() {
		return integerPartName;
	}
	public void setIntegerPartName(String integerPartName) {
		this.integerPartName = integerPartName;
	}
	public String getFractionPartName() {
		return fractionPartName;
	}
	public void setFractionPartName(String fractionPartName) {
		this.fractionPartName = fractionPartName;
	}
	public int getIntegerValue() {
		return integerValue;
	}
	public void setIntegerValue(int integerValue) {
		this.integerValue = integerValue;
	}
	public int getFractionValue() {
		return fractionValue;
	}
	public void setFractionValue(int fractionValue) {
		this.fractionValue = fractionValue;
	}	
}
