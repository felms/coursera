package collinearpoints;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {


    @Test
    void testCompareY1LessThanY2() {
        Point a = new Point(2, 2);
        Point b = new Point(2, 3);

        assertEquals(-1, a.compareTo(b));
    }

    @Test
    void testCompareY1BiggerThanY2() {
        Point a = new Point(2, 3);
        Point b = new Point(2, 2);

        assertEquals(1, a.compareTo(b));
    }




    @Test
    void testCompareX1LessThanX2() {
        Point a = new Point(2, 3);
        Point b = new Point(3, 3);

        assertEquals(-1, a.compareTo(b));
    }

    @Test
    void testCompareX1BiggerThanX2() {
        Point a = new Point(3, 3);
        Point b = new Point(2, 3);

        assertEquals(1, a.compareTo(b));
    }


    @Test
    void testCompareTwoEqualPoints() {
        Point a = new Point(2, 3);
        Point b = new Point(2, 3);

        assertEquals(0, a.compareTo(b));
    }


    @Test
    void testTheSlopeOfTwoPoints() {
        Point a = new Point(2, 3);
        Point b = new Point(7, -9);

        assertEquals(-2.4, a.slopeTo(b));
    }

    @Test
    void testTheSlopeOfAHorizontalLine() {
        Point a = new Point(2, 3);
        Point b = new Point(7, 3);

        assertEquals(+0.0, a.slopeTo(b));
    }

    @Test
    void testTheSlopeOfAVerticalLine() {
        Point a = new Point(2, 3);
        Point b = new Point(2, 7);

        assertEquals(Double.POSITIVE_INFINITY, a.slopeTo(b));
    }

    @Test
    void testTheSlopeOfADegenerateSegment() {
        Point a = new Point(2, 3);
        Point b = new Point(2, 3);

        assertEquals(Double.NEGATIVE_INFINITY, a.slopeTo(b));
    }

}