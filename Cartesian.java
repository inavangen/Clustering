import ui.DrawUserInterface;
import ui.Colour;
import ui.UserInterfaceFactory;
import ui.properties.*;

public class Cartesian implements View {

	private UserInterfaceFactory ui;	
	private DrawUserInterface window;	
	private Dataset dataset;

	private Cluster currentCluster;
	private UnitRow currentUnitRow;
	private Unit currentUnit;

	private Cluster prevCluster;
	private UnitRow prevUnitRow;
	private Unit prevUnit;	

	private double x;
	private double y;
	private int x_coord;	// real coordinates
	private int y_coord;	// real coordinates

	private Colour [] colourArray;
	private Colour c_blk;
	private Colour colour;
	private int colour_cnt;

	private ClusterRow clusters;
	private int cnt;

	private int [] coordsX;
	private int [] coordsY;

	Cartesian(Dataset dataset){
		this.dataset = dataset;
		ui = new UserInterfaceFactory();
		window = ui.getDrawUI(500, 500);
		c_blk = new Colour(50, 50, 50); 
		window.printf("Press any key do to clustering...");

	}
	public DrawUserInterface returnui (){
	// Methods used by event (to update ui)
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

		window.drawLine( 20, 20, 20, 480, c_blk); 	// Vertical line	
		window.drawLine( 20, 20, 480, 20, c_blk);	// Horizontal line
		window.drawText(380, 5, dataset.getVariableName(0), c_blk);	
		window.drawText(5, 485, dataset.getVariableName(1), c_blk);		

		for (int i = 0; i < clusters.getSize(); i++){
			currentCluster = clusters.getCluster(i);
			currentUnitRow = currentCluster.getUnits();

			// This is used for drawin transparent circles
			coordsX = new int [currentCluster.getWidth()];
			coordsY = new int [currentCluster.getWidth()];
			cnt = 0;

			colour = getRandomColour();

			if (clusters.getCluster(i) instanceof Leaf){
				drawLeaf();
			} else {
				drawNode();
			}
			drawTransparentCircles();
			coordsX = null;		// Reset coords
			coordsY = null;		
		}
	}

	public void drawLeaf(){
		// Draw as un clustered
		currentUnit = currentUnitRow.getUnit(0);
		// Now that you have one unit, (with 2 variables) calculate x and y cords
		x = currentUnit.getValue(0);
		y = currentUnit.getValue(1);
		// Since number varies from 0-1 they need to be calculated to fit window size
		x = 20+(x*460);
		y = 20+(y*460);
		x_coord = (int)x;
		y_coord = (int)y;
	
		window.drawCircle(x_coord, y_coord, 10, 10, getRandomColour(), true);
		// Save the cords in the coords array
		coordsX[cnt] = x_coord;
		coordsY[cnt] = y_coord;
		cnt++;
	}

	public void drawNode(){

		int sumX = 0;
		int sumY = 0;

		// For all units in a cluster
		for (int j = 0; j < currentCluster.getWidth(); j++){
			currentUnit = currentUnitRow.getUnit(j);

			x = currentUnit.getValue(0);
			y = currentUnit.getValue(1);
			x = 20+(x*460);
			y = 20+(y*460);
			x_coord = (int)x; 
			y_coord = (int)y;
			sumX = sumX + x_coord;
			sumY = sumY + y_coord;

			window.drawCircle(x_coord, y_coord, 10, 10, colour, true);
			window.showChanges();
			// Save the cords in the coords array
			coordsX[cnt] = x_coord;
			coordsY[cnt] = y_coord;
			cnt++;
		}	
	}

	public void drawTransparentCircles(){
		
		int sumX = 0;
		int sumY = 0;
		int new_x_coord = 0;
		int new_y_coord = 0;

		Cluster currentCluster = null;
		currentUnitRow = null;
		currentUnit = null;

		for (int i = 0; i < clusters.getSize(); i++){
			
			currentCluster = clusters.getCluster(i); 

			// This is used for drawin transparent circles
			int [] coordsX = new int [currentCluster.getWidth()];
			int [] coordsY = new int [currentCluster.getWidth()];
			int cnt = 0;

			// Only draw circles (only) for nodes
			if (currentCluster instanceof Node){

				for (int j = 0; j < currentCluster.getWidth(); j++){
					currentUnitRow = currentCluster.getUnits();
					currentUnit = currentUnitRow.getUnit(j);
					x = currentUnit.getValue(0);
					y = currentUnit.getValue(1);
					x = 20+(x*460);
					y = 20+(y*460);
					x_coord = (int)x;
					y_coord = (int)y;

					sumX = sumX + x_coord;
					sumY = sumY + y_coord;
					// Save the cords in the coords array (represent coordinates of all clusters)
					coordsX[cnt] = x_coord;
					coordsY[cnt] = y_coord;
					cnt++;				
				}
				new_x_coord = (sumX/currentCluster.getWidth());
				new_y_coord = (sumY/currentCluster.getWidth());

				int circleSize = getCircleSize(new_y_coord, coordsX, coordsY, x_coord, y_coord);

				window.drawCircle(new_x_coord, new_y_coord, circleSize, circleSize, c_blk, false);
				sumX = 0;
				sumY = 0;
			}
		}
	}

	public int getCircleSize(int new_y_coord, int [] coordsX, int [] coordsY, int x_coord, int y_coord){
	// Need to find right size of the circles
		double var1 = Math.pow(new_y_coord, 2); // square of Y
		double var2 = 0;
		double var3 = 0;
		int sum = 0;
		double longestDistance = 0;
		int mostDistantPoint = -1;
		// For all coords ( of the cluster clusters), check which is furthest away from		
		for (int k = 0; k < coordsX.length; k++){
		    double distanceSquared = Math.sqrt(Math.pow(coordsX[k] - x_coord, 2) + Math.pow(coordsY[k] - y_coord, 2));
		    if (distanceSquared > longestDistance){
		        longestDistance = distanceSquared;
		        mostDistantPoint = k;
		    }
		}
		return (int)longestDistance*2;
	}
}