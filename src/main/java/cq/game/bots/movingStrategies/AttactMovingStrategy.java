package cq.game.bots.movingStrategies;

import cq.game.api.InputGameState;
import cq.game.models.Order;
import cq.game.models.Unit;
import cq.game.models.path.CQPaths;
import cq.game.models.path.Path;
import cq.game.simulation.FightSimulation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Fredi Šarić on 27.05.17..
 */
public class AttactMovingStrategy implements MovingStrategy {
	@Override
	public List<Unit> makeMove(InputGameState state) {
		Map<Path, List<Unit>> groupedByPath = state.getMyUnits()
			.stream()
			.collect(Collectors.groupingBy(Unit::getPath));
		List<Unit> newUnits = new ArrayList<>();
		newUnits.addAll(makeMoveForPath(CQPaths.GOLD, groupedByPath.getOrDefault(CQPaths.GOLD, Collections.emptyList()), state));
		newUnits.addAll(makeMoveForPath(CQPaths.CANNON, groupedByPath.getOrDefault(CQPaths.CANNON, Collections.emptyList()), state));
		newUnits.addAll(makeMoveForPath(CQPaths.SHORT, groupedByPath.getOrDefault(CQPaths.SHORT, Collections.emptyList()), state));
		return newUnits;
	}

	private Collection<Unit> makeMoveForPath(Path path, List<Unit> units, InputGameState state) {
		Map<Integer, List<Unit>> enemiesGrouppedByPossition = state.getEnemyUnits()
			.stream()
			.filter(unit -> path.equals(unit.getPath()))
			.collect(Collectors.groupingBy(Unit::getOffset));
		Map<Integer, List<Unit>> grouppedByOffset = units.stream().collect(Collectors.groupingBy(Unit::getOffset));

		ArrayList<Unit> newUnits = new ArrayList<>();
		grouppedByOffset.forEach((position, unitsAtPosition) -> {
			Optional<Map.Entry<Integer, List<Unit>>> first = enemiesGrouppedByPossition.entrySet().stream()
				.sorted(Comparator.comparing(e ->
					(path.getLength() - e.getKey()) // Computes relative position current position
						- position))
				.findFirst();

			// No enemies
			if (!first.isPresent()) {
				// Move all upfront -> not one of th path
				unitsAtPosition.forEach(unit -> {
					int maxMove = path.getLength() - position;
					int move = Math.min(10, maxMove);

					unit.setOrder(new Order(path, Math.max(0, move + position)));
					newUnits.add(unit);
				});
				return;
			}
			Map.Entry<Integer, List<Unit>> other = first.get();
			Integer otherPosition = path.getLength() - other.getKey();
			List<Unit> otherunits = other.getValue();

			Map<Integer, List<Unit>> nextPositions = otherunits.stream()
				.collect(Collectors.groupingBy(unit -> otherPosition - unit.getSpeed()));

			List<Integer> positions = nextPositions.entrySet().stream()
				.map(Map.Entry::getKey)
				.sorted()
				.collect(Collectors.toList());
			if (positions.get(0) < position) { // They can attack
				FightSimulation.FightResult result = FightSimulation.fight(unitsAtPosition, nextPositions.get(positions.get(0)));
				if (result.isWon()) {
					if (positions.size() == 2) {
						unitsAtPosition.forEach(unit -> {
							// Move to the position of the emeny troops.
							int pos = Math.min(position + 10, positions.get(1));
							unit.setOrder(new Order(path, Math.max(0, pos)));
							newUnits.add(unit);
						});
					} else {
						unitsAtPosition.forEach(unit -> {
							int maxMove = path.getLength() - position;
							int move = Math.min(10, maxMove);
							unit.setOrder(new Order(path,  Math.max(0, move + position)));
							newUnits.add(unit);
						});
					}
				} else {
					// Do not move any units.
					unitsAtPosition.forEach(unit -> {
						unit.setOrder(new Order(path,  Math.max(0, unit.getOffset())));
						newUnits.add(unit);
					});
				}
			} else {
				unitsAtPosition.forEach(unit -> {
					int maxMove = path.getLength() - position;
					int move = Math.min(10, maxMove);
					unit.setOrder(new Order(path, Math.max(0, move + position)));
					newUnits.add(unit);
				});
			}
		});

		return newUnits;
	}
}
