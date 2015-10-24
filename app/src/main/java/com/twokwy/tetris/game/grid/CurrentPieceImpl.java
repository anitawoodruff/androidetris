package com.twokwy.tetris.game.grid;

import com.twokwy.tetris.game.grid.shapes.TetrisPiece;

/**
 * Created by anita on 19/09/2015.
 */
public class CurrentPieceImpl implements CurrentPiece {

    private final TetrisPiece mTetrisPiece;
    private int mXLocation;
    private int mYLocation;

    public CurrentPieceImpl(TetrisPiece tetrisPiece, int x, int y) {
        mTetrisPiece = tetrisPiece;
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
        mTetrisPiece.rotateLeft(grid, mXLocation, mYLocation);
    }

    @Override
    public void rotateRight(TileGrid grid) {
        mTetrisPiece.rotateRight(grid, mXLocation, mYLocation);
    }

    private boolean move(TileGrid tileGrid, int xIncrement, int yIncrement) {
        mTetrisPiece.removeFromGridAtLocation(tileGrid, mXLocation, mYLocation);
        if (mTetrisPiece.addToGridAtLocation(tileGrid, mXLocation + xIncrement, mYLocation + yIncrement)) {
            mXLocation += xIncrement;
            mYLocation += yIncrement;
            return true;
        } else {
            // put it back where it was
            mTetrisPiece.addToGridAtLocation(tileGrid, mXLocation, mYLocation);
            return false;
        }
    }
}
