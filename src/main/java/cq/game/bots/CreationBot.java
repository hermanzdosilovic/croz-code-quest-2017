package cq.game.bots;

import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;
import cq.game.bots.creationStrategies.CreationStrategy;

/**
 *
 * Created by Fredi Šarić on 27.05.17..
 */
public class CreationBot implements Bot {

	private final CreationStrategy creationStrategy;

	public CreationBot(CreationStrategy creationStrategy) {
		this.creationStrategy = creationStrategy;
	}

	@Override
	public GameStateWriter generateOrders(InputGameState state, GameStateWriter gsw) {
		creationStrategy.create(state).forEach(unit -> {
			System.err.println("Creating: " + unit);
			gsw.addUnitOnBuildQueue(unit);
		});
		return gsw;
	}
}
