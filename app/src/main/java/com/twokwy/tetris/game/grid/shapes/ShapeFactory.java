package com.twokwy.tetris.game.grid.shapes;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;

import java.util.Random;

/**
 * Created by anita on 26/09/2015.
 */
public class ShapeFactory {

    private final Random mRandom;

    public ShapeFactory() {
        mRandom = new Random(1337);
    }

    public Shape createLongShape() {
        return createShapeWithRandomOrientation(ImmutableList.of(
                ImmutableSet.of(
                        new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3, 0)),
                ImmutableSet.of(
                        new Coordinate(1, 0),
                        new Coordinate(1, 1),
                        new Coordinate(1, 2),
                        new Coordinate(1, 3))));
    }

    public Shape createJShape() {
        return createShapeWithRandomOrientation(
                ImmutableList.of(ImmutableSet.of(
                                      new Coordinate(1, 0),
                                      new Coordinate(1, 1),
                new Coordinate(0, 2), new Coordinate(1, 2)),

                ImmutableSet.of(
                        new Coordinate(0, 0),
                        new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1)),

                ImmutableSet.of(
                        new Coordinate(1, 0), new Coordinate(2, 0),
                        new Coordinate(1, 1),
                        new Coordinate(1, 2)),

                ImmutableSet.of(
                        new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0),
                                                                    new Coordinate(2, 1))));
    }

    public Shape createLShape() {
        return createShapeWithRandomOrientation(ImmutableList.of(
                ImmutableSet.of(
                        new Coordinate(0, 0),
                        new Coordinate(0, 1),
                        new Coordinate(0, 2), new Coordinate(1, 2)),

                ImmutableSet.of(
                        new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0),
                        new Coordinate(0, 1)),
                ImmutableSet.of(
                        new Coordinate(0, 0), new Coordinate(1, 0),
                                              new Coordinate(1, 1),
                                              new Coordinate(1, 2)),
                ImmutableSet.of(
                                                                    new Coordinate(2, 0),
                        new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1))));
    }

    public Shape createSquareShape() {
        return new Shape(ImmutableList.of(ImmutableSet.of(
                new Coordinate(0, 0), new Coordinate(1, 0),
                new Coordinate(1, 0), new Coordinate(1, 1))), 0);
    }

    public Shape createSShape() {
        return createShapeWithRandomOrientation(ImmutableList.of(ImmutableSet.of(
                        new Coordinate(1, 0), new Coordinate(2, 0),
                        new Coordinate(0, 1), new Coordinate(1, 1)),

                ImmutableSet.of(
                        new Coordinate(0, 0),
                        new Coordinate(0, 1), new Coordinate(1, 1),
                        new Coordinate(1, 2))));
    }
    public Shape createTShape() {
        return createShapeWithRandomOrientation(ImmutableList.of(ImmutableSet.of(
                        new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0),
                        new Coordinate(1, 1)),
                ImmutableSet.of(
                        new Coordinate(1, 0),
                        new Coordinate(0, 1), new Coordinate(1, 1),
                        new Coordinate(1, 2)),

                ImmutableSet.of(
                        new Coordinate(1, 0),
                        new Coordinate(0, 1), new Coordinate(1, 1), new Coordinate(2, 1)),

                ImmutableSet.of(
                        new Coordinate(1, 0),
                        new Coordinate(1, 1), new Coordinate(2, 1),
                        new Coordinate(1, 2))));
    }

    public Shape createZShape() {
        return createShapeWithRandomOrientation(
                ImmutableList.of(ImmutableSet.of(
                                new Coordinate(0, 0), new Coordinate(1, 0),
                                new Coordinate(1, 1), new Coordinate(2, 1)),

                        ImmutableSet.of(
                                new Coordinate(1, 0),
                                new Coordinate(0, 1), new Coordinate(1, 1),
                                new Coordinate(0, 2))));
    }

    private Shape createShapeWithRandomOrientation(ImmutableList<ImmutableSet<Coordinate>> coords) {
        final int n = coords.size();
        final int orientation = mRandom.nextInt(n);
        return new Shape(coords, orientation);
    }
}
