package com.twokwy.tetris.game.grid.shapes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;

/**
 * Created by anita on 20/09/2015.
 */
class Shape {

    private final ImmutableList<ImmutableSet<Coordinate>> mAllOrientations;
    private int mCurrentOrientationIndex;

    /**
     * Construct a new shape with the given co-ordinates and initial orientation.
     * @param allOrientations A list of sets of local co-ordinates, each set corresponds to a
     *                        particular orientation of the shape.
     * @param initialOrientation An initial orientation index into the above list of orientations.
     * @throws IllegalArgumentException if the list of orientations passed in is empty.
     */
    Shape(final ImmutableList<ImmutableSet<Coordinate>> allOrientations,
          final int initialOrientation) throws IllegalArgumentException {
        if (allOrientations.isEmpty()) {
            throw new IllegalArgumentException("At least one set of coordinates must be provided");
        }
        mAllOrientations = allOrientations;
        mCurrentOrientationIndex = toValidOrientationIndex(initialOrientation);
    }

    ImmutableSet<Coordinate> getCoordinatesForNRotations(final int nRotations) {
        return mAllOrientations.get(toValidOrientationIndex(mCurrentOrientationIndex + nRotations));
    }

    void offsetCurrentOrientation(final int offset) {
        mCurrentOrientationIndex = toValidOrientationIndex(mCurrentOrientationIndex + offset);
    }

    ImmutableSet<Coordinate> getCurrentLocalCoordinates() {
        return getCoordinatesForNRotations(0);
    }

    /**
     * Returns a valid index into mAllOrientations given some integer (wraps around if OOB).
     */
    private int toValidOrientationIndex(int i) {
        return (i + mAllOrientations.size()) % mAllOrientations.size();
    }
}
