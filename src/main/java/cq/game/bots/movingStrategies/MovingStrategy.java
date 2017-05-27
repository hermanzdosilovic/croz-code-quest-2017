package cq.game.bots.movingStrategies;

import cq.game.api.InputGameState;
import cq.game.models.Unit;
import java.util.List;

/**
 * Created by Fredi Šarić on 27.05.17..
 */
public interface MovingStrategy {

	 List<Unit> makeMove(InputGameState state);

}
