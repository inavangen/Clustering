public class Manhattan implements DistanceMeasure {
	
	public double calculateDistance(Unit unit1, Unit unit2){
		
		int numOfVariables = unit1.getNumOfVariables();
		double sum = 0;
		double unit1_var = 0;
		double unit2_var = 0;

		for (int i = 0; i < numOfVariables; i++){
			unit1_var = unit1.getValue(i);
			unit2_var = unit2.getValue(i);
			sum +=  Math.abs((unit1_var - unit2_var));
 		}
		return sum;
	} 
}