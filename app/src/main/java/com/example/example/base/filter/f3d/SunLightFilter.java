package com.example.example.base.filter.f3d;

public class SunLightFilter extends NoLightFilter{
    public SunLightFilter(){
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

                        //---------------------------------------------
                        "uniform vec3 lightdirection;"+
//--------------------------------------------------------
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
//                        "    vec3 fragPos=(uMatrix*aPosition).xyz;\n" +
//                        "    //光照方向\n" +
//                        "    vec3 direction=normalize(uLightPosition-fragPos);\n" +
                        "   vec3 direction = normalize(-lightdirection); "+
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
                        "    vColor=(ambientColor() + diffuseColor() + specularColor())* uBaseColor;\n" +
                        "}";

    }
}
