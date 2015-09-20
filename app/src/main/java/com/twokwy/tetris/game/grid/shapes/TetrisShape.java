package com.twokwy.tetris.game.grid.shapes;

import com.twokwy.tetris.game.grid.tile.Tile;
import com.twokwy.tetris.game.grid.TileGrid;

/**
 * Created by anita on 18/09/2015.
 */
public interface TetrisShape {

    Tile.Color getColor();

    boolean addToGridAtLocation(TileGrid grid, int x, int y);

    void removeFromGridAtLocation(TileGrid tileGrid, int mXLocation, int mYLocation);

    boolean rotateLeft(TileGrid grid, int x, int y);

    boolean rotateRight(TileGrid grid, int x, int y);
}
