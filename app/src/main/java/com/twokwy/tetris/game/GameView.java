package com.twokwy.tetris.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.twokwy.tetris.R;
import com.twokwy.tetris.game.grid.TileGrid;
import com.twokwy.tetris.game.grid.TileGridFactory;
import com.twokwy.tetris.game.grid.tile.PositionedTile;
import com.twokwy.tetris.game.grid.tile.Tile;

import java.lang.ref.WeakReference;

public class GameView extends View {

    private static final int UPDATE_TASK = 0;
    private static final int MAX_SPEED = 300;
    private static final int MAX_TICK_FRACTION = 2;

    private TileGrid mTileGrid;
    private GameOverListener mGameOverListener;

    private RefreshHandler mRefreshCurrentPieceHandler = new RefreshHandler(this);
    private int mCurrentTick = 500;
    private int mCurrentScore = 0;
    private boolean mPaused = false;
    private int mTickFraction = 0;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTileGrid = TileGridFactory.createToFillWidthAndHeight(w, h);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            mGameOverListener = (GameOverListener) this.getContext();
        } catch (ClassCastException e) {
            if (!(getContext() instanceof Activity)) {
                return; // workaround for layout preview screen in Android Studio
            }
            throw new ClassCastException(
                    this.getContext().toString() + " must implement GameOverListener");
        }
        this.setFocusable(true);
        setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                }
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                    case KeyEvent.KEYCODE_SOFT_LEFT:
                    case KeyEvent.KEYCODE_A:
                        onMoveLeftControl();
                        return true;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                    case KeyEvent.KEYCODE_D:
                        onMoveRightControl();
                        return true;
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                    case KeyEvent.KEYCODE_SPACE:
                    case KeyEvent.KEYCODE_S:
                        onDropControl();
                        return true;
                    case KeyEvent.KEYCODE_R:
                    case KeyEvent.KEYCODE_DPAD_UP:
                    case KeyEvent.KEYCODE_W:
                    case KeyEvent.KEYCODE_J:
                        onRotateRightControl();
                        return true;
                    case KeyEvent.KEYCODE_P:
                        if (mPaused) {
                            onResumeGame();
                        } else {
                            onPauseGame();
                        }
                        return true;
                    default:
                        Log.d("UnknownKeycode", "" + keyCode);
                        return false;
                }
            }
        });
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

    private ShapeDrawable createTileDrawable(Optional<Tile.Color> tileColor, final Rect bounds) {
        final ShapeDrawable tileDrawable = new ShapeDrawable(new RectShape());
        final int colorResId = tileColor.isPresent() ? translateToResId(tileColor.get())
                : R.color.grey;
        tileDrawable.getPaint().setColor(getResources().getColor(colorResId));
        tileDrawable.setBounds(bounds.left, bounds.top + 1, bounds.right - 1, bounds.bottom);
        return tileDrawable;
    }

    private static int translateToResId(Tile.Color tileColor) {
        switch (tileColor) {
            case RED:
                return R.color.red;
            case BLUE:
                return R.color.blue;
            case GREEN:
                return R.color.green;
            case YELLOW:
                return R.color.yellow;
            case CYAN:
                return R.color.cyan;
            case MAGENTA:
                return R.color.magenta;
        }
        return -1; // shouldn't happen
    }

    private void updateCurrentPieceAndScheduleNextUpdate() {
        if (!mTileGrid.moveCurrentShapeDown()) {
            // it's reached the bottom, check for full rows & add a new shape at the top
            final int rowsRemoved = mTileGrid.removeFullRows();
            if (rowsRemoved > 0) {
                // give em some points
                mCurrentScore += rowsRemoved * 5;
                Activity parentActivity = (Activity) getContext();
                final TextView scoreTextView = (TextView) parentActivity.findViewById(R.id.currentScore);
                scoreTextView.setText(String.format("%d", mCurrentScore));
            }
            final boolean inserted = mTileGrid.insertNewShapeAtTop();
            if (!inserted) {
                // could not insert, so we must have reached the top
                mGameOverListener.gameOver(mCurrentScore);
                return;
            }
        }
        mRefreshCurrentPieceHandler.sleep(mCurrentTick);
        mTickFraction++;
        if (mTickFraction >= MAX_TICK_FRACTION) {
            mCurrentTick = Math.max(MAX_SPEED, mCurrentTick - 1); // speed up linearly to max speed.
            mTickFraction = 0;
        }
    }

    public void onStartGame() {
        mTileGrid.insertNewShapeAtTop();
        updateCurrentPieceAndScheduleNextUpdate();
    }

    public void onPauseGame() {
        mPaused = true;
        mRefreshCurrentPieceHandler.removeMessages(UPDATE_TASK);
    }

    public void onResumeGame() {
        mPaused = false;
        mRefreshCurrentPieceHandler.sleep(mCurrentTick);
    }

    public void onDropControl() {
        mTileGrid.dropCurrentPiece();
        invalidate();
    }

    public void onMoveLeftControl() {
        mTileGrid.moveCurrentShapeLeft();
        invalidate();
    }

    public void onMoveRightControl() {
        mTileGrid.moveCurrentShapeRight();
        invalidate();
    }

    public void onRotateLeftControl() {
        mTileGrid.rotateCurrentPieceLeft();
        invalidate();
    }

    public void onRotateRightControl() {
        mTileGrid.rotateCurrentPieceRight();
        invalidate();
    }

    static class RefreshHandler extends Handler {

        private WeakReference<GameView> mGameView;

        RefreshHandler(GameView gameView) {
            mGameView = new WeakReference<>(gameView);
        }

        @Override
        public void handleMessage(Message msg) {
            GameView gameView = mGameView.get();
            if (!gameView.mPaused) {
                gameView.updateCurrentPieceAndScheduleNextUpdate();
                gameView.invalidate();
            }
        }

        public void sleep(long delayMillis) {
            this.removeMessages(UPDATE_TASK);
            sendMessageDelayed(obtainMessage(UPDATE_TASK), delayMillis);
        }
    }
}
