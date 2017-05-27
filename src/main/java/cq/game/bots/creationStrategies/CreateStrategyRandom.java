package cq.game.bots.creationStrategies;

import cq.game.api.InputGameState;
import cq.game.models.Unit;
import cq.game.models.enums.UnitType;
import cq.game.models.path.CQPaths;
import cq.game.models.path.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * Created by Fredi Šarić on 27.05.17..
 */
public class CreateStrategyRandom implements CreationStrategy {

	@Override
	public List<Unit> create(InputGameState state) {
		List<Unit> enemy = state.getEnemyUnits();
		Map<Path, List<Unit>> grouppedByPath = enemy.stream()
			.collect(Collectors.groupingBy(Unit::getPath));
		List<Map.Entry<Path, List<Unit>>> unitsSorted = grouppedByPath.entrySet()
				.stream()
				.sorted(Comparator.comparingDouble(e -> -computeFactor(e)))
				.collect(Collectors.toList());

		if (unitsSorted.isEmpty()) { // Noo other units conserve gold
			return createUniformly(state);
		}
		List<Unit> mostDengurus = unitsSorted.get(0).getValue();

		long eh = mostDengurus.stream().filter(u -> u.getType() == UnitType.HORSEMAN).count();
		long ea = mostDengurus.stream().filter(u -> u.getType() == UnitType.ARCHER).count();
		long es = mostDengurus.stream().filter(u -> u.getType() == UnitType.SPEARMAN).count();

		ArrayList<Unit> newUnits = new ArrayList<>();
		Integer h = state.getMyBase().getNumberOfHorsemen();
		Integer gold = state.getMyBase().getGold();
		if (h < eh) {
			while (gold >= 100) {
				System.err.println("Creating horseman");
				newUnits.add(Unit.byType(UnitType.HORSEMAN));
				h++;
				gold-=100;
			}
		}
		Integer a = state.getMyBase().getNumberOfArchers();
		if (a < ea) {
			while (gold >= 100)  {
				System.err.println("Creating archmen ");
				newUnits.add(Unit.byType(UnitType.ARCHER));
				a++;
				gold-=100;
			}
		}
		Integer s = state.getMyBase().getNumberOfSpearmen();
		if (s < es) {
			while (gold >= 70)  {
				System.err.println("Creating spearman");
				newUnits.add(Unit.byType(UnitType.SPEARMAN));
				s++;
				gold -= 70;
			}
		}
		return newUnits;
	}

	private List<Unit> createUniformly(InputGameState state) {
		ArrayList<Unit> units = new ArrayList<>();
		Integer gold = state.getMyBase().getGold();
		while (gold > 70) {
			if (gold > 100) {
				units.add(Unit.byType(UnitType.ARCHER));
				gold-=100;
			}
			if (gold > 100) {
				units.add(Unit.byType(UnitType.HORSEMAN));
				gold-=100;
			}
			if (gold > 70) {
				units.add(Unit.byType(UnitType.SPEARMAN));
				gold-=70;
			}
		}
		return units;
	}

	private double computeFactor(Map.Entry<Path, List<Unit>> e) {
		double factor = 0.0;
		if (e.getKey().equals(CQPaths.GOLD)) {
			factor = 5/100.0;
		} else if (e.getKey().equals(CQPaths.CANNON)) {
			factor = 1/50.0;
		} else {
			factor =  1/100.0;
		}
		return factor * e.getValue().stream().mapToInt(Unit::getPointFactor).sum();
	}
}
