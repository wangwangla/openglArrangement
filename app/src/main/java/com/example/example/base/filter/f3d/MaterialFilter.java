package com.example.example.base.filter.f3d;

import android.opengl.GLES20;

import com.example.example.base.BaseFilter;

/**
 * 加入材质
 */
public class MaterialFilter extends LightFilter {
    int materialAmbient;
    int materialDiffuse;
    int materialspecular;
    public MaterialFilter(){
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
//                        镜面强度
                        "uniform float uSpecularStrength;\n" +
//                        光源的位置
                        "uniform vec3 uLightPosition;\n" +
                        "" +
                        "uniform vec3 materialAmbient; \n" +

                        "uniform vec3 materialDiffuse;\n" +

                        "uniform vec3 materialspecular;\n" +
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
                        "    //模型变换后的位置\n" +
                        "    vec3 fragPos=(uMatrix*aPosition).xyz;\n" +
                        "    //光照方向\n" +
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
                        "    vec3 specular=uSpecularStrength*diff*uLightColor;\n" +
                        "    return vec4(specular,1.0);\n" +
                        "}\n\n" +
                        "void main(){\n" +
                        "    gl_Position=uMatrix*aPosition;\n" +
                        "    vColor=(ambientColor() * materialAmbient + diffuseColor() * materialDiffuse+ materialspecular * specularColor())* uBaseColor;\n" +
                        "}";
    }


    @Override
    public void create() {
        super.create();
        materialAmbient = GLES20.glGetUniformLocation(mProgram, "materialAmbient");
        materialDiffuse = GLES20.glGetUniformLocation(mProgram, "materialDiffuse");
        materialspecular = GLES20.glGetUniformLocation(mProgram, "materialspecular");
    }

    @Override
    public void render() {
        utils.rotateY(0.1F);
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
        GLES20.glUniform3f(glULightColor, 1.0f, 0.0f, 0.0f);

//        int materialAmbient;
//        int materialDiffuse;
//        int materialspecular;
        GLES20.glUniform1f(materialAmbient, 1.0f);

        GLES20.glUniform1f(materialDiffuse, 1.0f);

        GLES20.glUniform1f(materialspecular, 1.0f);



        //物体颜色
        GLES20.glUniform4f(glUBaseColor, 1.0f, 1.0f, 1.0f, 1.0f);
        //光源位置
        GLES20.glUniform3f(glULightPosition, lx, ly, lz);
        //传入顶点信息
        GLES20.glEnableVertexAttribArray(glAPosition);
        GLES20.glVertexAttribPointer(glAPosition, 3, GLES20.GL_FLOAT, false, 6 * 4, vertexBuffer);
        //传入法线信息
        GLES20.glEnableVertexAttribArray(glANormal);
        vertexBuffer.position(3);
        GLES20.glVertexAttribPointer(glANormal, 3, GLES20.GL_FLOAT, false, 6 * 4, vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, triangleCoords.length / 6);
        //禁止顶点数组的句柄
        GLES20.glUniformMatrix4fv(glUMatrix, 1, false, utils.getmMVPMatrix(), 0);
    }
}
