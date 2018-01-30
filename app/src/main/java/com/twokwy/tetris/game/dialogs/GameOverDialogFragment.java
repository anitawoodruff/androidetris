package com.twokwy.tetris.game.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.twokwy.tetris.R;
import com.twokwy.tetris.game.GamePlayActivity;
import com.twokwy.tetris.scores.HighScoresActivity;

/**
 * Created by anita on 19/09/2015.
 */
public class GameOverDialogFragment extends DialogFragment {
    public static final String GAME_OVER_SCORE_KEY = "game_over_score";
    public static final String WAS_HIGH_SCORE_KEY = "was_high_score";
    public static final String HIGH_SCORE_POSITION_KEY = "high_score_position";

    public GameOverDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();
        final int score = args.getInt(GAME_OVER_SCORE_KEY);
        final boolean wasHighScore = args.getBoolean(WAS_HIGH_SCORE_KEY);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = createGameOverDialogView(getActivity(), score, wasHighScore);
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GamePlayActivity activity = (GamePlayActivity) getActivity();
                if (wasHighScore) {
                    final EditText editText = (EditText) view.findViewById(R.id.new_high_score_name);
                    String name = editText.getText().toString().trim();
                    final int position = args.getInt(HIGH_SCORE_POSITION_KEY, -1);
                    final SharedPreferences prefs = getActivity().getSharedPreferences("scores", 0);
                    prefs.edit().putString(HighScoresActivity.NAME_PREF_KEYS.get(position), name).apply();
                    activity.moveToHighScores();
                } else {
                    activity.backToMainMenu();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private View createGameOverDialogView(
            final Activity activity, final int score, final boolean wasHighScore) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_game_over, null);
        TextView scoreTextView = (TextView)view.findViewById(R.id.gameOverScore);
        scoreTextView.setText("" + score);
        if (wasHighScore) {
            ((TextView) view.findViewById(R.id.score_label_text_box)).setText("NEW HIGH SCORE: ");
            view.findViewById(R.id.new_high_score_name).setVisibility(View.VISIBLE);
        }
        return view;
    }
}
