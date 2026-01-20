package model;

/* ===================================================
 * Filename     : Bola.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 11 Juni 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : package model sebagai representasi Bola/target
 * 
 * ===================================================
 */

public class Bola {
    private int positionX;
    private int positionY;
    private int speed;
    private int value;
    private int size;
    private int direction;

    private ArtifactType type;

    public Bola(int startX, int startY, ArtifactType type){
        this.positionX = startX;
        this.positionY = startY;
        this.speed = 5;
        this.value = 0;
        this.size = 64;
        this.type = type;
    }

    public int getPosX() { return this.positionX; }
    public int getPosY() { return this.positionY; }
    public int getSpeed() { return this.speed; }
    public int getValue() { return this.value; }
    public int getSize() { return this.size; }
    public int getDirection() { return this.direction; }
    public ArtifactType getArtifactType() { return this.type; }

    public void setDirection(int direction) { this.direction = direction; }
    public void setValue(int value){ this.value = value; }

    public void gerak(){
        /*
         * Method untuk membuat bola dapat bergerak
         * horizontal berdasarkan direction
         */
        positionX += this.speed * direction;
    }

    public void updatePosition(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }
}
