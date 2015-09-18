package com.twokwy.tetris.game;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.base.Optional;
import com.twokwy.tetris.R;

import java.util.List;

public class GameView extends View {

    private TileGrid mTileGrid;
    private int mCurrentLevel = 0;

    public void onDownControl() {
        mTileGrid.occupyTileAtPosition(mCurrentLevel++, 0, Tile.Color.BLUE);
        invalidate();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GameView);
        int tileSize = a.getDimensionPixelSize(R.styleable.GameView_tileSize, 12);
        mTileGrid = new TileGrid(tileSize);
        a.recycle();
    }

    private ShapeDrawable createTileDrawable(Optional<Tile.Color> tileColor, Rect bounds) {
        final ShapeDrawable tileDrawable = new ShapeDrawable(new RectShape());
        final int colorResId = tileColor.isPresent() ? translateToResId(tileColor.get())
                : R.color.grey;
        tileDrawable.getPaint().setColor(getResources().getColor(colorResId));
        tileDrawable.setBounds(bounds);
        return tileDrawable;
    }

    private int translateToResId(Tile.Color tileColor) {
        switch(tileColor) {
            case RED:
                return R.color.red;
            case BLUE:
                return R.color.blue;
            case GREEN:
                return R.color.green;
            case YELLOW:
                return R.color.yellow;
        }
        return -1; // shouldn't happen
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mTileGrid.updateGridSize(w, h);
        mTileGrid.occupyTileAtPosition(0, 0, Tile.Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final List<PositionedTile> tilesToDraw = mTileGrid.getPositionedTiles();
        for (PositionedTile tile : tilesToDraw) {
            createTileDrawable(tile.getColor(), tile.getBounds()).draw(canvas);
        }
    }
}
