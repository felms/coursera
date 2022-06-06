package collinearpoints;

import java.util.Arrays;

public class BruteCollinearPoints {

    private final Point[] points;
    private final LineSegment[] lineSegments;
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

        this.numberOfSegments = 0;
        LineSegment[] segments = processSegments();
        this.lineSegments = new LineSegment[this.numberOfSegments];
        for (int i = 0; i < this.numberOfSegments; i++) {
            this.lineSegments[i] = segments[i];
        }
    }

    public int numberOfSegments() {        // the number of line segments
        return this.numberOfSegments;
    }

    public LineSegment[] segments() {
        return this.lineSegments.clone();
    }

    private LineSegment[] processSegments() {

        int numberOfPoints = this.points.length;
        LineSegment[] lineSegments1 = new LineSegment[this.points.length];

        if (numberOfPoints >= 4) {
            Point[] localPoints = this.points.clone();
            while (localPoints.length >= 4) {
                Point p = localPoints[0];
                Point[] remainingPoints = new Point[localPoints.length - 1];
                int countR = 0;
                for (int j = 1; j < localPoints.length; j++) {
                    remainingPoints[countR++] = localPoints[j];
                }

                Arrays.sort(remainingPoints, p.slopeOrder());
                Point[] segmentPoints = new Point[4];
                double slope = p.slopeTo(remainingPoints[0]);
                segmentPoints[0] = p;
                int addedPoints = 1;
                int k = 0;
                while (addedPoints < 4 && k < remainingPoints.length) {

                    if (p.slopeTo(remainingPoints[k]) == slope) {
                        segmentPoints[addedPoints] = remainingPoints[k];
                        addedPoints++;
                    }

                    k++;
                }

                if (addedPoints >= 4) {
                    Arrays.sort(segmentPoints);
                    LineSegment ls = new LineSegment(segmentPoints[0], segmentPoints[3]);
                    boolean segmentUsed = false;
                    for (int ns = 0; ns < this.numberOfSegments; ns++) {
                        LineSegment line = lineSegments1[ns];
                        if (line.toString().equals(ls.toString())) {
                            segmentUsed = true;
                        }
                    }

                    if (!segmentUsed) {
                        lineSegments1[this.numberOfSegments] = ls;
                        this.numberOfSegments++;
                    }
                }
                localPoints = remainingPoints.clone();
            }

        }

        return lineSegments1;
    }

}
