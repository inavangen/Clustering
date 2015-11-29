public class AverageLinkage implements ClusterMethod {

	private UnitRow unitRow2;
	private DistanceMeasure distanceMeasure;

	AverageLinkage(DistanceMeasure distanceMeasure ) {
		this.distanceMeasure = distanceMeasure; 
	}

	public double calculateDistance(Cluster cluster1, Cluster cluster2){

		UnitRow unitRow1 = cluster1.getUnits();
		UnitRow unitRow2 = cluster2.getUnits();
		Unit currentUnit1, currentUnit2;

		int numOfDistances = 0;
		double currentDistance = 0;
		double sum = 0;
		//For all units in unitRow
		for (int i = 0; i < unitRow1.getLength(); i++){
			currentUnit1 = unitRow1.getUnit(i);

			// Compare them to all units in the second unitRow
			for (int j = 0; j < unitRow2.getLength(); j++){
				currentUnit2 = unitRow2.getUnit(j);
				// Finds ditance using selected distance method
				currentDistance = distanceMeasure.calculateDistance(currentUnit1, currentUnit2);
				sum = sum + currentDistance;
				numOfDistances++;
			}
		}
		return sum/numOfDistances;
	}
}