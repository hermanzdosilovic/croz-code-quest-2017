package cq.game.bots;

import cq.game.api.BaseStats;
import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;
import cq.game.models.Order;
import cq.game.models.Unit;
import cq.game.models.enums.UnitType;
import cq.game.models.path.CQPaths;
import java.util.List;

/**
 * Created by Fredi Šarić on 27.05.17..
 */
public class MovingBot implements Bot {

	@Override
	public GameStateWriter generateOrders(InputGameState state, GameStateWriter gsw) {
		BaseStats base = state.getMyBase();
//		moveFromBase(gsw, base);
		moveHorsemen(state, gsw);
		return gsw;
	}

	private void moveHorsemen(InputGameState state, GameStateWriter gsw) {
		List<Unit> movingUnits = state.getMovingUnits();
		int maxOffseet = 100;
		movingUnits.stream()
			.forEach(horsemen -> {
				int offset = horsemen.getOffset();
				int move = Math.min(horsemen.getSpeed(), maxOffseet - offset);
				horsemen.setOrder(new Order(horsemen.getPath(), move + offset));
				gsw.addMovingUnit(horsemen);
			});
	}

	private void moveFromBase(GameStateWriter gsw, BaseStats base) {
		Integer numberOfHorsemen = base.getNumberOfHorsemen();

		int goldPath = numberOfHorsemen / 2;
		int cannonPath = numberOfHorsemen - goldPath;

		for (int i = 0; i < goldPath; i++) {
			Unit unit = Unit.byType(UnitType.HORSEMAN);
			unit.setOrder(new Order(CQPaths.GOLD, 0));
			gsw.addUnitOnPath(unit);
		}
		for (int i = 0; i < cannonPath; i++) {
			Unit unit = Unit.byType(UnitType.HORSEMAN);
			unit.setOrder(new Order(CQPaths.CANNON, 0));
			gsw.addUnitOnPath(unit);
		}

		Integer numberOfArchers = base.getNumberOfArchers();
		for (int i = 0; i < numberOfArchers; i++) {
			Unit unit = Unit.byType(UnitType.ARCHER);
			unit.setOrder(new Order(CQPaths.SHORT, 0));
			gsw.addUnitOnPath(unit);
		}
		Integer numberOfSpearmen = base.getNumberOfSpearmen();
		for (int i = 0; i < numberOfSpearmen; i++) {
			Unit unit = Unit.byType(UnitType.SPEARMAN);
			unit.setOrder(new Order(CQPaths.SHORT, 0));
			gsw.addUnitOnPath(unit);
		}
	}
}
