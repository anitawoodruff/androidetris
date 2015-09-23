package com.twokwy.tetris.game.grid.tile;

import com.google.common.base.Optional;

/**
 * Created by anita on 18/09/2015.
 */
public class Tile {

    public enum Color {
        RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA
    }

    private Optional<Color> mColor;

    public Tile() {
        mColor = Optional.absent();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (!mColor.equals(tile.mColor)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mColor.hashCode();
    }
}
