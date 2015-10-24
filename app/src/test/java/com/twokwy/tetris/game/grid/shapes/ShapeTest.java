package com.twokwy.tetris.game.grid.shapes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Created by anita on 24/10/2015.
 */
public class ShapeTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateShapeWithNoSetsOfCoordinates() throws Exception {
        try {
            new Shape(ImmutableList.<ImmutableSet<Coordinate>>of(), 0);
            fail("Should have thrown an IllegalStateException for passing an empty list");
        } catch(IllegalArgumentException e) {}
    }

    @Test
    public void testGetCoordinatesForRotation_line() throws Exception {
        // a small line consisting of two adjacent co-ordinates
        final ImmutableSet<Coordinate> horizontalCoords = ImmutableSet.of(
                new Coordinate(0, 0), new Coordinate(1, 0));
        final ImmutableSet<Coordinate> verticalCoords = ImmutableSet.of(
                new Coordinate(0, 0),
                new Coordinate(0, 1));
        final Shape line = new Shape(ImmutableList.of(horizontalCoords, verticalCoords), 0);

        ImmutableSet coordsToTest = line.getCoordinatesForNRotations(1);
        assertEquals(verticalCoords, coordsToTest);

        coordsToTest = line.getCoordinatesForNRotations(0);
        assertEquals(horizontalCoords, coordsToTest);

        coordsToTest = line.getCoordinatesForNRotations(-1);
        assertEquals(verticalCoords, coordsToTest);

        // should return horizontal co-ordinates for even numbers of rotations
        coordsToTest = line.getCoordinatesForNRotations(58);
        assertEquals(horizontalCoords, coordsToTest);

        // .. and vertical co-ordinates for odd ones
        coordsToTest = line.getCoordinatesForNRotations(121);
        assertEquals(verticalCoords, coordsToTest);
    }

    @Test
    public void testGetCoordinatesForRotation_dot() throws Exception {
        // simplest shape possible - a single co-ord
        final ImmutableSet<Coordinate> initialCoords = ImmutableSet.of(new Coordinate(0, 0));
        final Shape dot = new Shape(ImmutableList.of(initialCoords), 0);

        ImmutableSet coordsToTest = dot.getCoordinatesForNRotations(1);
        assertEquals(initialCoords, coordsToTest);

        coordsToTest = dot.getCoordinatesForNRotations(0);
        assertEquals(initialCoords, coordsToTest);

        coordsToTest = dot.getCoordinatesForNRotations(-1);
        assertEquals(initialCoords, coordsToTest);

        coordsToTest = dot.getCoordinatesForNRotations(Integer.MAX_VALUE);
        assertEquals(initialCoords, coordsToTest);

        coordsToTest = dot.getCoordinatesForNRotations(Integer.MIN_VALUE);
        assertEquals(initialCoords, coordsToTest);
    }

    @Test
    public void testUpdateOrientationAndGetCurrentCoordinates_line() throws Exception {
        // Construct a small line consisting of two adjacent co-ordinates
        final ImmutableSet<Coordinate> horizontalCoords = ImmutableSet.of(
                new Coordinate(0, 0), new Coordinate(1, 0));
        final ImmutableSet<Coordinate> verticalCoords = ImmutableSet.of(
                new Coordinate(0, 0),
                new Coordinate(0, 1));
        final Shape line = new Shape(ImmutableList.of(horizontalCoords, verticalCoords), 0);

        assertEquals(horizontalCoords, line.getCurrentLocalCoordinates());

        line.offsetCurrentOrientation(1);

        assertEquals(verticalCoords, line.getCurrentLocalCoordinates());

        line.offsetCurrentOrientation(-1);

        assertEquals(horizontalCoords, line.getCurrentLocalCoordinates());

        line.offsetCurrentOrientation(2);

        assertEquals(horizontalCoords, line.getCurrentLocalCoordinates());

        line.offsetCurrentOrientation(-3);

        assertEquals(verticalCoords, line.getCurrentLocalCoordinates());

        line.offsetCurrentOrientation(0);

        assertEquals(verticalCoords, line.getCurrentLocalCoordinates());
    }
}