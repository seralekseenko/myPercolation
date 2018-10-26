import org.junit.Test;

import static org.junit.Assert.*;

public class PercolationTest {


    @Test
    public void Percolation() {
        Percolation percolation = new Percolation(4);

        assertFalse(percolation == null);
        assertEquals(0, percolation.numberOfOpenSites());
    }

    @Test
    public void testOpenInvalidCoordinate() {
        Percolation percolation = new Percolation(4);

        try {
            percolation.open(0, 3);
            percolation.open(-1, 3);
            percolation.open(1, 5);
            percolation.open(0, 0);
        } catch (IllegalArgumentException e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void testOpenValidCoordinate() {
        Percolation percolation = new Percolation(4);

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 4);
        percolation.open(4, 4);
    }

    @Test
    public void testIsOpen() {
        Percolation percolation = new Percolation(4);

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);

        assertTrue(percolation.isOpen(1, 1));
        assertTrue(percolation.isOpen(2, 1));
        assertTrue(percolation.isOpen(2, 2));
    }

    @Test
    public void testIsFull() {
        Percolation percolation = new Percolation(4);

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);

        assertTrue(percolation.isFull(1, 1));
        assertTrue(percolation.isFull(2, 2));
        assertFalse(percolation.isFull(3, 3));
    }

    @Test
    public void testNumberOfOpenSites() {
        Percolation percolation = new Percolation(4);

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 4);
        percolation.open(4, 4);

        assertEquals(7, percolation.numberOfOpenSites());
    }

    @Test
    public void testPercolates() {
        Percolation percolation = new Percolation(4);

        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 4);
        percolation.open(4, 4);

        assertTrue(percolation.percolates());
    }
}