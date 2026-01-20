package presenter;

/*
* ===================================================
* Filename     : LeaderboardPresenter.java
* Programmer   : Kasyful Haq Bachariputra
* Date         : 15 Juni 2025
* Email        : kasyfulhaqb@upi.edu
* Deskripsi    : Package presenter untuk segala hal
*                bersangkutan dengan leaderboard
* ===================================================
*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.DatabaseManager;
import model.THasil;
import view.LeaderboardView;

public class LeaderboardPresenter{
    private DatabaseManager databaseManager;
    private LeaderboardView view;
    private StartGameListener startGameListener;

    public LeaderboardPresenter(DatabaseManager databaseManager, LeaderboardView leaderboardView){
        this.databaseManager = databaseManager;
        this.view = leaderboardView;

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action){
                handlePlayButtonClick();
            }
        };
        
        this.view.addPlayButtonListener(actionListener);
        loadDisplayScore();
    }

    private void loadDisplayScore(){
        ArrayList<THasil> data = databaseManager.getAllScores();
        view.displayScores(data);
    }

    public void setStartGameListener(StartGameListener startGameListener){ this.startGameListener = startGameListener; }

    public void handlePlayButtonClick(){
        String username = view.getUsername();
        if (username.equals(" ") || username.equals("")) {
            view.showErrorMess();
            return;
        }

        boolean userExists = databaseManager.checkUserString(username);
        if (!userExists) {
            databaseManager.addNewUser(username);
        }
        if (startGameListener != null) {
            startGameListener.onStartGame(username);
        }
    }

    public void refreshScore(){ loadDisplayScore(); }
}