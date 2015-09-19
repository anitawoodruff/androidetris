package com.twokwy.tetris.game;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Created by anita on 18/09/2015.
 */
public class TileGridImpl implements TileGrid {

    private final int mWidthInTiles;
    private final int mHeightInTiles;
    private final List<PositionedTile> mTiles;

    TileGridImpl(int widthInTiles, int heightInTiles, List<PositionedTile> tiles) {
        if (tiles == null || tiles.size() != widthInTiles * heightInTiles) {
            throw new IllegalArgumentException("Provided " + (tiles != null ? tiles.size() : 0)
                    + " tiles for " + widthInTiles * heightInTiles + " slots");
        }
        mWidthInTiles = widthInTiles;
        mHeightInTiles = heightInTiles;
        mTiles = tiles;
    }

    PositionedTile getTileAtPosition(int x, int y) throws TileOutOfGridException {
        if (x > mWidthInTiles - 1 || y > mHeightInTiles - 1) {
            throw new TileOutOfGridException(
                    String.format("Tried to get tile at position (%d, %d) when the grid is only " +
                            " %d tiles wide and %d tiles tall",
                            x, y, mWidthInTiles, mHeightInTiles));
        }
        int index = mWidthInTiles * y + x;
        return mTiles.get(index);
    }

    void occupyTileAtPosition(int x, int y, Tile.Color color) throws TileOutOfGridException {
        getTileAtPosition(x, y).occupy(color);
    }

    @Override
    public ImmutableList<PositionedTile> getPositionedTiles() {
        return ImmutableList.copyOf(mTiles);
    }

    @Override
    public boolean insertShapeAtTop(TetrisShape shape) {
        return shape.addToGridAtLocation(this, mWidthInTiles / 2 - 1, 0);
    }
}
