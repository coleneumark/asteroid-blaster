package Logic;

//Class used to store the player score
public class Score {
    private int score;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }
    
    
}
