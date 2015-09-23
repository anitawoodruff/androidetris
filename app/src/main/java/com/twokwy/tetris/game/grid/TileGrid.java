package com.twokwy.tetris.game.grid;

import com.google.common.collect.ImmutableList;
import com.twokwy.tetris.game.grid.tile.PositionedTile;
import com.twokwy.tetris.game.grid.tile.Tile;

/**
 * Created by anita on 18/09/2015.
 */
public interface TileGrid {

    /**
     * Tries to insert the given shape at the top of the grid.
     * This shape will become the new currentPiece if the insert succeeds.
     * @return true if it succeeded, false if there was no space to insert it.
     */
    boolean insertNewShapeAtTop();

    void occupyTileAtPosition(int x, int y, Tile.Color color) throws TileOutOfGridException;

    ImmutableList<PositionedTile> getPositionedTiles();

    /**
     *
     * @return true if the move succeeded.
     */
    boolean moveCurrentShapeDown();

    /**
     *
     * @return true if the move succeeded
     */
    boolean moveCurrentShapeLeft();

    /**
     *
     * @return true if the move succeeded
     */
    boolean moveCurrentShapeRight();

    void clearTileAtPosition(int x, int y);

    PositionedTile getTileAtPosition(int x, int y) throws TileOutOfGridException;

    /**
     * Returns true if the given location is in bounds and unoccupied.
     */
    boolean isLocationAvailable(int x, int y);

    void dropCurrentPiece();

    void rotateCurrentPieceLeft();

    void rotateCurrentPieceRight();

    /**
     * Remove any full rows and shift any occupied rows above the removed rows down.
     * @return the number of rows removed.
     */
    int removeFullRows();
}
