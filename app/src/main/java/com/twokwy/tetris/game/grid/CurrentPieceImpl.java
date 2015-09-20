package com.twokwy.tetris.game.grid;

import com.twokwy.tetris.game.grid.shapes.TetrisShape;

/**
 * Created by anita on 19/09/2015.
 */
public class CurrentPieceImpl implements CurrentPiece {

    private final TetrisShape mShape;
    private int mXLocation;
    private int mYLocation;

    public CurrentPieceImpl(TetrisShape shape, int x, int y) {
        mShape = shape;
        mXLocation = x;
        mYLocation = y;
    }

    /**
     * Moves the current piece down by one on the given grid, and updates its current location.
     * If this would cause a collision the piece is not moved and we return false.
     * @param tileGrid
     * @return true if the move succeeded.
     */
    @Override
    public boolean moveDownByOneTile(TileGrid tileGrid) {
        return move(tileGrid, 0, 1);
    }

    @Override
    public boolean moveLeftByOneTile(TileGrid tileGrid) {
        return move(tileGrid, -1, 0);
    }

    @Override
    public boolean moveRightByOneTile(TileGrid tileGrid) {
        return move(tileGrid, 1, 0);
    }

    @Override
    public void rotateLeft(TileGrid grid) {
        mShape.rotateLeft(grid, mXLocation, mYLocation);
    }

    @Override
    public void rotateRight(TileGrid grid) {
        mShape.rotateRight(grid, mXLocation, mYLocation);
    }

    private boolean move(TileGrid tileGrid, int xIncrement, int yIncrement) {
        mShape.removeFromGridAtLocation(tileGrid, mXLocation, mYLocation);
        if (mShape.addToGridAtLocation(tileGrid, mXLocation + xIncrement, mYLocation + yIncrement)) {
            mXLocation += xIncrement;
            mYLocation += yIncrement;
            return true;
        } else {
            // put it back where it was
            mShape.addToGridAtLocation(tileGrid, mXLocation, mYLocation);
            return false;
        }
    }
}
