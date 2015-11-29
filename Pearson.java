public class Pearson implements DistanceMeasure {

	public double calculateDistance(Unit unit1, Unit unit2){

		int numOfVariables = unit1.getNumOfVariables();
		double sum = 0;

		double u1_currentVal = 0;
		double u1_avgVal = calculateAverageValue(unit1);
		double u1_sd = calculateStandardDeviation(unit1);

		double u2_currentVal = 0;
		double u2_avgVal = calculateAverageValue(unit2);
		double u2_sd = calculateStandardDeviation(unit2);
		// For all variables in one unit
		for (int i = 0; i < unit1.getNumOfVariables(); i++){

			u1_currentVal = unit1.getValue(i);
			u2_currentVal = unit2.getValue(i);
			double unit1_val = ((u1_currentVal- u1_avgVal)/u1_sd);
			double unit2_val = ((u2_currentVal- u2_avgVal)/u2_sd);  
			sum = sum + (unit1_val * unit2_val);
		}
		return (1-((1/numOfVariables-1) * sum));	
	} 

	// Calculates SD of one unit's variables
	public double calculateStandardDeviation(Unit unit){

		double sum = 0;
		double currentValue = 0;
		double averageValue = calculateAverageValue(unit);
		int numOfVariables = unit.getNumOfVariables();

		for (int i = 0; i < numOfVariables; i++){		
			currentValue = unit.getValue(i);
			sum += (currentValue - averageValue);
			sum = Math.pow(sum, 2);
		}
		
		double var = 1/((numOfVariables-1)*sum);
		return Math.sqrt(var);
	}

	// Calculates avgerage value of one unit's variables
	public double calculateAverageValue(Unit unit){

		int numOfVariables = unit.getNumOfVariables();
		double sum = 0;		
		// Adds all variables in one sum
		for (int i = 0; i < numOfVariables; i++){
			sum = sum + unit.getValue(i);
		}
		// Divide by numofvariables to get avg value
		return (sum/numOfVariables);
	}
}