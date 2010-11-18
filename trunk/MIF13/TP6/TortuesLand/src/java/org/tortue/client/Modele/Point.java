package org.tortue.client.Modele;

import java.io.Serializable;

/**
 * Un point.
 * La classe Point n'étant pas prise en charge par GWT, il faut la recréer.
 */
public class Point implements Serializable{

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
