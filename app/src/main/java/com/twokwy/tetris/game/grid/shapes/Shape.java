package com.twokwy.tetris.game.grid.shapes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;

/**
 * Created by anita on 20/09/2015.
 */
public class Shape {

    private final ImmutableList<ImmutableSet<Coordinate>> mAllOrientations;
    protected int mCurrentOrientationIndex;

    protected Shape(final ImmutableList<ImmutableSet<Coordinate>> allOrientations,
                    final int currentOrientationIndex) {
        mAllOrientations = allOrientations;
        mCurrentOrientationIndex = currentOrientationIndex;
    }

    protected ImmutableSet<Coordinate> getCoordinatesForRotation(int rotation) {
        final int n = mAllOrientations.size();
        int index = (((mCurrentOrientationIndex + rotation) % n) + n) % n;
        return mAllOrientations.get(index);
    }

    protected void updateOrientation(int offset) {
        mCurrentOrientationIndex += offset;
    }

    protected ImmutableSet<Coordinate> getCurrentLocalCoordinates() {
        return getCoordinatesForRotation(mCurrentOrientationIndex);
    }

}
