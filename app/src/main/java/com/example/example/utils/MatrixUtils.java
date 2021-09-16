package com.example.example.utils;

import android.opengl.Matrix;

public class MatrixUtils {
    //相机位置
    private float[] mViewMatrix=new float[16];
    //透视
    private float[] mProjectMatrix=new float[16];
    //变换矩阵
    private float[] mMVPMatrix=new float[]{
            1,0,0,0,
            0,1,0,0,
            0,0,1,0,
            0,0,0,1
    };

    public void flip(boolean x,boolean y){
        if(x||y){
            Matrix.scaleM(mMVPMatrix,0,x?-1:1,y?-1:1,1);
        }
    }


    public void scale(float x,float y){
        Matrix.scaleM(mMVPMatrix,0,x,y,1);
    }

    public void rotateX(float angle){
        Matrix.rotateM(mMVPMatrix,0,angle,1,0,0);
    }

    public void rotateY(float angle){
        Matrix.rotateM(mMVPMatrix,0,angle,0,1,0);
    }

    public void rotateZ(float angle){
        Matrix.rotateM(mMVPMatrix,0,angle,0,0,1);
    }


    /**
     * 图片的宽度/高度大于屏幕，说明是宽视频  举个极端的例子
     * 1.720x1280播放1280x720的视频
     * 2.然后左右都为1，说明视频的宽度和屏幕一样
     * 3.上下会留空出来，不拉伸的情况
     * 4.上下liu
     * @param imgWidth
     * @param imgHeight
     * @param viewWidth
     * @param viewHeight
     */
    public void getCenterInsideMatrix(int imgWidth,int imgHeight,int viewWidth,int viewHeight){
        if(imgHeight>0&&imgWidth>0&&viewWidth>0&&viewHeight>0){
            float sWhView=(float)viewWidth/viewHeight;
            float sWhImg=(float)imgWidth/imgHeight;
            if(sWhImg>sWhView){
                Matrix.orthoM(mProjectMatrix,0,
                        -1,1,-sWhImg/sWhView,
                        sWhImg/sWhView,1,3);
            }else{
                Matrix.orthoM(mProjectMatrix,0,-sWhView/sWhImg,sWhView/sWhImg,-1,1,1,3);
            }
            Matrix.setLookAtM(mViewMatrix,0,
                    0,0,2,
                    0,0,0,
                    0,1,0);
            Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
        }
    }

    public float[] getmMVPMatrix() {
        return mMVPMatrix;
    }

    public static float[] getOriginalMatrix(){
        return new float[]{
                1,0,0,0,
                0,1,0,0,
                0,0,1,0,
                0,0,0,1
        };
    }
}
