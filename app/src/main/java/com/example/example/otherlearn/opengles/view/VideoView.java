package com.example.example.otherlearn.opengles.view;


import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;

import com.example.example.otherlearn.core.play.OnPlayListener;
import com.example.example.otherlearn.core.play.PlayManager;
import com.example.example.otherlearn.data.base.Size;
import com.example.example.otherlearn.opengles.render.base.OnSurfaceTextureListener;
import com.example.example.otherlearn.opengles.view.base.OesView;

public class VideoView extends OesView implements OnPlayListener {
    private final PlayManager playManager = new PlayManager();

    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        playManager.addOnPlayListener(this);
    }

    public void play(final String path) {
        render.setOnSurfaceTextureListener(new OnSurfaceTextureListener() {
            @Override
            public void onSurfaceTexture(SurfaceTexture surfaceTexture) {
                surfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                    @Override
                    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        requestRender();
                    }
                });
                playManager.play(path, surfaceTexture);
            }
        });
        requestRender();
    }

    public void pause() {
        playManager.pause();
    }

    public void stop() {
        playManager.stop();
    }

    public void continuePlay() {
        playManager.continuePlay();
    }

    public void seekTo(int time) {
        playManager.seekTo(time);
    }

    public void addOnPlayListener(OnPlayListener onPlayListener) {
        playManager.addOnPlayListener(onPlayListener);
    }

    @Override
    public void onStartPlay(Size videoSize, long duration) {
        render.setOesSize(videoSize.getWidth(), videoSize.getHeight());
        requestRender();
    }

    @Override
    public void onPausePlay(long currentPos) {

    }

    @Override
    public void onStopPlay() {

    }

    @Override
    public void onPlayCompletion() {

    }

    @Override
    public void onErrorPlay(Exception e) {

    }

    public PlayManager getPlayManager() {
        return playManager;
    }
}
