package test.datastorage;
import main.datastorage.DatabaseConnection;
import main.domain.Shapes;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class ShapeDAO {
    /**
     * stub
     */
    public List<Shapes> findShapes(Shapes shapes) {
        return new ArrayList<>();
    }

    /**
     * database must be up and running
     */
    @Test
    public void doInsertCilinder() {
        boolean rs = false;
        DatabaseConnection connection = new DatabaseConnection();
        boolean result = connection.openConnection();
        if (result) {
            String sql = "INSERT INTO shape_objects (shape, height, radius) VALUES (1, 234.2, 432.2)";
            rs = connection.executeSqlDmlStatement(sql);
        }

        // test verification
        assertTrue("database connection successfully established", result);
        assertTrue("result set no null", rs);
    }

    /**
     * database must be up and running
     */
    @Test
    public void doInsertSphere() {
        // test preparation and execution
        boolean rs = false;
        DatabaseConnection connection = new DatabaseConnection();
        boolean result = connection.openConnection();
        if (result) {
            String sql = "INSERT INTO shape_objects (shape, height, radius) VALUES (5, 234.2, 432.2)";
            rs = connection.executeSqlDmlStatement(sql);
        }

        // test verification
        assertTrue("database connection successfully established", result);
        assertTrue("result set no null", rs);
    }


}
