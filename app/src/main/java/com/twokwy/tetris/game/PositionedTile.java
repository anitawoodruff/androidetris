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

    Optional<GameView.TileColor> getColor() {
        return mTile.getColor();
    }

    public Rect getBounds() {
        return mBounds;
    }

    public void occupy(GameView.TileColor color) {
        mTile.occupy(color);
    }
}
