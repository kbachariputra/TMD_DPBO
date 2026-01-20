/*
 * ===================================================
 * Filename     : DB.java
 * Programmer   : Kasyful Haq Bachariputra
 * Date         : 6 June 2025
 * Email        : kasyfulhaqb@upi.edu
 * Deskripsi    : File App untuk dapat bisa menjalankan game
 * 
 * ===================================================
 */

/*
 * Saya Kasyful Haq Bachariputra dengan NIM 2304820 mengerjakan Tugas Masa Depan dalam mata kuliah Desain dan Pemrograman Berorientasi Objek
 * untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.
 */

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Pemain;
import model.BolaManager;
import model.DatabaseManager;
import model.Keranjang;
import presenter.GamePresenter;
import presenter.LeaderboardPresenter;
import presenter.GameOverListener;
import presenter.StartGameListener;
import view.MainGameView;
import view.LeaderboardView;

public class App implements GameOverListener, StartGameListener{
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static GamePresenter presenter;
    private static LeaderboardPresenter lPresenter;
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            // Menggunakan invokeLater agar tidak terjadi error ketika menjalankan App
            public void run(){
                mulaiGame();
            }
        });
    }

    private static void mulaiGame(){
        // Buat Pemain (Temporary)
        Pemain pemain = new Pemain(0, 0);

        // Buat frame
        JFrame frame = new JFrame("TMD Bola-bola");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menggunakan CardLayout untuk merubah halaman
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Instansiasi berbagai macam view yang ada
        LeaderboardView leaderboardView = new LeaderboardView();
        MainGameView gameView = new MainGameView();

        // Tambahkan view ke Panel
        mainPanel.add(leaderboardView, "MENU");
        mainPanel.add(gameView, "GAME");

        // Membuat model dan presenter untuk bagian Game utama (Gameloop)
        BolaManager managerBola = new BolaManager();
        DatabaseManager databaseManager = new DatabaseManager();
        Keranjang keranjang = new Keranjang(0, 0);

        presenter = new GamePresenter(pemain, gameView, managerBola, keranjang, databaseManager);
        lPresenter = new LeaderboardPresenter(databaseManager, leaderboardView);

        presenter.setGameOverListener(new App());
        lPresenter.setStartGameListener(new App());
        gameView.setPemain(pemain);

        // Logika untuk mengubah presenter
        gameView.setPresenter(presenter);

        // Setup dari frame
        frame.add(mainPanel);
        frame.pack();
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Set posisi pemain di tengah layar
        pemain.setPosition((frame.getWidth()/2) - (pemain.getWidth() / 2), (frame.getHeight() / 2) - (pemain.getHeight() / 2));

        // Set posisi keranjang
        keranjang.setPosition(((frame.getWidth() * 3) / 4) - (keranjang.getHeight() / 2), (frame.getHeight() / 2) - (keranjang.getHeight() / 2));

        // Tunjukkan menu terlebih dahulu
        cardLayout.show(mainPanel, "MENU");

    }

    @Override
    public void gameOver(){
        /*
         * Menunjukkan view game over dari cardlayout
         * serta melakukan reset posisi dan score
         */
        cardLayout.show(mainPanel, "MENU");

        lPresenter.refreshScore();

        presenter.resetGame();
    }

    @Override
    public void onStartGame(String username){
        System.out.println(username);

        presenter.setCurrentPlayerUsername(username);

        cardLayout.show(mainPanel, "GAME");

        mainPanel.getComponent(1).setFocusable(true);
        mainPanel.getComponent(1).requestFocusInWindow();

        presenter.startGame();
    }
}
