public class CompleteLinkage implements ClusterMethod {

	private DistanceMeasure distanceMeasure;

	CompleteLinkage(DistanceMeasure distanceMeasure){
		this.distanceMeasure = distanceMeasure;
	}

	// Finds the two clusters with the lowest distance
	public double calculateDistance(Cluster cluster1, Cluster cluster2){

		UnitRow unitRow1 = cluster1.getUnits();
		UnitRow unitRow2 = cluster2.getUnits();
		Unit currentUnit1, currentUnit2;

		double highestDistance = 0; 
		double currentDistance = 0;

		//For each unit in first unitRow
		for (int i = 0; i < unitRow1.getLength(); i++){
			currentUnit1 = unitRow1.getUnit(i);
			// Compare them to all units in the second unitRow
			for (int j = 0; j < unitRow2.getLength(); j++){
				currentUnit2 = unitRow2.getUnit(j);
				currentDistance = distanceMeasure.calculateDistance(currentUnit1, currentUnit2);
				if (currentDistance > highestDistance){
					highestDistance = currentDistance;
				}
			}
		}
		return highestDistance;
	}
}