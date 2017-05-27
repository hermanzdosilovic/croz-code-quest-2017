package cq.game.models;

import cq.game.models.enums.UnitType;
import cq.game.models.path.Path;

public abstract class Unit {
    private String name;
    private Integer cost;

    public Unit(String name, Integer cost) {
        this.name = name;
        this.cost = cost;
    }

    public static Unit byType(UnitType type)  {
        if (type.equals(UnitType.ARCHER))   {
			Archer archer = new Archer();
			archer.setSpeed(10);
			archer.setPointFactor(12);
			return archer;
        }
        if (type.equals(UnitType.SPEARMAN))   {
			Spearman spearman = new Spearman();
			spearman.setSpeed(10);
			spearman.setPointFactor(8);
			return spearman;
        }
        if (type.equals(UnitType.HORSEMAN))   {
			Horseman horseman = new Horseman();
			horseman.setSpeed(20);
			horseman.setPointFactor(8);
			return horseman;
        }
        throw new RuntimeException();
    }

    public String getName(){
        return this.name;
    }

    private UnitType type;

    private Integer speed;

    private Integer pointFactor;

    private Path path;

    private Order order;

    private Integer offset = 0;

    public UnitType getType() {
        return type;
    }

    public Integer getSpeed() {
        return speed;
    }

    public Integer getPointFactor() {
        return pointFactor;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public void setPointFactor(Integer pointFactor) {
        this.pointFactor = pointFactor;
    }

    public Integer getCost(){
        return cost;
    }
    @Override
    public String toString() {
        return type + " - " + (path == null ? "unassigned" : path.getDescription()) + ":" + offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        if (type != unit.type) return false;
        if (speed != null ? !speed.equals(unit.speed) : unit.speed != null) return false;
        if (pointFactor != null ? !pointFactor.equals(unit.pointFactor) : unit.pointFactor != null) return false;
        if (path != null ? !path.equals(unit.path) : unit.path != null) return false;
        if (order != null ? !order.equals(unit.order) : unit.order != null) return false;
        return offset != null ? offset.equals(unit.offset) : unit.offset == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (speed != null ? speed.hashCode() : 0);
        result = 31 * result + (pointFactor != null ? pointFactor.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (offset != null ? offset.hashCode() : 0);
        return result;
    }
}
