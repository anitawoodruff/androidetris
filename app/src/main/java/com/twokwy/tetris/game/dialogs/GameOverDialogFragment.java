package com.twokwy.tetris.game.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.twokwy.tetris.R;

/**
 * Created by anita on 19/09/2015.
 */
public class GameOverDialogFragment extends DialogFragment {
    public static final String GAME_OVER_SCORE_KEY = "game_over_score";

    public GameOverDialogFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final int score = args.getInt(GAME_OVER_SCORE_KEY);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = createGameOverDialogView(getActivity(), score);
        builder.setView(view);
        return builder.create();
    }

    private View createGameOverDialogView(final Activity activity, final int score) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_game_over, null);
        TextView scoreTextView = (TextView)view.findViewById(R.id.gameOverScore);
        scoreTextView.setText("" + score);
        return view;
    }
}
