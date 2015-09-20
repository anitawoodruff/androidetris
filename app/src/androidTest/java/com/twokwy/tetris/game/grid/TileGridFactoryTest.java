package com.twokwy.tetris.game.grid;

import android.graphics.Rect;

import com.twokwy.tetris.game.grid.tile.PositionedTile;

import junit.framework.TestCase;

import java.util.List;

public class TileGridFactoryTest extends TestCase {

    public void testCreateListOfEmptyTilesForGrid_noOffset() {
        final int tileSize = 10;
        final int widthInTiles = 10;
        List<PositionedTile> tiles =  TileGridFactory.createListOfEmptyTilesForGrid(
                tileSize, widthInTiles, 20, 0, 0);
        assertEquals(200, tiles.size());
        for (PositionedTile tile : tiles) {
            assertEquals(tileSize, tile.getBounds().width());
            assertEquals(tileSize, tile.getBounds().height());
        }

        // check first tile bounds
        Rect bounds = tiles.get(0).getBounds();
        assertEquals(0, bounds.left);
        assertEquals(10, bounds.right);
        assertEquals(0, bounds.top);
        assertEquals(10, bounds.bottom);

        // check rest of first row
        for (int i = 1; i < widthInTiles; i++) {
            Rect nextBounds = tiles.get(i).getBounds();
            assertEquals(bounds.right, nextBounds.left);
            bounds = nextBounds;
        }

        // check 2nd row first tile bounds
        bounds = tiles.get(10).getBounds();
        assertEquals(0, bounds.left);
        assertEquals(10, bounds.right);
        assertEquals(10, bounds.top);
        assertEquals(20, bounds.bottom);

        // check last tile bounds
        bounds = tiles.get(199).getBounds();
        assertEquals(90, bounds.left);
        assertEquals(100, bounds.right);
        assertEquals(190, bounds.top);
        assertEquals(200, bounds.bottom);
    }

    public void testCreateListOfEmptyTilesForGrid_withOffset() {
        List<PositionedTile> tiles =  TileGridFactory.createListOfEmptyTilesForGrid(10, 10, 20, 2, 3);
        assertEquals(200, tiles.size());
        assertEquals(2, tiles.get(0).getBounds().left);
        assertEquals(12, tiles.get(0).getBounds().right);
        assertEquals(3, tiles.get(0).getBounds().top);
        assertEquals(13, tiles.get(0).getBounds().bottom);
    }

    public void testCreateToFillWidthAndHeight() {
        int width = 400;
        int height = 400;
        TileGrid tileGrid = TileGridFactory.createToFillWidthAndHeight(width, height);
        List<PositionedTile> tiles = tileGrid.getPositionedTiles();
        assertEquals(200, tiles.size());

        // check first tile
        assertEquals(100, tiles.get(0).getBounds().left);
        assertEquals(120, tiles.get(0).getBounds().right);
        assertEquals(0, tiles.get(0).getBounds().top);
        assertEquals(20, tiles.get(0).getBounds().bottom);

        // check first tile on second row
        Rect bounds = tiles.get(10).getBounds();
        assertEquals(100, bounds.left);
        assertEquals(120, bounds.right);
        assertEquals(20, bounds.top);
        assertEquals(40, bounds.bottom);
    }
}