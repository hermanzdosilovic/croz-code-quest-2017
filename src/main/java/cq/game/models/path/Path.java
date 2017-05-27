package cq.game.models.path;



public abstract class Path {

    private Integer length;

    private String description;

    public Integer getLength() {
        return length;
    }

    void setLength(Integer length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;

        Path path = (Path) o;

        if (length != null ? !length.equals(path.length) : path.length != null) return false;
        return description != null ? description.equals(path.description) : path.description == null;
    }

    @Override
    public int hashCode() {
        int result = length != null ? length.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
