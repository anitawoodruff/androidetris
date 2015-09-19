package com.twokwy.tetris.game;

/**
 * Created by anita on 18/09/2015.
 */
public interface TetrisShape {

    void rotateClockwise();

    Tile.Color getColor();

    boolean addToGridAtLocation(TileGridImpl grid, int x, int y);
}
