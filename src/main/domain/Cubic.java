package main.domain;

public class Cubic implements Shapes {
    private final int id;
    private double length;
    private double width;
    private double height;
    private String name;

    /**
     * cubic model
     * @param id id
     * @param name name
     * @param length length from db
     * @param width width from db
     * @param height height from db
     */
    public Cubic(int id, String name, double length, double width, double height) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.width = width;
        this.height = height;
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
        return this.length;
    }

    @Override
    public double width() {
        return this.width;
    }

    @Override
    public double radius() {
        return 0;
    }

    /**
     * returns volume
     */
    @Override
    public double volume() {
        return length * width * height;
    }

    /**
     * @return calculated cubic
     */
    @Override
    public String volumeView() {
        return id + ") " + "Cubic " + length + " " + width + " " + height;
    }


    /**
     * @return name
     */
    @Override
    public String shapeName() {
        return name;
    }
}
