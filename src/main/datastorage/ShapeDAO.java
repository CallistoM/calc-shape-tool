package main.datastorage;


import flexjson.JSONException;
import main.domain.Cilinder;
import main.domain.Cubic;
import main.domain.Shapes;
import main.domain.Sphere;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class ShapeDAO {


    public ShapeDAO() {
    }

    /**
     * return all shapes with respected model
     *
     * @return array with models
     */
    public ArrayList findShapes() {
        ArrayList shapes = new ArrayList<>();

        // open database connection
        DatabaseConnection connection = new DatabaseConnection();

        if (connection.openConnection()) {

            // if a connection was successfully setup, execute the SELECT statement.
            ResultSet resultset = connection.executeSQLSelectStatement(
                    "SELECT *\n" +
                            "FROM shape_objects\n" +
                            "JOIN shape ON shape_objects.shape = shape.shape_id\n");

            // check if result is not null
            if (resultset != null) {
                try {
                    while (resultset.next()) {

                        int idFromDb = resultset.getInt("id");
                        double heightFromDb = resultset.getDouble("height");
                        double radiusFromDb = resultset.getDouble("radius");
                        double lengthFromDb = resultset.getDouble("width");
                        double widthFromDb = resultset.getDouble("length");
                        String shapeNameFromDb = resultset.getString("name");


                        if (Objects.equals(shapeNameFromDb, "cilinder")) {
                            Cilinder newCilinder = new Cilinder(idFromDb, shapeNameFromDb, heightFromDb, radiusFromDb);
                            shapes.add(newCilinder);
                        }

                        if (Objects.equals(shapeNameFromDb, "sphere")) {
                            Sphere newSphere = new Sphere(idFromDb, shapeNameFromDb, radiusFromDb);
                            shapes.add(newSphere);
                        }

                        if (Objects.equals(shapeNameFromDb, "cubic")) {
                            Cubic newCubic = new Cubic(idFromDb, shapeNameFromDb, lengthFromDb, widthFromDb, heightFromDb);
                            shapes.add(newCubic);
                        }
                    }
                } catch (SQLException e) {
                    shapes.clear();
                }
            }

            // close database connection
            connection.closeConnection();
        }

        return shapes;
    }

    /**
     * delete shape
     *
     * @param id model id
     * @return Array
     */
    public boolean deleteShape(int id) {
        boolean result = false;

        // first open the database connection.
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.openConnection()) {
            // execute the delete statement using the shape_object id
            result = connection.executeSqlDmlStatement(
                    "DELETE FROM shape_objects WHERE id = " + id + ";");

            // finished with the connection, so close it.
            connection.closeConnection();
        }
        // else an error occurred leave 'shapes' to null.
        return result;
    }


    /**
     * insert new cilinder
     *
     * @return true or false
     */
    public boolean insertCilinder(double radius, double height) {
        boolean result = false;

        // first open the database connection.
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.openConnection()) {

            // set sql string
            String sql = "INSERT INTO shape_objects(shape, height, radius) " + "VALUES(" + 1 + "," + radius + "," + height + ")";

            // execute sql statement
            result = connection.executeSqlDmlStatement(sql);

            // closing connection
            connection.closeConnection();
        }

        return result;

    }

    /**
     * insert new sphere
     *
     * @return true or false
     */
    public boolean insertSphere(double radius) {
        boolean result = false;

        // first open the database connection.
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.openConnection()) {

            // set sql string
            String sql = "INSERT INTO shape_objects(shape, radius) " + "VALUES(" + 5 + "," + radius + ")";

            // execute sql statement
            result = connection.executeSqlDmlStatement(sql);

            // closing connection
            connection.closeConnection();
        }

        return result;

    }


    /**
     * insert new sphere
     *
     * @return true or false
     */
    public boolean insertCubic(double length, double height, double width) {
        boolean result = false;

        // first open the database connection.
        DatabaseConnection connection = new DatabaseConnection();

        if (connection.openConnection()) {

            // set sql string
            String sql = "INSERT INTO shape_objects(shape, length, height, width) " + "VALUES(" + 6 + "," + length + "," + height + "," + width + ")";

            // execute sql statement
            result = connection.executeSqlDmlStatement(sql);

            // closing connection
            connection.closeConnection();
        }

        return result;

    }

    /**
     * update cilinder
     *
     * @return true or false
     */
    public boolean updateCilinder(int id, double radius, double height) {

        boolean result = false;

        // first open the database connection.
        DatabaseConnection connection = new DatabaseConnection();

        if (connection.openConnection()) {

            // set sql string
            String sql = "UPDATE shape_objects " +
                    "SET radius=" + radius + "," + "height=" + height +
                    " WHERE id=" + id;


            // execute sql statement
            result = connection.executeSqlDmlStatement(sql);

            // closing connection
            connection.closeConnection();
        }

        return result;
    }

    /**
     * update sphere
     *
     * @return true or false
     */
    public boolean updateSphere(int id, double radius) {

        boolean result = false;

        // first open the database connection.
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.openConnection()) {

            // set sql string
            String sql = "UPDATE shape_objects " +
                    "SET radius=" + radius +
                    " WHERE id=" + id;

            // execute sql statement
            result = connection.executeSqlDmlStatement(sql);

            // closing connection
            connection.closeConnection();
        }

        return result;
    }

    /**
     * update cubic
     *
     * @return true or false
     */
    public boolean updateCubic(int id, double length, double height, double width) {
        boolean result = false;

        // first open the database connection.
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.openConnection()) {

            // set sql string
            String sql = "UPDATE shape_objects " +
                    "SET length=" + length + "," + "height=" + height +
                    "," + "width=" + width +
                    " WHERE id=" + id;

            // execute sql statement
            result = connection.executeSqlDmlStatement(sql);

            // closing connection
            connection.closeConnection();
        }

        return result;

    }

    /**
     * export data with chosen datastructure
     * @param path
     * @param datastructure datastructure JSON / TEXT
     * @return boolean
     */
    public boolean exportData(String path, String datastructure) {
        boolean result = false;
        String new_path = "";
        ArrayList<String> shapes = new ArrayList<>();
        ArrayList<Shapes> json_list = new ArrayList<Shapes>();

        // open database connection
        DatabaseConnection connection = new DatabaseConnection();

        if (connection.openConnection()) {

            // if a connection was successfully setup, execute the SELECT statement.
            ResultSet resultset = connection.executeSQLSelectStatement(
                    "SELECT *\n" +
                            "FROM shape_objects\n" +
                            "JOIN shape ON shape_objects.shape = shape.shape_id\n");

            // check if result is not null
            if (resultset != null) {
                try {
                    while (resultset.next()) {

                        // set data from database
                        int idFromDb = resultset.getInt("id");
                        double heightFromDb = resultset.getDouble("height");
                        double radiusFromDb = resultset.getDouble("radius");
                        double lengthFromDb = resultset.getDouble("width");
                        double widthFromDb = resultset.getDouble("length");
                        String shapeNameFromDb = resultset.getString("name");

                        // check if datastructure is JSON
                        if (datastructure.contains("JSON")) {

                            // set chosen path + format
                            new_path = path + "/data.json";

                            if (shapeNameFromDb.contains("cilinder")) {
                                Cilinder cilinder = new Cilinder(idFromDb, shapeNameFromDb, heightFromDb, radiusFromDb);
                                json_list.add(cilinder);
                            }

                            if (shapeNameFromDb.contains("sphere")) {
                                Sphere sphere = new Sphere(idFromDb, shapeNameFromDb, radiusFromDb);
                                json_list.add(sphere);
                            }

                            if (shapeNameFromDb.contains("cubic")) {
                                Cubic cubic = new Cubic(idFromDb, shapeNameFromDb, lengthFromDb, widthFromDb, heightFromDb);
                                json_list.add(cubic);
                            }

                            // execute write to JSON
                            writeToJSON(json_list, new_path);
                        }

                        // check if datastructure is pure text
                        if (datastructure.contains("Text")) {

                            // set chosen path with format
                            new_path = path + "/data.txt";

                            if (shapeNameFromDb.contains("cilinder")) {
                                shapes.add(idFromDb + " " + shapeNameFromDb + " " + heightFromDb + " " + radiusFromDb);
                            }

                            if (shapeNameFromDb.contains("sphere")) {
                                shapes.add(idFromDb + " " + shapeNameFromDb + " " + radiusFromDb);
                            }

                            if (shapeNameFromDb.contains("cubic")) {
                                shapes.add(idFromDb + " " + shapeNameFromDb + " " + lengthFromDb + " " + widthFromDb + " " + heightFromDb);
                            }
                        }
                    }
                    // execute write to file function
                    writeToFile(shapes, new_path);

                } catch (SQLException e) {
                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));
                    JOptionPane.showMessageDialog(new JFrame(), errors.toString(), "Dialog", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        return result;

    }

    /**
     * Write text to file with this function.
     * @param list list with all shapes
     * @param path chosen path
     */
    public static void writeToFile(java.util.List<String> list, String path) {

        // set file writer
        BufferedWriter out;
        try {
            // selected path
            File file = new File(path);
            out = new BufferedWriter(new FileWriter(file, true));
            for (Object s : list) {
                out.write((String) s);
                out.newLine();

            }
            out.close();
        } catch (IOException e) {
            // check errors and set show message dialog
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            JOptionPane.showMessageDialog(new JFrame(), errors.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * write json to file
     * @param list list with all shapes
     * @param path chosen path
     */
    public static void writeToJSON(ArrayList<Shapes> list, String path) {
        // set json array
        JSONArray collective_shapes = new JSONArray();

        for (int i = 0; i < list.size(); i++) {
            // set json objects
            JSONObject obj = new JSONObject();
            JSONObject cubic_values = new JSONObject();
            JSONObject sphere_values = new JSONObject();
            JSONObject cilinder_values = new JSONObject();

            try {
                if (list.get(i).shapeName().contains("cubic")) {
                    // put cubic values inside new json object
                    cubic_values.put("id", list.get(i).id());
                    cubic_values.put("length", list.get(i).length());
                    cubic_values.put("width", list.get(i).width());
                    cubic_values.put("height", list.get(i).height());
                    // put values inside collective object
                    obj.put("cubic", cubic_values);
                }
                if (list.get(i).shapeName().contains("sphere")) {
                    // put sphere values inside new json object
                    sphere_values.put("id", list.get(i).id());
                    sphere_values.put("radius", list.get(i).radius());
                    // put values inside collective object
                    obj.put("sphere", sphere_values);
                }

                if (list.get(i).shapeName().contains("cilinder")) {
                    // put cilinder values inside new json object
                    cilinder_values.put("id", list.get(i).id());
                    cilinder_values.put("height", list.get(i).height());
                    cilinder_values.put("radius", list.get(i).radius());
                    // put values inside collective object
                    obj.put("cilinder", cilinder_values);
                }
            } catch (JSONException e) {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                JOptionPane.showMessageDialog(new JFrame(), errors.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // add empty array
            collective_shapes.add(obj);
        }

        // try to write file to selected path else error
        try (FileWriter file = new FileWriter(path)) {
            file.write(collective_shapes.toJSONString());
            file.flush();

        } catch (IOException e) {
            // check errors and set show message dialog
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            JOptionPane.showMessageDialog(new JFrame(), errors.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
