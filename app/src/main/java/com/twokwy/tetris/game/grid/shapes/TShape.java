package com.twokwy.tetris.game.grid.shapes;

import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;
import com.twokwy.tetris.game.grid.TileGrid;
import com.twokwy.tetris.game.grid.tile.Tile;

/**
 * Created by anita on 18/09/2015.
 */
public class TShape implements TetrisShape {

    private static final ImmutableSet<Coordinate> LOCAL_COORDINATES = ImmutableSet.of(
            new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0),
                                  new Coordinate(1, 1));

    private final Tile.Color mColor;

    public TShape(Tile.Color color) {
        mColor = color;

    }

    @Override
    public void rotateClockwise() {
        return; // rotation has no effect
    }

    @Override
    public Tile.Color getColor() {
        return mColor;
    }

    @Override
    public boolean addToGridAtLocation(final TileGrid grid, int x, int y) {
        // check space is available first.
        for (Coordinate coord : LOCAL_COORDINATES) {
            if (!grid.isLocationAvailable(x + coord.getX(), y + coord.getY())) {
                return false;
            }
        }
        for (Coordinate coord : LOCAL_COORDINATES) {
            grid.occupyTileAtPosition(x + coord.getX(), y + coord.getY(), mColor);
        }
        return true;
    }

    @Override
    public void removeFromGridAtLocation(TileGrid grid, int x, int y) {
        for (Coordinate coord : LOCAL_COORDINATES) {
                grid.clearTileAtPosition(x + coord.getX(), y + coord.getY());
        }
    }
}