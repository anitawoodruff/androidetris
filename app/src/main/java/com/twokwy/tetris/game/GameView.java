package com.twokwy.tetris.game;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.twokwy.tetris.R;

public class GameView extends View {

    private TileGrid mTileGrid;
    private final int mTileSize;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GameView);
        mTileSize = a.getDimensionPixelSize(R.styleable.GameView_tileSize, 24);
        a.recycle();
        init();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GameView);
        mTileSize = a.getDimensionPixelSize(R.styleable.GameView_tileSize, 24);
        a.recycle();
    }

    private void init() {
        // initialise the real tile grid later in onSizeChanged when dimensions are known
        mTileGrid = TileGridFactory.createEmptyTileGrid();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mTileGrid = TileGridFactory.createToFillWidthAndHeight(mTileSize, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final ImmutableList<PositionedTile> tilesToDraw = mTileGrid.getPositionedTiles();
        for (PositionedTile tile : tilesToDraw) {
            Drawable tileDrawable = createTileDrawable(tile.getColor(), tile.getBounds());
            tileDrawable.draw(canvas);
        }
    }

    private ShapeDrawable createTileDrawable(Optional<Tile.Color> tileColor, Rect bounds) {
        final ShapeDrawable tileDrawable = new ShapeDrawable(new RectShape());
        final int colorResId = tileColor.isPresent() ? translateToResId(tileColor.get())
                : R.color.grey;
        tileDrawable.getPaint().setColor(getResources().getColor(colorResId));
        tileDrawable.setBounds(bounds);
        return tileDrawable;
    }

    private static int translateToResId(Tile.Color tileColor) {
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

    public void onDownControl() {
        mTileGrid.insertShapeAtTop(new Square(Tile.Color.BLUE));
        invalidate();
    }
}
