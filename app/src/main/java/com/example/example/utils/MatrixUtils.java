package com.example.example.utils;

import android.opengl.Matrix;

public class MatrixUtils {
    //相机位置
    private static float[] mViewMatrix=new float[16];
    //透视
    private static float[] mProjectMatrix=new float[16];
    //变换矩阵
    private static float[] mMVPMatrix=new float[]{
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


    public void getCenterInsideMatrix(int imgWidth,int imgHeight,int viewWidth,int
            viewHeight){
        if(imgHeight>0&&imgWidth>0&&viewWidth>0&&viewHeight>0){
            float sWhView=(float)viewWidth/viewHeight;
            float sWhImg=(float)imgWidth/imgHeight;
//            float[] projection=new float[16];
//            float[] camera=new float[16];
            if(sWhImg>sWhView){
                Matrix.frustumM(mProjectMatrix,0,
                        -1,1,-sWhImg/sWhView,
                        sWhImg/sWhView,1,3);
            }else{
                Matrix.frustumM(mProjectMatrix,0,-sWhView/sWhImg,sWhView/sWhImg,-1,1,1,3);
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
