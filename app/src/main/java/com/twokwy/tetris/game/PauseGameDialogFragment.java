package com.twokwy.tetris.game;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_game_pause, null);
        view.findViewById(R.id.pauseDialogContinueButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PauseGameDialogFragment.this.dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }

}