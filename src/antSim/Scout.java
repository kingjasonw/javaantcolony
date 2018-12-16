package antSim;

public class Scout extends Ant {
	// no unique data fields
	
	
	public Scout(ColonyNode cn, Colony c) {
		this.currentLocation = cn;
		this.colony = c;
		setID(noAnts);
	}
	
	
	public void takeTurn(int turnNo) {
		if (!moved) {
			age(turnNo);
		}
		if (isAlive) {
			if (moved == false) {
				findAdjacentSquaresScout();
				// ignore previous location
				for (int i = 0; i < adjacent.size(); i++) {
					 if (adjacent.get(i) == prevLocation) {
						 adjacent.remove(i);
					 }
				 }
				// random number between zero and number of valid adjacent squares
				int random = Simulation.getRandom(adjacent.size());
				nextLocation = adjacent.get(random);
				move(nextLocation);
				if (!nextLocation.opened) {
					nextLocation.openNode();
				}
			}
		}
		moved = true;
	}
	
}
