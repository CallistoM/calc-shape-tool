package main.presentation;

import main.datastorage.ShapeDAO;
import main.domain.Cilinder;
import main.domain.Cubic;
import main.domain.Shapes;
import main.domain.Sphere;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class VAT extends JFrame {
    private JComboBox shapeComboBox;
    private JTextField totalVolume;
    private JButton loadButton;
    private JButton deleteShapeButton;
    private JPanel first_panel;
    private JButton saveButton;
    private JTextField volume;
    private JList<String> shapeList;
    private JButton exportButton;
    private boolean commit;

    public VAT() {

        double sum = 0;
        DefaultListModel<String> dlm = new DefaultListModel<>();
        List<Shapes> data = getAllShapes();

        for (Shapes shape : data) {
            if (Objects.equals(shape.shapeName(), "cilinder")) {
                sum += shape.volume();
                double cilinder_volume = shape.volume();
                String cilinder_full = String.valueOf(sum);
                totalVolume.setText(cilinder_full);
                dlm.addElement(shape.volumeView());
            }
        }

        shapeList.setModel(dlm);

        saveButton.addActionListener(e ->
                customDialog()
        );

        loadButton.addActionListener(event -> {

            // set file chooser library
            JFileChooser fileChooser = new JFileChooser();

            // set current home directory for selection
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            // open dialog for file selection
            int result = fileChooser.showOpenDialog(this);

            // checks approved option
            if (result == JFileChooser.APPROVE_OPTION) {

                // set selected file path
                File selectedFile = fileChooser.getSelectedFile();

                // check extension of file to load correct
                String extension = selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().lastIndexOf("."), selectedFile.getAbsolutePath().length());

                // if extension is set to .txt then open function
                if (Objects.equals(extension, ".txt")) {
                    openTextfile(selectedFile);
                }

                if (Objects.equals(extension, ".json")) {
                    openJSONFile(selectedFile);
                }


            }


        });


        deleteShapeButton.addActionListener(e -> {

            // delete index from list
            String s = shapeList.getSelectedValue();


            // delete id from database
            String substr = "[)]";
            String[] parts = s.split(substr);
            String deletedId = parts[0];
            ShapeDAO shapesDAO = new ShapeDAO();
            Integer deletedInt = Integer.parseInt(deletedId);
            shapesDAO.deleteShape(deletedInt);

            //valid value when value pressed
            int index = shapeList.getSelectedIndex();

            // if isn't there any row selected, then jList1.getSelectedIndex() returns -1
            int selectedIndex = shapeList.getSelectedIndex();

            if (selectedIndex != -1) { // or selectedIndex > -1
                DefaultListModel listModel = (DefaultListModel) shapeList.getModel();
                listModel.removeElementAt(index);
            }

            // refresh list
            refreshList();

        });

        shapeList.addMouseListener(new MouseAdapter() {
            public boolean commit;

            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {

                    String s = (String) list.getSelectedValue();

                    String[] extended_string = s.replace(")", "").split("\\s+");

                    int id = Integer.parseInt(extended_string[0]);

                    if (extended_string[1].contains("Cilinder")) {
                        // set text fields
                        JTextField height = new JTextField();
                        JTextField radius = new JTextField();

                        height.setText(extended_string[2]);
                        radius.setText(extended_string[3]);


                        // set component with fields
                        final JComponent[] inputs = new JComponent[]{
                                new JLabel("Height"),
                                height,
                                new JLabel("Radius"),
                                radius,
                        };

                        // set dialog
                        int result = JOptionPane.showConfirmDialog(null, inputs, "Update Cilinder", JOptionPane.PLAIN_MESSAGE);

                        // check if dialog ok button is clicked
                        if (result == JOptionPane.OK_OPTION) {

                            double height_ = 0;
                            double radius_ = 0;

                            if (isDouble(height.getText())) {
                                height_ = Double.parseDouble(height.getText());
                                this.commit = true;
                            } else {
                                this.commit = false;
                                JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value", "Dialog",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                            if (isDouble(radius.getText())) {
                                this.commit = true;
                                radius_ = Double.parseDouble(radius.getText());
                            } else {
                                this.commit = false;
                                JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value for radius", "Dialog",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                            if (this.commit) {
                                // update cilinder
                                ShapeDAO shapesDAO = new ShapeDAO();
                                shapesDAO.updateCilinder(id, radius_, height_);
                            }

                        } else {
                            // panel is closed or canceled
                            System.out.println("User canceled / closed the dialog, result = " + result);
                        }

                    }
                    if (extended_string[1].contains("Sphere")) {

                        // set textfield + final component
                        JTextField sphere_radius = new JTextField();

                        sphere_radius.setText(extended_string[2]);

                        final JComponent[] inputs = new JComponent[]{
                                new JLabel("Radius"),
                                sphere_radius
                        };

                        // open dialog
                        int result = JOptionPane.showConfirmDialog(null, inputs, "New Sphere", JOptionPane.PLAIN_MESSAGE);
                        if (result == JOptionPane.OK_OPTION) {
                            double radius_ = 0;

                            if (isDouble(sphere_radius.getText())) {
                                radius_ = Double.parseDouble(sphere_radius.getText());
                                this.commit = true;
                            } else {
                                this.commit = false;
                                JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value  ", "Dialog",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                            if (this.commit) {
                                // update sphere
                                ShapeDAO shapesDAO = new ShapeDAO();
                                shapesDAO.updateSphere(id, radius_);
                            }

                        } else {
                            System.out.println("User canceled / closed the dialog, result = " + result);
                        }
                    }
                    if (extended_string[1].contains("Cubic")) {

                        // set textfields
                        JTextField height = new JTextField();
                        JTextField length = new JTextField();
                        JTextField width = new JTextField();

                        height.setText(extended_string[2]);
                        length.setText(extended_string[3]);
                        width.setText(extended_string[4]);

                        // set textfield + final component

                        final JComponent[] inputs = new JComponent[]{
                                new JLabel("Height"),
                                height,
                                new JLabel("Length"),
                                length,
                                new JLabel("Width"),
                                width
                        };

                        // open dialog
                        int result = JOptionPane.showConfirmDialog(null, inputs, "New Cubic", JOptionPane.PLAIN_MESSAGE);

                        // check if dialog ok button is clicked
                        if (result == JOptionPane.OK_OPTION) {

                            // double variables
                            double height_ = 0;
                            double length_ = 0;
                            double width_ = 0;

                            if (isDouble(height.getText())) {
                                height_ = Double.parseDouble(height.getText());
                                this.commit = true;
                            } else {
                                this.commit = false;
                                JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value ", "Dialog",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                            if (isDouble(length.getText())) {
                                this.commit = true;
                                length_ = Double.parseDouble(length.getText());
                            } else {
                                this.commit = false;
                                JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value for length", "Dialog",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                            if (isDouble(width.getText())) {
                                this.commit = true;
                                width_ = Double.parseDouble(width.getText());
                            } else {
                                this.commit = false;
                                JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value for width", "Dialog",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                            if (this.commit) {
                                // update cubic
                                ShapeDAO shapesDAO = new ShapeDAO();
                                shapesDAO.updateCubic(id, length_, height_, width_);
                            }


                        } else {
                            // panel is closed or canceled
                            System.out.println("User canceled / closed the dialog, result = " + result);
                        }

                    }

                    refreshList();
                }
            }
        });

        shapeComboBox.addItemListener(e -> {
            // check if item is selected
            if (e.getStateChange() == ItemEvent.SELECTED) {

                // get item that is selected
                Object item = e.getItem();

                // set string to lowercase
                item.toString().toLowerCase();

                // set double
                double sum1 = 0; //average will have decimal point

                // set list model
                DefaultListModel<String> dlm1 = new DefaultListModel<>();

                // get all shapes and put them in list
                List<Shapes> data1 = getAllShapes();

                // set index
                shapeList.setSelectedIndex(0);

                // for loop with list data
                for (Shapes shape : data1) {
                    // check which shape is set
                    if (Objects.equals(shape.shapeName(), item.toString().toLowerCase())) {

                        // set volume
                        sum1 += shape.volume();
                        String cilinder_full = String.valueOf(sum1);

                        totalVolume.setText(cilinder_full);
                        dlm1.addElement(shape.volumeView());
                    }
                }

                shapeList.setModel(dlm1);
            }
        });
        shapeList.addListSelectionListener(e -> {
            JList list = (JList) e.getSource();

            final Object selectedValue = list.getSelectedValue();

            String s = (String) list.getSelectedValue();
            if (selectedValue != null) {

                String[] extended_string = s.replace(")", "").split("\\s+");

                if (extended_string[1].contains("Sphere")) {
                    double radius_sphere = Double.parseDouble(extended_string[2]);
                    Sphere sphere = new Sphere(0, "", radius_sphere);
                    volume.setText(String.valueOf(sphere.volume()));
                }
                if (extended_string[1].contains("Cilinder")) {
                    double radius_sphere = Double.parseDouble(extended_string[2]);
                    double height_sphere = Double.parseDouble(extended_string[3]);
                    Cilinder cilinder = new Cilinder(0, "", radius_sphere, height_sphere);
                    volume.setText(String.valueOf(cilinder.volume()));
                }
                if (extended_string[1].contains("Cubic")) {
                    double length_sphere = Double.parseDouble(extended_string[2]);
                    double width_sphere = Double.parseDouble(extended_string[3]);
                    double height_sphere = Double.parseDouble(extended_string[4]);
                    Cubic cubic = new Cubic(0, "", length_sphere, width_sphere, height_sphere);
                    volume.setText(String.valueOf(cubic.volume()));
                }
            }


        });
        exportButton.addActionListener(e -> {
            // set options
            String[] items = {"Text", "JSON"};

            // open dialog with above options
            int selection = JOptionPane.showOptionDialog(null, null, "Export data",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    items, items[0]);

            // check if equals sphere
            if (items[selection].equals("Text")) {

                // set file chooser library
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                fileChooser.setCurrentDirectory(new java.io.File("."));

                fileChooser.setAcceptAllFileFilterUsed(false);

                fileChooser.setDialogTitle("Choose directory to export file");

                // open dialog for file selection
                int result = fileChooser.showOpenDialog(this);

                // checks approved option
                if (result == JFileChooser.APPROVE_OPTION) {

                    // set selected file path
                    File selectedFile = fileChooser.getCurrentDirectory();

                    // set class
                    ShapeDAO shapesDAO = new ShapeDAO();

                    // export data
                    shapesDAO.exportData(selectedFile.toString(), items[selection]);
                }
            }

            if (items[selection].equals("JSON")) {

                // set file chooser library
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                fileChooser.setDialogTitle("Choose directory to export file");

                // set current home directory for selection
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

                // open dialog for file selection
                int result = fileChooser.showOpenDialog(this);

                // checks approved option
                if (result == JFileChooser.APPROVE_OPTION) {

                    // set selected file path
                    File selectedFile = fileChooser.getCurrentDirectory();

                    // set class
                    ShapeDAO shapesDAO = new ShapeDAO();

                    // export data
                    shapesDAO.exportData(selectedFile.toString(), items[selection]);
                }
            }
        });
    }


    /**
     * open custom dialog
     */
    private void customDialog() {
        // set options
        String[] items = {"Cilinder", "Sphere", "Cubic"};

        // open dialog with above options
        int selection = JOptionPane.showOptionDialog(null, null, "New shape",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                items, items[0]);

        // check if equals sphere
        if (items[selection].equals("Sphere")) {

            // set textfield + final component
            JTextField sphere_radius = new JTextField();
            final JComponent[] inputs = new JComponent[]{
                    new JLabel("Radius"),
                    sphere_radius
            };

            // open dialog
            int result = JOptionPane.showConfirmDialog(null, inputs, "New Sphere", JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                double radius_ = 0;

                if (isDouble(sphere_radius.getText())) {
                    radius_ = Double.parseDouble(sphere_radius.getText());
                    this.commit = true;
                } else {
                    this.commit = false;
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value  ", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (this.commit) {
                    // insert new sphere
                    ShapeDAO shapesDAO = new ShapeDAO();
                    shapesDAO.insertSphere(radius_);
                }

            } else {
                System.out.println("User canceled / closed the dialog, result = " + result);
            }
        }

        if (items[selection].equals("Cubic")) {

            // set textfields
            JTextField height = new JTextField();
            JTextField length = new JTextField();
            JTextField width = new JTextField();

            // set textfield + final component

            final JComponent[] inputs = new JComponent[]{
                    new JLabel("Height"),
                    height,
                    new JLabel("Length"),
                    length,
                    new JLabel("Width"),
                    width
            };

            // open dialog
            int result = JOptionPane.showConfirmDialog(null, inputs, "New Cubic", JOptionPane.PLAIN_MESSAGE);

            // check if dialog ok button is clicked
            if (result == JOptionPane.OK_OPTION) {

                // double variables
                double height_ = 0;
                double length_ = 0;
                double width_ = 0;

                if (isDouble(height.getText())) {
                    height_ = Double.parseDouble(height.getText());
                    this.commit = true;
                } else {
                    this.commit = false;
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value ", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (isDouble(length.getText())) {
                    this.commit = true;
                    length_ = Double.parseDouble(length.getText());
                } else {
                    this.commit = false;
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value for length", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (isDouble(width.getText())) {
                    this.commit = true;
                    width_ = Double.parseDouble(width.getText());
                } else {
                    this.commit = false;
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value for width", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (this.commit) {
                    // insert new cubic
                    ShapeDAO shapesDAO = new ShapeDAO();
                    shapesDAO.insertCubic(length_, height_, width_);
                }


            } else {
                // panel is closed or canceled
                System.out.println("User canceled / closed the dialog, result = " + result);
            }
        }

        if (items[selection].equals("Cilinder")) {

            // set text fields
            JTextField height = new JTextField();
            JTextField radius = new JTextField();


            // set component with fields
            final JComponent[] inputs = new JComponent[]{
                    new JLabel("Height"),
                    height,
                    new JLabel("Radius"),
                    radius,
            };

            // set dialog
            int result = JOptionPane.showConfirmDialog(null, inputs, "New Cilinder", JOptionPane.PLAIN_MESSAGE);

            // check if dialog ok button is clicked
            if (result == JOptionPane.OK_OPTION) {

                double height_ = 0;
                double radius_ = 0;

                if (isDouble(height.getText())) {
                    height_ = Double.parseDouble(height.getText());
                    this.commit = true;
                } else {
                    this.commit = false;
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (isDouble(radius.getText())) {
                    this.commit = true;
                    radius_ = Double.parseDouble(radius.getText());
                } else {
                    this.commit = false;
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter a double value for radius", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (this.commit) {
                    // insert new cilinder
                    ShapeDAO shapesDAO = new ShapeDAO();
                    shapesDAO.insertCilinder(radius_, height_);
                }

            } else {
                // panel is closed or canceled
                System.out.println("User canceled / closed the dialog, result = " + result);
            }
        }

        // refresh list
        refreshList();
    }


    /**
     * init components
     */
    public void initComponents() {

        // set jframe
        JFrame frame = new JFrame("Vat TOOL");

        // set content pane with first_panel xml file
        frame.setContentPane(new VAT().first_panel);

        // pack frame
        frame.pack();

        // center frame
        frame.setLocationRelativeTo(null);

        // set visibility
        frame.setVisible(true);

    }


    /**
     * set selected file and read every line.
     *
     * @param selectedFile selected file
     */
    public void openTextfile(File selectedFile) {

        ShapeDAO shapesDAO = new ShapeDAO();

        try {

            //Create object of FileReader
            FileReader inputFile = new FileReader(selectedFile.getAbsolutePath());

            //Instantiate the BufferedReader Class
            BufferedReader bufferReader = new BufferedReader(inputFile);

            //Variable to hold the one line data
            String line;

            // Read file line by line and print on the console
            while ((line = bufferReader.readLine()) != null) {
                String[] parts = line.split("\\s+");

                if (parts[0].equals("Cilinder")) {
                    // insert new cilinder
                    double radius_ = Double.parseDouble(parts[1]);
                    double height_ = Double.parseDouble(parts[2]);

                    shapesDAO.insertCilinder(radius_, height_);
                }

                if (parts[0].equals("Sphere")) {
                    double radius_ = Double.parseDouble(parts[1]);
                    shapesDAO.insertSphere(radius_);
                }

                if (parts[0].equals("Cubic")) {
                    double length_ = Double.parseDouble(parts[1]);
                    double height_ = Double.parseDouble(parts[2]);
                    double width_ = Double.parseDouble(parts[3]);
                    shapesDAO.insertCubic(length_, height_, width_);
                }
            }

            //Close the buffer reader
            bufferReader.close();

            // refresh list
            refreshList();

        } catch (Exception e) {
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }

    }

    /**
     *
     */
    public void openJSONFile(File selectedFile) {

        ShapeDAO shapesDAO = new ShapeDAO();

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(selectedFile.getAbsolutePath())) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject sphere = (JSONObject) jsonObject.get("Sphere");
            JSONObject cilinder = (JSONObject) jsonObject.get("Cilinder");
            JSONObject cubic = (JSONObject) jsonObject.get("Cubic");

            if (cubic.containsKey("length") && cubic.containsKey("height") && cubic.containsKey("width")) {
                double length_cubic = Double.parseDouble(cubic.get("length").toString());
                double height_cubic = Double.parseDouble(cubic.get("height").toString());
                double width_cubic = Double.parseDouble(cubic.get("width").toString());
                shapesDAO.insertCubic(length_cubic, height_cubic, width_cubic);
            }

            if (sphere.containsKey("radius")) {
                double radius_sphere = Double.parseDouble(sphere.get("radius").toString());
                shapesDAO.insertSphere(radius_sphere);
            }


            if (cilinder.containsKey("height") && cilinder.containsKey("radius")) {
                double height_cilinder = Double.parseDouble(cilinder.get("height").toString());
                double radius_cilinder = Double.parseDouble(cilinder.get("radius").toString());
                shapesDAO.insertCilinder(height_cilinder, radius_cilinder);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null,
                "JSON data to database",
                "Successful",
                JOptionPane.INFORMATION_MESSAGE);

        refreshList();
    }

    /**
     * helper function to get all current shapes
     *
     * @return ArrayList with current shapes
     */
    private ArrayList<main.domain.Shapes> getAllShapes() {
        ShapeDAO shapesDAO = new ShapeDAO();
        ArrayList<main.domain.Shapes> shapes = shapesDAO.findShapes();
        return shapes;
    }

    /**
     * refresh list with current shapes
     *
     * @return boolean
     */
    private boolean refreshList() {

        // set shape box selected item to string
        Object item = shapeComboBox.getSelectedItem().toString();

        // to lower case
        item.toString().toLowerCase();

        // set decimal for calculation
        double sum1 = 0;

        // set default list model to match jlist
        DefaultListModel<String> dlm1 = new DefaultListModel<>();

        // get all current data
        List<Shapes> data1 = getAllShapes();

        // set selected index
        shapeList.setSelectedIndex(0);

        // loop to match current selected combo box
        for (Shapes shape : data1) {
            if (Objects.equals(shape.shapeName(), item.toString().toLowerCase())) {

                // calculate volume
                sum1 += shape.volume();
                String cilinder_full = String.valueOf(sum1);

                // calculate total volume
                totalVolume.setText(cilinder_full);

                // insert all data in default model list
                dlm1.addElement(shape.volumeView());
            }
        }

        // set model list
        shapeList.setModel(dlm1);

        return true;
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
