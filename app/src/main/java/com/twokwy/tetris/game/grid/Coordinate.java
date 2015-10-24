package com.twokwy.tetris.game.grid;

/**
 * Created by anita on 20/09/2015.
 */
public class Coordinate {
    private final int mX;
    private final int mY;

    public Coordinate(int x, int y) {
        mX = x;
        mY = y;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (mX != that.mX) return false;
        return mY == that.mY;

    }

    @Override
    public int hashCode() {
        int result = mX;
        result = 31 * result + mY;
        return result;
    }
}
