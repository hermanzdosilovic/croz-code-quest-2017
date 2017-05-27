package cq.game.bots;

import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;
import cq.game.bots.movingStrategies.MovingStrategy;

/**
 *
 * Created by Fredi Šarić on 27.05.17..
 */
public class MovingBot implements Bot {

	private final MovingStrategy strategies;

	public MovingBot(MovingStrategy strategies) {
		this.strategies = strategies;
	}

	@Override
	public GameStateWriter generateOrders(InputGameState state, GameStateWriter gsw) {
		strategies.makeMove(state).forEach(unit -> {
			System.err.println(unit);
			gsw.addMovingUnit(unit);
		});
		return gsw;
	}
}
