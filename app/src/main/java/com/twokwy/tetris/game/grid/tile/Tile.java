package com.twokwy.tetris.game.grid.tile;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

import java.util.Random;

/**
 * Created by anita on 18/09/2015.
 */
public class Tile {

    public enum Color {
        RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA
    }

    public static class RandomColorSupplier implements Supplier<Color> {
        private final Random mRandom;

        public RandomColorSupplier(Random random) {
            mRandom = random;
        }

        public Color get() {
            return Tile.Color.values()[mRandom.nextInt(Tile.Color.values().length)];
        }
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
