package Y2U.DataStructure;

import java.util.Hashtable;

public class EventStack {

	private int eventNum;
	private Hashtable<String, Integer> eventTable;
	
		
	
	public EventStack() {
		super();
		
		eventNum = 0;
		
		eventTable = new Hashtable<String, Integer>();
	}
	
	
	public int addEventToTable(String eventName) {
		eventNum++;
		eventTable.put(eventName, eventNum);
		
		return eventNum;
	}
	
	public int findEventIndex(String eventName) {
		Integer eventIndex = eventTable.get(eventName);
		
		if(eventIndex == null)	{
			return 0;
		}
		
		return eventIndex.intValue();
	}
	
	
	
	
	
	public int getEventNum() {
		return eventNum;
	}
	public void setEventNum(int eventNum) {
		this.eventNum = eventNum;
	}
	public Hashtable<String, Integer> getEventTable() {
		return eventTable;
	}
	public void setEventTable(Hashtable<String, Integer> eventTable) {
		this.eventTable = eventTable;
	}
	
	
}
