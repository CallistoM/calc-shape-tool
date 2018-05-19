package test.datastorage;

import main.datastorage.DatabaseConnection;
import org.junit.*;

import java.sql.ResultSet;

import static org.junit.Assert.assertTrue;

public class DatabaseConnectionTest {

    public DatabaseConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void doConnectTest() {
        // test preparation and execution
        ResultSet rs = null;
        DatabaseConnection connection = new DatabaseConnection();
        boolean result = connection.openConnection();
        if (result) {
            rs = connection.executeSQLSelectStatement("select * from shape_objects");
        }

        // test verification
        assertTrue("database connection successfully established", result);
        assertTrue("result set no null", rs != null);
    }
}
