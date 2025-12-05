package Y2U.DataStructure;

public class Position {

	private int x;
	private int y;
	
	
	public Position() {
		super();
		this.x = 0;
		this.y = 0;
	}
	
	
	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public void adjust(double factor) {
		//x = (int) Math.round(x * 1) + 300;
		x = (int) Math.round(x * factor) + 100;
		y = (int) Math.round(y * factor);
	}
	
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
