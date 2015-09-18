package com.twokwy.tetris.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TileGridTest {

    private static final int TILE_SIZE = 10;
    private TileGrid mTileGrid;

    @Before
    public void setUp() throws Exception {
        mTileGrid = new TileGrid(TILE_SIZE);
    }

    @After
    public void tearDown() throws Exception {
        mTileGrid = null;
    }

    @Test
    public void testUpdateGridSize() throws Exception {
        mTileGrid.updateGridSize(100, 50);

        final List<PositionedTile> tiles = mTileGrid.getPositionedTiles();
        assertEquals(tiles.size(), 10*5);

        mTileGrid.updateGridSize(30, 10);

        final List<PositionedTile> updatedTiles = mTileGrid.getPositionedTiles();
        assertEquals(3, updatedTiles.size());
    }

    @Test
    public void testInsertSquareOnEmptyGrid() {
        mTileGrid.updateGridSize(40, 30);
        assertEquals(12, mTileGrid.getPositionedTiles().size());

        mTileGrid.insertShapeAtTop(new Square(Tile.Color.RED));

        assertFalse(mTileGrid.getTileAtPosition(0, 0).isOccupied());
        assertFalse(mTileGrid.getTileAtPosition(1, 0).isOccupied());
        assertTrue(mTileGrid.getTileAtPosition(0, 2).isOccupied());
        assertTrue(mTileGrid.getTileAtPosition(0, 2).isOccupied());
        assertFalse(mTileGrid.getTileAtPosition(0, 3).isOccupied());

    }

    @Test
    public void testOccupyTileAtPosition() throws Exception {
        mTileGrid.updateGridSize(100, 100);
        mTileGrid.occupyTileAtPosition(0, 0, Tile.Color.BLUE);

        final List<PositionedTile> tiles = mTileGrid.getPositionedTiles();
        assertEquals(Tile.Color.BLUE, tiles.get(0).getColor().get());
        assertTrue(tiles.get(0).isOccupied());
    }
}