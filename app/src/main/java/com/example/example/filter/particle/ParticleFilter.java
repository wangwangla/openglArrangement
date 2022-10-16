package com.example.example.filter.particle;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniformMatrix4fv;

import android.graphics.Color;
import android.opengl.GLES20;

import com.example.example.base.filter.Filter;

public class ParticleFilter extends Filter {
    private int uTimeLocation;
    private int aPositionLocation;
    private int aColorLocation;
    private int aDirectionVectorLocation;
    private int aParticleStartTimeLocation;
    private int currentParticlesCount; //当前粒子的总数
    private int nextParticleOffset = 0;  //下一个例子放在数组的位
    private float[] particles;
    private VertexArray vertexArray;
    // Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TIME = "u_Time";
    // Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    protected static final String A_PARTICLE_START_TIME = "a_ParticleStartTime";
    private static int POSITION_COMPONENT_COUNT = 3;
    private static int COLOR_COMPONENT_COUNT = 3;
    private static int VECTOR_COMPONENT_COUNT = 3;
    private static int PARTICLE_START_TIME_COMPONENT_COUNT = 1;
    private static int TOTAL_COMPONENT_COUNT =
            POSITION_COMPONENT_COUNT +
                    COLOR_COMPONENT_COUNT +
                    VECTOR_COMPONENT_COUNT +
                    POSITION_COMPONENT_COUNT;
    private static int STRIDE = TOTAL_COMPONENT_COUNT * 4;
    float time = 0;

    public ParticleFilter (){
        fragmentShaderCode  = "precision mediump float;\n" +
                "\n" +
                "varying vec3 v_Color;\n" +
                "varying float v_ElapseTime;\n" +
                "\n" +
                "void main() {\n" +
                "\n" +
                "gl_FragColor=vec4(v_Color/v_ElapseTime,1.0);\n" +
                "\n" +
                "}";
        vertexShaderCode = "\n" +
                "uniform float u_Time;                   //当前系统的时间\n" +
                "\n" +
                "attribute vec3 a_Position;\n" +
                "attribute vec3 a_Color;\n" +
                "attribute vec3 a_DirectionVector;\n" +
                "attribute float a_ParticleStartTime;        //例子创建的时间\n" +
                "varying float v_ElapseTime;\n" +
                "varying vec3 v_Color;\n" +
                "\n" +
                "void main() {\n" +
                "    v_Color=a_Color;\n" +
                "    v_ElapseTime=u_Time-a_ParticleStartTime;\n" +
                "    vec3 currentPosition=a_Position+(a_DirectionVector*v_ElapseTime);\n" +
                "\n" +
                "    gl_Position=vec4(currentPosition,1.0);\n" +
                "    gl_PointSize=10.0;\n" +
                "\n" +
                "}";
    }
    @Override
    public void create() {
        super.create();
        int maxParticlesCount = 10000;
        particles = new float[maxParticlesCount * TOTAL_COMPONENT_COUNT];
        this.vertexArray = new VertexArray(particles);

    }

    @Override
    public void getLocation() {
//        uMatrixLocation = glGetUniformLocation(mProgram, U_MATRIX);
        uTimeLocation = glGetUniformLocation(mProgram, U_TIME);
        aPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
        aColorLocation = glGetAttribLocation(mProgram, A_COLOR);
        aDirectionVectorLocation = glGetAttribLocation(mProgram, A_DIRECTION_VECTOR);
        aParticleStartTimeLocation = glGetAttribLocation(mProgram, A_PARTICLE_START_TIME);
    }

    public void addParticles(Point positionPoint, int color, Vector direction, float particleStrtTime) {
        final int particleOffset = nextParticleOffset * TOTAL_COMPONENT_COUNT;  //记住新粒子从数组的哪个编号开始
        int currentOffset = particleOffset;                       //记住新粒子的每个属性从哪里开始
        nextParticleOffset++;
        if (currentParticlesCount < 1000) {
            currentParticlesCount++;
        }
        //当超出数组范围时，将下一个粒子放在数组的开头位置，达到回收的目的
        if (nextParticleOffset == 1000) {
            nextParticleOffset = 0;
        }
        //把新粒子的数据写到数组中
        particles[currentOffset++] = positionPoint.x;
        particles[currentOffset++] = positionPoint.y;
        particles[currentOffset++] = positionPoint.z;
        particles[currentOffset++] = Color.red(color) / 255f;       //OpenGL需要[0,1)的颜色值
        particles[currentOffset++] = Color.green(color) / 255f;
        particles[currentOffset++] = Color.blue(color) / 255f;
        particles[currentOffset++] = direction.x;
        particles[currentOffset++] = direction.y;
        particles[currentOffset++] = direction.z;
        particles[currentOffset++] = particleStrtTime;
        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);
    }



    @Override
    public void addOtherRender() {

    }

    @Override
    public void change(int imageWidth, int imageHight, int width, int hight) {

    }


    @Override
    public void render() {
        time+=0.01;
        GLES20.glUseProgram(mProgram);
        glUniform1f(uTimeLocation,time);
        addParticles(new Point(0,0,0),
                Color.rgb(255, 50, 5),
                new Vector((int)(Math.random()*5), (int)(Math.random()*20), 0f),time);
        int dataOffset = 0;
        vertexArray.setVertexAttribPointer(dataOffset,
                aPositionLocation,
                POSITION_COMPONENT_COUNT, STRIDE);
        dataOffset += POSITION_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset,
                aColorLocation,
                COLOR_COMPONENT_COUNT,
                STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset,
                aDirectionVectorLocation,
                VECTOR_COMPONENT_COUNT,
                STRIDE);
        dataOffset += VECTOR_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset,
                aParticleStartTimeLocation,
                PARTICLE_START_TIME_COMPONENT_COUNT,
                STRIDE);
        glDrawArrays(GL_POINTS, 0, currentParticlesCount);
    }
}
