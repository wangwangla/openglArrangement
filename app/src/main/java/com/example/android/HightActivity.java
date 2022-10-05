package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.example.R;
import com.example.game.kwgame.KwSurfaceView;

public class HightActivity extends AppCompatActivity {
    private KwSurfaceView surfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hight);
        surfaceView = findViewById(R.id.hightex);
        Intent intent = getIntent();
        int itemid = intent.getIntExtra("itemid",0);
        surfaceView.setType(1000+itemid);
    }
}