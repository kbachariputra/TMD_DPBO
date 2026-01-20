package view;

/* ===================================================
* Filename     : Pancingan.java
* Programmer   : Kasyful Haq Bachariputra
* Date         : 15 Juni 2025
* Email        : kasyfulhaqb@upi.edu
* Deskripsi    : Package view untuk main menu dan
*                Leaderboard
* ===================================================
*/

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Flow;

import model.THasil;

public class LeaderboardView extends JPanel{
    private JLabel titleLabel = new JLabel();
    private JScrollPane leaderboard = new JScrollPane();
    private JPanel leaderboardInside = new JPanel();
    private JPanel south = new JPanel();
    private JTextField usernameField = new JTextField();
    private JButton playButton = new JButton();
    private JPanel leaderboardWrapper = new JPanel();
    private JLabel credits = new JLabel();

    private ArrayList<THasil> data;

    public LeaderboardView(){
        setLayout(new BorderLayout());
        titleLabel.setText("THE ARCHIVIST");
        add(titleLabel, BorderLayout.NORTH);

        leaderboardInside.setLayout(new BoxLayout(leaderboardInside, BoxLayout.Y_AXIS));
        leaderboard.setViewportView(leaderboardInside);
        leaderboard.setPreferredSize(new Dimension(400, 500));
        
        playButton.setText("Play");
        usernameField.setColumns(15);
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        credits.setText("<html>Background: https://lornn.itch.io/witches-wizards-backgrounds<br>" +
                        "Character Sprite: https://craftpix.net/freebies/free-wizard-sprite-sheets-pixel-art/</html>");
        JPanel inputLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputLabel.add(usernameField);
        inputLabel.add(playButton);
        south.add(inputLabel);
        south.add(credits);
        add(south, BorderLayout.SOUTH);
        
        leaderboardWrapper.setLayout(new FlowLayout());
        leaderboardWrapper.add(leaderboard);
        add(leaderboardWrapper, BorderLayout.CENTER);
    }

    public void displayScores(ArrayList<THasil> data){
        /*
         * Method utama untuk display score dari database
         */
        leaderboardInside.removeAll();
        for(int i = 0;i < data.size(); i++){
            JLabel temp = new JLabel();
            temp.setText((i + 1) + data.get(i).getUsername() + " - Score " + data.get(i).getScore() + " - Artifacts " + data.get(i).getCount());
            leaderboardInside.add(temp);
        }
    }

    public String getUsername() { return usernameField.getText(); }
    public void addPlayButtonListener(ActionListener action){ playButton.addActionListener(action); }
    public void showErrorMess(){ System.out.println("Nama tidak bisa kosong"); JOptionPane.showMessageDialog(this, "Username tidak bisa kosong.", "Error", JOptionPane.ERROR_MESSAGE); }
}