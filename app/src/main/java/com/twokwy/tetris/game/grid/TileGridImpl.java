package com.twokwy.tetris.game.grid;

import com.google.common.collect.ImmutableList;
import com.twokwy.tetris.game.grid.shapes.TetrisShape;
import com.twokwy.tetris.game.grid.shapes.TetrisShapeSupplier;
import com.twokwy.tetris.game.grid.tile.PositionedTile;
import com.twokwy.tetris.game.grid.tile.Tile;

import java.util.List;

/**
 * Created by anita on 18/09/2015.
 */
public class TileGridImpl implements TileGrid {

    private final int mWidthInTiles;
    private final int mHeightInTiles;
    private final List<PositionedTile> mTiles;
    private final TetrisShapeSupplier mNewPieceSuppier;
    private CurrentPiece mCurrentPiece;

    TileGridImpl(int widthInTiles, int heightInTiles, List<PositionedTile> tiles) {
        if (tiles == null || tiles.size() != widthInTiles * heightInTiles) {
            throw new IllegalArgumentException("Provided " + (tiles != null ? tiles.size() : 0)
                    + " tiles for " + widthInTiles * heightInTiles + " slots");
        }
        mWidthInTiles = widthInTiles;
        mHeightInTiles = heightInTiles;
        mTiles = tiles;
        mNewPieceSuppier = new TetrisShapeSupplier();
        mCurrentPiece = new NullCurrentPiece();
    }

    private static class NullCurrentPiece implements CurrentPiece {

        @Override
        public boolean moveDownByOneTile(TileGrid tileGrid) {
            return false;
        }

        @Override
        public boolean moveLeftByOneTile(TileGrid tileGrid) {
            return false;
        }

        @Override
        public boolean moveRightByOneTile(TileGrid tileGrid) {
            return false;
        }
    }

    @Override
    public PositionedTile getTileAtPosition(int x, int y) throws TileOutOfGridException {
        if (x > mWidthInTiles - 1 || y > mHeightInTiles - 1) {
            throw new TileOutOfGridException(
                    String.format("Tried to get tile at position (%d, %d) when the grid is only " +
                            " %d tiles wide and %d tiles tall",
                            x, y, mWidthInTiles, mHeightInTiles));
        }
        int index = indexFromLocation(x, y);
        return mTiles.get(index);
    }

    private int indexFromLocation(int x, int y) {
        return mWidthInTiles * y + x;
    }

    @Override
    public boolean isLocationAvailable(int x, int y) {
        return (x < mWidthInTiles && y < mHeightInTiles && x >= 0 && y >= 0 &&
                !mTiles.get(indexFromLocation(x, y)).isOccupied());
    }

    @Override
    public void occupyTileAtPosition(int x, int y, Tile.Color color) throws TileOutOfGridException {
        getTileAtPosition(x, y).occupy(color);
    }

    @Override
    public ImmutableList<PositionedTile> getPositionedTiles() {
        return ImmutableList.copyOf(mTiles);
    }

    @Override
    public boolean moveCurrentShapeDown() {
        if (!mCurrentPiece.moveDownByOneTile(this)) {
            // it's reached the bottom, add a new shape at the top
            insertNewShapeAtTop();
            return false;
        }
        return true;
    }

    @Override
    public boolean moveCurrentShapeLeft() {
        return mCurrentPiece.moveLeftByOneTile(this);
    }

    @Override
    public boolean moveCurrentShapeRight() {
        return mCurrentPiece.moveRightByOneTile(this);
    }

    @Override
    public void clearTileAtPosition(int x, int y) {
        getTileAtPosition(x, y).clear();
    }

    @Override
    public boolean insertNewShapeAtTop() {
        final int x = mWidthInTiles / 2 - 1;
        final int y = 0;
        final TetrisShape shape = mNewPieceSuppier.get();
        if (shape.addToGridAtLocation(this, x, y)) {
            mCurrentPiece = new CurrentPieceImpl(shape, x, y);
            return true;
        } else {
            return false;
        }
    }
}
