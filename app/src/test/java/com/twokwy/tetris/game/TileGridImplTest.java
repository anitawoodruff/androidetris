package com.twokwy.tetris.game;

import com.google.common.collect.ImmutableList;
import com.twokwy.tetris.game.grid.tile.PositionedTile;
import com.twokwy.tetris.game.grid.shapes.Square;
import com.twokwy.tetris.game.grid.TileGridImpl;
import com.twokwy.tetris.game.grid.TileOutOfGridException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TileGridImplTest {

    private static final int WIDTH = 6;
    private static final int HEIGHT = 3;
    private static final int TOTAL_TILES = WIDTH * HEIGHT;
    private TileGridImpl mTileGrid;
    private List<PositionedTile> mMockTiles;

    @Before
    public void setUp() throws Exception {
        List<PositionedTile> tiles = new ArrayList<>(TOTAL_TILES);
        for (int i = 0; i < TOTAL_TILES; i++) {
            PositionedTile mockPositionedTile = mock(PositionedTile.class);
            when(mockPositionedTile.isOccupied()).thenReturn(false);
            tiles.add(mockPositionedTile);
        }
        mMockTiles = tiles;
        mTileGrid = new TileGridImpl(WIDTH, HEIGHT, mMockTiles);
    }

    @After
    public void tearDown() throws Exception {
        mTileGrid = null;
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBadInitialisationThrowsException_listEmpty() {
        new TileGridImpl(4, 5, new ArrayList<PositionedTile>());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBadInitialisationThrowsException_listNull() {
        new TileGridImpl(4, 5, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBadInitialisationThrowsException_listTooBig() {
        List<PositionedTile> tiles = ImmutableList.of(new PositionedTile(null, null), new PositionedTile(null, null));
        new TileGridImpl(1, 1, tiles);
    }

    @Test
    public void testGetPositionedTiles() {
        List<PositionedTile> tiles = mTileGrid.getPositionedTiles();
        assertEquals(TOTAL_TILES, tiles.size());
        for (int i = 0; i < TOTAL_TILES; i++) {
            assertEquals(mMockTiles.get(i), tiles.get(i));
        }
    }

    @Test
    public void testGetTileAtPosition_inRange() {
        PositionedTile firstTile = mTileGrid.getTileAtPosition(0, 0);
        assertEquals(mMockTiles.get(0), firstTile);

        PositionedTile secondTile = mTileGrid.getTileAtPosition(1, 0);
        assertEquals(mMockTiles.get(1), secondTile);

        PositionedTile penultimateTile = mTileGrid.getTileAtPosition(WIDTH-2, HEIGHT-1);
        assertEquals(mMockTiles.get(TOTAL_TILES-2), penultimateTile);

        PositionedTile lastTile = mTileGrid.getTileAtPosition(WIDTH-1, HEIGHT-1);
        assertEquals(mMockTiles.get(TOTAL_TILES-1), lastTile);
    }

    @Test(expected = TileOutOfGridException.class)
    public void testGetTileAtPosition_outOfRange() {
        mTileGrid.getTileAtPosition(WIDTH, HEIGHT);
    }

    @Test
    public void testInsertSquareOnEmptyGrid() {

        verify(mMockTiles.get(0), never()).occupy(any(Tile.Color.class));

        mTileGrid.insertShapeAtTop(new Square(Tile.Color.RED));

        // Should take up space as follows:
        // ..xx..
        // ..xx..
        // ......
        verify(mMockTiles.get(0), never()).occupy(any(Tile.Color.class));
        verify(mMockTiles.get(1), never()).occupy(any(Tile.Color.class));
        verify(mMockTiles.get(2)).occupy(Tile.Color.RED);
        verify(mMockTiles.get(3)).occupy(Tile.Color.RED);
        verify(mMockTiles.get(4), never()).occupy(any(Tile.Color.class));
        verify(mMockTiles.get(5), never()).occupy(any(Tile.Color.class));

        verify(mMockTiles.get(6), never()).occupy(any(Tile.Color.class));
        verify(mMockTiles.get(7), never()).occupy(any(Tile.Color.class));
        verify(mMockTiles.get(8)).occupy(Tile.Color.RED);
        verify(mMockTiles.get(9)).occupy(Tile.Color.RED);
        verify(mMockTiles.get(10), never()).occupy(any(Tile.Color.class));
        verify(mMockTiles.get(11), never()).occupy(any(Tile.Color.class));

        for (int i = 12; i < 18; i++) {
            verify(mMockTiles.get(i), never()).occupy(any(Tile.Color.class));
        }

    }

    @Test
    public void testOccupyTileAtPosition() throws Exception {
        assertFalse(mTileGrid.getTileAtPosition(0, 0).isOccupied());
        mTileGrid.occupyTileAtPosition(0, 0, Tile.Color.BLUE);

        verify(mMockTiles.get(0)).occupy(Tile.Color.BLUE);
    }
}