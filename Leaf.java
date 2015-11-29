public class Leaf implements Cluster{
	// Holds single element
	private UnitRow unitRow; 
	private int depth;
	private int width;

	Leaf(Unit unit){
		unitRow = new UnitRow(1);
		unitRow.addPredefinedUnit(unit);
		depth = 0; 
		width = 1; 
	}	

	public int getDepth(){
		return depth;
	}

	public int getWidth(){
		return width;
	}
	public UnitRow getUnits(){
		return unitRow;
	}
	public boolean hasChildren(){
		return false;
	}
}
