package com.twokwy.tetris.game;

import java.util.List;

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
    public List<PositionedTile> addToGridAtLocation(final TileGrid grid, final int row, final int column) {
        for (int i = 0; i < SIDE_LENGTH; i++) {
            for (int j = 0; j < SIDE_LENGTH; j++) {
                try {
                    grid.occupyTileAtPosition(row + i, column + j, mColor);
                } catch (TileOutOfGridException e) {
                    throw new TileOutOfGridException(e.getMessage() + " i="+i + ", j="+j);
                }
            }
        }
        return grid.getPositionedTiles();
    }
}