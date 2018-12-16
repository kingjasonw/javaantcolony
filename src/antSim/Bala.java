package antSim;

public class Bala extends Ant {
	// no unique data fields
	
	
	public Bala(ColonyNode cn, Colony c) {
		this.currentLocation = cn;
		this.colony = c;
		setID(noAnts);
	}
	
	
	
	// methods
	public void takeTurn(int turnNo) {
		if (!moved && isAlive) {
			age(turnNo);
		
		
			findAdjacentSquaresScout();
			int random = Simulation.getRandom(adjacent.size());
			nextLocation = adjacent.get(random);
			move(nextLocation);
			// prioritize killing queen
			if (currentLocation.isEntrance) {
				for (int i = 0; i < currentLocation.nonBalaAnts.size(); i++) {
					if (currentLocation.nonBalaAnts.get(i) instanceof Queen) {
						attack(currentLocation.nonBalaAnts.get(i));
					}
				}
			}
			else if (currentLocation.nonBalaAnts.size() > 0) {
				random = Simulation.getRandom(currentLocation.nonBalaAnts.size());
				attack(currentLocation.nonBalaAnts.get(random));
			}
			moved = true;
		}
	}
	
	public void attack(Ant a) {
		int random = Simulation.getRandom(2);
		// 50-50 chance
		if (random == 1) {
			a.die();
		}
	}
	
}
