package Logic;
import Interfaces.IOnPauseEvent;
import Logic.GameSettings;
import Logic.GameState;
import Interfaces.IStage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;

// updated throughout all classes sharing instance of game engine, 
// will change across all classes that make use of game engine (shares the same instance) 
// based off singleton design pattern
public class GameEngine {
    private GameState state;
    private int fps = 0;
    public ArrayList<IStage> stages = new ArrayList<>();
    private int currentStage = -1;
    public static GameEngine engine;
    private final GameSettings settings ;
    private int score = 0;
    public List<IOnPauseEvent> pausedListener = new ArrayList<>();
    
    public void setScore(int score){
        this.score = score;
    }
    
    public int getScore(){
        return this.score;
    }
    
    public GameEngine(){
        this.settings = GameSettings.GetInstance();
    }
    
    //Adds a new pause event to the pause event listener
    public void addOnPauseEvent(IOnPauseEvent event){
        this.pausedListener.add(event);
    }
    
    /// returns a static instance of the game engine
    // returns a single instance of game engine class
    public static GameEngine getEngine(){
        if(GameEngine.engine == null){ // static variable, stays the same everywhere
            GameEngine.engine = new GameEngine();
        }
        return GameEngine.engine;
    }
    
    //loads a stage into the stage list
    public void loadStage(IStage stage){ // all objects passed to this function must make implement the IStage Interface
        this.stages.add(stage); // all stages are added to list of stages (individual names to be used later)
    }
    
    //returns the current game state
    public GameState getGameState(){
        return this.state;
    }
    
    //Gets the current stage
    public IStage getCurrentStage(){
        return this.stages.get(currentStage);
    }
    
    //Sets the current stage
    public void setCurrentStage(String name) throws Exception{ // name of stage passed, returns index based off name
        //If there is a current stage, hide the current stage to show the new one
        if(currentStage != -1){ // default set to -1 
            getCurrentStage().hide(); // need to hide active stage (if not -1 indicates a stage is already assigned)
        }
        //loop through all the stage and look for a matching stage name
        for(var x = 0; x < this.stages.size();x++){ // loop through all stages in array to retrieve name of stage
            if(stages.get(x).getName().equals(name)){
                
                this.currentStage = x; // set index 
                // list of "listeners" for stage, check if game paused
                this.pausedListener = new ArrayList<>();//reset the listener for the new stage, clear all for paused event
                this.getCurrentStage().loadStage(); // defined in interface, each stage loads own stage
                return; // not loop further, if gets to end not finding anything, exception will be thrown
            }
        }
        throw new Exception("Invalid stage name"); // necessary to handle invalid "developer" input
    }

    //Pause the game engine
    public void pauseGame(){
        this.state = GameState.Paused;
        //Execute the game paused event
        for(var event:this.pausedListener){
            event.onPause();
        }
    }
    
    //Resumes the game
    public void resumeGame(){
        this.state = GameState.Playing;
    }
    
    //Warning is suppressed due to needing the thread.sleep in the main game loop
    @SuppressWarnings("SleepWhileInLoop")
    public void start() throws Exception{
        this.resumeGame();//Resume the game just incase in paused state (just to make sure)
        long startTime = System.currentTimeMillis(); // get current milliseconds
        
        //only run if the game is in a playing state
        while(true){
            if(state == GameState.Playing){ // is busy playing
                if((System.currentTimeMillis() - startTime) > 1000 ){ // get current milliseconds minus previous start time
                    //A second has passed so print the FPS to the console
                    System.out.println(fps);// if larger than 1000 means second passed
                    fps = 0; // check how many seconds passed
                    startTime = System.currentTimeMillis(); // need to reset loop
                }else{
                    //Excute the game 
                    executeGame(); // if second not passed (loop) increase frame
                    fps++;
                }
            }
            //sleep the tread to ensure the game runs at a max x frames
           Thread.sleep(1000 / settings.maxFramesPerSecond); // sleep for second / how many times want to loop in second
        } // if not sleep will execute insanely fast can't update
    }
    
    //calls the draw function of a stage every x times per second
    public void executeGame(){ // split in case want to implement further logic into game at later stage
        this.getCurrentStage().draw(); // --> get draw function of current stage
    }// since all stages implement IStage, we can access draw function defined in the IStage interface
    
    //pause the game if enter is pressed
    public void handleKeyPress(KeyCode code) {
        switch (code) {
            case ENTER:
                if(this.state == GameState.Paused){ // if enter is pressed and state is paused --> resume
                    this.resumeGame();
                }else{
                    this.pauseGame();
                }
                break;
        }

    }
}
