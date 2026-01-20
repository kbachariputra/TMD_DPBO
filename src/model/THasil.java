package model;

public class THasil {
    private String username;
    private int score;
    private int count;

    public THasil(String username, int score, int count){
        this.username = username;
        this.score = score;
        this.count = count;
    }

    public String getUsername() { return this.username; }
    public int getScore() { return this.score; }
    public int getCount() { return this.count; }

    public void setUsername(String username) { this.username = username; }
    public void setScore(int score) { this.score = score; }
    public void setCount(int count) { this.count = count; }
}
