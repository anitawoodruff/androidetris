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
    private List<Tile> mTiles;

    public TileGrid(int tileSize) {
        mTileSize = tileSize;
    }

    public void updateGridSizeAndClearTiles(int w, int h) {
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
        mTiles = createListOfEmptyTilesForGrid(mTilesPerRow, mNumberOfRows);
    }

    private static List<Tile> createListOfEmptyTilesForGrid(int tilesPerRow, int numberOfRows) {
        final int totalTiles = tilesPerRow * numberOfRows;
        List<Tile> tiles = new ArrayList<>(totalTiles);
        for (int i = 0; i < totalTiles; i++) {
            tiles.add(i, new Tile());
        }
        return tiles;
    }

    public void occupyTileAtPosition(int xIndex, int yIndex, GameView.TileColor color) {
        int index = mTilesPerRow * yIndex + xIndex;
        mTiles.get(index).occupy(color);
    }

    public List<PositionedTile> getPositionedTiles() {
        final List<PositionedTile> positionedTiles = new ArrayList<>();
        for (int i = 0; i < mTiles.size(); i++) {
            final int xIndex = i % mTilesPerRow;
            final int yIndex = i / mTilesPerRow;

            final int leftBound = mXOffset + xIndex * mTileSize;
            final int rightBound = leftBound + mTileSize;
            final int topBound = mYOffset + yIndex * mTileSize;
            final int bottomBound = topBound + mTileSize;

            final Rect bounds = new Rect(leftBound, topBound, rightBound, bottomBound);

            final PositionedTile positionedTile = new PositionedTile(mTiles.get(i), bounds);
            positionedTiles.add(positionedTile);
        }
        return positionedTiles;
    }
}
