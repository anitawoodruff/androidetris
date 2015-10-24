package com.twokwy.tetris.game.grid;

import com.google.common.base.Optional;
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

    TileGridImpl(int widthInTiles, int heightInTiles, List<PositionedTile> tiles,
                 TetrisShapeSupplier newPieceSupplier) {
        if (tiles == null || tiles.size() != widthInTiles * heightInTiles) {
            throw new IllegalArgumentException("Provided " + (tiles != null ? tiles.size() : 0)
                    + " tiles for " + widthInTiles * heightInTiles + " slots");
        }
        mWidthInTiles = widthInTiles;
        mHeightInTiles = heightInTiles;
        mTiles = tiles;
        mNewPieceSuppier = newPieceSupplier;
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

        @Override
        public void rotateLeft(TileGrid grid) {

        }

        @Override
        public void rotateRight(TileGrid grid) {

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

    int indexFromLocation(int x, int y) {
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

    @Override
    public boolean moveCurrentShapeDown() {
        return mCurrentPiece.moveDownByOneTile(this);
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
    public void dropCurrentPiece() {
        boolean hitTheBottom = false;
        int i = 0;
        while (!hitTheBottom) {
            hitTheBottom = !moveCurrentShapeDown();
            i++;
            if (i > mHeightInTiles) {
                throw new IllegalStateException("Current shape moved down by " + i + " tiles, but" +
                        " didn't yet hit the bottom. Exception thrown to escape endless loop.");
            }
        }
    }

    @Override
    public void rotateCurrentPieceLeft() {
        mCurrentPiece.rotateLeft(this);
    }

    @Override
    public void rotateCurrentPieceRight() {
        mCurrentPiece.rotateRight(this);
    }

    private int getNumberOfRowsToRemoveAtBottom() {
        int rowsToRemove = 0;
        for (int y = mHeightInTiles - 1; y >= 0; y--) {
            for (int x = 0; x < mWidthInTiles; x++) {
                if (!getTileAtPosition(x, y).isOccupied()) {
                    // no more full rows to remove
                    System.out.println("early termination at x=" + x + ", y=" + y);
                    return rowsToRemove;
                }
            }
            rowsToRemove++;
        }
        return rowsToRemove;
    }

    @Override
    public int removeFullRows() {
        int numberOfRowsToShiftBy = 0;
        // shift rows down by n, starting n rows from the bottom
        for (int y = mHeightInTiles - 1; y >= 0; y--) {
            boolean rowToBeCopiedIsFull = true;
            for (int x = 0; x < mWidthInTiles; x++) {
                // first check if row to be copied is full too
                if (!getTileAtPosition(x, y).isOccupied()) {
                    rowToBeCopiedIsFull = false;
                    break;
                }
            }
            if (rowToBeCopiedIsFull) {
                numberOfRowsToShiftBy++;
                continue; // try the row above
            }
            // now we copy
            for (int x = 0; x < mWidthInTiles; x++) {
                // TODO This isn't great
                final PositionedTile tileToCopy = getTileAtPosition(x, y);
                final Optional<Tile.Color> color = tileToCopy.getColor();

                final PositionedTile destinationTile = getTileAtPosition(x, y + numberOfRowsToShiftBy);
                if (color != null && color.isPresent()) {
                    destinationTile.occupy(color.get());
                } else {
                    destinationTile.clear();
                }
            }
        }
        // clear rows at the top
        for (int y = 0; y < numberOfRowsToShiftBy; y++) {
            for (int x = 0; x < mWidthInTiles; x++) {
                getTileAtPosition(x, y).clear();
            }
        }
        return numberOfRowsToShiftBy;
    }

    @Override
    public void clearTileAtPosition(int x, int y) {
        getTileAtPosition(x, y).clear();
    }
}
