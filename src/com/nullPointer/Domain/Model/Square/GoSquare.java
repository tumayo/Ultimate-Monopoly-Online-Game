package com.nullPointer.Domain.Model.Square;
import com.nullPointer.Domain.Model.GameEngine;
import com.nullPointer.Domain.Model.Player;

public class GoSquare extends Square {

	public GoSquare(String n, String t) {
		super(n, t);
		// TODO Auto-generated constructor stub
		this.setFlyover(true);
	}


	/**
	 * REQUIRES: gameEngine != null
	 * MODIFIES: gameEngine.getPlayerController().getCurrentPlayer()
	 * EFFECTS: Increases player's money by 300.
	 * @param gameEngine engine that controls the system
	 */
	@Override
	public void evaluateSquare(GameEngine gameEngine) {
		Player currentPlayer = gameEngine.getPlayerController().getCurrentPlayer();
		gameEngine.getMoneyController().increaseMoney(currentPlayer, 300);
	}

	/**
	 * REQUIRES: gameEngine != null
	 * MODIFIES: gameEngine.getPlayerController().getCurrentPlayer()
	 * EFFECTS: Calls the default evaluation method if args is "flyover".
	 * @param gg engine that controls the system
	 * @param args additional arguments
	 */
	@Override
	public void evaluateSquare(GameEngine gg, String args)
	{
		if (args.equals("flyover"))
		{
			this.evaluateSquare(gg);
		}
		else
		{
			throw new IllegalArgumentException("Illegal argument: " + args);
		}
	}

}
