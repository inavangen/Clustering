import ui.DrawUserInterface;
import ui.Colour;
import ui.UserInterfaceFactory;
import ui.properties.*;
import java.awt.Point;

public class Dendrogram implements View {

	private UserInterfaceFactory ui;	
	private DrawUserInterface window;	
	private int windowWidth, windowHeight;

	// Used to get names on x and y axis (variable name)
	private Dataset dataset;
	private ClusterRow clusters;

	private Colour [] colourArray;
	private Colour c_blk;
	private Colour colour;
	private int colour_cnt;

	private int numOfClusters;
	private int numOfLeaves; //total size of a cluster
	private int numOfLevels; // depth of a cluster
	private int heightPerLeaf;
	private int widthPerLevel;
	private int currentY; 
	private int x;
	// Used for drawing the ending lines
	private int end_x;
	private int end_y;

	Dendrogram(Dataset dataset){
		this.dataset = dataset;
		ui = new UserInterfaceFactory();
		c_blk = new Colour(50, 50, 50); // init color

		windowWidth = 500;
		windowHeight = 510;
		window = ui.getDrawUI(windowWidth, windowHeight); // window size
		window.printf("Press any arrow do to clustering...");
	}

	// Methods used by event (to update ui)
	public DrawUserInterface returnui (){
		return window;
	}

	public Colour getRandomColour(){

		int r, g, b;
		r = (int)(Math.round(Math.random() * 255));
		g = (int)(Math.round(Math.random() * 255));
		b = (int)(Math.round(Math.random() * 255));		
		return new Colour(r, g, b);
	}

	public void draw(ClusterRow clusters){
		window.clear();
		this.clusters = clusters;
		Cluster currentCluster = null;
		currentY = windowHeight - heightPerLeaf;
		
		for (int i = 0; i < clusters.getSize(); i++){
		// for all clusters in clusterRow
			colour = getRandomColour(); 
			currentCluster = clusters.getCluster(i);
			numOfLeaves  = currentCluster.getWidth();
			numOfLevels = currentCluster.getDepth();
			heightPerLeaf = 20;
			widthPerLevel = 60;

			drawComponent(currentCluster, currentY);
			// Drawing Ending lines depending on leaf of node
			if(currentCluster instanceof Leaf){
				window.drawLine(x-10, currentY+30, 0, currentY+30, c_blk);	
				window.drawCircle(x-10, currentY+30, 10, 10, getRandomColour(), true);
			} else {
				window.drawLine(end_x, end_y, 0, end_y, c_blk);
				window.drawCircle(end_x, end_y, 10, 10, colour, true);
			}
		}
	}

	public Point drawComponent(Cluster cluster, int y){
		
		Cluster child0, child1;

		if(cluster instanceof Leaf){
			
			x = windowWidth - 80;

			String name = cluster.getUnits().getUnit(0).getName(); 
			window.drawText(x, currentY+4, name, c_blk);

			int resultX = x;
			int resultY = currentY;
			currentY -= heightPerLeaf;
			
			window.showChanges();
			return new Point(resultX, resultY); 

		} else {

			Node node = (Node)cluster;	//Must type convert in order to get children
			child0 = node.clusters[0];
			child1 = node.clusters[1];

			Point p0 = drawComponent(child0, y);
			Point p1 = drawComponent(child1, y+heightPerLeaf);

			int dx = widthPerLevel;
			int vx = Math.min(p0.x-dx, p1.x-dx);
			end_x = vx-10; //For the endling line

			window.drawLine( vx-10, p0.y+10, p0.x-10, p0.y+10, c_blk);	// Horizontal line
			window.drawLine( vx-10, p1.y+10, p1.x-10, p1.y+10, c_blk);	// Horizontal line
			window.drawLine( vx-10, p0.y+10, vx-10, p1.y+10, c_blk);	// Vertical line
			
			window.drawCircle(p0.x-10, p0.y+10, 10, 10, colour, true); 
			window.drawCircle(p1.x-10, p1.y+10, 10, 10, colour, true);
			end_y = ((p0.y+p1.y)/2)+10; // Used for ending line

			Point p = new Point(vx, p0.y+(p1.y - p0.y)/2);
			window.showChanges();
			return p;
		}
	}
}
