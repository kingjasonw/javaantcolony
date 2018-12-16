package antSim;

public class Colony {
	Simulation sim;
	ColonyNode[][] grid;
	private ColonyNodeView nodeView;
	private ColonyNode node;
	private ColonyView colonyView;
	
	
	public Colony(Simulation sim) {
		this.sim = sim;
		grid = new ColonyNode[27][27];
		colonyView = new ColonyView(27, 27);
	}
	
	public ColonyView getColonyView() {
		return this.colonyView;
	}
	
	// create view, nodes. add nodes to view
	public void intialize() {
		for (int i = 0; i < 27; i++) {
		 	for (int j = 0; j < 27; j++) {
		 		nodeView = new ColonyNodeView();
		 		node = new ColonyNode(nodeView, i ,j, this);
		 		grid[i][j] = node;
				colonyView.addColonyNodeView(nodeView, i, j);
				
				// set entrance node
				if  (i == 13 && j == 13) {
					node.openNode();
					node.makeEntrance();
				}
				else if ((i == 12 && j == 12) || (i == 12 && j == 13) || (i == 12 && j == 14) || (i == 13 && j == 12) || (i == 13 && j == 14) || (i == 14 && j == 12) || (i == 14 && j == 13) || (i == 14 && j == 14)) {
					node.openNode();
				}
			}
		}
	}
	
	// create view, nodes. add nodes to view, create only one forager
	public void foragerModeInit() {
		for (int i = 0; i < 27; i++) {
		 	for (int j = 0; j < 27; j++) {
		 		nodeView = new ColonyNodeView();
		 		node = new ColonyNode(nodeView, i ,j, this);
		 		grid[i][j] = node;
				colonyView.addColonyNodeView(nodeView, i, j);
				// set entrance node
				if  (i == 13 && j == 13) {
					node.foodUnits = 1000;
					node.isEntrance = true;
					for (int k = 0; k < 2; k++ ) {
						Forager f = new Forager(node, this);
						node.ants.add(f);
					}
					node.openNode();			
				}
				else if ((i == 12 && j == 12) || (i == 12 && j == 13) || (i == 12 && j == 14) || (i == 13 && j == 12) || (i == 13 && j == 14) || (i == 14 && j == 12) || (i == 14 && j == 13) || (i == 14 && j == 14)) {
					node.openNode();
				}
			}
		}
	}
	
	// create view, nodes. add nodes to view, create only one scout
	public void scoutModeInit() {
		for (int i = 0; i < 27; i++) {
		 	for (int j = 0; j < 27; j++) {
		 		nodeView = new ColonyNodeView();
			 	node = new ColonyNode(nodeView, i ,j, this);
			 	grid[i][j] = node;
				colonyView.addColonyNodeView(nodeView, i, j);
				// set entrance node
				if  (i == 13 && j == 13) {
					node.foodUnits = 1000;
					node.isEntrance = true;
					for (int k = 0; k < 2; k++ ) {
						Scout s = new Scout(node, this);
						node.ants.add(s);
					}
					
					node.openNode();			
				}
				else if ((i == 12 && j == 12) || (i == 12 && j == 13) || (i == 12 && j == 14) || (i == 13 && j == 12) || (i == 13 && j == 14) || (i == 14 && j == 12) || (i == 14 && j == 13) || (i == 14 && j == 14)) {
					node.openNode();
				}
			}
		}
	}
	
	// create view, nodes. add nodes to view, create only one soldier
		public void soldierModeInit() {
			for (int i = 0; i < 27; i++) {
			 	for (int j = 0; j < 27; j++) {
			 		nodeView = new ColonyNodeView();
				 	node = new ColonyNode(nodeView, i ,j, this);
				 	grid[i][j] = node;
					colonyView.addColonyNodeView(nodeView, i, j);
					// set entrance node
					if  (i == 13 && j == 13) {
						node.foodUnits = 1000;
						node.isEntrance = true;
						for (int k = 0; k < 2; k++ ) {
							Soldier s = new Soldier(node, this);
							node.ants.add(s);
						}
						node.openNode();
					}
					else if ((i == 12 && j == 12) || (i == 12 && j == 13) || (i == 12 && j == 14) || (i == 13 && j == 12) || (i == 13 && j == 14) || (i == 14 && j == 12) || (i == 14 && j == 13) || (i == 14 && j == 14)) {	
						Bala b = new Bala(node, this);
						node.ants.add(b);
						node.openNode();
					}
				}
			}
		}
		
		public void queenModeInit() {
			for (int i = 0; i < 27; i++) {
			 	for (int j = 0; j < 27; j++) {
			 		nodeView = new ColonyNodeView();
			 		node = new ColonyNode(nodeView, i ,j, this);
			 		grid[i][j] = node;
					colonyView.addColonyNodeView(nodeView, i, j);
					
					// set entrance node
					if  (i == 13 && j == 13) {
						node.isEntrance = true;
						node.foodUnits = 1000;
						Queen q = new Queen(node, this);
						node.ants.add(q);
						nodeView.setQueen(true);
						nodeView.showQueenIcon();
						node.openNode();
					}
					else if ((i == 12 && j == 12) || (i == 12 && j == 13) || (i == 12 && j == 14) || (i == 13 && j == 12) || (i == 13 && j == 14) || (i == 14 && j == 12) || (i == 14 && j == 13) || (i == 14 && j == 14)) {
						node.openNode();
					}
				}
			}
		}
		
	// have each square perform its actions
	public void takeTurn(int turnNo) {
		// bala creation
		int random = Simulation.getRandom(100) + 1;
		// 3% chance. 1-100, 1, 2, 3 create balas 
		if (random < 4) {
			// enter from top left corner
			Bala b = new Bala(grid[0][0], this);
			grid[0][0].ants.add(b);
		}
		for (int i = 0; i < 27; i++) {
			for (int j = 0; j < 27; j++) {
				node = grid[i][j];
				node.takeTurn(turnNo);
			}
		}
		
		// update all node views
		for (int i = 0; i < 27; i++) {
		 	for (int j = 0; j < 27; j++) {
		 		node = grid[i][j];
		 		for (int a = 0; a < node.ants.size(); a++) {
					node.ants.get(a).moved = false;
				}
				node.updateNodeView();
		 	}
		 }
	}
	
}
