package com.example.example.otherlearn.opengles.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;

import com.example.example.otherlearn.core.camera.CameraManager;
import com.example.example.otherlearn.core.camera.OnCameraListener;
import com.example.example.otherlearn.data.base.Size;
import com.example.example.otherlearn.opengles.render.base.OnSurfaceTextureListener;
import com.example.example.otherlearn.opengles.view.base.OesView;

public class CameraView extends OesView implements OnCameraListener {
    private final CameraManager cameraManager = new CameraManager();
    private final Activity activity;

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (Activity) context;
        cameraManager.addOnCameraListener(this);
    }

    public void openCamera() {
        render.setOnSurfaceTextureListener(new OnSurfaceTextureListener() {
            @Override
            public void onSurfaceTexture(SurfaceTexture surfaceTexture) {
                surfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                    @Override
                    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        requestRender();
                    }
                });
                cameraManager.openCamera(activity, surfaceTexture);
            }
        });
        requestRender();
    }

    public void closeCamera() {
        cameraManager.closeCamera();
    }

    public void switchCamera() {
        cameraManager.switchCamera();
        openCamera();
    }

    public void switchCamera(int facing) {
        cameraManager.switchCamera(facing);
        openCamera();
    }

    public void addOnCameraListener(OnCameraListener onCameraListener) {
        cameraManager.addOnCameraListener(onCameraListener);
    }

    @Override
    public void onCameraOpened(Size cameraSize, int facing) {
        render.setOesSize(cameraSize.getHeight(), cameraSize.getWidth());
        requestRender();
    }

    @Override
    public void onCameraClosed() {

    }

    @Override
    public void onCameraError(Exception e) {

    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }
}
