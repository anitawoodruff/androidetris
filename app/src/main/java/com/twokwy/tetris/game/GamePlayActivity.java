package com.twokwy.tetris.game;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.twokwy.tetris.R;
import com.twokwy.tetris.game.dialogs.GameOverDialogFragment;
import com.twokwy.tetris.game.dialogs.PauseGameDialogFragment;
import com.twokwy.tetris.scores.HighScoresActivity;

/**
 * Created by anita on 16/09/2015.
 */
public class GamePlayActivity extends Activity implements GameOverListener, PauseGameDialogFragment.OnUserEndedGameListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        getActionBar().hide();
    }

    @Override
    public void onBackPressed() {
        showPauseDialog();
    }

    public void onClickPauseButton(View view) {
        showPauseDialog();
    }

    private void showPauseDialog() {
        DialogFragment pauseGameDialogFragment = new PauseGameDialogFragment();
        pauseGameDialogFragment.show(getFragmentManager(), "pause-game-popup");
    }

    @Override
    public void onUserEndedGame() {
        Intent intent = new Intent(this, HighScoresActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void gameOver(int score) {
        DialogFragment gameOverDialogFragment = new GameOverDialogFragment();
        Bundle args = new Bundle();
        args.putInt(GameOverDialogFragment.GAME_OVER_SCORE_KEY, score);
        gameOverDialogFragment.setArguments(args);
        gameOverDialogFragment.show(getFragmentManager(), "game-over-popup");
    }

    public void onClickGameOverOKButton(View view) {
        onUserEndedGame();
    }

    public void onClickNewPieceButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onInsertNewPiece();
    }

    public void onClickLeftButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onMoveLeftControl();
    }

    public void onClickRightButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onMoveRightControl();
    }

    public void onClickDownButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onMoveDownControl();
    }
}
