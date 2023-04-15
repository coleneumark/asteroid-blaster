package Logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Class is used to handle anything score related
public class ScoreHandler {
    //score text file
    private final String filename = "high_score.txt";
    
    //function to add the score to the leaderboard
    public void addScore(Score playerScore) throws IOException{
        
        //Check first if a file exists
        this.createIfNotExists();
        
        //New top 10 list which we build
        List<Score> scores = new ArrayList<>();
        
        //Current top 10 list
        List<Score> top10 =  this.readScores();
        
        //If the score is empty add it
        if(top10.isEmpty()){
            scores.add(playerScore);
            //store the score
            this.writeScores(scores);
        }else{
            //Check to ensure the score is only added once
            boolean added = false;
            
            for(Score item :top10){
                //If 10 records has been added write the file and return
                if(scores.size() == 10){
                    this.writeScores(scores);
                    return;
                }
                //if the score has not yet been added, and the score is more than 
                //the current score the player is point to add it above 
                //that score. If the count of the score has not reached
                //10 yet also add the old score after the player score
                if(!added && item.getScore() < playerScore.getScore()){
//                      System.out.println("Adding player score 1");
                    added = true;
                    scores.add(playerScore);
                    if(scores.size() != 10){
                        scores.add(item);
                    }
                }else{
                    scores.add(item);
                }
            }
            
            // if the player did not beat any other players
            // but there is not yet 10 records, also add the player's
            //score to the scoreboard
            if(!added && scores.size() !=  10){
                scores.add(playerScore);
                added = true;
            }
            if(added){
                this.writeScores(scores);
            }
        }
    }
    
    //Checks if a file exists, if it does not it will create it
    private void createIfNotExists() throws IOException{
        File file = new File(filename);
        file.createNewFile();
    }
    
    //Write the new textfile containing the score
    private void writeScores(List<Score> scores) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(""); // Clear file content
            for (Score score : scores) {
                writer.write(score.getName() + "|" + score.getScore() + "\n");
            }
        } // Clear file content
    }
    
    //Read all the scores from the text file
    public  List<Score> readScores() {
        List<Score> scores = new ArrayList<>();
        // self-disposing BufferReader
        try (BufferedReader br = new BufferedReader(new FileReader(this.filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("[|]");
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                scores.add(new Score(score, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores;
    }

    
}
