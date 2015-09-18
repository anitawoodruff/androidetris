package com.twokwy.tetris.game;

import com.google.common.collect.ImmutableList;

/**
 * Created by anita on 18/09/2015.
 */
public class EmptyTileGrid implements TileGrid {
    @Override
    public void updateGridSize(int w, int h) {
        return;
    }

    @Override
    public void insertShapeAtTop(TetrisShape shape) {
        return;
    }

    @Override
    public ImmutableList<PositionedTile> getPositionedTiles() {
        return ImmutableList.of();
    }
}
