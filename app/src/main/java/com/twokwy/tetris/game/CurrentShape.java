package com.twokwy.tetris.game;

/**
 * Created by anita on 19/09/2015.
 */
public interface CurrentShape {
    boolean moveDownByOneTile(TileGrid tileGrid);

    boolean moveLeftByOneTile(TileGrid tileGrid);

    boolean moveRightByOneTile(TileGrid tileGrid);
}
