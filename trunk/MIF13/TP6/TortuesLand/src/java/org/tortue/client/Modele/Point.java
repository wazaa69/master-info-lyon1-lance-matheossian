package org.tortue.client.Modele;

/**
 * Un point. la classe Point n'étant as prise en charge par GWT, il faut la recréer
 */
public class Point {

    int x;
    int y;

     public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void clone(Point p){
        x = p.getX();
        y = p.getY();
    }

}
