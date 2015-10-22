package com.twokwy.tetris.game.grid.shapes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;

/**
 * Created by anita on 20/09/2015.
 */
public class Shape {

    private final ImmutableList<ImmutableSet<Coordinate>> mAllOrientations;
    private int mCurrentOrientationIndex;

    protected Shape(final ImmutableList<ImmutableSet<Coordinate>> allOrientations,
                    final int currentOrientationIndex) {
        mAllOrientations = allOrientations;
        mCurrentOrientationIndex = currentOrientationIndex;
    }

    protected ImmutableSet<Coordinate> getCoordinatesForRotation(final int rotation) {
        return mAllOrientations.get(rotation);
    }

    protected void updateOrientation(final int offset) {
        final int n = mAllOrientations.size();
        mCurrentOrientationIndex = (mCurrentOrientationIndex + offset) % n;
    }

    protected ImmutableSet<Coordinate> getCurrentLocalCoordinates() {
        return getCoordinatesForRotation(mCurrentOrientationIndex);
    }

}
