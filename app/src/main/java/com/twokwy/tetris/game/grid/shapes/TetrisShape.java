package com.twokwy.tetris.game.grid.shapes;

import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;
import com.twokwy.tetris.game.grid.TileGrid;
import com.twokwy.tetris.game.grid.tile.Tile;

/**
 * Created by anita on 18/09/2015.
 */
public class TetrisShape {

    private final Shape mShape;
    private final Tile.Color mColor;

    public TetrisShape(Shape shape, Tile.Color color) {
        mShape = shape;
        mColor = color;
    }

    public Tile.Color getColor() {
        return mColor;
    }

    public boolean addToGridAtLocation(TileGrid grid, int x, int y) {
        // check space is available first.
        ImmutableSet<Coordinate> localCoordinates = mShape.getCurrentLocalCoordinates();
        if (!checkSpaceAvailable(grid, x, y, localCoordinates)) {
            return false;
        }
        for (Coordinate coord : localCoordinates) {
            grid.occupyTileAtPosition(x + coord.getX(), y + coord.getY(), mColor);
        }
        return true;
    }

    private boolean checkSpaceAvailable(TileGrid grid, int x, int y,
                                        ImmutableSet<Coordinate> localCoordinates) {
        for (Coordinate coord : localCoordinates) {
            if (!grid.isLocationAvailable(x + coord.getX(), y + coord.getY())) {
                return false;
            }
        }
        return true;
    }

    public void removeFromGridAtLocation(TileGrid grid, int x, int y) {
        for (Coordinate coord : mShape.getCurrentLocalCoordinates()) {
            grid.clearTileAtPosition(x + coord.getX(), y + coord.getY());
        }
    }

    public boolean rotateLeft(TileGrid grid, int x, int y) {
        return rotateByNRotations(grid, x, y, -1);
    }

    public boolean rotateRight(TileGrid grid, int x, int y) {
        return rotateByNRotations(grid, x, y, 1);
    }

    private boolean rotateByNRotations(TileGrid grid, int x, int y, int nRotations) {
        removeFromGridAtLocation(grid, x, y);
        ImmutableSet<Coordinate> newCoords = mShape.getCoordinatesForRotation(nRotations);
        if (!checkSpaceAvailable(grid, x, y, newCoords)) {
            addToGridAtLocation(grid, x, y); // put it back
            return false;
        } else {
            mShape.updateOrientation(nRotations);
            addToGridAtLocation(grid, x, y);
            return true;
        }

    }
}
