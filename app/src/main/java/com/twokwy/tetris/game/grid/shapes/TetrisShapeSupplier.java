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

    private final Supplier<Shape> mShapeSupplier;
    private final Supplier<Tile.Color> mColorSupplier;

    public TetrisShapeSupplier() {
        ShapeFactory shapeFactory = new ShapeFactory(new Random());
        mShapeSupplier = new RandomShapeSupplier(new Random(), shapeFactory);
        mColorSupplier = new Tile.RandomColorSupplier(new Random());
    }

    @Override
    public TetrisShape get() {
        final Tile.Color color = mColorSupplier.get();
        final Shape shape = mShapeSupplier.get();
        return new TetrisShape(shape, color);
    }
}
