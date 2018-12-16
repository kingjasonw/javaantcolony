package antSim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;




public class Simulation implements SimulationEventListener { 
	private AntSimGUI gui;
	private Colony c;
	int turn = 0;
	int day = 0;
	public boolean stepMode = true;
	public boolean queenAlive = true;
	static Random r = new Random();
	
	
	public Simulation(AntSimGUI gui) {
		this.gui = gui;
		c = new Colony(this);
		gui.initGUI(c.getColonyView());
		gui.setTime("Day: " + day + " Turn: " + turn);
		gui.addSimulationEventListener(this);
	}

	
	static public int getRandom(int bound) {
		int rand = r.nextInt(bound);
		return rand;
	}
	
	
	// keep track of current turn and take next turn
	public void takeTurn() {
		if (stepMode) {
			turn++;
			if (turn != 0 && turn % 10 == 0) {
				day++;
			}
			gui.setTime("Day: " + day + " Turn: " + turn);
			c.takeTurn(turn);
		}
		else {
			int delay = 250;
			ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				turn++;
				if (turn != 0 && turn % 10 == 0) {
					day++;
				}
				gui.setTime("Day: " + day + " Turn: " + turn);
				c.takeTurn(turn);  
		      }
		  };
		  new Timer(delay, taskPerformer).start();
		}
	}
	
	public void initColony() {
		c.intialize();
	}
	
	public void foragerModeInitColony() {
		c.foragerModeInit();
	}
	
	public void scoutModeInitColony() {
		c.scoutModeInit();
	}
	
	public void soldierModeInitColony() {
		c.soldierModeInit();
	}
	
	public void queenModeInitColony() {
		c.queenModeInit();
	}
	
	public void endSim() {
		queenAlive = false;
		gui.simulationEnded();
		System.exit(0);
	}
	
	public void simulationEventOccurred(SimulationEvent simEvent) {
		// normal
		if (simEvent.getEventType() == 0) {
			initColony();
		}
		// run
		if (simEvent.getEventType() == 5) {
			stepMode = false;
			takeTurn();
		}
		// step
		else if (simEvent.getEventType() == 6) {
			stepMode = true;
			takeTurn();
		}
		// forager test
		else if (simEvent.getEventType() == 3) {
			foragerModeInitColony();
		}
		// scout test
		else if (simEvent.getEventType() == 2) {
			scoutModeInitColony();
		}
		// soldier test
		else if (simEvent.getEventType() == 4) {
			soldierModeInitColony();
		}
		// queen test
		else if (simEvent.getEventType() == 1) {
			queenModeInitColony();
		}
	}
	
	
	
}
