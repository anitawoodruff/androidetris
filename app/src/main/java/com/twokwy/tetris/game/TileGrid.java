package com.twokwy.tetris.game;

import com.google.common.collect.ImmutableList;

/**
 * Created by anita on 18/09/2015.
 */
public interface TileGrid {

    /**
     * Tries to insert the given shape at the top of the grid.
     * This shape will become the new currentPiece if the insert succeeds.
     * @return true if it succeeded, false if there was no space to insert it.
     */
    boolean insertShapeAtTop(TetrisShape shape);

    ImmutableList<PositionedTile> getPositionedTiles();
}
