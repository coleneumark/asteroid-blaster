package com.javalamps.asteriods;
import Models.GameState;
import Models.IGameObject;
import Models.IStage;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private GameState state;
    private int fps = 0;
    private int fpsCap = 60;
    public ArrayList<IStage> stages = new ArrayList<IStage>();
    private int currentStage = 0;

    public GameEngine(ArrayList<IStage> stages,int currentStage){
        this.stages = stages;
        this.currentStage = currentStage;
    }
    

    public void pauseGame(){
        state = GameState.Paused;
    }
    

    public void resumeGame(){
        state = GameState.Playing;
    }
    

    public void start() throws Exception{
        this.resumeGame();
        stages.get(currentStage).loadStage();
        long startTime = System.currentTimeMillis();
        while(true){
           if(state == GameState.Playing){
                if(( System.currentTimeMillis() - startTime) > 1000 ){
                    System.out.println(fps);
                    fps = 0;
                    startTime = System.currentTimeMillis();
                }else{
                    ExecuteGame();
                    fps++;
                }
               Thread.sleep(1000 / fpsCap);
           }
        }
    }
    
    public void ExecuteGame(){
        for(IGameObject object:stages.get(currentStage).getGameObjects()){
            object.Update();
        }
        stages.get(currentStage).draw();
    }
    
}
