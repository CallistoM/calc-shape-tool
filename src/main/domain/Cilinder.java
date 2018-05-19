package main.domain;

public class Cilinder implements Shapes{
    private int id;
    private String name;
    private final int height;
    private final int radius;


    /**
     * Cilinder model
     * @param id id
     * @param name name
     * @param height height
     * @param radius radius
     */
    public Cilinder(int id, String name, double height, double radius) {
        this.id = id;
        this.name = name;
        this.height = (int) height;
        this.radius = (int) radius;
    }

    /**
     * @return id + name
     */
    public String toString() {
        return id + " : " + name;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public double height() {
        return this.height;
    }

    @Override
    public double length() {
        return 0;
    }

    @Override
    public double width() {
        return 0;
    }

    @Override
    public double radius() {
        return this.radius;
    }

    /**
     * @return volume
     */
    @Override
    public double volume() {
        double value = Math.PI * radius * radius * height;
        return Math.floor(value * 100) / 100;
    }

    /**
     * @return volumeview
     */
    @Override
    public String volumeView() {
        return id + ") " + "Cilinder " + radius + " " + height;
    }

    /**
     * @return shape name
     */
    @Override
    public String shapeName() {
        return name;
    }
}
