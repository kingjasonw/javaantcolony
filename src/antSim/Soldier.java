package antSim;

import java.util.ArrayList;

public class Soldier extends Ant {
	
	boolean scoutMode = true;
	ArrayList<ColonyNode> balaList = new ArrayList<ColonyNode>();
	ArrayList<Ant> balas = new ArrayList<Ant>();
	
	
	public Soldier(ColonyNode cn, Colony c) {
		this.currentLocation = cn;
		this.colony = c;
		setID(noAnts);
	}
	
	public void takeTurn(int turnNo) {
		if (!moved) {
			age(turnNo);
		}
		if (isAlive) {
			if (!moved) {
				checkSurroundings();
			}
		}
		moved = true;
	}
	
	public void checkSurroundings() {
		// if balas in current location, attack
		if (currentLocation.containsBalas()) {
			balas = currentLocation.getBalas();
			attack(balas);
		}
		else {
			balaList.clear();
			findAdjacentSquares();
			// get adjacent squares containing bala ants
			// for each adjacent square
			for (int i = 0; i < adjacent.size(); i++) {
				// if bala add square to list of squares containing balas
				if (adjacent.get(i).containsBalas()) {
					balaList.add(adjacent.get(i));
				}
			}
			if (!balaList.isEmpty()) {
				attackMode();
			}
			else {
				scoutMode();
			}
		}
	}
	
	public void scoutMode() {
		int random = Simulation.getRandom(adjacent.size());
		nextLocation = adjacent.get(random);
		move(nextLocation);
	}
	
	public void attackMode() {	
		// pick random bala containing square and attack
		int random = Simulation.getRandom(balaList.size());
		nextLocation = balaList.get(random);
		move(nextLocation);
		balas = currentLocation.getBalas();
		if (!balas.isEmpty()) {
			attack(balas);
		}
	}
	

	
	public void attack(ArrayList<Ant> balas) {
		// pick random bala to attack
		int i = balas.size();
		if (i > 0) {
			int random = Simulation.getRandom(i);
			Ant b = currentLocation.balasList.get(random);	
			// 50% chance of killing bala
			random = Simulation.getRandom(2);
			// kill
			if (random == 0) {
				b.die();
			}	
		}
	}
}
