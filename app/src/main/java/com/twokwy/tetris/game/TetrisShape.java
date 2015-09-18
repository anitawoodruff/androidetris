package com.twokwy.tetris.game;

import java.util.List;

/**
 * Created by anita on 18/09/2015.
 */
public interface TetrisShape {

    void rotateClockwise();

    Tile.Color getColor();

    List<PositionedTile> addToGridAtLocation(TileGrid grid, int row, int column);
}
