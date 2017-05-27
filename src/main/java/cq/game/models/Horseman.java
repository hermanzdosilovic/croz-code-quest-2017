package cq.game.models;


import cq.game.models.enums.UnitType;

public class Horseman extends Unit {

    public Horseman()   {
        super("horseman", 100);
        this.setType(UnitType.HORSEMAN);

        this.setSpeed(15);

        this.setPointFactor(8);
    }

}
