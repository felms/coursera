package collinearpoints;

import java.util.Arrays;

public class FastCollinearPoints {
    private int numberOfSegments;
    private final LineSegment[] lineSegments;
    private final Point[] points;
    private final Point[][] usedSegments;

    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
        if (points == null) {
            throw new IllegalArgumentException();
        }

        int n = points.length;
        this.points = new Point[n];
        this.usedSegments = new Point[n * 10][];

        for (int i = 0; i < n; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }

            this.points[i] = points[i];
            this.usedSegments[i] = new Point[2];
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

        LineSegment[] lineSegments1 = new LineSegment[this.points.length * 10];

        for (int i = 0; i < this.points.length; i++) {
            Point p = this.points[i];

            Point[] remainingPoints = new Point[this.points.length - 1];
            int countRemaining = 0;
            for (int j = 0; j < this.points.length; j++) {
                if (i != j) {
                    remainingPoints[countRemaining] = this.points[j];
                    countRemaining++;
                }
            }

            Arrays.sort(remainingPoints, p.slopeOrder());

            for (Point remainingPoint : remainingPoints) {
                double slope = p.slopeTo(remainingPoint);

                Point[] sPoints = new Point[this.points.length];
                int countSlopePoints = 0;

                for (Point q : remainingPoints) {
                    if (p.slopeTo(q) == slope) {
                        sPoints[countSlopePoints] = q;
                        countSlopePoints++;
                    }
                }

                sPoints[countSlopePoints++] = p;

                if (countSlopePoints >= 4) {
                    if (countSlopePoints < this.points.length) {
                        Point[] aux = new Point[countSlopePoints];
                        for (int k = 0; k < countSlopePoints; k++) {
                            aux[k] = sPoints[k];
                        }
                        sPoints = aux;
                    }

                    Arrays.sort(sPoints);

                    Point segStart = sPoints[0];
                    Point segEnd = sPoints[sPoints.length - 1];

                    boolean usedSegment = false;
                    for (int nos = 0; nos < numberOfSegments; nos++) {
                        Point[] testedSeg = this.usedSegments[nos];
                        if (testedSeg[0].compareTo(segStart) == 0
                                && testedSeg[1].compareTo(segEnd) == 0) {
                            usedSegment = true;
                            break;
                        }

                    }

                    if (!usedSegment) {
                        lineSegments1[this.numberOfSegments] = new LineSegment(segStart, segEnd);
                        usedSegments[this.numberOfSegments] = new Point[]{segStart, segEnd};
                        this.numberOfSegments++;
                    }

                }

            }

        }
        return lineSegments1;
    }
}
