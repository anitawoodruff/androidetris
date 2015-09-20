package com.twokwy.tetris.game.grid.shapes;


import com.google.common.base.Supplier;
import com.twokwy.tetris.game.grid.tile.Tile;

import java.util.Random;

/**
 * Supplier for getting a random tetris shape.
 *
 * Created by anita on 20/09/2015.
 */
public class TetrisShapeSupplier implements Supplier<TetrisShape> {

    private final Random mRandom;

    private static enum SupplyableTetrisShape {
        SQUARE,
        T_SHAPE,
        LONG,
        S_SHAPE,
        L_SHAPE
    }

    public TetrisShapeSupplier() {
        mRandom = new Random(0);
    }

    @Override
    public TetrisShape get() {
        // TODO Move tile color logic to separate supplier in tile package
        Tile.Color color = Tile.Color.values()[
                mRandom.nextInt(Tile.Color.values().length)];
        SupplyableTetrisShape shape = SupplyableTetrisShape.values()[
                mRandom.nextInt(SupplyableTetrisShape.values().length)];
        return toTetrisShape(shape, color);
    }

    private static TetrisShape toTetrisShape(SupplyableTetrisShape shape, Tile.Color color) {
        switch (shape) {
            case SQUARE:
                return new Square(color);
            case T_SHAPE:
                return new TShape(color);
            case LONG:
                return new LongShape(color);
            case L_SHAPE:
                return new LShape(color);
            case S_SHAPE:
                return new SShape(color);
        }
        return null; // shouldn't occur
    }
}
