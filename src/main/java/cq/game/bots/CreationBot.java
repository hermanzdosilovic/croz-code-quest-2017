package cq.game.bots;

import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;
import cq.game.models.Unit;
import cq.game.models.enums.UnitType;

/**
 *
 * Created by Fredi Šarić on 27.05.17..
 */
public class CreationBot implements Bot {

	private static final double horsemenPercentage = 4/5.0;
	private static final double archerPercentage = 1/5.0;

	@Override
	public GameStateWriter generateOrders(InputGameState state, GameStateWriter gsw) {
		Integer gold = state.getMyBase().getGold();
		double numOfH = Math.floor((gold * horsemenPercentage) / 100);
		for (int i = 0; i < numOfH; i++) {
			System.err.println("Creating horseman");
			Unit unit = Unit.byType(UnitType.HORSEMAN);
			gsw.addUnitOnBuildQueue(unit);
		}
		double numOfA = Math.floor((gold * archerPercentage) / 100);
		for (int i = 0; i < numOfA; i++) {
			System.err.println("Creating archer");
			Unit unit = Unit.byType(UnitType.ARCHER);
			gsw.addUnitOnBuildQueue(unit);
		}
		return gsw;
	}
}
