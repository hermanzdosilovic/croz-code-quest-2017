package cq.game.bots;

import cq.game.api.BaseStats;
import cq.game.api.GameStateWriter;
import cq.game.api.InputGameState;
import cq.game.bots.addToPathStrategies.AddToPathStrategy;
import cq.game.models.Order;
import cq.game.models.Unit;
import cq.game.models.enums.UnitType;
import cq.game.models.path.CQPaths;
import cq.game.models.path.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		if (state.getTurn() < 50) {
			goldAccumulation(state, gsw);
		} else if (state.getTurn() < 80) {
			// Accumulation in base
			if (isEmergancy(state)) {
				emergencyMove(state, gsw);
			}
		} else if (state.getTurn() < 120) {
			goldAccumulation(state, gsw);
		} else if (state.getTurn() == 121) {
			attack(state, gsw);
		} else if (isEmergancy(state)) {
			emergencyMove(state, gsw);
		}
		return gsw;
	}

	private void attack(InputGameState state, GameStateWriter gsw) {
		state.getMyBase().allUnits().forEach(unit-> {
			unit.setOrder(new Order(CQPaths.SHORT, unit.getSpeed()));
			gsw.addUnitOnPath(unit);
		});
	}

	private void goldAccumulation(InputGameState state, GameStateWriter gsw) {
		if (isEmergancy(state)) {
			emergencyMove(state, gsw);
			return;
		}
		BaseStats myBase = state.getMyBase();
		int a = myBase.getNumberOfArchers();
		int s = myBase.getNumberOfSpearmen();
		int h = myBase.getNumberOfHorsemen();

		int min = Math.min(Math.min(a, h), s);
		if (min == 0) {
			return;
		}

		for (Unit unit : myBase.allUnits()) {
			if (a > 0 && unit.getType() == UnitType.ARCHER){
				unit.setOrder(new Order(CQPaths.GOLD, 10));
				a--;
			} else if (h > 0  && unit.getType() == UnitType.HORSEMAN) {
				unit.setOrder(new Order(CQPaths.GOLD, 10));
				h--;
			} else if(s > 0 && unit.getType() == UnitType.SPEARMAN) {
				unit.setOrder(new Order(CQPaths.GOLD, 10));
				s--;
			}
			gsw.addUnitOnPath(unit);
		}
	}

	private void emergencyMove(InputGameState state, GameStateWriter gsw) {
		Map<Integer, List<Tuple<Integer, Unit>>> collect = state.getEnemyUnits()
			.stream()
			.map(unit -> new Tuple<>(unit.getPath().getLength() - unit.getOffset() - unit.getSpeed(), unit))
			.filter(t -> t.getLeft() <= 10)
			.sorted(Comparator.comparing(Tuple::getLeft))
			.collect(Collectors.groupingBy(Tuple::getLeft));
		collect.entrySet().stream().min(Comparator.comparing(Map.Entry::getKey))
			.map(Map.Entry::getValue)
			.map(u -> u.stream().map(Tuple::getRight).collect(Collectors.toList()))
			.ifPresent(units -> {
				Path path = units.get(0).getPath();
				BaseStats myBase = state.getMyBase();
				int a = myBase.getNumberOfArchers();
				int s = myBase.getNumberOfSpearmen();
				int h = myBase.getNumberOfHorsemen();

				int eh = (int) units.stream().filter(u -> u.getType() == UnitType.HORSEMAN).count();
				int ea = (int) units.stream().filter(u -> u.getType() == UnitType.ARCHER).count();
				int es = (int) units.stream().filter(u -> u.getType() == UnitType.SPEARMAN).count();

				int atp = Math.min(a, ea);
				int stp = Math.min(s, es);
				int htp = Math.min(h, eh);

				while (atp > 0) {
					Unit unit = Unit.byType(UnitType.ARCHER);
					unit.setOrder(new Order(path, 1));
					atp--;
					gsw.addUnitOnPath(unit);
				}
				while (stp > 0) {
					Unit unit = Unit.byType(UnitType.SPEARMAN);
					unit.setOrder(new Order(path, 1));
					gsw.addUnitOnPath(unit);
					stp--;
				}
				while (htp > 0) {
					htp--;
					Unit unit = Unit.byType(UnitType.HORSEMAN);
					unit.setOrder(new Order(path, 1));
					gsw.addUnitOnPath(unit);
				}
			});
	}

	private boolean isEmergancy(InputGameState state) {
		List<Unit> enemy = state.getEnemyUnits();
		return enemy
			.stream()
			.map(unit -> unit.getPath().getLength() - unit.getOffset() - unit.getSpeed() <= 10)
			.anyMatch(b -> b);
	}
}
