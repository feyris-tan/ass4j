package moe.yo3explorer.ass4j.model;

public enum Collisions {
    NORMAL("Normal");

    private final String normal;

    Collisions(String normal) {

        this.normal = normal;
    }

    public String getNormal() {
        return normal;
    }
}
