package com.twokwy.tetris.game.grid;

import com.google.common.collect.ImmutableList;
import com.twokwy.tetris.game.grid.shapes.Square;
import com.twokwy.tetris.game.grid.shapes.TetrisShapeSupplier;
import com.twokwy.tetris.game.grid.tile.PositionedTile;
import com.twokwy.tetris.game.grid.tile.Tile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TileGridImplTest {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
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
        mTileGrid = new TileGridImpl(WIDTH, HEIGHT, mMockTiles, null);
    }

    @After
    public void tearDown() throws Exception {
        mTileGrid = null;
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBadInitialisationThrowsException_listEmpty() {
        new TileGridImpl(4, 5, new ArrayList<PositionedTile>(), null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBadInitialisationThrowsException_listNull() {
        new TileGridImpl(4, 5, null, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBadInitialisationThrowsException_listTooBig() {
        List<PositionedTile> tiles = ImmutableList.of(new PositionedTile(null, null), new PositionedTile(null, null));
        new TileGridImpl(1, 1, tiles, null);
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

        // first tile on 2nd row
        assertEquals(mMockTiles.get(WIDTH), mTileGrid.getTileAtPosition(0, 1));

        PositionedTile penultimateTile = mTileGrid.getTileAtPosition(WIDTH-2, HEIGHT-1);
        assertEquals(mMockTiles.get(TOTAL_TILES-2), penultimateTile);

        PositionedTile lastTile = mTileGrid.getTileAtPosition(WIDTH-1, HEIGHT-1);
        assertEquals(mMockTiles.get(TOTAL_TILES - 1), lastTile);
    }

    @Test(expected = TileOutOfGridException.class)
    public void testGetTileAtPosition_outOfRange() {
        mTileGrid.getTileAtPosition(WIDTH, HEIGHT);
    }

    @Test
    public void testIndexFromLocation() {
        TileGridImpl tileGrid = new TileGridImpl(10, 20, mMockTiles, null);
        assertEquals(0, tileGrid.indexFromLocation(0, 0));
        assertEquals(10, tileGrid.indexFromLocation(0, 1));
        assertEquals(199, tileGrid.indexFromLocation(9, 19));
    }

    @Test
    public void testInsertNewShapeAtTop() {
        // set up to always insert a square
        TetrisShapeSupplier mockShapeSupplier = mock(TetrisShapeSupplier.class);
        when(mockShapeSupplier.get()).thenReturn(new Square(Tile.Color.RED));
        TileGridImpl tileGrid = new TileGridImpl(6, 3, mMockTiles.subList(0, 18), mockShapeSupplier);

        boolean result = tileGrid.insertNewShapeAtTop();
        assertTrue(result);

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

    @Test
    public void testRemoveFullRows_removesNoneFromEmptyGrid() {
        // create real tiles that can be checked later.
        List<PositionedTile> originalTiles = TileGridFactory.createListOfEmptyTilesForGrid(
                10, WIDTH, HEIGHT, 0, 0);

        TileGridImpl tileGrid = new TileGridImpl(WIDTH, HEIGHT, originalTiles, null);
        final int rowsRemoved = tileGrid.removeFullRows();

        // check correct number of rows removed
        assertEquals("Expected to remove no rows", 0, rowsRemoved);

        // check total tiles is still the same
        final List<PositionedTile> tiles = mTileGrid.getPositionedTiles();
        assertEquals(TOTAL_TILES, tiles.size());

        // check tiles all as they were
        for (int i = 0; i < TOTAL_TILES; i++) {
            assertEquals(originalTiles.get(i).isOccupied(), tiles.get(i).isOccupied());
        }
    }

    @Test
    public void testRemoveFullRows_noFullRowsButSomeOccupiedInBottomRow() {
        // create real tiles that can be checked later.
        List<PositionedTile> originalTiles = TileGridFactory.createListOfEmptyTilesForGrid(
                10, WIDTH, HEIGHT, 0, 0);

        // occupy first half of bottom row
        for (int i = TOTAL_TILES - WIDTH; i < TOTAL_TILES - WIDTH / 2; i++) {
            PositionedTile positionedTile = originalTiles.get(i);
            positionedTile.occupy(Tile.Color.RED);
        }

        TileGridImpl tileGrid = new TileGridImpl(WIDTH, HEIGHT, originalTiles, null);
        final int rowsRemoved = tileGrid.removeFullRows();

        // check correct number of rows removed
        assertEquals("Expected to remove no rows", 0, rowsRemoved);

        // check total tiles is still the same
        final List<PositionedTile> tiles = mTileGrid.getPositionedTiles();
        assertEquals(TOTAL_TILES, tiles.size());

        // check tiles unchanged
        for (int i = 0; i < TOTAL_TILES; i++) {
            assertEquals(false, tiles.get(i).isOccupied());
        }
    }

    @Test
    public void testRemoveFullRows_singleFullRowAtBottom_allOtherRowsEmpty() {
        // create real tiles that can be checked later.
        List<PositionedTile> originalTiles = TileGridFactory.createListOfEmptyTilesForGrid(
                10, WIDTH, HEIGHT, 0, 0);

        for (int i = TOTAL_TILES - WIDTH; i < TOTAL_TILES; i++) {
            PositionedTile positionedTile = originalTiles.get(i);
            positionedTile.occupy(Tile.Color.RED);
        }

        TileGridImpl tileGrid = new TileGridImpl(WIDTH, HEIGHT, originalTiles, null);

        final int rowsRemoved = tileGrid.removeFullRows();

        // check correct number of rows removed
        assertEquals( "Wrong number of rows removed", 1, rowsRemoved);

        // check total tiles is still the same
        final List<PositionedTile> newTiles = tileGrid.getPositionedTiles();
        assertEquals(TOTAL_TILES, newTiles.size());

        // check all tiles now unoccupied
        for (int i = 0; i < TOTAL_TILES; i++) {
            assertEquals(false, newTiles.get(i).isOccupied());
        }
    }

    @Test
    public void testRemoveFullRows_singleFullRowAtBottom_someOtherTilesOccupied() {
        // create real tiles that can be checked later.
        List<PositionedTile> originalTiles = TileGridFactory.createListOfEmptyTilesForGrid(
                10, WIDTH, HEIGHT, 0, 0);

        // occupy all tiles on bottom row
        for (int i = TOTAL_TILES - WIDTH; i < TOTAL_TILES; i++) {
            PositionedTile positionedTile = originalTiles.get(i);
            positionedTile.occupy(Tile.Color.RED);
        }
        // also occupy a few other positions:
        // last tile on penultimate row:
        originalTiles.get(TOTAL_TILES - WIDTH - 1).occupy(Tile.Color.BLUE);
        // last tile on penpenultimate row:
        originalTiles.get(TOTAL_TILES - 2 * WIDTH - 1).occupy(Tile.Color.GREEN);
        // first tile on penultimate row:
        originalTiles.get(TOTAL_TILES - 2 * WIDTH).occupy(Tile.Color.YELLOW);

        TileGridImpl tileGrid = new TileGridImpl(WIDTH, HEIGHT, originalTiles, null);
        // check initial state
        assertEquals(true, tileGrid.getTileAtPosition(WIDTH - 1, HEIGHT - 2).isOccupied());
        assertEquals(true, tileGrid.getTileAtPosition(WIDTH - 1, HEIGHT - 3).isOccupied());
        assertEquals(true, tileGrid.getTileAtPosition(0, HEIGHT - 2).isOccupied());

        final int rowsRemoved = tileGrid.removeFullRows();

        // check correct number of rows removed
        assertEquals( "Wrong number of rows removed", 1, rowsRemoved);

        // check total tiles is still the same
        final List<PositionedTile> newTiles = tileGrid.getPositionedTiles();
        assertEquals(TOTAL_TILES, newTiles.size());

        // check bottom row cleared, except for first and last tiles
        for (int i = TOTAL_TILES - WIDTH + 1; i < TOTAL_TILES - 1; i++) {
            assertEquals("expected empty tile at index " + i, false, newTiles.get(i).isOccupied());
        }
        assertEquals(true, newTiles.get(TOTAL_TILES - WIDTH).isOccupied());
        assertEquals(true, newTiles.get(TOTAL_TILES - 1).isOccupied());

        // check last tile occupied on penultimate row
        assertEquals(true, newTiles.get(TOTAL_TILES - WIDTH - 1).isOccupied());

        // check other tiles still unoccupied
        for (int i = 0; i < TOTAL_TILES - WIDTH - 1; i++) {
            assertEquals(false, newTiles.get(i).isOccupied());
        }
    }

    @Test
    public void testRemoveFullRows_twoFullRowsAtBottom_allOtherRowsEmpty() {
        // create real tiles that can be checked later.
        List<PositionedTile> originalTiles = TileGridFactory.createListOfEmptyTilesForGrid(
                10, WIDTH, HEIGHT, 0, 0);

        // fill the bottom two rows
        for (int i = TOTAL_TILES - 2 * WIDTH; i < TOTAL_TILES; i++) {
            PositionedTile positionedTile = originalTiles.get(i);
            positionedTile.occupy(Tile.Color.RED);
        }

        TileGridImpl tileGrid = new TileGridImpl(WIDTH, HEIGHT, originalTiles, null);

        final int rowsRemoved = tileGrid.removeFullRows();

        // check correct number of rows removed
        assertEquals( "Wrong number of rows removed", 2, rowsRemoved);

        // check total tiles is still the same
        final List<PositionedTile> newTiles = tileGrid.getPositionedTiles();
        assertEquals(TOTAL_TILES, newTiles.size());

        // check all tiles now unoccupied
        for (int i = 0; i < TOTAL_TILES; i++) {
            assertEquals(false, newTiles.get(i).isOccupied());
        }
    }

    @Test
    public void testRemoveFullRows_fullPenultimateRow_allEmptyAbove() {
        // create real tiles that can be checked later.
        List<PositionedTile> originalTiles = TileGridFactory.createListOfEmptyTilesForGrid(
                10, WIDTH, HEIGHT, 0, 0);

        // fill the penultimate row
        for (int i = TOTAL_TILES - 2 * WIDTH; i < TOTAL_TILES - WIDTH; i++) {
            PositionedTile positionedTile = originalTiles.get(i);
            positionedTile.occupy(Tile.Color.RED);
        }
        // fill a couple of tiles in the last row:
        // last tile:
        originalTiles.get(TOTAL_TILES - 1).occupy(Tile.Color.BLUE);
        // first tile on last row:
        originalTiles.get(TOTAL_TILES - WIDTH).occupy(Tile.Color.GREEN);

        TileGridImpl tileGrid = new TileGridImpl(WIDTH, HEIGHT, originalTiles, null);

        final int rowsRemoved = tileGrid.removeFullRows();

        // check correct number of rows removed
        assertEquals( "Wrong number of rows removed", 1, rowsRemoved);

        // check total tiles is still the same
        final List<PositionedTile> newTiles = tileGrid.getPositionedTiles();
        assertEquals(TOTAL_TILES, newTiles.size());

        // check all tiles above bottom row remain unoccupied
        for (int i = 0; i < TOTAL_TILES - WIDTH; i++) {
            assertEquals(false, newTiles.get(i).isOccupied());
        }
        // check first and last in bottom row are occupied
        assertEquals(true, newTiles.get(TOTAL_TILES - WIDTH).isOccupied());
        assertEquals(true, newTiles.get(TOTAL_TILES - 1).isOccupied());

        // check other tiles in bottom row are unoccupied
        for (int i = TOTAL_TILES - WIDTH + 1; i < TOTAL_TILES - 1; i++) {
            assertEquals(false, newTiles.get(i).isOccupied());
        }
    }

    @Test
    public void testRemoveFullRows_twoFullFloatingRows_notEmptyAbove() {
        // create real tiles that can be checked later.
        List<PositionedTile> originalTiles = TileGridFactory.createListOfEmptyTilesForGrid(
                10, WIDTH, HEIGHT, 0, 0);

        // fill a couple of tiles in the last row:
        // last tile:
        originalTiles.get(TOTAL_TILES - 1).occupy(Tile.Color.BLUE);
        // first tile on last row:
        originalTiles.get(TOTAL_TILES - WIDTH).occupy(Tile.Color.GREEN);

        // fill the penultimate row
        fillRowBeginningAtIndex(TOTAL_TILES - 2 * WIDTH, originalTiles);

        // fill a couple on the penpenultimate row
        // 2nd tiles:
        originalTiles.get(TOTAL_TILES - 3 * WIDTH + 1).occupy(Tile.Color.BLUE);

        // 3rd tile:
        originalTiles.get(TOTAL_TILES - 3 * WIDTH + 2).occupy(Tile.Color.BLUE);

        // fill the row above that
        fillRowBeginningAtIndex(TOTAL_TILES - 4 * WIDTH, originalTiles);

        // fill one tile on the row above
        originalTiles.get(TOTAL_TILES - 5 * WIDTH + WIDTH / 2).occupy(Tile.Color.BLUE);


        TileGridImpl tileGrid = new TileGridImpl(WIDTH, HEIGHT, originalTiles, null);

        // check initial state:
        assertEquals(true, tileGrid.getTileAtPosition(0, HEIGHT - 1).isOccupied());
        assertEquals(true, tileGrid.getTileAtPosition(WIDTH - 1, HEIGHT - 1).isOccupied());
        assertEquals(true, tileGrid.getTileAtPosition(1, HEIGHT - 3).isOccupied());
        assertEquals(true, tileGrid.getTileAtPosition(2, HEIGHT - 3).isOccupied());
        assertEquals(true, tileGrid.getTileAtPosition(WIDTH / 2, HEIGHT - 5).isOccupied());

        final int rowsRemoved = tileGrid.removeFullRows();

        // check correct number of rows removed
        assertEquals( "Wrong number of rows removed", 2, rowsRemoved);

        // check total tiles is still the same
        final List<PositionedTile> newTiles = tileGrid.getPositionedTiles();
        assertEquals(TOTAL_TILES, newTiles.size());

        // check first and last in bottom row are still occupied
        assertEquals(true, newTiles.get(TOTAL_TILES - WIDTH).isOccupied());
        assertEquals(true, newTiles.get(TOTAL_TILES - 1).isOccupied());

        // check just 2nd and 3rd in penultimate row are now occupied
        assertEquals(false, newTiles.get(TOTAL_TILES - 2 * WIDTH).isOccupied());
        assertEquals(true, newTiles.get(TOTAL_TILES - 2 * WIDTH + 1).isOccupied());
        assertEquals(true, newTiles.get(TOTAL_TILES - 2 * WIDTH + 2).isOccupied());
        for (int i = TOTAL_TILES - 2 * WIDTH + 3; i < TOTAL_TILES - WIDTH; i++) {
            assertEquals("Expect empty at position: " + i, false, newTiles.get(i).isOccupied());
        }

        // TODO bored now
    }

    private static void fillRowBeginningAtIndex(int index, List<PositionedTile> tiles) {
        for (int i = index; i < index + WIDTH; i++) {
            PositionedTile positionedTile = tiles.get(i);
            positionedTile.occupy(Tile.Color.RED);
        }
    }
}