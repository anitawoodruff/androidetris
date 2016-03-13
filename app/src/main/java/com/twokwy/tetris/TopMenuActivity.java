package com.twokwy.tetris;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.Firebase;
import com.twokwy.tetris.game.GamePlayActivity;
import com.twokwy.tetris.scores.HighScoresActivity;


public class TopMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_top_menu);
    }

    public void onClickPlayButton(View view) {
        Intent intent = new Intent(this, GamePlayActivity.class);
        startActivity(intent);
    }

    public void onClickHighScoresButton(View view) {
        Intent intent = new Intent(this, HighScoresActivity.class);
        startActivity(intent);
    }
}
