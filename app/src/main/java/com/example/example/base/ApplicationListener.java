package com.example.example.base;

public interface ApplicationListener {
    public void create ();
    public void render();
    public void pause ();
    public void resume ();
    public void dispose ();
    void surfaceChanage(int width, int height);
}
