package com.twokwy.tetris.game;

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

    private OnUserEndedGameListener mListener;

    // Container Activity must implement this interface
    public interface OnUserEndedGameListener {
        public void onUserEndedGame();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnUserEndedGameListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement OnUserEndedGameListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = createPauseDialogView(getActivity(), this, mListener);
        builder.setView(view);
        return builder.create();
    }

    private static View createPauseDialogView(final Activity activity,
                                              final DialogFragment parent,
                                              final OnUserEndedGameListener listener) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_game_pause, null);
        view.findViewById(R.id.pauseDialogContinueButton)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.dismiss();
            }
        });
        view.findViewById(R.id.pauseDialogEndGameButton)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.dismiss();
                listener.onUserEndedGame();
            }
        });
        return view;
    }
}