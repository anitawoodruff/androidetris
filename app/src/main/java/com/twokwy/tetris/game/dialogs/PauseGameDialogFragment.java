package com.twokwy.tetris.game.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.twokwy.tetris.R;

/**
 * Created by anita on 16/09/2015.
 */
public class PauseGameDialogFragment extends DialogFragment {

    private OnUserContinuedGameListener mContinueListener;
    private OnUserEndedGameListener mEndGameListener;

    // Container Activity must implement this interface
    public interface OnUserEndedGameListener {
        void onUserEndedGame();
    }

    // Container Activity must implement this interface
    public interface OnUserContinuedGameListener {
        void onUserContinuedGame();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mContinueListener = (OnUserContinuedGameListener) activity;
            mEndGameListener = (OnUserEndedGameListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement OnUserEndedGameListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = createPauseDialogView(getActivity(), this, mEndGameListener, mContinueListener);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private static View createPauseDialogView(final Activity activity,
                                              final DialogFragment parent,
                                              final OnUserEndedGameListener gameOverListener,
                                              final OnUserContinuedGameListener continueListener) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_game_pause, null);
        view.findViewById(R.id.pauseDialogContinueButton)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.dismiss();
                continueListener.onUserContinuedGame();
            }
        });
        view.findViewById(R.id.pauseDialogEndGameButton)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.dismiss();
                gameOverListener.onUserEndedGame();
            }
        });
        return view;
    }
}