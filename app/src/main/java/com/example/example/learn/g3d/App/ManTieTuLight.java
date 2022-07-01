package com.example.example.learn.g3d.App;

import com.example.example.base.filter.f3d.ManTieTuLitghtFilter;
import com.example.example.utils.MatrixUtils;

public class ManTieTuLight extends Material {
    public ManTieTuLight(){
        filter = new ManTieTuLitghtFilter();
    }

    @Override
    public void create() {
        super.create();
        MatrixUtils utils = filter.getUtils();
        utils.rotateY(10);
    }
}
