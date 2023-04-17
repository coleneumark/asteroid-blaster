package Stages;

import java.util.ArrayList;
import java.util.List;

import Interfaces.IAlienShip;
import Interfaces.IBullet;
import Logic.Asteroid;
import Interfaces.IGameObject;
import Interfaces.IPlayer;
import Interfaces.IStage;
import Interfaces.IAsteroid;
import Interfaces.IOnPauseEvent;
import Logic.AlienShip;
import Logic.AsteroidSize;
import static Logic.AsteroidSize.MEDIUM;
import Logic.GameSettings;
import Logic.Ship;
import Logic.GameEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//This stage handles the main game.
public class GameStage extends Application implements IStage, IOnPauseEvent {
    private String name;
    private ArrayList<IGameObject> gameObjects;
    private Stage stage = null;
    private Scene scene = null;
    private Pane root = null;
    private int score = 0;
    private int level = 1;
    private final GameEngine engine;
    private Ship ship;
    private GameSettings settings ;
    private List<IGameObject> bulletQueue = new ArrayList<>();
    private int livesGranted = 0;
    public GameStage(String name){
        this.engine = GameEngine.getEngine();
        this.name = name;
    }
    
    //Bullet queue list is used to queue bullets so that the game object list
    //is not updated while getting looped
    public void addBulletToQueue(IGameObject bullet){
        this.bulletQueue.add(bullet);
    }
    
    public Ship getShip(){
       return this.ship;
    }
    
