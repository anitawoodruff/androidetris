package com.twokwy.tetris.game;

import com.google.common.base.Optional;

/**
 * Created by anita on 18/09/2015.
 */
class Tile {

    private Optional<GameView.TileColor> mColor = Optional.absent();

    void occupy(GameView.TileColor color) {
        mColor = Optional.of(color);
    }

    boolean isOccupied() {
        return mColor.isPresent();
    }

    void clear() {
        mColor = Optional.absent();
    }

    public Optional<GameView.TileColor> getColor() {
        return mColor;
    }
}
