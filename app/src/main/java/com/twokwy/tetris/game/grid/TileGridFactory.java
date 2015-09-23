package com.twokwy.tetris.game.grid;

import android.graphics.Rect;

import com.google.common.collect.ImmutableList;
import com.twokwy.tetris.game.grid.shapes.TetrisShapeSupplier;
import com.twokwy.tetris.game.grid.tile.PositionedTile;
import com.twokwy.tetris.game.grid.tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anita on 18/09/2015.
 */
public class TileGridFactory {

    private static final int DEFAULT_NUMBER_OF_ROWS = 20;
    private static final int DEFAULT_NUMBER_OF_COLUMNS = 10;

    public static TileGrid createEmptyTileGrid() {
        return new TileGrid() {

            @Override
            public boolean insertNewShapeAtTop() {
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

            @Override
            public void dropCurrentPiece() {

            }

            @Override
            public void rotateCurrentPieceLeft() {

            }

            @Override
            public void rotateCurrentPieceRight() {

            }

            @Override
            public int removeFullRows() {
                return 0;
            }
        };
    }

    /**
     * Creates a tile grid with the right number of tiles per row and rows to fit into the given dimensions,
     * determined by the tile size set on construction.
     * @param w width (px) available for the tile grid
     * @param h height (px) available for the tile grid
     */
    public static TileGrid createToFillWidthAndHeight(int w, int h) {
        System.out.println("Creating with width="+w+", height="+h);
        final int maxTileHeight = (int) Math.floor(h / DEFAULT_NUMBER_OF_ROWS);
        final int maxTileWidth = (int) Math.floor(w / DEFAULT_NUMBER_OF_COLUMNS);

        final int tileSize = Math.min(maxTileHeight, maxTileWidth);

        int xOffset = (w - (tileSize * DEFAULT_NUMBER_OF_COLUMNS)) / 2;
        int yOffset = (h - (tileSize * DEFAULT_NUMBER_OF_ROWS)) / 2;

        System.out.println("tileSize = " + tileSize + ", xOffset = " + xOffset + ", yOffset = " + yOffset);
        List<PositionedTile> tiles = createListOfEmptyTilesForGrid(
                tileSize, DEFAULT_NUMBER_OF_COLUMNS, DEFAULT_NUMBER_OF_ROWS, xOffset, yOffset);

        return new TileGridImpl(DEFAULT_NUMBER_OF_COLUMNS, DEFAULT_NUMBER_OF_ROWS, tiles, new TetrisShapeSupplier());
    }

    static List<PositionedTile> createListOfEmptyTilesForGrid(int tileSize,
                                                              int widthInTiles, int heightInTiles,
                                                              int xOffset, int yOffset) {
        final int totalTiles = widthInTiles * heightInTiles;
        List<PositionedTile> tiles = new ArrayList<>(totalTiles);
        for (int i = 0; i < totalTiles; i++) {
            final int x = i % widthInTiles;
            final int y = i / widthInTiles;

            final int leftBound = xOffset + x * tileSize;
            final int rightBound = leftBound + tileSize;
            final int topBound = yOffset + y * tileSize;
            final int bottomBound = topBound + tileSize;

            final Rect bounds = new Rect(leftBound, topBound, rightBound, bottomBound);
            tiles.add(i, new PositionedTile(new Tile(), bounds));
        }
        return tiles;
    }
}