    @Override
    public String getName() {
       return this.name; 
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<IGameObject> getGameObjects() {
        return this.gameObjects;   
    }

    @Override
    public void setGameObject(ArrayList<IGameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public void addGameObject(IGameObject object){
        this.gameObjects.add(object);
    }
    
    @Override
    public void removeGameObject(IGameObject object){
        this.gameObjects.remove(object);
    }
    
    //Check if all asteroids are dead, if they are advance to the next stage
    public void checkLevelUp(){ // handles level changing of player (goes up level if all asteroids on level are destroyed)
        // if alien ship not destroyed, two alien ships added, need to destroy 
        for(IGameObject item : this.gameObjects){
            if(IAsteroid.class.isAssignableFrom(item.getClass()) && item.getIsAlive()) {
                return; // if asteroid was found, should not level up, thus we return from this function
            }
        }
        this.level++; // if we reach this point, need to level up
        for (int i = 0; i < this.level; i++) {
            Asteroid asteroid = new Asteroid(AsteroidSize.LARGE); // create new asteroid based on current level
            asteroid.onCreate(); 
            this.gameObjects.add(asteroid);   // add to Game Object list
        }
        // each configured level to appear (currently set at 1)
        if(this.level % this.settings.alienShipEveryLevel == 0){
            this.gameObjects.add(new AlienShip(this.level * 1));
        }
    }
    
    @Override
    public void draw() { // gets called by GameEngine x amount of times per second
        //The scene has not finished its initialization
        if(root == null) { // to prevent NullPointerException
            return;
        }
       
        Platform.runLater(() -> {
            //Add the queued bullets to the list, this is to avoid modfiying the list as you are looping
            this.gameObjects.addAll(this.bulletQueue); // wait for loop to complete, first add all current bullets and the clear queue
            
            //clear the queue
            this.bulletQueue =  new ArrayList<>();
            root.getChildren().clear(); // clears all elements of form to be redrawn (show movement)

            ArrayList<IGameObject> tempLst =  new ArrayList<>(); // used to add all objects still alive to scene
            //Here we loop though all the objects to remove any dead objects --> determine if object is alive
            for(IGameObject item : this.gameObjects){
                if(item.getIsAlive()){//if the item is still alive add it to the temp list
                    tempLst.add(item);
                }else if(IAsteroid.class.isAssignableFrom(item.getClass())){ // check if object is asteroid that died, need to switch
                    //recreate a smaller asteroid if Large or medium one dies
                    switch(((IAsteroid)item).getSize()){ // convert to asteroid and add scoreLabel and two mediums, or small, depending on use case
                        case LARGE: // logic varies depending on type of asteroid, what will need to be done, e.g large broken into two medium
                                this.score += 100;
                                tempLst.add(new Asteroid(AsteroidSize.MEDIUM,item.getX(),item.getY()));
                                tempLst.add(new Asteroid(AsteroidSize.MEDIUM,item.getX(),item.getY()));
                            break;
                        case MEDIUM: // medium into small
                                this.score += 150;
                                tempLst.add(new Asteroid(AsteroidSize.SMALL,item.getX(),item.getY()));
                                tempLst.add(new Asteroid(AsteroidSize.SMALL,item.getX(),item.getY()));
                            break;
                        case SMALL: // small is destroyed and points added
                                this.score += 180;
                            break;
                    }
                    // implement 1000 lives reached, granted additional life
                    int scoreRounded = (int) Math.floor(this.score / 1000.0) * 1000;
                    if (scoreRounded > this.livesGranted * 1000) {
                        this.livesGranted++;
                        this.ship.addLife();
                    }
                }
            }
            
            //Set the game objects to the temp list which only contains the alive objects
            this.gameObjects = tempLst; // add all objects currently alive
            //after all dead objects have been removed, check if there are any objects left on this level
            this.checkLevelUp();
            
            //loop through all the game objects and call their update function
            for(IGameObject item : this.gameObjects){
                item.update(); 
                
                //The polygon of the item to the root
                Polygon object = item.getPolygon(); // shape to be drawn
                root.getChildren().add(object); // add to form
              
                //Logic should only execute on none player classes
                if(!IPlayer.class.isAssignableFrom(item.getClass())){ 
                    //If the item is a bullet scan all the asteroids
                    if(IBullet.class.isAssignableFrom(item.getClass())){
                        //Check if this is a alien ship bullet, if it is check if the player ship has collided with it
                       if(!((IBullet)item).getIsPlayerBullet()){ 
                           //Check if the alien ship has shot the player ship
                           if(ship.hasCollided(item)){
                               ship.subtractLife();
                               if(ship.getLives() == 0){
                                    try {
                                        this.gameOver();
                                        return;//return out of this current loop not to show the stage.
                                    } catch (Exception ex) {
                                    }
                                }
                           }
                        }else{ // indicates Player's bullet
                           //Check if any of the npc's (asteroid/alien ship) has collided with the bullets from the player ship
                            for(IGameObject npc : this.gameObjects){
                                if(IAsteroid.class.isAssignableFrom(npc.getClass()) || IAlienShip.class.isAssignableFrom(npc.getClass())){
                                    if(item.hasCollided(npc)){ 
                                        npc.hasCollided(item);
                                    }
                                }
                            }
                        }
                    }
                    // if not bullet and collision occurred with enemy object
                    if(!IBullet.class.isAssignableFrom(item.getClass()) && ship.hasCollided(item)){
                        ship.subtractLife();
                        if(ship.getLives() == 0){
                            try {
                                this.gameOver();
                                return;
                            } catch (Exception ex) {
                            }
                        }
                    }
                }
            }

            int lifeNum = ship.getLives();                  // shows hearts instead of number
            StringBuilder hearts = new StringBuilder();
            for (int i = 0; i < lifeNum; i++) {
                hearts.append("\u2764");
            }
            Text lives = new Text("LIVES: " + hearts.toString());
            lives.setFill(Color.WHITE);
            lives.setFont(new Font("Arial", 20));

            Text level = new Text("LEVEL: " + this.level);
            level.setFill(Color.WHITE);
            level.setFont(new Font("Arial", 20));

            Text score = new Text("SCORE: " + this.score);
            score.setFill(Color.WHITE);
            score.setFont(new Font("Arial", 20));

            Text enterInfo = new Text("Press [Enter] key to Pause");
            enterInfo.setFill(Color.WHITE);
            enterInfo.setFont(new Font("Arial", 20));
            
            score.setX(20);
            score.setY(40);
            level.setX(20);
            level.setY(20);
            lives.setX(20);
            lives.setY(60);
            enterInfo.setX(scene.getWidth() - 275);
            enterInfo.setY(20);
            root.getChildren().add(score);
            root.getChildren().add(lives);
            root.getChildren().add(level);
            root.getChildren().add(enterInfo);       
            
            root.setStyle("-fx-background-color: black; -fx-border-color: white;");
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            double centerX = (screenBounds.getWidth() - scene.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - scene.getHeight()) / 2;

            stage.setX(centerX);
            stage.setY(centerY);
            stage.setScene(scene);
            if(this.ship.getLives()> 0){            
                stage.show();
            } 
        });
    }

    @Override // only runs once
    public void loadStage() { // called as soon as stage is set
        this.score = 0; // initialise stage with all objects
        this.level = 1;
        this.livesGranted = 0;
        this.gameObjects = new ArrayList<>();
        // add new asteroid (only one on level one)
        this.gameObjects.add(new Asteroid(AsteroidSize.LARGE)); 
        // everything added to game object
        this.settings = GameSettings.GetInstance(); 
 
        this.gameObjects.add(new AlienShip(this.level * 1)); // HP * 3 
        // add ship to game object
        this.ship = new Ship();
        this.gameObjects.add(ship);
        for(IGameObject item : this.gameObjects){
                item.onCreate();
        }
        // to run on main thread later
        Platform.runLater(() -> {
            this.stage = new Stage();
            this.stage.initStyle(StageStyle.UNDECORATED);
            this.root = new Pane();
            this.scene = new Scene(root, settings.BOARD_WIDTH,settings.BOARD_HEIGHT);
            this.stage.setX(0);
            this.stage.setY(0);
            // key press events
            this.scene.setOnKeyPressed((event) -> {
                // handles game pausing
                this.engine.handleKeyPress(event.getCode()); // check if code entered
                // handle player movement
                this.ship.handleKeyPress(event.getCode());
             });
            
            this.scene.setOnKeyReleased((event) ->{
                // forces player to release key to enable shooting of bullet
                // prevent single string (make nice bullets) :p
                if(event.getCode() == KeyCode.SPACE){
                    this.ship.shoot();
                }
            } );
          
            // from GameEngine            
            this.engine.addOnPauseEvent(this); 
            this.draw(); // "draws" picture -- game loop of stage in GameStage
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.loadStage();
    }

    @Override
    public void hide() {
        this.stage.hide();
    }

    //open the scoreLabel screen if the game is over
    private void gameOver() throws Exception {
      this.engine.setScore(score);
      this.engine.setCurrentStage("GameOverStage");
    }

    //Popup if the game is paused
    @Override
    public void onPause() {
        Platform.runLater(() -> {
            Stage popup = new Stage(); // generate 'pop-up' pause screen
            popup.initStyle(StageStyle.UNDECORATED);
            popup.initOwner(stage);
            popup.initModality(Modality.APPLICATION_MODAL);

            VBox container = new VBox(10);
            container.setAlignment(Pos.CENTER);
            container.setPrefSize(250, 100);
            container.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 2;");
            
            Label resumeLabel = new Label("Resume Game");
            resumeLabel.setFont(Font.font("Arial", 20));
            resumeLabel.setTextFill(Color.WHITE);

            Label quitLabel = new Label("Back to Main Menu");
            quitLabel.setFont(Font.font("Arial", 20));
            quitLabel.setTextFill(Color.WHITE);

            resumeLabel.setOnMouseClicked(e -> {
                popup.close(); 
                this.engine.resumeGame();
            });

            quitLabel.setOnMouseClicked(e -> {
                popup.close();
                this.engine.resumeGame(); // back to main menu
                try {
                    this.engine.setCurrentStage("MainStage");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            String normalLabelStyle = "-fx-text-fill: white;";
            String hoverLabelStyle = "-fx-text-fill: red;";

            resumeLabel.setStyle(normalLabelStyle);
            resumeLabel.setOnMouseEntered(event -> {
                resumeLabel.setStyle(hoverLabelStyle);
            });
            resumeLabel.setOnMouseExited(event -> {
                resumeLabel.setStyle(normalLabelStyle);
            });

            quitLabel.setStyle(normalLabelStyle);
            quitLabel.setOnMouseEntered(event -> {
                quitLabel.setStyle(hoverLabelStyle);
            });
            quitLabel.setOnMouseExited(event -> {
                quitLabel.setStyle(normalLabelStyle);
            });

            container.getChildren().addAll(resumeLabel, quitLabel);
            Scene popupScene = new Scene(container);
            
            // Add key event listener for the ENTER key
            popupScene.setOnKeyPressed((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    popup.close();
                    this.engine.resumeGame();
                }
            });
            
            // Calculate the center of the GameStage
            double centerX = stage.getX() + (scene.getWidth() - container.getPrefWidth()) / 2;
            double centerY = stage.getY() + (scene.getHeight() - container.getPrefHeight()) / 2;

            // Set the position of the popup
            popup.setX(centerX);
            popup.setY(centerY);

            popup.setScene(popupScene);
            popup.show();
    });




    }
}
