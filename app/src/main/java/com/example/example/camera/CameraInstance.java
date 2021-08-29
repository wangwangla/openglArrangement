package com.example.example.camera;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import com.example.example.base.Constant;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CameraInstance {
    private Camera camera;
    private CameraSizeComparator sizeComparator;
    private Camera.Size picSize;
    private Camera.Size preSize;
    private Config config;

    public CameraInstance(){
        config = new Config();
        config.minPictureWidth = 720;
        config.minPreviewWidth = 720;
        config.rate = 1.778F; //1280
        sizeComparator = new CameraSizeComparator();
    }

    public void open(int cameId){
        camera = Camera.open(cameId);
        if (camera != null){
            Camera.Parameters parameters = camera.getParameters();
            picSize = getPropPictureSize(
                    parameters.getSupportedPictureSizes(),
                    config.rate,
                    config.minPictureWidth
            );
            preSize = getPropPictureSize(
                    parameters.getSupportedPictureSizes(),
                    config.rate,
                    config.minPreviewWidth
            );
            parameters.setPictureSize(picSize.width,picSize.height);
            parameters.setPreviewSize(preSize.width,preSize.height);
            camera.setParameters(parameters);
        }
    }
    private SurfaceTexture texture;
    public void setPreviewTexture(SurfaceTexture texture){
        this.texture = texture;
        if (camera!=null){
            try {
                camera.setPreviewTexture(this.texture);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void start(){
        if (camera != null){
            camera.startPreview();
        }
    }

    public void close(){
        if (camera!=null){
            camera.stopPreview();
            camera.release();
        }
    }

    public void switchTo(int cameraId){
        close();
        open(cameraId);
    }

    private Camera.Size getPropPictureSize(List<Camera.Size> supportedPictureSizes, float rate, int minPictureWidth) {
        Collections.sort(supportedPictureSizes, sizeComparator);
        int index = 0;
        for (Camera.Size supportedPictureSize : supportedPictureSizes) {
            if (supportedPictureSize.height >= minPictureWidth && equalRate(supportedPictureSize,rate)){
                break;
            }
            index ++;
        }
        if (index == supportedPictureSizes.size()){
            index = 0;
        }
        return supportedPictureSizes.get(index);
    }

    private boolean equalRate(Camera.Size s, float rate){
        float r = (float)(s.width)/(float)(s.height);
        if(Math.abs(r - rate) <= 0.03)
        {
            return true;
        }
        else{
            return false;
        }
    }

    public void render() {
        texture.updateTexImage();
    }

    private class CameraSizeComparator implements Comparator<Camera.Size> {
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if(lhs.height == rhs.height){
                return 0;
            }
            else if(lhs.height > rhs.height){
                return 1;
            }
            else{
                return -1;
            }
        }
    }

    class Config{
        public float rate; //宽高比
        public int minPreviewWidth;
        public int minPictureWidth;
    }

    public int getWidth(){
        return picSize.width;
    }

    public int getHight(){
        return picSize.height;
    }

}
