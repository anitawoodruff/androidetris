package com.twokwy.tetris.game;

/**
 * Created by anita on 18/09/2015.
 */
public interface TetrisShape {

    void rotateClockwise();

    Tile.Color getColor();

    void addToGridAtLocation(TileGridImpl grid, int x, int y);
}
