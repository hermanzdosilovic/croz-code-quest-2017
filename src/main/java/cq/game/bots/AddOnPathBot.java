package cq.game.bots;

import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;
import cq.game.bots.addToPathStrategies.AddToPathStrategy;
import cq.game.models.Order;

/**
 *
 * Created by Fredi Šarić on 27.05.17..
 */
public class AddOnPathBot implements Bot {

	private final AddToPathStrategy strategies;

	public AddOnPathBot(AddToPathStrategy strategies) {
		this.strategies = strategies;
	}

	@Override
	public GameStateWriter generateOrders(InputGameState state, GameStateWriter gsw) {

		state.getMyBase().allUnits().forEach(unit -> {
			Order order = strategies.compute(state, unit);
			unit.setOrder(order);
			System.err.println("Adding to " + order.getPath().getDescription() + ": " + unit.getType());
			gsw.addUnitOnPath(unit);
		});
		return gsw;
	}
}
