package com.example.example.utils;

import android.opengl.Matrix;

//这种方式改变的是相机的位置， 旋转也是旋转相机
public class MatrixUtils {
    //相机位置
    private float[] mMatrixCamera =new float[16];
    //透视
    private float[] mMatrixProjection = new float[16];
    //变换矩阵
    private float[] mMVPMatrix=new float[16];
    private float[] mMatrixCurrent =new float[]{
            1,0,0,0,
            0,1,0,0,
            0,0,1,0,
            0,0,0,1
    };

    public void flip(boolean x,boolean y){
        if(x||y){
            Matrix.scaleM(mMatrixCurrent,0,x?-1:1,y?-1:1,1);
        }
    }

    //沿X、Y、Z轴方向进行平移变换的方法
    public void translate(float x,float y,float z) {
//        Matrix.translateM(mMatrixCurrent, 0, x, y, z);
        Matrix.translateM(mMatrixCurrent,0,x,y,z);
    }

    public void scale(float x,float y){
        Matrix.scaleM(mMatrixCurrent,0,x,y,1);
    }

    public void rotateX(float angle){
        Matrix.rotateM(mMatrixCurrent,0,angle,1,0,0);
    }

    public void rotateY(float angle){
        Matrix.rotateM(mMatrixCurrent,0,angle,0,1,0);
    }

    public void rotateZ(float angle){
        Matrix.rotateM(mMatrixCurrent,0,angle,0,0,1);
    }

    float zom = 2;
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
    public void getCenterInsideMatrix2d(int imgWidth, int imgHeight, int viewWidth, int viewHeight){
        if(imgHeight>0&&imgWidth>0&&viewWidth>0&&viewHeight>0){
            float sWhView=(float)viewWidth/viewHeight;
            float sWhImg=(float)imgWidth/imgHeight;
            if(sWhImg>sWhView){
                Matrix.orthoM(mMatrixProjection,0,
                        -1 * zom,1* zom,-sWhImg/sWhView* zom,
                        sWhImg/sWhView* zom,0,100);
            }else{
                Matrix.orthoM(mMatrixProjection,0,-sWhView/sWhImg* zom,
                        sWhView/sWhImg* zom,-1* zom,1* zom,1,100);
            }
            lookAt(0,0,6,0,0,0,0,1,0);
        }
    }

    public void lookAt( float eyeX,     float eyeY,     float eyeZ,
                        float centerX,  float centerY,  float centerZ,
                        float upX,      float upY,      float upZ){
        Matrix.setLookAtM(mMatrixCamera,0,
                eyeX,eyeY,eyeZ,
                centerX,centerY,centerZ,
                upX,upY,upZ);
    }


    public void getCenterInsideMatrix1(int imgWidth,int imgHeight){
        if(imgHeight>0&&imgWidth>0){
            if(imgWidth>imgHeight){
                Matrix.orthoM(mMatrixProjection,0,
                    -2,2,-imgWidth/imgHeight,
                        imgWidth/imgHeight,1,3);
            }else{
                Matrix.orthoM(mMatrixProjection,0,
                        -imgHeight/imgWidth * 2,imgHeight/imgWidth * 2,-2,2,1,3);
            }
            Matrix.setLookAtM(mMatrixCamera,0,
                    0,0,2,
                    0,0,0,
                    0,1,0);
        }
    }


    public float[] getmMVPMatrix() {
        for (int i = 0; i < mMVPMatrix.length; i++) {
            mMVPMatrix[i] = 0;
        }
        Matrix.multiplyMM(mMVPMatrix,0, mMatrixCamera,0, mMatrixCurrent,0);
        Matrix.multiplyMM(mMVPMatrix,0, mMatrixProjection,0,mMVPMatrix,0);
        return mMVPMatrix;
    }


}
