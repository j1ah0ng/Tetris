package game;

import javafx.scene.text.Font;

public class Score extends javafx.scene.text.Text {

    // Attributes
    private int score;

    public Score() {
        super();
        score = 0;
        super.setFont(new Font("Chewy", 25));
        updateDisplay();
    }

    public void updateDisplay() { super.setText("" + score); }

    public void setScore(int newScore) {
        this.score = newScore;
        updateDisplay();
    }

    public void addScore(int addition) {
        this.score += addition;
        updateDisplay();
    }

    public int getScore() { return this.score; }
}
