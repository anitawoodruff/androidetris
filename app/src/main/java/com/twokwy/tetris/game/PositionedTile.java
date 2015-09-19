package com.twokwy.tetris.game;

import android.graphics.Rect;

import com.google.common.base.Optional;

/**
 * Created by anita on 18/09/2015.
 */
public class PositionedTile {

    private final Tile mTile;
    private final Rect mBounds;

    PositionedTile(Tile tile, Rect bounds) {
        mTile = tile;
        mBounds = bounds;
    }

    Optional<Tile.Color> getColor() {
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
}
