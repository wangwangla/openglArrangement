package com.example.example.filter.particle;

public class Point {
    public final float x, y, z;

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }



    public Point translateY(float distance){
        return new Point(x,y+distance,z);
    }


    public Point translate(Vector vector){
        return new Point(
                x+vector.x,
                y+vector.y,
                z+vector.z
        );
    }

    @Override
    public String toString() {
        return "Point{" +
                x + "," +
                y + "," +
                z +
                '}';
    }


}

