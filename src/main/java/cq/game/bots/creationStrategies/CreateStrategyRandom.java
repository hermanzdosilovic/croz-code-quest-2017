package cq.game.bots.creationStrategies;

import cq.game.api.InputGameState;
import cq.game.models.Unit;
import cq.game.models.enums.UnitType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredi Šarić on 27.05.17..
 */
public class CreateStrategyRandom implements CreationStrategy {

	private static final double horsemenPercentage = 4/5.0;
	private static final double archerPercentage = 1/5.0;

	@Override
	public List<Unit> create(InputGameState state) {
		Integer gold = state.getMyBase().getGold();
		List<Unit> units = new ArrayList<>();
		double numOfH = Math.floor((gold * horsemenPercentage) / 100);
		for (int i = 0; i < numOfH; i++) {
			System.err.println("Creating horseman");
			units.add(Unit.byType(UnitType.HORSEMAN));
		}
		double numOfA = Math.floor((gold * archerPercentage) / 100);
		for (int i = 0; i < numOfA; i++) {
			System.err.println("Creating archer");
			units.add(Unit.byType(UnitType.ARCHER));
		}
		return units;
	}
}
