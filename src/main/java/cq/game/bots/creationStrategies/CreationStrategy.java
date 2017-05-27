package cq.game.bots.creationStrategies;

import cq.game.api.InputGameState;
import cq.game.models.Unit;
import java.util.List;

/**
 * Created by Fredi Šarić on 27.05.17..
 */
public interface CreationStrategy {

	List<Unit> create(InputGameState state);
}
