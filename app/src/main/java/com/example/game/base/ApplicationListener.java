package com.example.game.base;

import javax.microedition.khronos.opengles.GL10;

public interface ApplicationListener {
    void create(GL10 gl10);
    void render();
    void pause ();
    void resume ();
    void dispose ();
    void surfaceChanage(int width, int height);
    void chageType(int itemid1);
}
