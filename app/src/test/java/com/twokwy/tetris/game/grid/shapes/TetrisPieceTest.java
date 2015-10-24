package com.twokwy.tetris.game.grid.shapes;

import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;
import com.twokwy.tetris.game.grid.TileGrid;
import com.twokwy.tetris.game.grid.tile.Tile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by anita on 24/10/2015.
 */
public class TetrisPieceTest {

    private Shape mMockShape;
    private Tile.Color mColor;
    private TetrisPiece mTetrisPiece;

    @Before
    public void setUp() throws Exception {
        mMockShape = mock(Shape.class);
        mColor = Tile.Color.MAGENTA;
        mTetrisPiece = new TetrisPiece(mMockShape, mColor);
    }

    @After
    public void tearDown() throws Exception {
        mMockShape = null;
        mTetrisPiece = null;
        mColor = null;
    }

    @Test
    public void testAddToGridAtLocation_locationAvailable() throws Exception {
        final ImmutableSet coords = ImmutableSet.of(new Coordinate(0, 0), new Coordinate(1, 0));
        when(mMockShape.getCurrentLocalCoordinates()).thenReturn(coords);

        final TileGrid mockGrid = mock(TileGrid.class);
        when(mockGrid.isLocationAvailable(4, 5)).thenReturn(true);
        when(mockGrid.isLocationAvailable(5, 5)).thenReturn(true);

        final boolean result = mTetrisPiece.addToGridAtLocation(mockGrid, 4, 5);

        assertTrue("should return true if piece added to grid as expected", result);
        verify(mockGrid).occupyTileAtPosition(4, 5, mColor);
        verify(mockGrid).occupyTileAtPosition(5, 5, mColor);
    }

    @Test
    public void testAddToGridAtLocation_locationNotAvailable() throws Exception {
        final ImmutableSet coords = ImmutableSet.of(new Coordinate(0, 0), new Coordinate(1, 0));
        when(mMockShape.getCurrentLocalCoordinates()).thenReturn(coords);

        final TileGrid mockGrid = mock(TileGrid.class);
        when(mockGrid.isLocationAvailable(4, 5)).thenReturn(true);
        when(mockGrid.isLocationAvailable(5, 5)).thenReturn(false);

        final boolean result = mTetrisPiece.addToGridAtLocation(mockGrid, 4, 5);

        assertFalse("should return false if piece not added to grid as expected", result);
        verify(mockGrid, never()).occupyTileAtPosition(4, 5, mColor);
        verify(mockGrid, never()).occupyTileAtPosition(5, 5, mColor);
    }

    @Test
    public void testRemoveFromGridAtLocation() throws Exception {
        final ImmutableSet coords = ImmutableSet.of(new Coordinate(0, 0), new Coordinate(1, 0));
        when(mMockShape.getCurrentLocalCoordinates()).thenReturn(coords);

        final TileGrid mockGrid = mock(TileGrid.class);

        mTetrisPiece.removeFromGridAtLocation(mockGrid, 4, 5);

        verify(mockGrid).clearTileAtPosition(4, 5);
        verify(mockGrid).clearTileAtPosition(5, 5);
    }

    @Test
    public void testRotateLeft_spaceAvailable() throws Exception {
        final ImmutableSet coords = ImmutableSet.of(new Coordinate(0, 0), new Coordinate(1, 0));
        when(mMockShape.getCurrentLocalCoordinates()).thenReturn(coords);

        final ImmutableSet rotatedCoords = ImmutableSet.of(
                new Coordinate(0, 0),
                new Coordinate(0, 1));
        when(mMockShape.getCoordinatesForNRotations(-1)).thenReturn(rotatedCoords);

        final TileGrid mockGrid = mock(TileGrid.class);
        // space for original shape
        when(mockGrid.isLocationAvailable(4, 5)).thenReturn(true);
        when(mockGrid.isLocationAvailable(5, 5)).thenReturn(true);
        // extra space for rotated shape
        when(mockGrid.isLocationAvailable(4, 6)).thenReturn(true);

        assertTrue(mTetrisPiece.addToGridAtLocation(mockGrid, 4, 5));
        boolean result = mTetrisPiece.rotateLeft(mockGrid, 4, 5);

        assertTrue("should return true if piece rotated as expected", result);
        verify(mMockShape).offsetCurrentOrientation(-1);
    }

    @Test
    public void testRotateRight_noSpaceAvailable() throws Exception {
        final ImmutableSet coords = ImmutableSet.of(new Coordinate(0, 0), new Coordinate(1, 0));
        when(mMockShape.getCurrentLocalCoordinates()).thenReturn(coords);

        final ImmutableSet rotatedCoords = ImmutableSet.of(
                new Coordinate(0, 0),
                new Coordinate(0, 1));
        when(mMockShape.getCoordinatesForNRotations(1)).thenReturn(rotatedCoords);

        final TileGrid mockGrid = mock(TileGrid.class);
        // space for original shape
        when(mockGrid.isLocationAvailable(5, 5)).thenReturn(true);
        when(mockGrid.isLocationAvailable(6, 5)).thenReturn(true);
        // no space for rotated shape
        when(mockGrid.isLocationAvailable(5, 6)).thenReturn(false);

        assertTrue(mTetrisPiece.addToGridAtLocation(mockGrid, 5, 5));
        boolean result = mTetrisPiece.rotateRight(mockGrid, 5, 5);

        verify(mockGrid).clearTileAtPosition(5, 5);
        verify(mockGrid).clearTileAtPosition(6, 5);

        verify(mockGrid, times(2)).isLocationAvailable(6, 5);
        verify(mockGrid, times(3)).isLocationAvailable(5, 5);
        verify(mockGrid).isLocationAvailable(5, 6);

        verify(mockGrid, times(2)).occupyTileAtPosition(5, 5, mColor);
        verify(mockGrid, times(2)).occupyTileAtPosition(6, 5, mColor);
        verify(mockGrid, never()).occupyTileAtPosition(5, 6, mColor);

        assertFalse("should return false if piece not rotated as expected", result);
        verify(mMockShape, never()).offsetCurrentOrientation(anyInt());
    }
}