package com.example.iotmobius;

public class LED {
    int r;
    int g;
    int b;

    public void setR(int r) {
        this.r = r;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setB(int b) {
        this.b = b;
    }
    public String getRGB(){
        return ""+r+g+b;
    }
    public LED(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
