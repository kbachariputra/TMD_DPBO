package model;

/* ===================================================
* Filename     : Pancingan.java
* Programmer   : Kasyful Haq Bachariputra
* Date         : 11 Juni 2025
* Email        : kasyfulhaqb@upi.edu
* Deskripsi    : Package model sebagai representasi 
*                sebuah pancingan/hook dan perilakunya
* ===================================================
*/

import java.awt.Rectangle;
import java.util.ArrayList;
import java.lang.Math;

public class Pancingan {
    private int positionHookX;
    private int positionHookY;
    private int hookSpeed;

    private int targetX;
    private int targetY;

    private String state;
    private Bola caughtBola;

    public Pancingan(int x, int y){
        this.positionHookX = x;
        this.positionHookY = y;
        this.state = "IDLE";
        this.hookSpeed = 15;
    }

    public int getPosX(){ return this.positionHookX; }
    public int getPosY(){ return this.positionHookY; }
    public Bola getCaughtBola() { return this.caughtBola; }
    public String getState(){ return this.state; }

    public void setPosition(int x, int y){
        this.positionHookX = x;
        this.positionHookY = y;
    }

    public void setState(String state){
        this.state = state;
    }

    public void updateState(int targetX, int targetY, ArrayList<Bola> collectionBola, int playerX, int playerY){
        if (this.state.equals("SHOOTING")) {
            int travelX = targetX - this.positionHookX;
            int travelY = targetY - this.positionHookY;
            double distance = Math.sqrt((travelX * travelX) + (travelY * travelY));
            
            // Prevent division by zero
            if (distance > 0) {
                double directionX = travelX / distance;
                double directionY = travelY / distance;
                this.positionHookX += directionX * hookSpeed;
                this.positionHookY += directionY * hookSpeed;
            }

            // Check for collision
            for (Bola bola : collectionBola) {
                Rectangle hitBoxBola = new Rectangle(bola.getPosX(), bola.getPosY(), bola.getSize(), bola.getSize());
                if (hitBoxBola.contains(this.positionHookX, this.positionHookY)) {
                    this.caughtBola = bola;
                    this.state = "RETRACTING_CAUGHT";
                    return; 
                }
            }

            // Check for arrival (miss)
            if (distance <= this.hookSpeed) {
                this.state = "RETRACTING_EMPTY";
            }

        } else if (this.state.equals("RETRACTING_CAUGHT")){
            int travelX = playerX - this.positionHookX;
            int travelY = playerY - this.positionHookY;
            double distance = Math.sqrt((travelX * travelX) + (travelY * travelY));

            if (distance <= this.hookSpeed) {
                this.state = "IDLE"; 
            } else {
                double directionX = travelX / distance;
                double directionY = travelY / distance;
                this.positionHookX += directionX * hookSpeed;
                this.positionHookY += directionY * hookSpeed;
            }
            // Update the ball's position to stick to the hook
            this.updateCaughtBallPosition();
            
        } else if(this.state.equals("RETRACTING_EMPTY")){
            int travelX = playerX - this.positionHookX;
            int travelY = playerY - this.positionHookY;
            double distance = Math.sqrt((travelX * travelX) + (travelY * travelY));

            if (distance <= this.hookSpeed) {
                this.state = "IDLE";
            } else {
                 double directionX = travelX / distance;
                 double directionY = travelY / distance;
                 this.positionHookX += directionX * hookSpeed;
                 this.positionHookY += directionY * hookSpeed;
            }
        }
    }

    public void updateCaughtBallPosition(){
        if(this.caughtBola != null) {
            // Update posisi bola ketika terkena hook
            int ballX = this.positionHookX - (this.caughtBola.getSize() / 2);
            int ballY = this.positionHookY - (this.caughtBola.getSize() / 2);
            this.caughtBola.updatePosition(ballX, ballY);
        }
    }

    public void releaseHook(){
        // Method untuk melepaskan bola dan kembali ke state idle
        this.state = "IDLE";
        this.caughtBola = null;
    }
}
