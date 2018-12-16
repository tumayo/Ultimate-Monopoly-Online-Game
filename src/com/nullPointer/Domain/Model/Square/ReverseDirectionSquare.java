package com.nullPointer.Domain.Model.Square;
import com.nullPointer.Domain.Model.GameEngine;
import com.nullPointer.Domain.Model.Player;

public class ReverseDirectionSquare extends Square {

	public ReverseDirectionSquare(String n, String t) {
		super(n, t);
		// TODO Auto-generated constructor stub
	}

	/**
	 * REQUIRES: gameEngine != null
	 * MODIFIES: gameEngine.getPlayerController().getCurrentPlayer()
	 * EFFECTS: Sets the direction of currentPlayer to the opposite of its current value.
	 * @param gameEngine engine that controls the system
	 */
	@Override
	public void evaluateSquare(GameEngine gameEngine) {
		Player currentPlayer = gameEngine.getPlayerController().getCurrentPlayer();
		currentPlayer.setDirection(!gameEngine.getPlayerController().getCurrentPlayer().getDirection());
		// Moving the player should be done according to the direction boolean.
	}

}
