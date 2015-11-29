import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
public class Dataset{
	
	private File data;
	private Scanner scanner_data;
	private UnitRow unitRow; // Holds all units

	private int numOfClusters; // Numbers of clusters in the end
	private int numOfElements; // tuples, number uf units
	private int numOfVariables; // ex fat_content, protein_content
	
	private String [] variableName;// touple ex: fat, protein ---->
	private String [] elementName;// touple ex: fat, protein ---->
	private String categoryName; // Overall name. ex. flower, animal etc.
	private NumberRow numberRow; // Used temp. holds the numbers of all units

	public void add(Scanner scanner_data){
		this.scanner_data = scanner_data;
	}

	public UnitRow getUnitRow(){
		return unitRow;
	}

	public String getVariableName(int num){
		return variableName[num];
	}

	public String getElementName(int num){
		return elementName[num];
	}

	public int getNumOfElements(){
		return numOfElements;
	}

	public int getNumOfClusters(){
		return numOfClusters;
	}

	public void processDataFile(){

		try {
			Scanner scan = scanner_data;
			numOfClusters = Integer.parseInt(scan.nextLine());
			numOfElements = Integer.parseInt(scan.nextLine());
			numOfVariables  = Integer.parseInt(scan.nextLine());
			
			unitRow = new UnitRow(numOfElements); 
			numberRow = new NumberRow(numOfVariables);
			variableName = new String[numOfVariables];
			elementName = new String[numOfElements];

			double a;
			String unitName;
			String line = scan.nextLine();
			String [] parts = line.split("	");
			categoryName = parts [0];	

			for (int k = 0; k < parts.length-1; k++){
				variableName[k] = parts[k+1];
			}

			for (int i = 0; i < numOfElements; i++){
				line = scan.nextLine();
				parts = null;
				parts = line.split("	"); // Splits line at tab				
				unitName = parts[0];	// Name of unit (ex: cow)
				elementName[i] = parts[0];

				for (int j = 1; j <= numOfVariables; j++){

					a = Double.parseDouble(parts[j]);
					numberRow.add(a);
				}
				unitRow.addUnit(unitName, numOfVariables, numberRow);
				numberRow = null;
				numberRow = new NumberRow(numOfVariables);
			}
			scan.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void normalization(){

		UnitRow normalizedData = null;
		double currentVal = 0;
		double normalizedVal = 0;
		double minVal = 0;
		double maxVal = 0;
		Unit curUnit = null;

		// For each element in each unit in unitrow
		for (int i = 0; i < numOfVariables; i++) {
			
			maxVal = unitRow.getHighestValue(i);
			minVal = unitRow.getLowestValue(i);

			for (int j = 0; j < unitRow.getLength(); j++){
				
				curUnit = unitRow.getUnit(j);
				currentVal = curUnit.getValue(i);
				normalizedVal = (currentVal - minVal) / (maxVal - minVal); 
				curUnit.setValue(i, normalizedVal);
				unitRow.overrideUnit(j, curUnit);	// Overrides old data
			}
		}
		System.out.println("Data Normalized.");
	}

	public void preselection(){

		UnitRow preselectedData = null;
		double currentValue = 0;
		double averageValue = 0;
		double standardDeviation = 0;
		double sum = 0;
		// Temp vars 
		Unit currentUnit = null;
		double lowestSD = Double.MAX_VALUE;
		int varNumber = 0;
		
		//Calculate SD of one unit
		System.out.println("Running preselection (this might take a while)...");
		while (numOfVariables > 50){
			for (int i = 0; i<numOfVariables; i++){ 	// For all variables (categories) in UnitRow

				averageValue = unitRow.getAverageValue(i);	// Find avg value of one category

				for (int j = 0; j < numOfElements; j++){	// For all elements (tuples) in one category
					
					currentUnit = unitRow.getUnit(j);		// Gets first unit from unit row
					currentValue =  currentUnit.getValue(i); // Get unit's first variable value
					sum += (currentValue - averageValue);
					sum = Math.pow(sum, 2);
				}
				// Has gone through all elements in one category. Goes through all elements of one varaiable
				double var = 1/((numOfElements-1)*sum);
				standardDeviation = Math.sqrt(var);
				if (standardDeviation < lowestSD){
					lowestSD = standardDeviation;
					varNumber = i;
				}		
			}
			for (int k = 0; k < numOfElements; k++){
				unitRow.removeVariable(varNumber);	//Remove variable with lowest standard deviation
				variableName[varNumber] = null;
			}
			unitRow.trimAll();	// Removes zeros
			trimArray(); // Makes sure variable matches
			numOfVariables = unitRow.getnumOfVariables();
		}
		System.out.println("Data preselected.");
	}

	public void addUnitRow(UnitRow unitRow){
		this.unitRow = unitRow;
	}

	// Removes nulls. Used for preselection
	public void trimArray(){

		int cnt = 0;
   		for(int i = 0; i < variableName.length;  i++ ){
        	if (variableName[i] != null) { 
            	variableName[cnt++] = variableName[i];
            }
    	}
	    String [] newvariableName = new String[cnt];
	    System.arraycopy(variableName, 0, newvariableName, 0, cnt );
	    variableName = newvariableName;
	}

	//Used for testing
	/* public void printAll(){
		for(int i = 0; i < variableName.length; i++){
			System.out.print(variableName[i] + "	");
		}
		unitRow.printAll();
	} */
}