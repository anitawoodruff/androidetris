package com.twokwy.tetris.game;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.common.collect.ImmutableList;
import com.twokwy.tetris.R;
import com.twokwy.tetris.game.dialogs.GameOverDialogFragment;
import com.twokwy.tetris.game.dialogs.PauseGameDialogFragment;
import com.twokwy.tetris.scores.HighScoresActivity;

import static com.twokwy.tetris.scores.HighScoresActivity.HIGH_SCORE_SHARED_PREFS;

/**
 * Created by anita on 16/09/2015.
 */
public class GamePlayActivity extends Activity implements GameOverListener,
        PauseGameDialogFragment.OnUserEndedGameListener,
        PauseGameDialogFragment.OnUserContinuedGameListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        getActionBar().hide();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onPauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onResumeGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        showPauseDialog();
    }

    public void onClickPauseButton(View view) {
        showPauseDialog();
    }

    private void showPauseDialog() {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onPauseGame();
        Bundle bundle = new Bundle();
        DialogFragment pauseGameDialogFragment = new PauseGameDialogFragment();
        pauseGameDialogFragment.setArguments(bundle);
        pauseGameDialogFragment.show(getFragmentManager(), "pause-game-popup");
    }

    @Override
    public void onUserEndedGame(final int score) {
        gameOver(score);
    }

    @Override
    public void gameOver(int score) {
        final int newHighScorePosition = saveScore(score);
        final boolean gotHighScore = newHighScorePosition != -1;
        DialogFragment gameOverDialogFragment = new GameOverDialogFragment();
        Bundle args = new Bundle();
        args.putInt(GameOverDialogFragment.GAME_OVER_SCORE_KEY, score);
        args.putBoolean(GameOverDialogFragment.WAS_HIGH_SCORE_KEY, gotHighScore);
        if (gotHighScore) {
            args.putInt(GameOverDialogFragment.HIGH_SCORE_POSITION_KEY, newHighScorePosition);
        }
        gameOverDialogFragment.setArguments(args);
        gameOverDialogFragment.show(getFragmentManager(), "game-over-popup");
    }

    /*
     * Saves the score to prefs if it is a new high score.
     * @return position of new high score, or -1 if no high score achieved.
     */
    private int saveScore(int newScore) {
        final SharedPreferences prefs = getSharedPreferences(HIGH_SCORE_SHARED_PREFS, 0);
        final ImmutableList<String> scoreKeys = HighScoresActivity.SCORE_PREF_KEYS;
        boolean achievedHighScore = false;
        int position = -1;
        for (int i = 0; i < scoreKeys.size(); i++) {
            final int score = prefs.getInt(scoreKeys.get(i), 0);
            if (score > newScore) {
                // not beaten this score, try the next.
                continue;
            }
            if (!achievedHighScore) {
                // woop, we got a high score!
                position = i;
                achievedHighScore = true;
            }
            prefs.edit().putInt(scoreKeys.get(i), newScore).apply();
            newScore = score;
            // continue shifting scores down
        }
        return position;
    }

    public void onClickStartButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onStartGame();
        view.setEnabled(false);
    }

    public void onClickLeftButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onMoveLeftControl();
    }

    public void onClickRightButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onMoveRightControl();
    }

    public void onClickRotateLeftButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onRotateLeftControl();
    }

    public void onClickRotateRightButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onRotateRightControl();
    }

    public void onClickDropButton(View view) {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onDropControl();
    }

    @Override
    public void onUserContinuedGame() {
        GameView gameView = (GameView) findViewById(R.id.game_view);
        gameView.onResumeGame();
    }

    public void moveToHighScores() {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
        finish();
    }

    public void backToMainMenu() {
        finish();
    }
}
