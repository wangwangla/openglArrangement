package com.example.example.base.filter.f3d;

import android.opengl.GLES20;

import com.example.example.base.BaseFilter;
import com.example.example.base.Filter;
import com.example.example.utils.MatrixUtils;

import javax.microedition.khronos.opengles.GL;

public class ManTieTuLitghtFilter1  extends Filter{
    private int glProgramId;
    protected int glUMatrix;
    protected int glAPosition;
    protected int aCoordinate;

    public ManTieTuLitghtFilter1() {
        vertexShaderCode =
                //顶点坐标
                "attribute vec4 aPosition;\n" +
                        "uniform mat4 uMatrix;\n" +
                        "attribute vec2 vCoordinate;\n" +    // 纹理
                        "varying vec2 xxxxx;\n" +
                        "void main(){\n" +
                        "    gl_Position=uMatrix*aPosition;\n" +
                        "    xxxxx=vCoordinate;\n" +
                        "}";

        fragmentShaderCode =
                "precision mediump float;" +
                        "uniform sampler2D vTexture;\n" +
                        "varying vec2 xxxxx;\n" +
                        "void main() {" +
                        "  gl_FragColor = texture2D(vTexture,xxxxx);" +
                        "}";
        triangleCoords = new float[]{
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,     0.0f,0.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,      1.0f,0.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,       1.0f,1.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,       1.0f,1.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,      0.0f,1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,     0.0f,0.0f,

                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,       0.0f,0.0f,
                0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,        1.0f,0.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,         1.0f,1.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,         1.0f,1.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,        0.0f,1.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,       0.0f,0.0f,

                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,       1.0f,0.0f,
                -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f,      1.0f,1.0f,
                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,     0.0f,1.0f,
                -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,     0.0f,1.0f,
                -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f,      0.0f,0.0f,
                -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,       1.0f,0.0f,

                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,         1.0f,0.0f,
                0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f,        1.0f,1.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,       0.0f,1.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,       0.0f,1.0f,
                0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,        0.0f,0.0f,
                0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,         1.0f,0.0f,

                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 0.0f,1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,  1.0f,1.0f,
                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,   1.0f,0.0f,
                0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,   1.0f,0.0f,
                -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,  0.0f,0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 0.0f,1.0f,

                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,   0.0f,1.0f,
                0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,    1.0f,1.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,     1.0f,0.0f,
                0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,     1.0f,0.0f,
                -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,    0.0f,0.0f,
                -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,   0.0f,1.0f
        };
        color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    }

    @Override
    public void create() {
        super.create();
        utils = new MatrixUtils();
        glProgramId = mProgram;
        glAPosition = GLES20.glGetAttribLocation(glProgramId, "aPosition");
        glUMatrix = GLES20.glGetUniformLocation(glProgramId, "uMatrix");
        glHCoordinate = GLES20.glGetAttribLocation(mProgram, "vCoordinate");
        glHTexture = GLES20.glGetUniformLocation(mProgram, "vTexture");
    }

    @Override
    public void getLocation() {

    }

    @Override
        public void surfaceChange(int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            utils.getCenterInsideMatrix2d(width, height, width, height);
            utils.rotateX(10);
        }

        @Override
        public void dispose() {

        }

    @Override
    public void change(int imageWidth, int imageHight, int width, int hight) {

    }

    @Override
    public void render() {
        GLES20.glUseProgram(mProgram);
        vertexBuffer.position(0);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        //物体颜色
//            GLES20.glUniform4f(glUBaseColor, 1.0f, 1.0f, 1.0f, 1.0f);
        //传入顶点信息
        GLES20.glBindTexture(glHTexture,texture);
        GLES20.glUniformMatrix4fv(glUMatrix, 1, false, utils.getmMVPMatrix(), 0);
        GLES20.glEnableVertexAttribArray(glAPosition);
        GLES20.glEnableVertexAttribArray(glHCoordinate);
        GLES20.glVertexAttribPointer(glAPosition, 3, GLES20.GL_FLOAT, false, 8 * 4, vertexBuffer);
        vertexBuffer.position(6);
        GLES20.glVertexAttribPointer(glHCoordinate, 2, GLES20.GL_FLOAT, false, 8 * 4, vertexBuffer);
        //传入法线信息
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, triangleCoords.length / 8);
            //禁止顶点数组的句柄
        GLES20.glBindTexture(glHTexture,0);
    }

    @Override
    public void addOtherRender() {

    }
}
