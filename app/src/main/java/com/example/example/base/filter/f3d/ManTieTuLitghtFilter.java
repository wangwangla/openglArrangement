package com.example.example.base.filter.f3d;

import android.opengl.GLES20;

import com.example.example.base.BaseFilter;
import com.example.example.utils.MatrixUtils;

public class ManTieTuLitghtFilter extends BaseFilter {
    private int glProgramId;
    protected int glUMatrix;
    protected int glULightPosition;
    protected int glAPosition;
    protected int glANormal;
    protected int glUAmbientStrength;
    protected int glUDiffuseStrength;
    protected int glUSpecularStrength;
    protected int glUBaseColor;
    protected int vCoordinate;
    protected int vTexture;
//    protected int glULightColor;
    float lx = 0f;
    float ly = 0.8f;
    float lz = -1f;

    public ManTieTuLitghtFilter() {
        vertexShaderCode =
                //顶点坐标
                "attribute vec4 aPosition;\n" +
                        "attribute vec2 vCoordinate;\n" +    // 纹理
                        "varying vec2 aCoordinate;\n" +      //  传递纹理   片段着色器

//
                        "attribute vec3 aNormal;\n" +
//                        矩阵变换
                        "uniform mat4 uMatrix;\n" +
//                        颜色
                        "uniform vec4 uBaseColor;\n" +
//                        光的颜色
//                        "uniform vec3 uLightColor;\n" +
//                        环境光强度
                        "uniform float uAmbientStrength;\n" +
//                        散射光强度
                        "uniform float uDiffuseStrength;\n" +
//                        镜面强度
                        "uniform float uSpecularStrength;\n" +
//                        光源的位置
                        "uniform vec3 uLightPosition;\n" +
//
                        "varying vec4 vColor;\n" +

//                        "varying vec4 vColor;\n" +
                        "uniform sampler2D vTexture;\n" +
                        "\n" +
                        "//在片元着色器中计算光照会获得更好更真实的光照效果，但是会比较耗性能\n" +
                        "\n" +
                        "//环境光的计算\n" +
                        "vec4 ambientColor(){\n" +
                        "    vec3 ambient = texture2D(vTexture, vCoordinate).rgb;\n" +
                        "    return vec4(ambient,1.0);\n" +
                        "}\n" +
                        "\n" +
                        "//漫反射的计算\n" +
                        "vec4 diffuseColor(){\n" +
                        "    //模型变换后的位置\n" +
                        "    vec3 fragPos=(uMatrix*aPosition).xyz;\n" +
                        "    //光照方向\n" +
                        "    vec3 direction=normalize(uLightPosition-fragPos);\n" +
                        "    //模型变换后的法线向量\n" +
                        "    vec3 normal=normalize(mat3(uMatrix)*aNormal);\n" +
                        "    //max(cos(入射角)，0)\n" +
                        "    float diff = max(dot(normal,direction), 0.0);\n" +
                        "    //材质的漫反射系数*max(cos(入射角)，0)*光照颜色\n" +
                        "    vec3 diffuse=uDiffuseStrength * diff * texture2D(vTexture, vCoordinate).rgb;\n" +
                        "    return vec4(diffuse,1.0);\n" +
                        "}\n" +
                        "\n" +
                        "//镜面光计算，镜面光计算有两种方式，一种是冯氏模型，一种是Blinn改进的冯氏模型\n" +
                        "//这里使用的是改进的冯氏模型，基于Half-Vector的计算方式\n" +
                        "vec4 specularColor(){\n" +
                        "    //模型变换后的位置\n" +
                        "    vec3 fragPos=(uMatrix*aPosition).xyz;\n" +
                        "    //光照方向\n" +
                        "    vec3 lightDirection=normalize(uLightPosition-fragPos);\n" +
                        "    //模型变换后的法线向量\n" +
                        "    vec3 normal=normalize(mat3(uMatrix)*aNormal);\n" +
                        "    //观察方向，这里将观察点固定在（0，0，uLightPosition.z）处\n" +
                        "    vec3 viewDirection=normalize(vec3(0,0,uLightPosition.z)-fragPos);\n" +
                        "    //观察向量与光照向量的半向量\n" +
                        "    vec3 hafVector=normalize(lightDirection+viewDirection);\n" +
                        "    //max(0,cos(半向量与法向量的夹角)^粗糙度\n" +
                        "    float diff=pow(max(dot(normal,hafVector),0.0),4.0);\n" +
                        "    //材质的镜面反射系数*max(0,cos(反射向量与观察向量夹角)^粗糙度*光照颜色\n" +
                        "    //材质的镜面反射系数*max(0,cos(半向量与法向量的夹角)^粗糙度*光照颜色\n" +
                        "    vec3 specular=uSpecularStrength*diff*texture2D(vTexture, vCoordinate).rgb;\n" +
                        "    return vec4(specular,1.0);\n" +
                        "}\n\n" +
                        "void main(){\n" +
                        "    gl_Position=uMatrix*aPosition;\n" +
                        "    vColor=(ambientColor() + diffuseColor() + specularColor())* uBaseColor;\n" +
                        "}";

        fragmentShaderCode =
                "precision mediump float;" +
                        "varying vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
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
        glANormal = GLES20.glGetAttribLocation(glProgramId, "aNormal");
        glUMatrix = GLES20.glGetUniformLocation(glProgramId, "uMatrix");
        glULightPosition = GLES20.glGetUniformLocation(glProgramId, "uLightPosition");
        glUAmbientStrength = GLES20.glGetUniformLocation(glProgramId, "uAmbientStrength");
        glUDiffuseStrength = GLES20.glGetUniformLocation(glProgramId, "uDiffuseStrength");
        glUSpecularStrength = GLES20.glGetUniformLocation(glProgramId, "uSpecularStrength");
        vTexture = GLES20.glGetUniformLocation(glProgramId,"vTexture");
        vCoordinate = GLES20.glGetUniformLocation(glProgramId, "vCoordinate");
        glUBaseColor = GLES20.glGetUniformLocation(glProgramId, "uBaseColor");
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
    public void render() {

        GLES20.glUseProgram(mProgram);

        vertexBuffer.position(0);
        //准备三角形的坐标数据
        //环境光强度
        GLES20.glUniform1f(glUAmbientStrength, 0.5F);
        //漫反射光强度
        GLES20.glUniform1f(glUDiffuseStrength, 0.3F);
        //镜面光强度
        GLES20.glUniform1f(glUSpecularStrength, 0.8F);
        //光源颜色
//        GLES20.glUniform3f(glULightColor, 1.0f, 0.0f, 0.0f);
        //物体颜色
        GLES20.glUniform4f(glUBaseColor, 1.0f, 1.0f, 1.0f, 1.0f);
        //光源位置
        GLES20.glUniform3f(glULightPosition, lx, ly, lz);
        //传入顶点信息
        GLES20.glEnableVertexAttribArray(glAPosition);
        GLES20.glVertexAttribPointer(glAPosition, 3, GLES20.GL_FLOAT, false, 8 * 4, vertexBuffer);
        //传入法线信息
        GLES20.glEnableVertexAttribArray(glANormal);

        vertexBuffer.position(3);
        GLES20.glVertexAttribPointer(glANormal, 3, GLES20.GL_FLOAT, false, 8 * 4, vertexBuffer);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glUniform1i(vTexture, 0);

        vertexBuffer.position(6);
        GLES20.glEnableVertexAttribArray(vCoordinate);
        GLES20.glVertexAttribPointer(vCoordinate,2, GLES20.GL_FLOAT, false, 8 * 4, vertexBuffer);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, triangleCoords.length / 8);
        //禁止顶点数组的句柄
        GLES20.glUniformMatrix4fv(glUMatrix, 1, false, utils.getmMVPMatrix(), 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }
}
