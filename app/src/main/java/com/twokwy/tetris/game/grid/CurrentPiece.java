package com.twokwy.tetris.game.grid;

/**
 * Created by anita on 19/09/2015.
 */
public interface CurrentPiece {
    boolean moveDownByOneTile(TileGrid tileGrid);

    boolean moveLeftByOneTile(TileGrid tileGrid);

    boolean moveRightByOneTile(TileGrid tileGrid);

    void rotateLeft(TileGrid grid);

    void rotateRight(TileGrid grid);
}
