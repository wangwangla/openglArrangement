package com.example.example.learn.g3d.App;

import com.example.example.base.filter.f3d.NoLightFilter;

public class NoLight extends Base3DDrawder {
    public NoLight(){
        filter = new NoLightFilter();
    }
}
