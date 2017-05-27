package cq.game.api;

import cq.game.models.Archer;
import cq.game.models.Horseman;
import cq.game.models.Spearman;
import cq.game.models.Unit;

import java.util.ArrayList;
import java.util.List;

public class BaseStats {
    private Integer gold;
    private Integer points;
    private Integer archers;
    private Integer spearmen;
    private Integer horsemen;

    public BaseStats(Integer gold, Integer points, Integer archers, Integer spearmen, Integer horsemen) {
        this.gold = gold;
        this.points = points;
        this.archers = archers;
        this.spearmen = spearmen;
        this.horsemen = horsemen;
    }


    public BaseStats(String[] base) {
        this.gold = Integer.valueOf(base[0]);
        this.points = Integer.valueOf(base[1]);
        this.archers = Integer.valueOf(base[2]);
        this.spearmen = Integer.valueOf(base[3]);
        this.horsemen = Integer.valueOf(base[4]);
    }
    public Integer getGold() {
        return gold;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getNumberOfArchers() {
        return archers;
    }

    public Integer getNumberOfSpearmen() {
        return spearmen;
    }

    public Integer getNumberOfHorsemen() {
        return horsemen;
    }

    public List<Archer> getArchers(){
        List<Archer> archers = new ArrayList<>();
        for(int i = 0; i < this.archers; i++){
            archers.add(new Archer());
        }
        return archers;
    }

    public List<Horseman> getHorseman(){
        List<Horseman> horsemen = new ArrayList<>();
        for(int i = 0; i < this.horsemen; i++){
            horsemen.add(new Horseman());
        }
        return horsemen;
    }

    public List<Spearman> getSpearman(){
        List<Spearman> spearmen = new ArrayList<>();
        for(int i = 0; i < this.spearmen; i++){
            spearmen.add(new Spearman());
        }
        return spearmen;
    }

    public List<Unit> allUnits() {
        List<Unit> units = new ArrayList<>();
        units.addAll(getArchers());
        units.addAll(getHorseman());
        units.addAll(getSpearman());
        return units;
    }
}
