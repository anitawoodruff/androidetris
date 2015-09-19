package com.twokwy.tetris.game;

/**
 * Created by anita on 18/09/2015.
 */
public class Square implements TetrisShape {

    private static final int SIDE_LENGTH = 2;
    private final Tile.Color mColor;

    public Square(Tile.Color color) {
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
        for (int i = 0; i < SIDE_LENGTH; i++) {
            for (int j = 0; j < SIDE_LENGTH; j++) {
                if (!grid.isLocationAvailable(x + i, y + j)) {
                    return false;
                }
            }
        }
        for (int i = 0; i < SIDE_LENGTH; i++) {
            for (int j = 0; j < SIDE_LENGTH; j++) {
                grid.occupyTileAtPosition(x + i, y + j, mColor);
            }
        }
        return true;
    }

    @Override
    public void removeFromGridAtLocation(TileGrid grid, int x, int y) {
        for (int i = 0; i < SIDE_LENGTH; i++) {
            for (int j = 0; j < SIDE_LENGTH; j++) {
                grid.clearTileAtPosition(x + i, y + j);
            }
        }

    }
}