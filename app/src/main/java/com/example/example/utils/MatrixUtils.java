package com.example.example.utils;

import android.opengl.Matrix;

public class MatrixUtils {
    private static final float MatrixArr[] = new float[16];
    //相机位置
    private static float[] mViewMatrix=new float[16];
    //透视
    private static float[] mProjectMatrix=new float[16];
    //变换矩阵
    private static float[] mMVPMatrix=new float[16];


//    public void xx(){
////        设置相机类型
//        Matrix.frustumM(mProjectMatrix,0,-ratio,ratio,-1,1,3,7);
////        设置相机位置
//        Matrix.setLookAtM(mViewMatrix, 0,
//                0, 0, 7.0f,
//                0f, 0f, 0f,
//                0f, 1.0f, 0.0f);
//        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
//    }

    public static void setCrameType(float width,float hight,float bottom,float top,float near,float far){
        float ratio=(float)width/hight;
        Matrix.frustumM(mProjectMatrix,0,-ratio,ratio,bottom,top,near,far);
    }

    public static void setCramePostion(float eyeX,float eyeY,float eyeZ,
                                       float centerX,float centerY,float centerZ,
                                       float upX,float upY,float upZ){
        //        设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0,
                0, 0, 7.0f,
                0f, 0f, 0f,
                0f, 1.0f, 0.0f);
    }

    public static float[] getFinal(){
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
        return mMVPMatrix;
    }
}
