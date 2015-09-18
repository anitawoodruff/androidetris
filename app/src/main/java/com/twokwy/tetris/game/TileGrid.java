package com.twokwy.tetris.game;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anita on 18/09/2015.
 */
public class TileGrid {
    private final int mTileSize;
    private int mTilesPerRow;
    private int mNumberOfRows;
    private int mXOffset;
    private int mYOffset;
    private List<PositionedTile> mTiles;

    public TileGrid(int tileSize) {
        mTileSize = tileSize;
    }

    /**
     * Updates the number of tiles per row and total rows to fit into the given dimensions,
     * determined by the tile size set on construction. If this results in the grid size changing
     * how many tiles fit per row or column, all the tiles will be reset.
     * @param w width (px) available for the tile grid
     * @param h height (px) available for the tile grid
     */
    public void updateGridSize(int w, int h) {
        int newTilesPerRow = (int) Math.floor(w / mTileSize);
        int newNumberOfRows = (int) Math.floor(h / mTileSize);

        mXOffset = ((w - (mTileSize * mTilesPerRow)) / 2);
        mYOffset = ((h - (mTileSize * mNumberOfRows)) / 2);

        if (newTilesPerRow == mTilesPerRow && newNumberOfRows == mNumberOfRows) {
            // No need to repopulate tiles
            return;
        }
        mTilesPerRow = newTilesPerRow;
        mNumberOfRows = newNumberOfRows;
        mTiles = createListOfEmptyTilesForGrid(mTileSize, mTilesPerRow, mNumberOfRows, mXOffset, mYOffset);
    }

    static List<PositionedTile> createListOfEmptyTilesForGrid(int tileSize,
            int tilesPerRow, int numberOfRows, int xOffset, int yOffset) {
        final int totalTiles = tilesPerRow * numberOfRows;
        List<PositionedTile> tiles = new ArrayList<>(totalTiles);
        for (int i = 0; i < totalTiles; i++) {
            final int xIndex = i % tilesPerRow;
            final int yIndex = i / numberOfRows;

            final int leftBound = xOffset + xIndex * tileSize;
            final int rightBound = leftBound + tileSize;
            final int topBound = yOffset + yIndex * tileSize;
            final int bottomBound = topBound + tileSize;

            final Rect bounds = new Rect(leftBound, topBound, rightBound, bottomBound);
            tiles.add(i, new PositionedTile(new Tile(), bounds));
        }
        return tiles;
    }

    void occupyTileAtPosition(int xIndex, int yIndex, Tile.Color color) {
        int index = mTilesPerRow * yIndex + xIndex;
        mTiles.get(index).occupy(color);
    }

    List<PositionedTile> getPositionedTiles() {
        return mTiles;
    }
}
