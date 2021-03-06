package com.example.example.base;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;

import com.example.example.utils.MatrixUtils;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class BaseFilter {
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
    protected int texture;
    public BaseFilter(){}

    public BaseFilter(Resources resources){
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
        ByteBuffer bb = ByteBuffer.allocateDirect(
                triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(color.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());
        colorBuffer = byteBuffer.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);
    }

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

    public void render(){}

    public void surfaceChange(int width,int height){
        GLES20.glViewport(0,0,width,height);
    }

    public abstract void dispose();

    public void resume() {

    }

    /**
     * ????????????????????????   ???????????????
     * @param path
     * @return
     */
    public String uRes(String path){
        if (mRes == null) System.out.println("??????!");
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
        //??????type??????????????????????????????????????????
        int shader = GLES20.glCreateShader(type);
        //??????????????????????????????????????????
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        int []arr = new int[1];
        GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,arr,0);
        int i = arr[0];
        if (i == 0){
            //?????????
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


    public int getTexture(){
        return texture;
    }


    public void setTexture(int texture) {
        this.texture = texture;
    }
}
