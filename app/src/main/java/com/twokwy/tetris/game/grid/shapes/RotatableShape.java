package com.twokwy.tetris.game.grid.shapes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.twokwy.tetris.game.grid.Coordinate;
import com.twokwy.tetris.game.grid.TileGrid;
import com.twokwy.tetris.game.grid.tile.Tile;

/**
 * Created by anita on 20/09/2015.
 */
public abstract class RotatableShape implements TetrisShape {

    protected abstract ImmutableList<ImmutableSet<Coordinate>> getOrientations();

    private final Tile.Color mColor;
    protected int mOrientation;

    public RotatableShape(Tile.Color color) {
        mColor = color;
        mOrientation = 0;
    }

    private ImmutableSet<Coordinate> getCoordinatesForOrientation(int orientation) {
        ImmutableList<ImmutableSet<Coordinate>> orientations = getOrientations();
        int index = ((orientation % orientations.size()) + orientations.size()) % orientations.size();
        return orientations.get(index);
    }

    @Override
    public Tile.Color getColor() {
        return mColor;
    }

    @Override
    public boolean addToGridAtLocation(final TileGrid grid, int x, int y) {
        // check space is available first.
        ImmutableSet<Coordinate> localCoordinates = getCurrentCoordinates();
        if (!checkSpaceAvailable(grid, x, y, localCoordinates)) {
            return false;
        }
        for (Coordinate coord : localCoordinates) {
            grid.occupyTileAtPosition(x + coord.getX(), y + coord.getY(), mColor);
        }
        return true;
    }

    @Override
    public void removeFromGridAtLocation(TileGrid grid, int x, int y) {
        for (Coordinate coord : getCurrentCoordinates()) {
                grid.clearTileAtPosition(x + coord.getX(), y + coord.getY());
        }
    }

    @Override
    public boolean rotateLeft(TileGrid grid, int x, int y) {
        return rotateByNRotations(grid, x, y, -1);
    }

    @Override
    public boolean rotateRight(TileGrid grid, int x, int y) {
        return rotateByNRotations(grid, x, y, 1);
    }

    private boolean rotateByNRotations(TileGrid grid, int x, int y, int n) {
        removeFromGridAtLocation(grid, x, y);
        ImmutableSet<Coordinate> newCoords = getCoordinatesForOrientation(mOrientation + n);
        if (!checkSpaceAvailable(grid, x, y, newCoords)) {
            addToGridAtLocation(grid, x, y); // put it back
            return false;
        } else {
            mOrientation += n;
            addToGridAtLocation(grid, x, y);
            return true;
        }

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

    private ImmutableSet<Coordinate> getCurrentCoordinates() {
        return getCoordinatesForOrientation(mOrientation);
    }
}
