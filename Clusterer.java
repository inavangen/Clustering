public class Clusterer {
// Does the whoe step-by-step cluster process

	private	ClusterRow clusterRow;
	private	ClusterMethod clusterMethod;
	private	Cluster cluster1, cluster2, cluster1_lowest, cluster2_lowest,  cluster_previous;

	private	int count;
	private	double distance, distance_lowest;


	Clusterer(ClusterRow clusterRow, ClusterMethod clusterMethod){
		this.clusterRow = clusterRow;
		this.clusterMethod = clusterMethod;
		cluster_previous = null;
		count = 0;
	}

	public void initClustering(){

		cluster1 = null;
		cluster2 = null;
		cluster1_lowest = null;
		cluster2_lowest = null;
		distance = 0;
		distance_lowest = Double.MAX_VALUE;

		// For all clusters in clusterRow, calculate ditance between them
		for (int i = 0; i < clusterRow.getSize(); i++){
			
			cluster1 = clusterRow.getCluster(i);

			for (int j = 0; j < clusterRow.getSize(); j++){

				cluster2 = clusterRow.getCluster(j);
				// Checks if it is trying to compare the same cluster
				if(cluster1 != cluster2 && cluster1 != null && cluster2 != null && cluster1 != cluster_previous && cluster2 != cluster_previous){
					// Returns the highest ditance in in those clusters
					distance = clusterMethod.calculateDistance(cluster1, cluster2);
					// Checks if current distance is lower than the lowest so far. if so, save it.
					if(distance < distance_lowest){
						distance_lowest = distance;
						cluster1_lowest = cluster1;
						cluster2_lowest = cluster2;
					}
				}
			}
		}
		System.out.println("Does one step of clustering...");
		merge(cluster1_lowest, cluster2_lowest);
		count++;
		distance = Double.MAX_VALUE;
	}

	public void merge(Cluster cluster1, Cluster cluster2){
	// Create new node with the two clusters
		Cluster newNode = new Node(cluster1, cluster2);
		clusterRow.remove(cluster1);
		clusterRow.remove(cluster2);
		clusterRow.add(newNode);
		cluster_previous = newNode;
	}
}