package com.twokwy.tetris.game;

import com.google.common.base.Optional;

/**
 * Created by anita on 18/09/2015.
 */
class Tile {

    enum Color {
        RED, GREEN, BLUE, YELLOW
    }

    private Optional<Color> mColor = Optional.absent();

    /**
     * Occupy this tile with the given color.
     */
    void occupy(Color color) {
        mColor = Optional.of(color);
    }

    boolean isOccupied() {
        return mColor.isPresent();
    }

    void clear() {
        mColor = Optional.absent();
    }

    public Optional<Color> getColor() {
        return mColor;
    }
}
