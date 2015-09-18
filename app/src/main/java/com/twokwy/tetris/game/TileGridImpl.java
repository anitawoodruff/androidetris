package com.twokwy.tetris.game;

import android.graphics.Rect;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anita on 18/09/2015.
 */
public class TileGridImpl implements TileGrid {
    private final int mTileSize;
    private int mTilesPerRow;
    private int mNumberOfRows;
    private int mXOffset;
    private int mYOffset;
    private List<PositionedTile> mTiles;

    public TileGridImpl(int tileSize) {
        mTileSize = tileSize;
    }

    /**
     * Updates the number of tiles per row and total rows to fit into the given dimensions,
     * determined by the tile size set on construction. If this results in the grid size changing
     * how many tiles fit per row or column, all the tiles will be reset.
     * @param w width (px) available for the tile grid
     * @param h height (px) available for the tile grid
     */
    @Override
    public void updateGridSize(int w, int h) {
        mTilesPerRow = (int) Math.floor(w / mTileSize);
        mNumberOfRows = (int) Math.floor(h / mTileSize);

        mXOffset = (w - (mTileSize * mTilesPerRow)) / 2;
        mYOffset = (h - (mTileSize * mNumberOfRows)) / 2;

        mTiles = createListOfEmptyTilesForGrid(mTileSize,
                this.mTilesPerRow, mNumberOfRows, mXOffset, mYOffset);
    }

    static List<PositionedTile> createListOfEmptyTilesForGrid(int tileSize,
            int tilesPerRow, int numberOfRows, int xOffset, int yOffset) {
        final int totalTiles = tilesPerRow * numberOfRows;
        List<PositionedTile> tiles = new ArrayList<>(totalTiles);
        for (int i = 0; i < totalTiles; i++) {
            final int colIndex = i % tilesPerRow;
            final int rowIndex = i / numberOfRows;

            final int leftBound = xOffset + colIndex * tileSize;
            final int rightBound = leftBound + tileSize;
            final int topBound = yOffset + rowIndex * tileSize;
            final int bottomBound = topBound + tileSize;

            final Rect bounds = new Rect(leftBound, topBound, rightBound, bottomBound);
            tiles.add(i, new PositionedTile(new Tile(), bounds));
        }
        return tiles;
    }

    PositionedTile getTileAtPosition(int rowIndex, int columnIndex) throws TileOutOfGridException {
        int index = mTilesPerRow * rowIndex + columnIndex;
        if (rowIndex > mNumberOfRows || columnIndex > mTilesPerRow || index > mTiles.size()-1) {
            throw new TileOutOfGridException(
                    String.format("Tried to get tile at position (%d, %d) i.e. index %d when the grid is only" +
                                    " %d tiles wide and %d tiles tall (%d tiles stored)",
                            rowIndex, columnIndex, index, mTilesPerRow,
                            mNumberOfRows, mTiles.size()));
        }
        return mTiles.get(index);
    }

    void occupyTileAtPosition(int rowIndex, int columnIndex, Tile.Color color) throws TileOutOfGridException {
        getTileAtPosition(rowIndex, columnIndex).occupy(color);
    }

    @Override
    public ImmutableList<PositionedTile> getPositionedTiles() {
        return ImmutableList.copyOf(mTiles);
    }

    @Override
    public void insertShapeAtTop(TetrisShape shape) {
        shape.addToGridAtLocation(this, 0, mTilesPerRow / 2 - 1);
    }
}
