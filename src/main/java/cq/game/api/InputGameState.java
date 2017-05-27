package cq.game.api;

import cq.game.models.Unit;

import java.util.List;

public class InputGameState {
    private Integer turn;
    private BaseStats myBase;
    private BaseStats enemyBase;
    private List<Unit> myUnits;
    private List<Unit> enemyUnits;
    private Integer archersToProduce;
    private Integer spearmenToProduce;
    private Integer horsemenToProduce;
    private String goldPathOwner;
    private String cannonPathOwner;
    private Boolean goldPathActive;
    private Boolean cannonPathActive;
    private List<Unit> movingUnits;

    public InputGameState(Integer turn, BaseStats myBase, BaseStats enemyBase, List<Unit> myUnits, List<Unit> enemyUnits,
                          Integer archersToProduce, Integer spearmenToProduce, Integer horsemenToProduce, String goldPathOwner,
                          String cannonPathOwner, Boolean goldPathActive, Boolean cannonPathActive, List<Unit> movingUnits) {
        this.turn = turn;
        this.myBase = myBase;
        this.enemyBase = enemyBase;
        this.myUnits = myUnits;
        this.enemyUnits = enemyUnits;
        this.archersToProduce = archersToProduce;
        this.spearmenToProduce = spearmenToProduce;
        this.horsemenToProduce = horsemenToProduce;
        this.goldPathOwner = goldPathOwner;
        this.cannonPathOwner = cannonPathOwner;
        this.goldPathActive = goldPathActive;
        this.cannonPathActive = cannonPathActive;
        this.movingUnits = movingUnits;
    }

    public Integer getTurn() {
        return turn;
    }

    public BaseStats getMyBase() {
        return myBase;
    }

    public BaseStats getEnemyBase() {
        return enemyBase;
    }

    public List<Unit> getMyUnits() {
        return myUnits;
    }

    public List<Unit> getEnemyUnits() {
        return enemyUnits;
    }

    public Integer getArchersToProduce() {
        return archersToProduce;
    }

    public Integer getSpearmenToProduce() {
        return spearmenToProduce;
    }

    public Integer getHorsemenToProduce() {
        return horsemenToProduce;
    }

    public String getGoldPathOwner() {
        return goldPathOwner;
    }

    public String getCannonPathOwner() {
        return cannonPathOwner;
    }

    public Boolean getGoldPathActive() {
        return goldPathActive;
    }

    public Boolean getCannonPathActive() {
        return cannonPathActive;
    }

    public List<Unit> getMovingUnits() {
        return movingUnits;
    }
}
