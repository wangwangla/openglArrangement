package com.example.example.hight;


import com.example.example.hight.filter.BaseRenderBean;

public class GaussianBlurBean extends BaseRenderBean {
    /**
     * 缩放比例
     */
    private int scaleRatio;

    /**
     * 模糊半径
     */
    private int blurRadius;

    /**
     * 模糊步长
     */
    private int blurOffsetW;
    private int blurOffsetH;
    //30  1,1
    public GaussianBlurBean(String name, int scaleRatio, int blurRadius) {
        this(name, scaleRatio, blurRadius, 1, 1);
    }

    public GaussianBlurBean(String name, int scaleRatio, int blurRadius, int blurOffsetW, int blurOffsetH) {
        super(1, name);
        this.scaleRatio = scaleRatio;
        this.blurRadius = blurRadius;
        this.blurOffsetW = blurOffsetW;
        this.blurOffsetH = blurOffsetH;
    }

    public int getScaleRatio() {
        return scaleRatio;
    }

    public void setScaleRatio(int scaleRatio) {
        this.scaleRatio = scaleRatio;
    }

    public int getBlurRadius() {
        return blurRadius;
    }

    public void setBlurRadius(int blurRadius) {
        this.blurRadius = blurRadius;
    }

    public int getBlurOffsetW() {
        return blurOffsetW;
    }

    public void setBlurOffsetW(int blurOffsetW) {
        this.blurOffsetW = blurOffsetW;
    }

    public int getBlurOffsetH() {
        return blurOffsetH;
    }

    public void setBlurOffsetH(int blurOffsetH) {
        this.blurOffsetH = blurOffsetH;
    }

    @Override
    public String toString() {
        return super.toString() +
                "GaussianBlurBean{" +
                "scaleRatio=" + scaleRatio +
                ", blurRadius=" + blurRadius +
                ", blurOffsetW=" + blurOffsetW +
                ", blurOffsetH=" + blurOffsetH +
                '}';
    }
}
