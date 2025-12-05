package Y2U.DataStructure.Automata;

public class State {
	
	public State() {
		this.id = "";
		this.name = "";
		this.invariant = "";
		position = new Position();
	}
	
	public State(String id) {
		this.id = id;
		this.name = "";
		this.invariant = "";
		position = new Position();
	}
	
	public State(String id, String name) {
		this.id = id;
		this.name = name;
		this.invariant = "";
		position = new Position();
	}
	
	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}
	
	public void adjustPosition(double factor) {
		position.adjust(factor);
	}
	

	//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInvariant() {
		return invariant;
	}
	public void setInvariant(String invariant) {
		this.invariant = invariant;
	}
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}






	//
	private String id;
	private String name;
	private String invariant;
	private Position position;
}
