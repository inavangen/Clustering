public class NumberRow {

	private double [] numbers;
	private int count;

	NumberRow(int numOfVariables){

		numbers = new double[numOfVariables];
		count = 0;
	}

	public void add (double num){
		numbers[count++] = num;
	}

	public double getValue(int fromVarNum){
		return numbers[fromVarNum];
	}

	public void setValue (int toVarNum, double newValue){
		numbers[toVarNum] = newValue;
	}

	public int getSize(){
		return numbers.length;
	}

	public double getHighestNumber(){
		double highestValue = 0;
		for (int i = 0; i< numbers.length; i++){
			if(numbers[i] > highestValue){
				highestValue = numbers[i];
			}
		}
		return highestValue;
	}

	// Removes zeros (used in preselection)
	public void trimArray(){

		int count = 0;
   		for(int i = 0; i < numbers.length;  i++ ){
        	if (numbers[i] != Double.MAX_VALUE){ //make sure this is a number that can never happen 'normally'
            	numbers[count++] = numbers[i];
            }
    	}
	    double [] newNumbers = new double[count];
	    System.arraycopy(numbers, 0, newNumbers, 0, count );
	    numbers = newNumbers;
	}

	public void removeVariable(int variableNum){
		numbers[variableNum] = Double.MAX_VALUE; //make sure this is a number that can never happen 'normally'
	}

	// Used in testing
	public void printAll(){
		for(int i = 0; i<numbers.length; i++){
			System.out.print(numbers[i] + "	");
		}
	}
}