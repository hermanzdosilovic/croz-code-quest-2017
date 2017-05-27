package cq.game.bots;

import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;

/**
 * Created by Fredi Šarić on 27.05.17..
 */
public class CompositeBot implements Bot {

	private final Bot[] bots;

	public CompositeBot(Bot[] bots) {

		this.bots = bots;
	}

	@Override
	public GameStateWriter generateOrders(InputGameState state, GameStateWriter gsw) {
		for (Bot bot : bots) {
			bot.generateOrders(state, gsw);
		}
		return gsw;
	}
}
