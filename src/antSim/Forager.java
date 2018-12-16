package antSim;


import java.util.Stack;

public class Forager extends Ant {
	private boolean forageMode = true;
	private boolean hasFood = false;
	private Stack<ColonyNode> path = new Stack<ColonyNode>();
	
	
	public Forager(ColonyNode cn, Colony c) {
		this.currentLocation = cn;
		path.push(currentLocation);
		this.colony = c;
		setID(noAnts);
	}
	
	public void takeTurn(int turnNo) {
		if (!moved) {
			age(turnNo);
		}
		if (isAlive) {
			if (moved == false) {
				if (forageMode) {
					forageMode();
				}
				else {
					returnToNestMode();
				}
			}
		}
	}

	public ColonyNode detectPheromone() {
		findAdjacentSquares();
		// sort adjacent squares by pheromone level
		adjacent.sort((o1, o2) -> Integer.compare(o2.getPheromoneLevel(), o1.getPheromoneLevel()));
		ColonyNode highPheromone = adjacent.get(0);
		// if already visited square, move to next highest pheromone
		// if all paths visited, move to highest pheromone
		// seems to fix looping movement issue
		for (int i = 0; i < adjacent.size(); i++) {
			if (!path.contains(adjacent.get(i))) {
				highPheromone = adjacent.get(i);
				return highPheromone;
			}
		}
		return highPheromone;
	}
	
	
	public void forageMode() {
		nextLocation = detectPheromone();
		// remember path
		path.push(currentLocation);
		// if food in square
		if (nextLocation.foodUnits > 0 && !hasFood && !nextLocation.isEntrance) {
			hasFood = true;
			nextLocation.foodUnits--;
			forageMode = false;
			if (nextLocation.pheromoneUnits < 1000) {
				nextLocation.pheromoneUnits += 10;
			}	
		}
		move(nextLocation);
	}
	
	public void returnToNestMode() {
		nextLocation = path.pop();
		if (nextLocation.isEntrance) {
			nextLocation.foodUnits++;
			hasFood = false;
			forageMode = true;
			// reset movement history
			path.clear();
		}
		else {
			if (nextLocation.pheromoneUnits < 1000) {
				nextLocation.pheromoneUnits += 10;
			}	
		}
		move(nextLocation);
		// fix bug where ant would not return to previous square containing food if next to colony
		// because set to ignore previous location when foraging
		if (currentLocation.isEntrance) {
			prevLocation = currentLocation;
		}
		
	}
	
	public void die() {
		if (hasFood) {
			currentLocation.foodUnits++;
		}
		currentLocation.killAnt(this);
	}
	
}
