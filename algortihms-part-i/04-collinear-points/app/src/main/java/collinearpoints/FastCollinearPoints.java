package collinearpoints;

import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments;
    private final LineSegment[] lineSegments;
    private final Point[] points;

    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
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

        this.numberOfSegments = 0;
        LineSegment[] segments = processSegments();
        this.lineSegments = new LineSegment[this.numberOfSegments];
        for (int i = 0; i < this.numberOfSegments; i++) {
            this.lineSegments[i] = segments[i];
        }
    }

    public int numberOfSegments() { // the number of line segments
        return this.numberOfSegments;
    }

    public LineSegment[] segments() { // the line segments
        return this.lineSegments.clone();
    }

    private LineSegment[] processSegments() {

        LineSegment[] lineSegments1 = new LineSegment[this.points.length];

        for (int i = 0; i < this.points.length; i++) {
            Point p = this.points[i];

            Point[] remainingPoints = new Point[this.points.length - 1];
            int counter = 0;
            for (int j = 0; j < this.points.length; j++) {
                if (j != i) {
                    remainingPoints[counter] = this.points[j];
                    counter++;
                }
            }

            Arrays.sort(remainingPoints, p.slopeOrder());
            int counterP = 0;
            int segCounter = 0;
            Point[] seg = new Point[this.points.length];
            double slope = p.slopeTo(remainingPoints[0]);
            double currentSlope = 0;
            do {
                currentSlope = p.slopeTo(remainingPoints[counterP]);
                if (currentSlope == slope) {
                    seg[segCounter] = remainingPoints[counterP];
                    segCounter++;
                }
                counterP++;
            } while (slope == currentSlope && counterP < remainingPoints.length);
            Point[] collectedPoints = new Point[segCounter + 1];
            for (int s = 0; s < segCounter; s++) {
                collectedPoints[s] = seg[s];
            }
            collectedPoints[segCounter] = p;

            if (collectedPoints.length >= 4) {
                Arrays.sort(collectedPoints);
                LineSegment ls = new LineSegment(collectedPoints[0], collectedPoints[segCounter]);


                boolean segmentUsed = false;
                if (this.numberOfSegments > 0) {
                    for (int ns = 0; ns < this.numberOfSegments; ns++) {
                        LineSegment line = lineSegments1[ns];
                        if (line.toString().equals(ls.toString())) {
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
        return lineSegments1;
    }
}
