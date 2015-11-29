import ui.Colour;
public class Node implements Cluster {
	// Contains either Leaf or Cluster
	public Cluster [] clusters;
	private int depth, width;
	private Cluster cluster1, cluster2;
	private Colour colour;

	Node(Cluster cluster1, Cluster cluster2){

		this.cluster1 = cluster1;
		this.cluster2 = cluster2;
		clusters = new Cluster[2];
		clusters[0] = cluster1;
		clusters[1] = cluster2;
		width = 2;
		depth = getDepth();
	}

	public int getDepth(){
		depth = Math.max(cluster1.getDepth(), cluster2.getDepth()); 
		return depth;
	}

	public int getWidth(){
		
		if (cluster1 != null && cluster2 != null){		
			width = cluster1.getWidth() + cluster2.getWidth(); 
			return width;
		} else {
			return 0;	// Should not happen
		}
	}

	public UnitRow getUnits(){

		UnitRow newUnitRow;
		UnitRow unitRow1;
		UnitRow unitRow2;

		if (cluster1 != null && cluster2 != null){
			newUnitRow = new UnitRow(getWidth());
			unitRow1 = cluster1.getUnits();
			unitRow2 = cluster2.getUnits();

			for (int i = 0; i < cluster1.getWidth(); i++){
				newUnitRow.addPredefinedUnit(unitRow1.getUnit(i));
			}
			for (int i = 0; i < cluster2.getWidth(); i++){
				newUnitRow.addPredefinedUnit(unitRow2.getUnit(i));
			}
			return newUnitRow;
		} else {
			return null; // Should not happen
		}
	}
	
	public boolean hasChildren(){
		if (depth > 0){
			return true;
		} else {
			return false;
		}
	}
}


