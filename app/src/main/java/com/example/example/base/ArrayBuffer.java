package com.example.example.base;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;

import com.example.example.utils.MatrixUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class ArrayBuffer {
    protected float color[];
    protected float triangleCoords[];
    protected String fragmentShaderCode;
    protected String vertexShaderCode;
    protected FloatBuffer vertexBuffer;
    protected FloatBuffer colorBuffer;
    protected int mProgram ;
    private Resources mRes;
    protected int COORDS_PER_VERTEX = 3;
    protected int vertexStride = COORDS_PER_VERTEX * 4;
    protected Context context;
    protected MatrixUtils utils;
    public GL10 gl10;
    public ArrayBuffer(){}

    public ArrayBuffer(Resources resources){
        this.mRes = resources;
    }

    public void create(){
        initData();
        createProgame();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void initData(){
        vertexBuffer = loadbuffer(triangleCoords);
        colorBuffer = loadbuffer(color);
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(color.length*4);
//        byteBuffer.order(ByteOrder.nativeOrder());
//        colorBuffer = byteBuffer.asFloatBuffer();
//        colorBuffer.put(color);
//        colorBuffer.position(0);


        vb = new int[1];
        GLES20.glGenBuffers(1,vb,0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,vb[0]);
        //分配空间
//        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,triangleCoords.length * 4,null,GLES20.GL_STATIC_DRAW);
//        //设置值
//        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER,0,triangleCoords.length*4,vertexBuffer);
        //合为一个
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,triangleCoords.length * 4,vertexBuffer,GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER,0);
    }

    protected FloatBuffer loadbuffer(float [] triangleCoords) {
        ByteBuffer bb = ByteBuffer.allocateDirect(
                triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = bb.asFloatBuffer();
        floatBuffer.put(triangleCoords);
        floatBuffer.position(0);
        return floatBuffer;
    }

    protected IntBuffer loadbuffer(int [] triangleCoords) {
        ByteBuffer bb = ByteBuffer.allocateDirect(
                triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = bb.asIntBuffer();
        intBuffer.put(triangleCoords);
        intBuffer.position(0);
        return intBuffer;
    }

    protected int vb[];

    public void createProgame(){
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram,vertexShader);
        GLES20.glAttachShader(mProgram,fragmentShader);
        GLES20.glLinkProgram(mProgram);
        int lin[] = new int[1];
        GLES20.glGetProgramiv(mProgram,GLES20.GL_LINK_STATUS,lin,0);
        if (lin[0] == 0){
            String s = GLES20.glGetProgramInfoLog(mProgram);
            System.out.println(s);
        }
        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragmentShader);
    }

    public void pause(){

    }

    public void render(){
        GLES20.glClearColor(1,1,1,1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }

    public void surfaceChange(int width,int height){
        GLES20.glViewport(0,0,width,height);
    }



    public void resume() {

    }

    /**
     * 从文件中读取数据   得到字符串
     * @param path
     * @return
     */
    public String uRes(String path){
        if (mRes == null) System.out.println("八嘎!");
        StringBuilder result=new StringBuilder();
        try{
            InputStream is=mRes.getAssets().open(path);
            int ch;
            byte[] buffer=new byte[1024];
            while (-1!=(ch=is.read(buffer))){
                result.append(new String(buffer,0,ch));
            }
        }catch (Exception e){
            return null;
        }
        return result.toString().replaceAll("\\r\\n","\n");
    }

    public int loadShader(int type, String shaderCode){
        //根据type创建顶点着色器或者片元着色器
        int shader = GLES20.glCreateShader(type);
        //将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        int []arr = new int[1];
        GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,arr,0);
        int i = arr[0];
        if (i == 0){
            //失败了
            int [] length = new int[1];
            GLES20.glGetShaderiv(shader,GLES20.GL_INFO_LOG_LENGTH,length,0);
            if (length[0]>0){
                String s = GLES20.glGetShaderInfoLog(shader);
                System.out.println(s);
            }
        }
        return shader;
    }

    public MatrixUtils getUtils() {
        return utils;
    }

    public void setGL(GL10 gl10){
        this.gl10 = gl10;

    }
}
