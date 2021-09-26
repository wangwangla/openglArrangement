package com.example.example.otherlearn.core.camera;

import com.example.example.otherlearn.data.base.Size;

public interface OnCameraListener {

    /**
     * 相机打开
     */
    void onCameraOpened(Size cameraSize, int facing);

    /**
     * 相机关闭
     */
    void onCameraClosed();

    /**
     * 相机异常
     */
    void onCameraError(Exception e);
}
