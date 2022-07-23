package com.example.example.learn.g3d.App;

import com.example.example.filter.f3d.PingxingLightFilter;

/**
 * 平行光  可以理解为可以反射的光，它是均匀的
 */
public class PingXingLight extends Base3DDrawder {
    public PingXingLight(){
//        SharedLibraryLoader
//        System.loadLibrary();
        filter = new PingxingLightFilter();
    }
}
