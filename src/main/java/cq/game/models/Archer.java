package cq.game.models;


import cq.game.models.enums.UnitType;

public class Archer extends Unit {

    public Archer()    {
        super("archer", 100);
        this.setType(UnitType.ARCHER);

        this.setSpeed(10);

        this.setPointFactor(12);
    }

}
