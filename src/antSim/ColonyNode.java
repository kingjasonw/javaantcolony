package antSim;

import java.util.ArrayList;

public class ColonyNode {
	int x, y;
	boolean opened = false;
	ArrayList<Ant> ants = new ArrayList<Ant>();
	ArrayList<Ant> antsToAdd = new ArrayList<Ant>();
	ArrayList<Ant> antsToRemove = new ArrayList<Ant>();
	ArrayList<Ant> nonBalaAnts = new ArrayList<Ant>();
	ArrayList<Ant> balasList = new ArrayList<Ant>();
	int foodUnits = 0;
	int pheromoneUnits = 0;
	private ColonyNodeView nodeView;
	Colony c;
	boolean isEntrance = false;

	public ColonyNode(ColonyNodeView nodeView, int x, int y, Colony c) {
		this.nodeView = nodeView;
		this.x = x;
		this.y =y;
		nodeView.setID(x + ", " + y);
		this.c = c;
	}
	
	// sets node to entrance starting state
	public void makeEntrance() {
		foodUnits = 1000;
		isEntrance = true;
		// create queen ant
		Queen q = new Queen(this, c);
		ants.add(q);
		nodeView.setQueen(true);
		nodeView.showQueenIcon();
		
		// 10 soldier ants
		for (int i = 0; i < 10; i++) {
			Soldier s = new Soldier(this, c);
			ants.add(s);
		}
		
		// 50 forager ants
		for (int i = 0; i < 50; i++) {
			Forager f = new Forager(this, c);
			ants.add(f);
		}
		
		// 4 scout ants
		for (int i = 0; i < 4; i++) {
			Scout s = new Scout(this, c);
			ants.add(s);
		}
		this.updateNodeView();
		
	}
	
	public ColonyNodeView getNodeView() {
		return this.nodeView;
	}
	
	// get number of each type of ant
	public void updateNodeView() {
		for (int j = 0; j < antsToAdd.size(); j++) {
			ants.add(antsToAdd.get(j));
		}
		for (int k = 0; k < antsToRemove.size(); k++) {
			ants.remove(antsToRemove.get(k));
		}
		antsToAdd.clear();
		antsToRemove.clear();
		
		int antTypeNums[] = countAntTypes();
		// set bala numbers
		if (antTypeNums[0] > 0) {
			nodeView.showBalaIcon();
		}
		else if (antTypeNums[0] == 0) {
			nodeView.hideBalaIcon();
		}
		nodeView.setBalaCount(antTypeNums[0]);
		// set forager numbers
		if (antTypeNums[1] > 0) {
			nodeView.showForagerIcon();
		}
		else if (antTypeNums[1] == 0) {
			nodeView.hideForagerIcon();
		}
		nodeView.setForagerCount(antTypeNums[1]);
		// set scout numbers
		if (antTypeNums[2] > 0) {
			nodeView.showScoutIcon();
		}
		else if (antTypeNums[2] == 0) {
			nodeView.hideScoutIcon();
		}
		nodeView.setScoutCount(antTypeNums[2]);
		// set soldier numbers
		if (antTypeNums[3] > 0) {
			nodeView.showSoldierIcon();
		}
		else if (antTypeNums[3] == 0) {
			nodeView.hideSoldierIcon();
		}
		nodeView.setSoldierCount(antTypeNums[3]);
		
		// food and pheromone levels
		nodeView.setFoodAmount(getFood());
		if (pheromoneUnits > 1000) {
			setPheromoneLevel(1000);
		}
		nodeView.setPheromoneLevel(getPheromoneLevel());
	}
	
	// get number of each type of ant in node
	public int[] countAntTypes() {
		nonBalaAnts.clear();
		balasList.clear();
		int balas = 0;
		int foragers = 0;
		int scouts = 0;
		int soldiers = 0;
		
		for (int i = 0; i < ants.size(); i++) {
			if (ants.get(i) instanceof Bala) {
				// add to balasList for soldiers to access
				balasList.add(ants.get(i));
				balas++;
			}
			else if (ants.get(i) instanceof Forager) {
				nonBalaAnts.add(ants.get(i));
				foragers++;
			}
			else if (ants.get(i) instanceof Scout) {
				nonBalaAnts.add(ants.get(i));
				scouts++;
			}
			else if (ants.get(i) instanceof Soldier) {
				nonBalaAnts.add(ants.get(i));
				soldiers++;
			}
		}
 		
		int antTypeNums[] = {balas, foragers, scouts, soldiers};
		return antTypeNums;
 	}
	
	public boolean containsBalas() {
		for (int i = 0; i < ants.size(); i++) {
			if (ants.get(i) instanceof Bala) {
				return true;
			}
		}
		return false;
	}
	
	// set amount of food in newly opened squares
	public void randomFoodAmount() {
		// 25% contains food 75% empty
		// random number 1-4
		int random = Simulation.getRandom(4) + 1;
		// 25% chance number will be 1. square gets food
		if (random == 1) {
			setFood(Simulation.getRandom(501) + 500);
		}
	}
	
	public int getFood() {
		return this.foodUnits;
	}
	
	public void setFood(int f) {
		this.foodUnits = f;
	}
	
	public int getPheromoneLevel() {
		return this.pheromoneUnits;
	}
	
	public void setPheromoneLevel(int p) {
		this.pheromoneUnits = p;
	}
	
	// methods
	public void openNode() {
		opened = true;
		this.randomFoodAmount();
		nodeView.showNode();
		this.updateNodeView();
	}
	
	// each ant moves. pheromoness decrease, each ant ages one day every 10 turns
	public void takeTurn(int turnNo) {
		for (int i = 0; i < ants.size(); i++) {
			ants.get(i).takeTurn(turnNo);
		}
		if (turnNo % 10 == 0 && getPheromoneLevel() > 0) {
			setPheromoneLevel(getPheromoneLevel() / 2); 
		}
	}
	
	public void killAnt(Ant a) {
		ants.remove(a);
		if (a instanceof Queen) {
			c.sim.endSim();
		}
	}
	
	public ArrayList<Ant> getBalas() {
		return balasList;
	}
	
}
