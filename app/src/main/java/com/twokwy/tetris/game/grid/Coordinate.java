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
}
