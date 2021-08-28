package com.example.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;

import com.example.example.kwgame.KwSurfaceView;

public class MainActivity extends AppCompatActivity {
    private KwSurfaceView surfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.kwSurface);
        Intent intent = getIntent();
        int itemid = intent.getIntExtra("itemid",0);
        surfaceView.setType(itemid);
    }
}
