package cq.game.bots.addToPathStrategies;

import cq.game.api.InputGameState;
import cq.game.models.Order;
import cq.game.models.Unit;

/**
 *
 * Created by Fredi Šarić on 27.05.17..
 */
public interface AddToPathStrategy {

	Order compute(InputGameState state, Unit unit);

}
