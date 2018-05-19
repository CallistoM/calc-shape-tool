package test.domain;

import main.domain.Cilinder;
import main.domain.Cubic;
import main.domain.Sphere;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 *
 */
public class JUnitTest {

    private Cilinder c;
    private Sphere s;
    private Cubic cb;

    @Before
    public void setUp() {
        // initializing Cylinder instance to work on
        c = new Cilinder(1, "Cilinder", 20.2, 21.2);
        s = new Sphere(1, "Sphere", 22.2);
        cb = new Cubic(1, "Cubic", 22.3, 33.2, 44.2);
    }

    /**
     * checks if set name is correct
     */
    @Test
    public void checkSetName() {
        // parameter is null, return value should be false
        assertFalse(c.shapeName().equals("cilinder"));
        // parameter is NOT null, return value should be true
        assertTrue(c.shapeName().equals("Cilinder"));
    }

    /**
     * check if volume calculation is true
     */
    @Test
    public void volumeCalculationCylinder() {
        c = new Cilinder(1, "Cilinder", 20.2, 21.2);

        double actual = c.volume();

        assertEquals(27708.84, actual, 0.001);
    }

    @Test
    public void volumeCalculationSphere() {
        s = new Sphere(1, "Sphere", 20.2);

        double actual = s.volume();

        assertEquals(33510.32163829113, actual, 0.001);
    }

    @Test
    public void volumeCalculationCubic() {
        cb = new Cubic(1, "Cubic", 20.2, 22.3, 22.1);

        double actual = cb.volume();

        assertEquals(9955.166000000001, actual, 0.001);
    }

    @Test
    public void checkIfJSONExists() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("load.json");
        File jsonFile = new File(url.getFile());
        assertTrue(jsonFile.exists());
    }



}
