package com.example.example.otherlearn.core.camera;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;

import com.example.example.otherlearn.data.base.Size;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Camera1 implements ICamera {
    private static final String TAG = "Camera1";
    /**
     * Camera
     */
    private Camera camera;

    /**
     * 预览尺寸
     */
    private Size cameraSize = new Size(-1, -1);

    /**
     * 默认摄像头方向
     */
    private int facing = CameraConstants.facing.BACK;

    /**
     * 旋转角度   旋转
     */
    private int displayOrientation = -1;

    /**
     * 相机回调
     */
    private final List<OnCameraListener> onCameraListenerList = new ArrayList<>();

    /**
     * 打开相机
     * @param activity
     * @param surfaceTexture
     */
    @Override
    public void openCamera(Activity activity, SurfaceTexture surfaceTexture) {
        openCamera(facing, activity, surfaceTexture);
    }

    @Override
    public void openCamera(int facing, Activity activity, SurfaceTexture surfaceTexture) {
        // 先关闭相机
        closeCamera();
        // 判断是否存在摄像头   获取相机的个数
        int cameraNum = Camera.getNumberOfCameras();
        if (cameraNum <= 0) {
            onCameraError(new IllegalStateException("camera num <= 0"));
            return;
        }

        // 设置相机
        int cameraIndex = -1;
        if (facing == CameraConstants.facing.BACK) {
            cameraIndex = Camera.CameraInfo.CAMERA_FACING_BACK;
        } else if (facing == CameraConstants.facing.FRONT) {
            cameraIndex = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        if (cameraIndex == -1) {
            onCameraError(new IllegalStateException("camera facing exception"));
            return;
        }

        // 判断摄像头个数，以决定使用哪个打开方式  如果只有一个就是唯一的哪一个
        if (cameraNum >= 2) {
            camera = Camera.open(cameraIndex);
        } else {
            camera = Camera.open();
        }

        // 判断Camera是否初始化成功
        if (camera == null) {
            onCameraError(new IllegalStateException("camera is null"));
            return;
        }

        this.facing = facing;

        try {
            // 获取摄像头参数
            Camera.Parameters parameters = camera.getParameters();
            List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();
            if (cameraSize.getWidth() <= 0 || cameraSize.getHeight() <= 0) {
                Camera.Size size = findTheBestSize(
                        previewSizeList,
                        activity.getResources().getDisplayMetrics().widthPixels,
                        activity.getResources().getDisplayMetrics().heightPixels);
                cameraSize.setWidth(size.width);
                cameraSize.setHeight(size.height);
            }

            // 设置预览尺寸
            parameters.setPreviewSize(cameraSize.getWidth(), cameraSize.getHeight());
            //图片尺寸
            parameters.setPictureSize(cameraSize.getWidth(),cameraSize.getHeight());
            // 这里设置使用的对焦模式
            if (this.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            }
            // 设置摄像头参数
            camera.setParameters(parameters);
            camera.setPreviewTexture(surfaceTexture);

            //旋转
            if (displayOrientation < 0) {
                displayOrientation = CameraUtils.getDisplayOrientation(activity, cameraIndex);
            }
            camera.setDisplayOrientation(displayOrientation);

            //预览回调
            camera.setOneShotPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    onCameraOpened(cameraSize, Camera1.this.facing);
                }
            });

            //开始预览
            camera.startPreview();
        } catch (IOException e) {
            onCameraError(e);
        }
    }

    /**
     * 寻找最合适的尺寸
     */
    private Camera.Size findTheBestSize(List<Camera.Size> sizeList, int screenW, int screenH) {
        if (sizeList == null || sizeList.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Log.d(TAG, "findTheBestSize: screenW:" + screenW + " screenH:" + screenH);

        Camera.Size bestSize = sizeList.get(0);
        for (Camera.Size size : sizeList) {
            int width = size.height;
            int height = size.width;

            Log.d(TAG, "findTheBestSize: width:" + width + " height:" + height);

            float ratioW = (float) width / screenW;
            float ratioH = (float) height / screenH;

            Log.d(TAG, "findTheBestSize: ratioW:" + ratioW + " ratioH:" + ratioH);

            if (ratioW == ratioH) {
                bestSize = size;
                break;
            }
        }
        return bestSize;
    }

    /**
     * 如果相机原本没有打开就返回
     * 如果开启了 ，那么就先停止绘制，然后回收一下相机
     */
    @Override
    public void closeCamera() {
        if (camera == null) {
            return;
        }
        camera.stopPreview();
        camera.release();
        camera = null;
        onCameraClosed();
    }

    //切换相机
    @Override
    public void switchCamera() {
        if (facing == CameraConstants.facing.BACK) {
            facing = CameraConstants.facing.FRONT;
        } else {
            facing = CameraConstants.facing.BACK;
        }
    }

    @Override
    public void switchCamera(int facing) {
        this.facing = facing;
    }

    @Override
    public void setCameraFacing(int facing) {
        this.facing = facing;
    }

    @Override
    public int getCameraFacing() {
        return facing;
    }

    @Override
    public void setPreviewSize(Size cameraSize) {
        this.cameraSize = cameraSize;
    }

    @Override
    public Size getPreviewSize() {
        return cameraSize;
    }

    @Override
    public void setDisplayOrientation(int displayOrientation) {
        this.displayOrientation = displayOrientation;
    }

    @Override
    public int getDisplayOrientation() {
        return displayOrientation;
    }

    /**
     * 关闭相机的时候删除所有的事件
     */
    @Override
    public void releaseCamera() {
        closeCamera();
        removeAllOnCameraListener();
    }

    @Override
    public void addOnCameraListener(OnCameraListener onCameraListener) {
        onCameraListenerList.add(onCameraListener);
    }

    @Override
    public void removeOnCameraListener(OnCameraListener onCameraListener) {
        onCameraListenerList.remove(onCameraListener);
    }

    @Override
    public void removeAllOnCameraListener() {
        onCameraListenerList.clear();
    }

    /**
     * 相机打开回调
     */
    private void onCameraOpened(Size cameraSize, int facing) {
        if (onCameraListenerList.isEmpty()) {
            return;
        }
        for (OnCameraListener listener : onCameraListenerList) {
            listener.onCameraOpened(cameraSize, facing);
        }
    }

    /**
     * 相机关闭回调
     */
    private void onCameraClosed() {
        if (onCameraListenerList.isEmpty()) {
            return;
        }
        for (OnCameraListener listener : onCameraListenerList) {
            listener.onCameraClosed();
        }
    }

    /**
     * 相机错误回调
     */
    private void onCameraError(Exception error) {
        if (onCameraListenerList.isEmpty()) {
            return;
        }
        for (OnCameraListener listener : onCameraListenerList) {
            listener.onCameraError(error);
        }
    }
}
