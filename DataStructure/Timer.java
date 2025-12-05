package Y2U.DataStructure;

import java.util.Hashtable;

public class Timer extends Automata {
	
	protected int time;
	protected String timerName; //chan timerName
	protected String timeUnit;
	

	public Timer(String name, int time, String timeUnit) {
		super(name + "Timer");	
		
		this.time = time;
		timerName = name;
		
		this.timeUnit = timeUnit;
	}

	//update the time value according to timeUnit
	public void updateTimeByUnit(String timeUnit, Hashtable<String, Integer> timeUnitList) {
		if(! this.timeUnit.equals(timeUnit)) {
			time = (int) (time * Math.pow(10, timeUnitList.get(this.timeUnit) - timeUnitList.get(timeUnit)));
			this.timeUnit = timeUnit;
		}
	}


	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getTimerName() {
		return timerName;
	}
	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}
	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}	
}
