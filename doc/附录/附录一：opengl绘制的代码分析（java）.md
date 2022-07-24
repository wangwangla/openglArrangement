#  图形绘制分析

分为以下步骤：
- 准备数据
- 准备着色器
- 编译着色器
- 生成程序
- 删除着色器
- 给着色器赋值
- 绘制

## 准备数据

(1)归一化数据：

```
triangleCoords = new float[]{
    0.5f,  0.5f, 0.0f, // top
    -0.5f, -0.5f, 0.0f, // bottom left
    0.5f, -0.5f, 0.0f  // bottom right
};
```

(2) 数据转换为本地数据

```
ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
bb.order(ByteOrder.nativeOrder());
vertexBuffer = bb.asFloatBuffer();
vertexBuffer.put(triangleCoords);
vertexBuffer.position(0);
```

## 准备着色器

(1)着色器语言

```
 vertexShaderCode =
    "attribute vec4 vPosition;" +
    "void main() {" +
    "  gl_Position = vPosition;" +
    "}";

fragmentShaderCode =
    "precision mediump float;" +
        "uniform vec4 vColor;" +
        "void main() {" +
        "  gl_FragColor = vColor;" +
        "}";

```

## 编译着色器

```
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
```

## 生成程序

```
mProgram = GLES20.glCreateProgram();
GLES20.glAttachShader(mProgram,vertexShader);
GLES20.glAttachShader(mProgram,fragmentShader);
GLES20.glLinkProgram(mProgram);
```

## 删除着色器

```
GLES20.glDeleteShader(vertexShader);
GLES20.glDeleteShader(fragmentShader);
```
## 着色器赋值

```
mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
GLES20.glEnableVertexAttribArray(mPositionHandle);
    //准备三角形的坐标数据
    GLES20.glVertexAttribPointer(
            mPositionHandle,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer);
    


mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
GLES20.glUniform4fv(mColorHandle, 1, color, 0);
    
```

## 绘制

```
GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);    
```

## 完成案例

```
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


    public int getTexture(){
        return texture;
    }


    public void setTexture(int texture) {
        this.texture = texture;
    }
}
```

```

public class UniformUseFilter extends BaseFilter {
    private int mPositionHandle;
    private int mColorHandle;
    // 每个顶点四个字节
    private int vertexCount;

    public UniformUseFilter(){
        vertexShaderCode =
                "attribute vec4 vPosition;" +
                        "void main() {" +
                        "  gl_Position = vPosition;" +
                        "}";

        fragmentShaderCode =
                "precision mediump float;" +
                        "uniform vec4 vColor;" +
                        "void main() {" +
                        "  gl_FragColor = vColor;" +
                        "}";

        triangleCoords = new float[]{
                0.5f,  0.5f, 0.0f, // top
                -0.5f, -0.5f, 0.0f, // bottom left
                0.5f, -0.5f, 0.0f  // bottom right
        };
        vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
        color = new float[]{ 1.0f, 1.0f, 1.0f, 1.0f };
    }

    @Override
    public void create() {
        super.create();
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        GLES20.glUseProgram(mProgram);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(
                mPositionHandle,
                COORDS_PER_VERTEX,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer);
        //设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}

```