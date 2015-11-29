public class UnitRow {

	private Unit [] units;
	private int cnt;

	UnitRow (int numOfElements){

		units = new Unit [numOfElements];
		cnt = 0;
	}

	public void addUnit(String name, int numOfVariables, NumberRow numberRow){
		units [cnt++] = new Unit(name, numOfVariables, numberRow);
	}

	public void addPredefinedUnit(Unit unit){
		units[cnt++] = unit;
	}

	public int getLength(){
		return units.length;
	}

	public Unit getUnit(int number){
		return units[number];
	}

	public int getnumOfVariables(){
		return units[0].getNumOfVariables();
	}

	public void overrideUnit(int unitNum, Unit newUnit){
		units[unitNum] = newUnit;
	}

	public double getHighestValue(int variableNum){

		double highestVal = 0;
		for(int i = 0; i < units.length; i++){
			if (units[i].getValue(variableNum) > highestVal){
				highestVal = units[i].getValue(variableNum);
			}
		}
		return highestVal;
	}

	public double getLowestValue(int variableNum){

		double lowestVal = 0;
		for(int i = 0; i < units.length; i++){
			if (units[i].getValue(variableNum) < lowestVal){
				lowestVal = units[i].getValue(variableNum);
			}
		}
		return lowestVal;
	}

	public double getAverageValue(int variableNum){

		double avgVal = 0;
		double sum = 0;
		double numOfUnits = units.length;
		for(int i = 0; i < numOfUnits; i++){
			sum += units[i].getValue(variableNum);
		}
		avgVal = sum/numOfUnits;
		return avgVal;
	}

	public void removeVariable(int variableNum){
		// Used in preselection. Removes one spesific variable form all units
		for (int i = 0; i < units.length; i++){
			units[i].removeVariable(variableNum);
		}
	}

	// Used in preselection
	public void trimAll(){
		for (int i = 0; i < units.length; i++){
			units[i].trimArray();
		}
	}

	// Used for testing
	/* public void printAll(){
		for(int i = 0; i < units.length; i++){
			units[i].printAll();
			System.out.println("");
		}
	} */
}
