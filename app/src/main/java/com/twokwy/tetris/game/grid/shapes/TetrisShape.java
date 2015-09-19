package com.twokwy.tetris.game.grid.shapes;

import com.twokwy.tetris.game.grid.tile.Tile;
import com.twokwy.tetris.game.grid.TileGrid;

/**
 * Created by anita on 18/09/2015.
 */
public interface TetrisShape {

    void rotateClockwise();

    Tile.Color getColor();

    boolean addToGridAtLocation(TileGrid grid, int x, int y);

    void removeFromGridAtLocation(TileGrid tileGrid, int mXLocation, int mYLocation);
}
