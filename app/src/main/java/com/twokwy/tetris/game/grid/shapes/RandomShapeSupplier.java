package com.twokwy.tetris.game.grid.shapes;

import com.google.common.base.Supplier;

import java.util.Random;

/**
 * Created by anita on 23/10/2015.
 */
public class RandomShapeSupplier implements Supplier<Shape> {

    private enum SupplyableShape {
        SQUARE,
        T_SHAPE,
        LONG,
        S_SHAPE,
        L_SHAPE,
        J_SHAPE,
        Z_SHAPE
    }

    private final Random mRandom;
    private final ShapeFactory mShapeFactory;

    public RandomShapeSupplier(final Random random, final ShapeFactory shapeFactory) {
        mRandom = random;
        mShapeFactory = shapeFactory;
    }

    @Override
    public Shape get() {
        final SupplyableShape supplyableShape = SupplyableShape.values()[
                mRandom.nextInt(SupplyableShape.values().length)];
        return translateShape(supplyableShape);
    }

    private Shape translateShape(SupplyableShape shape) {
        switch (shape) {
            case SQUARE:
                return mShapeFactory.createSquareShape();
            case T_SHAPE:
                return mShapeFactory.createTShape();
            case LONG:
                return mShapeFactory.createLongShape();
            case L_SHAPE:
                return mShapeFactory.createLShape();
            case J_SHAPE:
                return mShapeFactory.createJShape();
            case S_SHAPE:
                return mShapeFactory.createSShape();
            case Z_SHAPE:
                return mShapeFactory.createZShape();
        }
        return null; // shouldn't occur
    }
}
