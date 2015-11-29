import java.util.ArrayList;

public class ClusterRow {
	// Holds a number of Clusters 
	private Dataset data;
	private ArrayList<Cluster> clusters;

	ClusterRow(Dataset data){
		this.data = data;
		clusters = new ArrayList<Cluster>();
		initClusters();
	}

	ClusterRow(){
	// Used in clusterer-class. New ClusterRow to hold new clusters
		clusters = new ArrayList<Cluster>();
	}

	ClusterRow(Cluster cluster){
		clusters = new ArrayList<Cluster>();
		add(cluster);
	}

	public Cluster getCluster(int num){
		return clusters.get(num);
	}

	public void remove(Cluster c){
		clusters.remove(c);
	}

	public void add(Cluster cluster){
		clusters.add(cluster);
	}

	public void initClusters(){
	// Initializes new clusters by making Leaf-clusters
		UnitRow unitRow = data.getUnitRow();
		Unit currentUnit = null;
		Leaf leaf = null;

		for (int i = 0; i < unitRow.getLength(); i++){
			currentUnit = unitRow.getUnit(i);
			leaf = new Leaf(currentUnit);
			clusters.add(leaf);
		}
		System.out.println("Clusters initialized.");
	}

	public int getSize(){
		return clusters.size();
	}
/*
	//Test method for assignment 3
	public void printMaxValueFromCluster(int num){
		Cluster tempClus = clusters.get(num);
		UnitRow tempUR = tempClus.getUnits();
		Unit tempUnit = tempUR.getUnit(0);
		System.out.print("Highest val from first cluster: ");
		System.out.println(tempUnit.getHighestNumber());
	}

	// Test method for assignment 4
	public void calculateDistance(){
		Cluster c1 = clusters.get(0);
		Cluster c2 = clusters.get(1);

		SingleLinkage s1 = new SingleLinkage(euclidean);
		SingleLinkage s2 = new SingleLinkage(manhattan);
		SingleLinkage s3 = new SingleLinkage(pearson);

		CompleteLinkage cl1 = new CompleteLinkage(euclidean);
		CompleteLinkage cl2 = new CompleteLinkage(manhattan);
		CompleteLinkage cl3 = new CompleteLinkage(pearson);

		AverageLinkage a1 = new AverageLinkage(euclidean);
		AverageLinkage a2 = new AverageLinkage(manhattan);
		AverageLinkage a3 = new AverageLinkage(pearson);

		System.out.println("Printing out distances :");
		// EUC
		double d1 = s1.calculateDistance(c1, c2);
		double d4 = cl1.calculateDistance(c1, c2);
		double d7 = a1.calculateDistance(c1, c2);

		// MAN
		double d2 = s2.calculateDistance(c1, c2);
		double d5 = cl2.calculateDistance(c1, c2);
		double d8 = a2.calculateDistance(c1, c2);

		// PEAR
		double d3 = s3.calculateDistance(c1, c2);
		double d6 = cl3.calculateDistance(c1, c2);
		double d9 = a3.calculateDistance(c1, c2);
	}
	*/
}
