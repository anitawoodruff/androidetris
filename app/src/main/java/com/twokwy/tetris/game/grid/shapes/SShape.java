package com.twokwy.tetris.game.grid.shapes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;
import com.twokwy.tetris.game.grid.tile.Tile;

/**
 * Created by anita on 18/09/2015.
 */
public class SShape extends RotatableShape {

    public SShape(Tile.Color color) {
        super(color);
    }

    @Override
    protected ImmutableList<ImmutableSet<Coordinate>> getOrientations() {
        return ImmutableList.of(ImmutableSet.of(
                                  new Coordinate(1, 0), new Coordinate(2, 0),
            new Coordinate(0, 1), new Coordinate(1, 1)),

                ImmutableSet.of(
                        new Coordinate(0, 0),
                        new Coordinate(0, 1), new Coordinate(1, 1),
                                              new Coordinate(1, 2)));
    }

}