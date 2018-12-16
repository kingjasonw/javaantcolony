package antSim;

public class Queen extends Ant {
	private int ID = 0;
	private int lifeSpan = 73000;
	
	
	public Queen(ColonyNode cn, Colony c) {
		this.currentLocation = cn;
		this.colony = c;
	}
	
	public void hatch() {
		int random = Simulation.getRandom(4) + 1;
		Ant hatched;
		if (random < 3) {
			hatched = new Forager(currentLocation, colony);
		}
		else if (random == 3) {
			hatched = new Scout(currentLocation, colony);
		}
		else {
			hatched = new Soldier(currentLocation, colony);
		}
		// stay in entrance for turn hatched
		hatched.moved = true;
		currentLocation.ants.add(hatched);
	}
	
	public void takeTurn(int turnNo) {
		// first turn of the day
		if (turnNo % 10 == 1) {
			hatch();
		}
		// check if should die
		if (turnsAlive >= lifeSpan) {
			die();
		}
		eat();
		turnsAlive++;
 	}
	
	public void die() {
		colony.sim.endSim();
	}
	
	
	public void eat() {
		if (currentLocation.foodUnits < 1) {
			die();
		}
		currentLocation.foodUnits--;
	}
	
}
