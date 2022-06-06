package collinearpoints;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FastCollinearPointsTest {

    @Test
    void throwsExceptionOnNullArgument() {

        assertThrows(IllegalArgumentException.class, () -> {
            FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(null);
        });
    }

    @Test
    void throwsExceptionOnNullPoint() {

        Point[] points = {  new Point(2, 3),
                null,
                new Point(2, 7),
                new Point(4, 5),
                new Point(7, 3),};

        assertThrows(IllegalArgumentException.class, () -> {
            FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        });
    }

    @Test
    void throwsExceptionOnRepeatedPoints() {

        Point[] points = {  new Point(2, 3),
                new Point(2, 7),
                new Point(4, 5),
                new Point(7, 3),
                new Point(2, 7)};

        assertThrows(IllegalArgumentException.class, () -> {
            FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        });
    }

    @Test
    void createsASegmentWithFourPoints() {
        Point p = new Point(0, 2);
        Point q = new Point(1, 3);
        Point r = new Point(2, 4);
        Point s = new Point(4, 6);
        Point t = new Point(3, 7);
        Point u = new Point(0, -2);
        Point[] points = {u, p, t, r, q, s};

        LineSegment[] expectedLS = new LineSegment[] {new LineSegment(p, s)};
        StringBuilder sb = new StringBuilder();
        for (LineSegment l : expectedLS) {
            sb.append(l).append("\n");
        }
        String expectedString = sb.toString();

        LineSegment[] actualLS = new FastCollinearPoints(points).segments();
        sb = new StringBuilder();
        for (LineSegment l : actualLS) {
            sb.append(l).append("\n");
        }
        String actualString = sb.toString();

        assertEquals(expectedString, actualString);

    }

    @Test
    void createsASegmentWithNegativePoints() {
        Point p = new Point(-2, 11);
        Point q = new Point(-1, 8);
        Point r = new Point(0, 5);
        Point s = new Point(1, 2);
        Point t = new Point(3, 7);
        Point u = new Point(0, -2);
        Point[] points = {u, p, t, r, q, s};

        LineSegment[] expectedLS = new LineSegment[] {new LineSegment(s,p)};
        StringBuilder sb = new StringBuilder();
        for (LineSegment l : expectedLS) {
            sb.append(l).append("\n");
        }
        String expectedString = sb.toString();

        LineSegment[] actualLS = new FastCollinearPoints(points).segments();
        sb = new StringBuilder();
        for (LineSegment l : actualLS) {
            sb.append(l).append("\n");
        }
        String actualString = sb.toString();

        assertEquals(expectedString, actualString);
    }

    @Test
    void createsOneSegmentWhenFivePointsAreCollinear() {
        Point p = new Point(-3, -3);
        Point q = new Point(-1, 1);
        Point r = new Point(0, 3);
        Point s = new Point(1, 5);
        Point t = new Point(3, 9);
        Point u = new Point(0, -2);
        Point[] points = {u, p, t, r, q, s};

        LineSegment[] expectedLS = new LineSegment[] {new LineSegment(p, t)};
        StringBuilder sb = new StringBuilder();
        for (LineSegment l : expectedLS) {
            sb.append(l).append("\n");
        }
        String expectedString = sb.toString();

        LineSegment[] actualLS = new FastCollinearPoints(points).segments();
        sb = new StringBuilder();
        for (LineSegment l : actualLS) {
            sb.append(l).append("\n");
        }
        String actualString = sb.toString();

        assertEquals(expectedString, actualString);
    }
}