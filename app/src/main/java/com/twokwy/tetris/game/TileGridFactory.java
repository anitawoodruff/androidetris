package com.twokwy.tetris.game;

import android.graphics.Rect;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anita on 18/09/2015.
 */
public class TileGridFactory {

    public static TileGrid createEmptyTileGrid() {
        return new TileGrid() {

            @Override
            public boolean insertShapeAtTop(TetrisShape shape) {
                return false;
            }

            @Override
            public void occupyTileAtPosition(int x, int y, Tile.Color color) throws TileOutOfGridException {

            }

            @Override
            public ImmutableList<PositionedTile> getPositionedTiles() {
                return ImmutableList.of();
            }

            @Override
            public boolean moveCurrentShapeDown() {
                return false;
            }

            @Override
            public boolean moveCurrentShapeLeft() {
                return false;
            }

            @Override
            public boolean moveCurrentShapeRight() {
                return false;
            }

            @Override
            public void clearTileAtPosition(int x, int y) {}

            @Override
            public PositionedTile getTileAtPosition(int x, int y) {
                return null;
            }

            @Override
            public boolean isLocationAvailable(int x, int y) {
                return false;
            }
        };
    }

    /**
     * Creates a tile grid with the right number of tiles per row and rows to fit into the given dimensions,
     * determined by the tile size set on construction.
     * @param w width (px) available for the tile grid
     * @param h height (px) available for the tile grid
     */
    public static TileGrid createToFillWidthAndHeight(int tileSize, int w, int h) {
        int tilesPerRow = (int) Math.floor(w / tileSize);
        int numberOfRows = (int) Math.floor(h / tileSize);

        int xOffset = (w - (tileSize * tilesPerRow)) / 2;
        int yOffset = (h - (tileSize * numberOfRows)) / 2;

        List<PositionedTile> tiles = createListOfEmptyTilesForGrid(tileSize,
                tilesPerRow, numberOfRows, xOffset, yOffset);

        return new TileGridImpl(tilesPerRow, numberOfRows, tiles);
    }

    static List<PositionedTile> createListOfEmptyTilesForGrid(
            int tileSize, int tilesPerRow, int numberOfRows, int xOffset, int yOffset) {
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
}
