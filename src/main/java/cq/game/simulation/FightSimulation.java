package cq.game.simulation;

import cq.game.models.Unit;
import cq.game.models.enums.UnitType;
import lombok.experimental.var;

import java.util.List;

public class FightSimulation {
    public static void fight(List<Unit> myUnits, List<Unit> enemyUnits) {
        double archers1 = countByType(myUnits, UnitType.ARCHER);
        double horsemen1 = countByType(myUnits, UnitType.HORSEMAN);
        double spearmen1 = countByType(myUnits, UnitType.SPEARMAN);

        double archers2 = countByType(enemyUnits, UnitType.ARCHER);
        double horsemen2 = countByType(enemyUnits, UnitType.HORSEMAN);
        double spearmen2 = countByType(enemyUnits, UnitType.SPEARMAN);

        double total1 = Math.max(archers1 + horsemen1 + spearmen1, 1);
        double total2 = Math.max(archers2 + horsemen2 + spearmen2, 1);

        double factor1 = archers1 * (1 + spearmen2 / total2) * Math.max(Math.pow(horsemen1 + 0.25, 0.2), 1) +
                spearmen1 * (1 + horsemen2 / total2) * Math.max(Math.pow(archers1 + 0.25, 0.2), 1) +
                horsemen1 * (1 + archers2 / total2) * Math.max(Math.pow(spearmen1 + 0.25, 0.2), 1);

        double factor2 = archers2 * (1 + spearmen1 / total1) * Math.max(Math.pow(horsemen2 + 0.25, 0.2), 1) +
                spearmen2 * (1 + horsemen1 / total1) * Math.max(Math.pow(archers2 + 0.25, 0.2), 1) +
                horsemen2 * (1 + archers1 / total1) * Math.max(Math.pow(spearmen2 + 0.25, 0.2), 1);

        if (factor1 == factor2) {
            System.err.println("They're all dead");
        }

        if (factor1 == 0 || factor2 == 0) {
            System.err.println("Everyone is alive");
        }


        if (factor1 > factor2) {
            double survivorratio = (factor1 - factor2 * factor2 / factor1) / factor1;
            System.err.println("Survivor ratio: " + survivorratio);

            double survivors = total1 * survivorratio;
            double archersrate = archers1 / (total1 + horsemen2 * 2 + archers2);
            double horsemenrate = horsemen1 / (total1 + spearmen2 * 2 + horsemen2);
            double spearmenrate = spearmen1 / (total1 + archers2 * 2 + spearmen2);
            double archers1r = Math.round(Math.min(survivors * archersrate / (archersrate + horsemenrate + spearmenrate), archers1));
            double horsemen1r = Math.round(Math.min(survivors * horsemenrate / (archersrate + horsemenrate + spearmenrate), horsemen1));
            double spearmen1r = Math.round(Math.min(survivors * spearmenrate / (archersrate + horsemenrate + spearmenrate), spearmen1));
            if (archers1r + spearmen1r + horsemen1r == 0) {
                var av = archers1 * archers1 * spearmen1 / horsemen2;
                var hv = horsemen1 * horsemen1 * archers1 / spearmen2;
                var sv = spearmen1 * spearmen1 * horsemen1 / archers2;
                if (av > hv && av > sv)
                    archers1r = 1;
                if (hv > av && hv > sv)
                    horsemen1r = 1;
                if (sv > av && sv > hv)
                    spearmen1r = 1;
            }
            archers1 = archers1r;
            spearmen1 = spearmen1r;
            horsemen1 = horsemen1r;
            archers2 = 0;
            spearmen2 = 0;
            horsemen2 = 0;
        } else {
            double survivorratio = (factor2 - factor1 * factor1 / factor2) / factor2;
            System.err.println("Survivor ratio: " + survivorratio);

            double survivors = total2 * survivorratio;
            double archersrate = archers2 / (total2 + horsemen1 * 2 + archers1);
            double horsemenrate = horsemen2 / (total2 + spearmen1 * 2 + horsemen1);
            double spearmenrate = spearmen2 / (total2 + archers1 * 2 + spearmen1);
            double archers2r = Math.round(Math.min(survivors * archersrate / (archersrate + horsemenrate + spearmenrate), archers2));
            double horsemen2r = Math.round(Math.min(survivors * horsemenrate / (archersrate + horsemenrate + spearmenrate), horsemen2));
            double spearmen2r = Math.round(Math.min(survivors * spearmenrate / (archersrate + horsemenrate + spearmenrate), spearmen2));
            if (archers2r + spearmen2r + horsemen2r == 0) {
                var av = archers2 * archers2 * spearmen2 / horsemen1;
                var hv = horsemen2 * horsemen2 * archers2 / spearmen1;
                var sv = spearmen2 * spearmen2 * horsemen2 / archers1;
                if (av > hv && av > sv)
                    archers2r = 1;
                if (hv > av && hv > sv)
                    horsemen2r = 1;
                if (sv > av && sv > hv)
                    spearmen2r = 1;
            }
            archers1 = 0;
            spearmen1 = 0;
            horsemen1 = 0;
            archers2 = archers2r;
            spearmen2 = spearmen2r;
            horsemen2 = horsemen2r;
        }
    }

    private static int countByType(List<Unit> units, UnitType type) {
        int counter = 0;
        for (Unit unit : units) {
            if (unit.getType().equals(type)) {
                counter++;
            }
        }
        return counter;
    }
}
