package cq.game.models;


import cq.game.models.path.Path;

public class Order {

    private Path path;
    private Integer offset;

    public Order(Path path, Integer offset) {
        this.path = path;
        this.offset = offset;
    }

    public Path getPath() {
        return path;
    }

    public Integer getOffset() {
        return offset;
    }
}
