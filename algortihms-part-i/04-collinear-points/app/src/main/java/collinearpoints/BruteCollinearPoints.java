package collinearpoints;

import java.util.Arrays;

public class BruteCollinearPoints {

    private final int possibleSegments;
    private Point[] points;
    private LineSegment[] lineSegments;
    private int numberOfSegments;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points

        if (points == null) {
            throw new IllegalArgumentException();
        }

        int n = points.length;
        this.points = new Point[n];

        for (int i = 0; i < n; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }

            this.points[i] = points[i];
        }

        Arrays.sort(this.points);

        for (int i = 1; i < n; i++) {
            if (this.points[i - 1].compareTo(this.points[i]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        this.possibleSegments = factorial(points.length) / (factorial(4) * factorial(points.length - 4));
        this.numberOfSegments = 0;
        LineSegment[] segments = processSegments();
        this.lineSegments = new LineSegment[this.numberOfSegments];
        for (int i = 0; i < this.numberOfSegments; i++) {
            this.lineSegments[i] = segments[i];
            System.out.println(segments[i]);
        }
    }

    public int numberOfSegments() {        // the number of line segments
        return this.numberOfSegments;
    }

    public LineSegment[] segments() {

        return this.lineSegments;
    }

    private LineSegment[] processSegments() {

        int numberOfPoints = this.points.length;
        LineSegment[] lineSegments1 = new LineSegment[this.possibleSegments];

        if (numberOfPoints >= 4) {

            for (int i = 0; i < numberOfPoints; i++) {
                Point p = this.points[i];
                Point[] remainingPoints = new Point[this.points.length - 1];
                int countR = 0;
                for (int j = 0; j < numberOfPoints ; j++) {
                    if (i != j) {
                        remainingPoints[countR++] = this.points[j];
                    }
                }

                Arrays.sort(remainingPoints, (q, r) -> (int) (p.slopeTo(q) - p.slopeTo(r)));

                for (int k = 0; k < remainingPoints.length - 2; k++) {
                    if (p.slopeTo(remainingPoints[k]) == p.slopeTo(remainingPoints[k + 1])
                            && p.slopeTo(remainingPoints[k + 1]) == p.slopeTo(remainingPoints[k + 2])) {
                        Point[] segmentPoints = new Point[4];
                        segmentPoints[0] = p;
                        segmentPoints[1] = remainingPoints[k];
                        segmentPoints[2] = remainingPoints[k + 1];
                        segmentPoints[3] = remainingPoints[k + 2];

                        Arrays.sort(segmentPoints);
                        LineSegment ls = new LineSegment(segmentPoints[0], segmentPoints[3]);
                        boolean segmentUsed = false;
                        if (this.numberOfSegments > 0) {
                            for(int ns = 0; ns < this.numberOfSegments; ns++) {
                                LineSegment l = lineSegments1[ns];
                                if (l.toString().equals(ls.toString())) {
                                    segmentUsed = true;
                                }
                            }
                        }

                        if (!segmentUsed) {
                            lineSegments1[this.numberOfSegments] = ls;
                            this.numberOfSegments++;
                        }
                    }
                }

            }

        }

        return lineSegments1;
    }

    private int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        return n * factorial(n - 1);
    }

}
