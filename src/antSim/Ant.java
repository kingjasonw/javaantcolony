package antSim;

import java.util.ArrayList;

// Ant class
// define attributes unique to all ant types
// won't ever create type Ant
public class Ant {
	// data fields
	static int noAnts = 1;
	private int ID;
	int lifeSpan = 3650;
	int daysAlive = 0;
	int turnsAlive = 0;
	boolean moved = false;
	boolean isAlive = true;
	Colony colony;
	ColonyNode currentLocation;
	ColonyNode nextLocation;
	ColonyNode prevLocation;
	ArrayList<ColonyNode> adjacent = new ArrayList<ColonyNode>();
	
	
	public void setID(int ID) {
		this.ID = ID;
		noAnts++;
	}
	
	public void move(ColonyNode node) {
		nextLocation.antsToAdd.add(this);
		currentLocation.antsToRemove.add(this);
		prevLocation = currentLocation;
		currentLocation = nextLocation;
		moved = true;
	}
	
	// find adjacent open squares 
	public void findAdjacentSquares() {
		// clear values from last call
		 adjacent.clear();
		 int curX = currentLocation.x;
		 int curY = currentLocation.y;
		 // left one, down one
		 if (curX - 1 > -1 && curY + 1 < 27 && colony.grid[curX - 1][curY + 1].opened) {
			 adjacent.add(colony.grid[curX - 1][curY + 1]);
		 }
		 // down one
		 if (curY + 1 < 27 && colony.grid[curX][curY + 1].opened) {
			 adjacent.add(colony.grid[curX][curY + 1]);
		 }
		 // right one, down one
		 if (curX + 1 < 27 && curY + 1 < 27 && colony.grid[curX + 1][curY + 1].opened) {
			 adjacent.add(colony.grid[curX + 1][curY + 1]);
		 }
		 // left one
		 if (curX - 1 > -1 && colony.grid[curX - 1][curY].opened) {
			 adjacent.add(colony.grid[curX - 1][curY]);
		 }
		 // right one
		 if (curX + 1 < 27 && colony.grid[curX + 1][curY].opened) {
			 adjacent.add(colony.grid[curX + 1][curY]);
		 }
		 // left one, up one
		 if (curX - 1 > -1 && curY - 1 > -1 && colony.grid[curX - 1][curY - 1].opened) {
			 adjacent.add(colony.grid[curX - 1][curY - 1]);
		 }
		 // up one
		 if (curY - 1 > - 1 && colony.grid[curX][curY - 1].opened) {
			 adjacent.add(colony.grid[curX][curY - 1]);
		 }
		 // right one, up one
		 if (curX + 1 < 27 && curY - 1 > -1 && colony.grid[curX + 1][curY - 1].opened) {
			 adjacent.add(colony.grid[curX + 1][curY - 1]);
		 }
	}
	
	public void findAdjacentSquaresScout() {
		// clear values from last call
		 adjacent.clear();
		 int curX = currentLocation.x;
		 int curY = currentLocation.y;
		 // left one, down one
		 if (curX - 1 > -1 && curY + 1 < 27) {
			 adjacent.add(colony.grid[curX - 1][curY + 1]);
		 }
		 // down one
		 if (curY + 1 < 27) {
			 adjacent.add(colony.grid[curX][curY + 1]);
		 }
		 // right one, down one
		 if (curX + 1 < 27 && curY + 1 < 27) {
			 adjacent.add(colony.grid[curX + 1][curY + 1]);
		 }
		 // left one
		 if (curX - 1 > -1) {
			 adjacent.add(colony.grid[curX - 1][curY]);
		 }
		 // right one
		 if (curX + 1 < 27) {
			 adjacent.add(colony.grid[curX + 1][curY]);
		 }
		 // left one, up one
		 if (curX - 1 > -1 && curY - 1 > -1) {
			 adjacent.add(colony.grid[curX - 1][curY - 1]);
		 }
		 // up one
		 if (curY - 1 > - 1) {
			 adjacent.add(colony.grid[curX][curY - 1]);
		 }
		 // right one, up one
		 if (curX + 1 < 27 && curY - 1 > -1) {
			 adjacent.add(colony.grid[curX + 1][curY - 1]);
		 }
	}
	
	public void takeTurn(int turnNo) {
		
	}
	
	public void age(int turnNo) {
		this.turnsAlive++;
		if (turnsAlive > lifeSpan && turnNo % 10 == 1) {
			isAlive = false;
			die();
		}
	}
	
	public void die() {
		currentLocation.killAnt(this);
	}
	
}
