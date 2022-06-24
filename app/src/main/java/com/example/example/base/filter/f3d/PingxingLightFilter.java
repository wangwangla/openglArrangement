package com.example.example.base.filter.f3d;

import android.opengl.GLES20;

import com.example.example.utils.MatrixUtils;

public class PingxingLightFilter extends NoLightFilter {

    protected int glUAmbientStrength;
    protected int glULightColor;

    public PingxingLightFilter() {
        vertexShaderCode =
                //顶点坐标
                "attribute vec4 aPosition;\n" +
                        "attribute vec3 aNormal;\n" +
//                        矩阵变换
                        "uniform mat4 uMatrix;\n" +
//                        颜色
                        "uniform vec4 uBaseColor;\n" +
//                        光的颜色
                        "uniform vec3 uLightColor;\n" +
//                        环境光强度
                        "uniform float uAmbientStrength;\n" +
//                        散射光强度
                        "uniform float uDiffuseStrength;\n" +
//                        镜面强度
                        "uniform float uSpecularStrength;\n" +
//                        光源的位置
                        "uniform vec3 uLightPosition;\n" +
                        "varying vec4 vColor;\n" +
                        "//环境光的计算\n" +
                        "vec4 ambientColor(){\n" +
                        "    vec3 ambient = uAmbientStrength * uLightColor;\n" +
                        "    return vec4(ambient,1.0);\n" +
                        "}\n" +
                        "void main(){\n" +
                        "    gl_Position=uMatrix*aPosition;\n" +
                        "    vColor=(ambientColor())* uBaseColor;\n" +
                        "}";
    }

    @Override
    public void create() {
        super.create();
        glUAmbientStrength = GLES20.glGetUniformLocation(glProgramId, "uAmbientStrength");
        glULightColor = GLES20.glGetUniformLocation(glProgramId, "uLightColor");
    }


    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        utils.rotateY(0.1F);
        GLES20.glUseProgram(mProgram);
        vertexBuffer.position(0);
        //准备三角形的坐标数据
        //环境光强度
        GLES20.glUniform1f(glUAmbientStrength, 0.5F);
        //光源颜色
        GLES20.glUniform3f(glULightColor, 1.0f, 0.0f, 0.0f);
        //物体颜色
        GLES20.glUniform4f(glUBaseColor, 1.0f, 1.0f, 1.0f, 1.0f);
        //传入顶点信息
        GLES20.glEnableVertexAttribArray(glAPosition);
        GLES20.glVertexAttribPointer(glAPosition, 3, GLES20.GL_FLOAT, false, 6 * 4, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, triangleCoords.length / 6);
        //禁止顶点数组的句柄
        GLES20.glUniformMatrix4fv(glUMatrix, 1, false, utils.getmMVPMatrix(), 0);
    }
}