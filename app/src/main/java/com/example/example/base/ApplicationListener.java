package com.example.example.base;

import javax.microedition.khronos.opengles.GL10;

public interface ApplicationListener {
    public void create(GL10 gl10);
    public void render();
    public void pause ();
    public void resume ();
    public void dispose ();
    void surfaceChanage(int width, int height);
    public void chageType(int itemid1);
}
