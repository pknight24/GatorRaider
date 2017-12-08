package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.models.Defender;
import game.models.Game;
import game.models.Node;
import game.models.Maze;

import java.util.List;

public final class StudentController implements DefenderController
{
	public void init(Game game) { }

	public void shutdown(Game game) { }

	public int[] update(Game game,long timeDue)
	{
		int[] actions = new int[Game.NUM_DEFENDER];
		List<Defender> enemies = game.getDefenders();
		
		//Chooses a random LEGAL action if required. Could be much simpler by simply returning
		//any random number of all of the ghosts
		for(int i = 0; i < actions.length; i++)
		{
			Defender defender = enemies.get(i);
			List<Integer> possibleDirs = defender.getPossibleDirs();
			switch(i)
			{
				//ghost one
				case 0:
					actions[i] = ghostOne(game, defender);
					break;
				case 1:
					actions[i] = ghostTwo(game, defender);
					break;
				case 2:
					actions[i] = ghostThree(game, defender);
					break;
				case 3:
					actions[i] = ghostFour(game, defender);
					break;
				default:
					actions[i] = -1;

			}
		}



		return actions;
	}

	//By: Parker
	//this ghost simply chases the attacker
	public int ghostOne(Game game, Defender ghost)
	{
		if (closeToPill(game.getAttacker().getLocation(), game))
		{
			return goToQuad(game, ghost, 1);
		}
		else if (ghost.isVulnerable())
		{
			//return whenVulnerable(game, ghost, 1);
			return goToQuad(game, ghost, 1);
		}
		else
		{
			return ghost.getNextDir(game.getAttacker().getLocation(),true);

		}
	}

	//By: Parker
	public int ghostTwo(Game game, Defender ghost)
	{
		Node aLoc = game.getAttacker().getLocation();

		if (closeToPill(aLoc, game))
		{
			return goToQuad(game, ghost, 2);
		}
		else if (ghost.isVulnerable())
		{
			return whenVulnerable(game, ghost, 2);
			//return goToQuad(game, ghost, 2);
		}
		else
		{
			if (ghost.getLocation().getPathDistance(aLoc) <= 20)
				return ghost.getNextDir(aLoc, true);
			else
				return goToQuad(game, ghost, getQuadrant(aLoc));
		}
	}
	//By: Leo
	public int ghostThree(Game game, Defender ghost)
	{

		return goToQuad(game, ghost, 3);
		//return ghost.getNextDir(game.getCurMaze().getInitialDefendersPosition(), false);
	}

	//By: Maddy
	public int ghostFour(Game game, Defender ghost){
	
	Node aLoc = game.getAttacker().getLocation();

		if (ghost.isVulnerable()) {
			return goToQuad(game, ghost, 4);
		}

		else if (closeToPill(game.getAttacker().getLocation(), game))
		{
			return goToQuad(game, ghost, 4);
		}
		else  {
			if (ghost.getLocation().getPathDistance(aLoc) <= 10) {
				return goToQuad(game, ghost, 4);
			}
			else {
				return ghost.getNextDir(game.getAttacker().getLocation(), true);

			}
		}
	}




	//gets the quadrant that a certain node is in
	//used to find the general location of the attacker
	public int getQuadrant(Node node)
	{
		int x = node.getX();
		int y = node.getY();

		//left half of the board
		if (x >= 0 && x < 52)
		{
			//quadrant 1 (top left)
			if (y >= 0 && y < 58)
			{
				return 1;
			}

			//quadrant 3 (bottom left)
			else if (y >= 58 && y <= 116)
			{
				return 3;
			}
		}

		//right half of the board
		else if (x >= 52 && x <= 104)
		{
			//quadrant 2 (top right)
			if (y >= 0 && y < 58)
			{
				return 2;
			}

			//quadrant 4 (bottom right)
			else if (y >= 58 && y <= 116)
			{
				return 4;
			}
		}
		return -1;
	}




	//sends a ghost to a specific quadrant
	//will be used when a ghost is vulnerable to effectively scatter them
	//first looks for power pills in a specific quadrant so that the if pac man is stuck on a power pill, it will be triggered
	//this also tucks the ghosts tighter into the corners
	public int goToQuad(Game game, Defender ghost, int quadrant)
	{
		List<Node> pills = game.getPillList();
		List<Node> powerPills = game.getPowerPillList();

		//checks the list of power pills
		for (int j = 0;j < powerPills.size(); j++ )
		{
			if (getQuadrant(powerPills.get(j)) == quadrant)
				return ghost.getNextDir(powerPills.get(j), true);
		}

		//if a ghosts respective power pill has already been used, it will cycle in the general area
		for (int i = 0; i < pills.size(); i++)
		{
			//this makes sure that the pill is in the desired quadrant and that it is a good distance away from the ghost
			if (getQuadrant(pills.get(i)) == quadrant && ghost.getLocation().getPathDistance(pills.get(i)) >= 45)
				return ghost.getNextDir(pills.get(i),true);
		}

		return -1;
	}

	//checks if a node is close to a power pill
	public boolean closeToPill(Node node, Game game)
	{

		List<Node> powerPills = game.getPowerPillList();

		for (int i = 0; i < powerPills.size(); i++)
		{
			if (node.getPathDistance(powerPills.get(i)) <= 45)
			{
				return true;
			}
		}
		return false;
	}

	public int whenVulnerable(Game game, Defender ghost, int quadrant)
	{
		Node aLoc = game.getAttacker().getLocation();
		List<Node> powerPills = game.getPowerPillList();

		Maze maze = game.getCurMaze();

		boolean powerPillPresent = false;

		for (int i = 0;i < powerPills.size();i++)
		{
			if (getQuadrant(powerPills.get(i)) == quadrant)
				powerPillPresent = true;
		}

		if (powerPillPresent)
		{
			return ghost.getNextDir(maze.getInitialDefendersPosition(), true);
		}
		else
		{
			return goToQuad(game, ghost, quadrant);
		}
	}
}
