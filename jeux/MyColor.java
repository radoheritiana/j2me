package jeux;


public class MyColor {

    private int r;
    private int g;
    private int b;

    public MyColor(int _r, int _g, int _b) {
        this.r = _r;
        this.g = _g;
        this.b = _b;
    }

    public int getR(){
        return this.r;
    }

    public int getG(){
        return this.g;
    }

    public int getB(){
        return this.b;
    }

    public void setR(int _r) {
        this.r = _r;
    }

    public void setG(int _g) {
        this.g = _g;
    }

    public void setB(int _b) {
        this.b = _b;
    }
}