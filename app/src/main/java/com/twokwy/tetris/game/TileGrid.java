package com.twokwy.tetris.game;

import com.google.common.collect.ImmutableList;

/**
 * Created by anita on 18/09/2015.
 */
public interface TileGrid {
    void updateGridSize(int w, int h);

    void insertShapeAtTop(TetrisShape shape);

    ImmutableList<PositionedTile> getPositionedTiles();
}
