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

    private final ShapeFactory mShapeFactory;
    private final Random mRandom;

    private enum SupplyableTetrisShape {
        SQUARE,
        T_SHAPE,
        LONG,
        S_SHAPE,
        L_SHAPE,
        Z_SHAPE
    }

    public TetrisShapeSupplier() {
        mShapeFactory = new ShapeFactory();
        mRandom = new Random(0);
    }

    @Override
    public TetrisShape get() {
        // TODO Move tile color logic to separate supplier in tile package
        Tile.Color color = Tile.Color.values()[
                mRandom.nextInt(Tile.Color.values().length)];
        SupplyableTetrisShape shape = SupplyableTetrisShape.values()[
                mRandom.nextInt(SupplyableTetrisShape.values().length)];
        return new TetrisShape(translateShape(shape), color);
    }

    private Shape translateShape(SupplyableTetrisShape shape) {
        switch (shape) {
            case SQUARE:
                return mShapeFactory.createSquareShape();
            case T_SHAPE:
                return mShapeFactory.createTShape();
            case LONG:
                return mShapeFactory.createLongShape();
            case L_SHAPE:
                return mShapeFactory.createLShape();
            case S_SHAPE:
                return mShapeFactory.createSShape();
            case Z_SHAPE:
                return mShapeFactory.createZShape();
        }
        return null; // shouldn't occur
    }
}
