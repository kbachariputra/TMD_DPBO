package model;
/*
 * ===================================================
 * Filename     : Keranjang.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 9 June 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : Package model untuk representasi keranjang
 *                tempat memasukkan bola
 * ===================================================
 */

import java.awt.Rectangle;

public class Keranjang {
    private int positionX;
    private int positionY;
    private int width;
    private int height;

    public Keranjang(int x, int y){
        this.positionX = x;
        this.positionY = y;
        this.width = 200;
        this.height = 100;
    }

    public int getPosX() { return this.positionX; }
    public int getPosY() { return this.positionY; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }

    public void setPosition(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }

    public boolean isBolaInside(Bola bola){
        Rectangle hitBoxKeranjang = new Rectangle(this.positionX, this.positionY, this.width, this.height);
        Rectangle hitBoxBola = new Rectangle(bola.getPosX(), bola.getPosY(), bola.getSize(), bola.getSize());
        if (hitBoxKeranjang.contains(hitBoxBola)) {
            return true;
        }
        return false;
    }
}
