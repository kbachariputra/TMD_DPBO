package model;
/* ===================================================
* Filename     : DatabaseManager.java
* Programmer   : Kasyful Haq Bachariputra
* Date         : 15 Juni 2025
* Email        : kasyfulhaqb@upi.edu
* Deskripsi    : Package model sebagai sebuah
*                Manager untuk tabel thasil dan data 
*                di dalamnya
* ===================================================
*/

import model.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabaseManager {
    private DB db;
    private Connection conn;

    public DatabaseManager(){
        try {
            this.db = new DB();
            this.conn = db.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<THasil> getAllScores(){
        /*
         * Mendapatkan semua data dari tabel thasil
         * dan menginstansiasi Thasil sesuai data
         */
        ArrayList<THasil> data = new ArrayList<>();
        String query = "SELECT * FROM thasil ORDER BY score DESC";

        try(Statement stmt = conn.createStatement();
           ResultSet result = stmt.executeQuery(query)){
           while (result.next()) {
            String username = result.getString("username");
            int score = result.getInt("score");
            int count = result.getInt("count");

            THasil data_thasil = new THasil(username, score, count);
            data.add(data_thasil);
           }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return data;
    }

    public boolean checkUserString(String username){
        /*
         * Method untuk check apakah username sudah ada di database atau belum
         */
        String query = "SELECT COUNT(*) FROM thasil WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, username);
            
            try(ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addNewUser(String username){
        /*
         * Method untuk menambah user baru pada database
         */
        String query = "INSERT INTO thasil (username, score, count) VALUES (?, 0, 0)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            System.out.println("User ditambahkan! " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserScore(String username, int finalScore, int finalCount){
        /*
         * Method untuk update score ke database
         */
        int highScore = 0;
        String query = "SELECT score FROM thasil WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    highScore = rs.getInt("score");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (highScore < finalScore) {
            query = "UPDATE thasil SET score = ?, count = ? WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, finalScore);
                pstmt.setInt(2, finalCount);
                pstmt.setString(3, username);
    
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Score updated for user : " + username);
                }else{
                    System.out.println("Could not find user for update : " + username);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Not enough score!");
        }
    }
}