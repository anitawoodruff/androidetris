package com.twokwy.tetris.game.grid;

/**
 * Created by anita on 18/09/2015.
 */
public class TileOutOfGridException extends ArrayIndexOutOfBoundsException {

    public TileOutOfGridException(String message) {
        super(message);
    }
}
