package com.example.example.learn.g3d.App;

import com.example.example.base.filter.f3d.ManFanlightFilter;
import com.example.example.base.filter.f3d.PingxingLightFilter;

/**
 * 漫反射
 */
public class ManFanSheLight extends Base3DDrawder {
    public ManFanSheLight(){
        filter = new ManFanlightFilter();
    }
}
