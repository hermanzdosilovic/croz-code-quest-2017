package cq.game.bots.addToPathStrategies;

import cq.game.api.InputGameState;
import cq.game.models.Order;
import cq.game.models.Unit;
import cq.game.models.path.CQPaths;
import java.util.Random;

/**
 * Created by Fredi Šarić on 27.05.17..
 */
public class AddToPathRandom implements AddToPathStrategy {

	private static final Random rnd = new Random();

	@Override
	public Order compute(InputGameState state, Unit unit) {
		switch (rnd.nextInt(3)) {
			case 0: return new Order(CQPaths.SHORT, unit.getSpeed());
			case 1: return new Order(CQPaths.GOLD, unit.getSpeed());
			case 2: return new Order(CQPaths.CANNON, unit.getSpeed());
		}
		return null;
	}
}
