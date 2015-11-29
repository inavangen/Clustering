public class Unit {
	
	private String name;
	private int numOfVariables;
	private NumberRow numberRow;

	Unit (String name, int numOfVariables, NumberRow numberRow){
		this.name = name;
		this.numOfVariables = numOfVariables;
		this.numberRow = numberRow;
	}

	public double getValue(int fromVarNum){
		return numberRow.getValue(fromVarNum);
	}

	public String getName(){
		return name;
	}

	public void setValue(int toVarNum, double newValue){
		numberRow.setValue(toVarNum, newValue);
	}

	public double getHighestNumber(){
		return numberRow.getHighestNumber();
	}

	public int getNumOfVariables(){
		return numOfVariables;
	}

	public void removeVariable(int variableNum){
		numberRow.removeVariable(variableNum);
	}

	// Removes 0s. Used for preselection
	public void trimArray(){
		numberRow.trimArray();
		numOfVariables = numberRow.getSize();
	}

	// Used for testing
	/* public void printAll(){
		numberRow.printAll();
	} */
}
