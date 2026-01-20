package view;

/* ===================================================
 * Filename     : MainGameView.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 11 Juni 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : package view yang bertugas untuk
 *                menampilkan view ketika game loop 
 *                berjalan
 * ===================================================
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Pemain;
import model.ArtifactType;
import model.Bola;
import model.BolaManager;
import model.Keranjang;
import presenter.GamePresenter;
import view.GameLoopContract;

public class MainGameView extends JPanel implements GameLoopContract, Runnable, MouseListener, MouseMotionListener{
    private Pemain pemain;
    private ArrayList<Bola> bolas;
    private Keranjang keranjang;
    private GamePresenter presenter;

    // Variabel Thread
    private Thread gameThread;
    private volatile boolean running = false;
    private final int FPS = 60;

    private BufferedImage backgroundImage;
    private BufferedImage bookImage;
    private BufferedImage scrollImage;
    private BufferedImage keranjangImage;
    private BufferedImage[] playerRunAnimation;

    private int currentFrame = 0;
    private int scoreToShow = 0;
    private int bolaCollectedCount = 0;

    private static final int PLAYER_FRAME_COUNT = 8;

    public MainGameView(){
        setPreferredSize(new Dimension(1000, 800));
        loadAssets();
        addMouseListener(this);
        addMouseMotionListener(this);

        bolas = new ArrayList<Bola>();
    }

    private void loadAssets(){
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/Background.png"));
            bookImage = ImageIO.read(getClass().getResourceAsStream("/Book.png"));
            scrollImage = ImageIO.read(getClass().getResource("/Scroll.png"));

            // Initialize the animation array with the correct size
            playerRunAnimation = new BufferedImage[PLAYER_FRAME_COUNT];
            
            // Load each frame one by one
            playerRunAnimation[0] = ImageIO.read(getClass().getResourceAsStream("/Run 1.png"));
            playerRunAnimation[1] = ImageIO.read(getClass().getResourceAsStream("/Run 2.png"));
            playerRunAnimation[2] = ImageIO.read(getClass().getResourceAsStream("/Run 3.png"));
            playerRunAnimation[3] = ImageIO.read(getClass().getResourceAsStream("/Run 4.png"));

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading assets. Make sure files are in the 'resources' folder and the folder is in the build path.");
            e.printStackTrace();
        }
    }

    public void setPemain(Pemain pemain) { this.pemain = pemain; }
    public void setPresenter(GamePresenter presenter) { this.presenter = presenter; }
    public void setBola(ArrayList<Bola> bola) { this.bolas = bola; }
    public void setCurrentFrame(int frame) { this.currentFrame = frame; }
    public void setScore(int score) { this.scoreToShow = score; }
    public void setBolaCollectedCount(int bolaCount) { this.bolaCollectedCount = bolaCount; }
    public void setKeranjang(Keranjang keranjang) {this.keranjang = keranjang; }

    public void startGameLoop(){
        if (gameThread == null || !running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void stopGameLoop(){
        if (running) {
            running = false;
            gameThread = null;
        }
    }

    @Override
    public void run(){
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // Loop hanya akan menyuruh presenter untuk melakukan update game
                if (presenter != null) {
                    presenter.updateGame();
                }
                delta--;
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }else{
            g2d.setColor(Color.LIGHT_GRAY);
        }

        // Render Keranjang untuk menambah score
        if (keranjang != null) {
            g.setColor(new Color(211, 211, 211, 200));
            g.fillRect(keranjang.getPosX(), keranjang.getPosY(), keranjang.getWidth(), keranjang.getHeight());

            // Render Teks Score
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g.drawString("Score: " + scoreToShow, keranjang.getPosX(), keranjang.getPosY() - 20);
            g.drawString("Count: " + bolaCollectedCount, keranjang.getPosX(), keranjang.getPosY());
        }
        
        if (pemain != null) {
            /*
             * Render semua hal yang berhubungan dengan pemain
             */
            if (playerRunAnimation != null && playerRunAnimation[currentFrame] != null) {
                BufferedImage frameToDraw = playerRunAnimation[currentFrame];
                int direction = pemain.getFacingDirection();
                int screenX = pemain.getPosX();
                int pWidth = pemain.getWidth();
                int pHeight = pemain.getHeight();

                // Logic to flip the image horizontally if facing left
                if (direction == -1) {
                    g2d.drawImage(frameToDraw, screenX + pWidth, pemain.getPosY(), -pWidth, pHeight, null);
                } else {
                    g2d.drawImage(frameToDraw, screenX, pemain.getPosY(), pWidth, pHeight, null);
                }
            } else {
                // Fallback drawing if images failed to load
                g2d.setColor(Color.MAGENTA);
                g2d.fillRect(pemain.getPosX(), pemain.getPosY(), pemain.getWidth(), pemain.getHeight());
            }

            if (pemain.getPancingan() != null && !pemain.getPancingan().getState().equals("IDLE")) {
                int pemainCenterX = pemain.getPosX() + (pemain.getWidth() / 2);
                int pemainCenterY = pemain.getPosY() + (pemain.getHeight() / 2);
                int pancinganX = pemain.getPancingan().getPosX();
                int pancinganY = pemain.getPancingan().getPosY();

                g2d.setColor(Color.WHITE); // Changed color for better visibility
                g2d.drawLine(pemainCenterX, pemainCenterY, pancinganX, pancinganY);
            }

            if (pemain.getPancingan().getCaughtBola() != null) {
                /*
                 * Render bola yang tertangkap oleh pancingan
                 */
                Bola bolaToDraw = pemain.getPancingan().getCaughtBola();
                Image artifactImage = getArtifactImage(bolaToDraw.getArtifactType());
                if(artifactImage != null) {
                    g2d.drawImage(artifactImage, bolaToDraw.getPosX(), bolaToDraw.getPosY(), bolaToDraw.getSize(), bolaToDraw.getSize(), this);
                }
            }
        }

        for (Bola bola : bolas) {
            // [FIX] The entire drawing logic for a ball must be inside the null check.
            if (bola != null) {
                Image artifactImage = getArtifactImage(bola.getArtifactType());
                if (artifactImage != null) {
                    g2d.drawImage(artifactImage, bola.getPosX(), bola.getPosY(), bola.getSize(), bola.getSize(), this);
                }
            }
        }
    }

    private Image getArtifactImage(ArtifactType type){
        if (type == null) return null;
        switch (type) {
            case BOOK:
                return bookImage;
            case SCROLL:
                return scrollImage;
            default:
                return null;
        }
    }

    @Override
    public void refreshView(){
        SwingUtilities.invokeLater(() -> repaint());
    }

    @Override
    public void addKeyListener(KeyListener key){
        super.addKeyListener(key);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        /*
        * Method untuk update posisi pancingan/hook
        * berdasarkan posisi mouse
        */
        int x = e.getX();
        int y = e.getY();
        
        presenter.updateHookPosition(x, y);
    }

    @Override
    public void mouseDragged(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        
        presenter.updateHookPosition(x, y);
    }
    
    @Override
    public void mousePressed(MouseEvent e){presenter.handleMouseClicked(e.getX(), e.getY()); }
    @Override
    public void mouseEntered(MouseEvent e){ }
    @Override
    public void mouseReleased(MouseEvent e){ }
    @Override
    public void mouseClicked(MouseEvent e){ }
    @Override
    public void mouseExited(MouseEvent e){ }
}
