import java.util.*;
import java.io.*;
import ui.DrawUserInterface;
import ui.Colour;
import ui.UIAuxiliaryMethods;
import ui.UserInterfaceFactory;
import ui.properties.*;
import ui.Event;

public class Clustering {
	
	private Dataset dataset;// all info from file
	private ClusterRow clusterRow;
	private int numOfClusters; // Num of clusters after clustering is done

	private Clusterer clusterer;
	private Scanner scanner;

	private UIAuxiliaryMethods aux;	
	private DrawUserInterface ui;
	private String styleMeasurement;
	private String styleClustering;
	private String styleGraph;

	private DistanceMeasure distanceMeasure;
	private ClusterMethod clusterMethod;
	private View view;


	public static void main (String [] args){

		Clustering c = new Clustering();
		c.readFile();
		c.askForUserInput();
		c.startClustering();
	}

	public void readFile (){
	//Method used for real file input
		aux = new UIAuxiliaryMethods();
		aux.askUserForInput();
		scanner = new Scanner(System.in);
	}

	public void askForUserInput(){
		// Ask which measure methods user wants
		styleMeasurement = aux.askUserForChoice("Choose distance measurement:", "Euclidean", "Manhattan", "Pearson");
		styleClustering = aux.askUserForChoice("Choose clustering method:", "Single Linkage", "Complete Linkage", "Average Linkage");
		styleGraph = aux.askUserForChoice("Choose graph style:", "Cartesian", "Dendrogram");
		System.out.println("Chocies made: " + styleMeasurement + " + " + styleClustering + " + " + styleGraph);		
	}

	public void initializeMethods(){
		// Initialize distance measurement
		if (styleMeasurement.equalsIgnoreCase("Euclidean")){
			distanceMeasure = new Euclidean();
		} else if (styleMeasurement.equalsIgnoreCase("Manhattan")){
			distanceMeasure = new Manhattan();
		} else {
			distanceMeasure = new Pearson();			
		}
		// Initialize clustering method
		if (styleClustering.equalsIgnoreCase("Single Linkage")){
			clusterMethod = new SingleLinkage(distanceMeasure);
		} else if (styleClustering.equalsIgnoreCase("Complete Linkage")){
			clusterMethod = new CompleteLinkage(distanceMeasure);
		} else {
			clusterMethod = new AverageLinkage(distanceMeasure);
		}
		// Initialize View
		if(styleGraph.equalsIgnoreCase("Cartesian")){
			view = new Cartesian(dataset);
		} else {
			view = new Dendrogram(dataset);
		}
	}

	// Start the clustering process
	public void startClustering (){

		dataset = new Dataset();
		dataset.add(scanner);
		dataset.processDataFile();
		numOfClusters = dataset.getNumOfClusters();
		
		dataset.normalization();
		dataset.preselection();
		initializeMethods();
		
		clusterRow = new ClusterRow(dataset);
		clusterer = new Clusterer(clusterRow, clusterMethod);		
				
		// Need to do this type converting in order to be able to return ui
		if(view instanceof Cartesian){
			Cartesian d = (Cartesian)view;
			ui = d.returnui();
		} else {
			Dendrogram c = (Dendrogram)view;
			ui = c.returnui();
		}
		// Handles events and user input
		while(clusterRow.getSize() > numOfClusters){
			ui.getEvent();
			Event event = new Event("arrow", doClustering(view));
		}
	}
	
	public String doClustering(View view){
		if(clusterRow.getSize() > numOfClusters){
			clusterer.initClustering();
			view.draw(clusterRow);
		} else {
			System.out.println("Reached the desired number of clusters!");			
		}
		return "OK";
	}
}