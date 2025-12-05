package Y2U.DataStructure.Automata;

import java.util.ArrayList;
import java.util.List;

public class Transition {

	public Transition() {
		this.id = "";
		this.source = "";
		this.target = "";
		this.guard = "";
		this.originalGuard = "";
		this.synchronisation = "";
		this.update = "";
		this.priority = 0;
		positionList = new ArrayList<Position>();
		expressionPosition = new Position();
	}
	
	public Transition(String id) {
		this.id = id;
		this.source = "";
		this.target = "";
		this.guard = "";
		this.originalGuard = "";
		this.synchronisation = "";
		this.update = "";
		this.priority = 0;
		positionList = new ArrayList<Position>();
		expressionPosition = new Position();
	}
	
	public Transition(String id, String source, String target) {
		this.id = id;
		this.source = source;
		this.target = target;
		this.guard = "";
		this.originalGuard = "";
		this.synchronisation = "";
		this.update = "";
		this.priority = 0;
		positionList = new ArrayList<Position>();
		expressionPosition = new Position();
	}	
	
	
	public void setExpressionPosition(int x, int y) {
		expressionPosition.setX(x);
		expressionPosition.setY(y);
	}
	
	public void addPosition(int x, int y) {
		positionList.add(new Position(x, y));
	}
	
	public void adjustPosition(double factor) {
		expressionPosition.adjust(factor);
		
		for(int i = 0; i < positionList.size(); i++) {
			Position position = positionList.get(i);
			
			position.adjust(factor);
		}
	}
	
	//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getGuard() {
		return guard;
	}
	public void setGuard(String guard) {
		this.guard = guard;
	}
	public String getSynchronisation() {
		return synchronisation;
	}
	public void setSynchronisation(String synchronisation) {
		this.synchronisation = synchronisation;
	}	
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public List<Position> getPositionList() {
		return positionList;
	}
	public void setPositionList(List<Position> positionList) {
		this.positionList = positionList;
	}
	public Position getExpressionPosition() {
		return expressionPosition;
	}
	public void setExpressionPosition(Position expressionPosition) {
		this.expressionPosition = expressionPosition;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getOriginalGuard() {
		return originalGuard;
	}
	public void setOriginalGuard(String originalGuard) {
		this.originalGuard = originalGuard;
	}





	//
	private String id;
	private String source;
	private String target;
	private String guard;
	private String originalGuard; //in Yakindu statecharts, without added guard conditions according to transition priority
	private String synchronisation;
	private String update;
	private int priority;
	private List<Position> positionList; //transition nails positions
	private Position expressionPosition; //guard/update/synchronisation position
}
