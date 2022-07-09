package com.example.example.filter.f3d;

import android.opengl.GLES20;

public class ManFanlightFilter extends PingxingLightFilter {
    protected int glANormal;
    protected int glULightPosition;
    protected int glUDiffuseStrength;

    public ManFanlightFilter(){
        vertexShaderCode =
                //顶点坐标
                "attribute vec4 aPosition;\n" +
//
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
//                        光源的位置
                        "uniform vec3 uLightPosition;\n" +
//
                        "varying vec4 vColor;\n" +
                        "\n" +
                        "//在片元着色器中计算光照会获得更好更真实的光照效果，但是会比较耗性能\n" +
                        "\n" +
                        "//环境光的计算\n" +
                        "vec4 ambientColor(){\n" +
                        "    vec3 ambient = uAmbientStrength * uLightColor;\n" +
                        "    return vec4(ambient,1.0);\n" +
                        "}\n" +
                        "\n" +
                        "//漫反射的计算\n" +
                        "vec4 diffuseColor(){\n" +
                        "    //模型变换后的位置   点的位置\n" +
                        "    vec3 fragPos=(uMatrix*aPosition).xyz;\n" +
                        "    //光照方向     光照的方向\n" +
                        "    vec3 direction=normalize(uLightPosition-fragPos);\n" +
                        "    //模型变换后的法线向量\n" +
                        "    vec3 normal=normalize(mat3(uMatrix)*aNormal);\n" +
                        "    //max(cos(入射角)，0)\n" +
                        "    float diff = max(dot(normal,direction), 0.0);\n" +
                        "    //材质的漫反射系数*max(cos(入射角)，0)*光照颜色\n" +
                        "    vec3 diffuse=uDiffuseStrength * diff * uLightColor;\n" +
                        "    return vec4(diffuse,1.0);\n" +
                        "}\n" +
                        "\n" +
                        "void main(){\n" +
                        "    gl_Position=uMatrix*aPosition;\n" +
                        "    vColor=(ambientColor() + diffuseColor())* uBaseColor;\n" +
                        "}";
    }
    @Override
    public void create() {
        super.create();
        glANormal = GLES20.glGetAttribLocation(glProgramId, "aNormal");
        glULightPosition = GLES20.glGetUniformLocation(glProgramId, "uLightPosition");
        glUDiffuseStrength = GLES20.glGetUniformLocation(glProgramId, "uDiffuseStrength");
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
