package ufl.cs1.controllers;

import game.controllers.DefenderController;
import game.models.Defender;
import game.models.Game;
import game.models.Node;

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

		//prints quadrant of pac man
		System.out.println("Pac man Quadrant: " + getQuadrant(game.getAttacker().getLocation()));

		return actions;
	}

	public int ghostOne(Game game, Defender ghost)
	{
		return 0;
	}

	public int ghostTwo(Game game, Defender ghost)
	{
		return 1;
	}

	public int ghostThree(Game game, Defender ghost)
	{
		return 2;
	}

	public int ghostFour(Game game, Defender ghost)
	{
		return 3;
	}


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
	//specifically, it will send a ghost to the power pill in its respective quadrant
	public int goToQuad(Defender ghost, Game game, int quadrant)
	{
		switch (quadrant)
		{
			
		}

		return -1;
	}

}