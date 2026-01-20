package model;
/*
 * ===================================================
 * Filename     : Pemain.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 9 June 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : package model untuk representasi pemeran utama
 * 
 * ===================================================
 */

import model.Pancingan;

public class Pemain {
    private final int startX;
    private final int startY;
    private int positionX;
    private int positionY;
    private int SPD;
    private int width;
    private int height;
    private int score;
    private int bolaCollected;

    private int facingDirection = 1;
    private Pancingan pancingan;

    public Pemain(int startX, int startY){
        this.startX = startX;
        this.startY = startY;
        this.positionX = startX;
        this.positionY = startY;
        this.width = 210;
        this.height = 192;
        this.SPD = 9;
        this.score = 0;
        this.pancingan = new Pancingan(this.startX, this.startY);

        reset(); // Melakukan reset terlebih dahulu
    }

    public int getPosX(){ return this.positionX; }
    public int getPosY(){ return this.positionY; }
    public int getScore(){ return this.score; }
    public int getWidth() { return this.width ; }
    public int getHeight() { return this.height ; }
    public int getSPD() { return this.SPD; }
    public Pancingan getPancingan() { return this.pancingan; }
    public int getBolaCollected() { return this.bolaCollected; }
    public int getFacingDirection() { return this.facingDirection; }

    public void setPosition(int x, int y){ this.positionX = x; this.positionY = y; }
    public void incrementBolaCollected() { this.bolaCollected++; }
    public void setFacingDirection(int direction){
        if (direction != 0) {
            this.facingDirection = direction;
        }
    }

    public void gerak(int dx, int dy, int boundX, int boundY){
        // Method untuk berpindah posisi
        int newX = this.positionX + (dx * this.SPD);
        int newY = this.positionY + (dy * this.SPD);

        // Cek apakah pemain berada di dalam layar atau tidak
        if (newX >= 0 && (newX + this.width) <= boundX) {
            this.positionX = newX;
        }
        if (newY >= 0 && (newY + this.height) <= boundY) {
            this.positionY = newY;
        }
    }

    public void tambahScore(int newScore){
        // Method untuk menambahkan score pemeran utama
        this.score += newScore;
    }

    public void reset(){
        /*
         * Method untuk melakukan reset posisi dan skor awal pemain
         */
        this.positionX = this.startX;
        this.positionY = this.startY;
        this.score = 0;
        this.bolaCollected = 0;
    }
}
