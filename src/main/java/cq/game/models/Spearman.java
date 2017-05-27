package cq.game.models;


import cq.game.models.enums.UnitType;

public class Spearman extends Unit {

    public Spearman(){
        super("spearman", 70);
        this.setType(UnitType.SPEARMAN);

        this.setSpeed(10);

        this.setPointFactor(8);
    }

}
