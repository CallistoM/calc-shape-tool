package main.domain;

public class Sphere implements Shapes {
    private final int id;
    private final double radius;
    private final String name;

    /**
     * Sphere model
     * @param id id
     * @param name name
     * @param radius radius
     */
    public Sphere(int id, String name, double radius) {
        this.id = id;
        this.name = name;
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
        return 0;
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
     * @return calculated volume
     */
    @Override
    public double volume() {
        return (4 * Math.PI * radius * radius * radius) / 3;
    }

    /**
     * @return volume view
     */
    @Override
    public String volumeView() {
        return id + ") " + "Sphere " + radius;
    }

    /**
     * @return name
     */
    @Override
    public String shapeName() {
        return name;
    }
}
