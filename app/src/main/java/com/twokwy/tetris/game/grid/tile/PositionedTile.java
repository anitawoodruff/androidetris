package com.twokwy.tetris.game.grid.tile;

import android.graphics.Rect;

import com.google.common.base.Optional;

/**
 * Created by anita on 18/09/2015.
 */
public class PositionedTile {

    private final Tile mTile;
    private final Rect mBounds;

    public PositionedTile(Tile tile, Rect bounds) {
        mTile = tile;
        mBounds = bounds;
    }

    public Optional<Tile.Color> getColor() {
        return mTile.getColor();
    }

    public Rect getBounds() {
        return mBounds;
    }

    public void occupy(Tile.Color color) {
        mTile.occupy(color);
    }

    public boolean isOccupied() {
        return mTile.isOccupied();
    }

    /**
     * Sets the tile to unoccupied (no color). Tile bounds are unaffected.
     */
    public void clear() {
        mTile.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PositionedTile that = (PositionedTile) o;

        if (!mBounds.equals(that.mBounds)) return false;
        if (!mTile.equals(that.mTile)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mTile.hashCode();
        result = 31 * result + mBounds.hashCode();
        return result;
    }
}
