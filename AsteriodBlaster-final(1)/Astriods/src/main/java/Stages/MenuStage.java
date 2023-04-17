package Stages;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import Interfaces.IGameObject;
import Interfaces.IStage;
import Logic.GameEngine;
import Logic.GameSettings;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//The main menu stage
public class MenuStage implements IStage{
 private String name;
    private Stage stage;
    private Scene scene;
    private Pane root;
    private final GameEngine engine;
    public static MenuStage instance;
    private final GameSettings settings ;
    
    public MenuStage(String name){
        this.engine = GameEngine.getEngine();
        this.settings = GameSettings.GetInstance();
        this.name = name;
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
    public void draw() { // logic for stage happens on main thread
        
    }

    @Override
    public void loadStage() { // called as soon as stage is set
        Platform.runLater(() -> { // run on main thread as soon as it becomes available again for execution
            this.stage = new Stage();
            this.root = new Pane();
            this.scene = new Scene(root, settings.BOARD_WIDTH,settings.BOARD_HEIGHT);

            root.setStyle("-fx-background-color: black; -fx-border-color: white;");

            Font runnerFont = Font.loadFont(getClass().getResourceAsStream("/AlienRavagerItalic-4B214.ttf"), 18);
            
            Text title1 = new Text("ASTEROID");
            title1.setFill(Color.WHITE);
            title1.setFont(new Font(runnerFont.getName(), 120));
            title1.setX(27);
            title1.setY(91);
            root.getChildren().add(title1);

            Text title2 = new Text("BLASTER");
            title2.setFill(Color.WHITE);
            title2.setFont(new Font(runnerFont.getName(), 130));
            title2.setX(225);
            title2.setY(173);
            root.getChildren().add(title2);

            Text intro1 = new Text("Your spaceship is lost in the unknown outer space...");
            intro1.setFill(Color.WHITE);
            intro1.setFont(new Font("Arial", 22));
            intro1.setX(177);
            intro1.setY(217);
            root.getChildren().add(intro1);

            Text intro2 = new Text("Will you be able to save the universe?");
            intro2.setFill(Color.WHITE);
            intro2.setFont(new Font("Arial", 22));
            intro2.setX(200);
            intro2.setY(256);
            root.getChildren().add(intro2);
            
            String normalButtonStyle = "-fx-background-color: black; -fx-text-fill: white; -fx-border-color: white;";
            String hoverButtonStyle = "-fx-background-color: black; -fx-text-fill: red; -fx-border-color: red;";

            Button newGameButton = new Button("New Game");
            newGameButton.setLayoutX((root.widthProperty().doubleValue() - newGameButton.getWidth()) / 2);               
            newGameButton.setLayoutY(335);
            newGameButton.setFont(new Font("Arial", 25));
            newGameButton.setStyle(normalButtonStyle);

            newGameButton.setOnMouseEntered(event -> {
                newGameButton.setStyle(hoverButtonStyle);
            });
            
            newGameButton.setOnMouseExited(event -> {
                newGameButton.setStyle(normalButtonStyle);
            });

            root.getChildren().add(newGameButton);

            newGameButton.setOnMouseClicked(event -> {
                try {
                    // code to be executed when button is clicked
                    engine.setCurrentStage("GameStage");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
             });
           
            Button controlsButton = new Button("View Controls");
            controlsButton.setLayoutX((root.widthProperty().doubleValue() - controlsButton.getWidth()) / 2);               
            controlsButton.setLayoutY(389);
            controlsButton.setFont(new Font("Arial", 25));
            controlsButton.setStyle(normalButtonStyle);

            controlsButton.setOnMouseEntered(event -> {
                controlsButton.setStyle(hoverButtonStyle);
            });
            
            controlsButton.setOnMouseExited(event -> {
                controlsButton.setStyle(normalButtonStyle);
            });

            controlsButton.setOnMouseClicked(event -> {               
                    Stage popup = new Stage(); // generate 'pop-up' control view screen
                    popup.initStyle(StageStyle.UNDECORATED);
                    popup.initOwner(stage);
                    popup.initModality(Modality.APPLICATION_MODAL);

                    VBox container = new VBox(10);
                    container.setPrefSize(700, 500);
                    container.setStyle("-fx-background-color: black; -fx-border-color: white; -fx-border-width: 2;");
                    
                    // add all buttons for demonstration
                    Button upButton = new Button("\u2191");
                    upButton.setStyle(normalButtonStyle);
                    upButton.setFont(new Font("Arial", 20));
                    upButton.setFocusTraversable(false);

                    Button leftButton = new Button("\u2190");
                    leftButton.setStyle(normalButtonStyle);
                    leftButton.setFont(new Font("Arial", 20));
                    leftButton.setFocusTraversable(false);

                    Button rightButton = new Button("\u2192");
                    rightButton.setStyle(normalButtonStyle);
                    rightButton.setFont(new Font("Arial", 20));
                    rightButton.setFocusTraversable(false);

                    Button downButton = new Button("\u2193");
                    downButton.setStyle(normalButtonStyle);
                    downButton.setFont(new Font("Arial", 20));
                    downButton.setFocusTraversable(false);

                    Button spacebarButton = new Button("          Spacebar          ");
                    spacebarButton.setStyle(normalButtonStyle);
                    spacebarButton.setFont(new Font("Arial", 20));
                    spacebarButton.setFocusTraversable(false);

                    Button xButton = new Button("X");
                    xButton.setStyle(normalButtonStyle);
                    xButton.setFont(new Font("Arial", 20));
                    xButton.setFocusTraversable(false);

                    Button enterButton = new Button("\u21B5 Enter");
                    enterButton.setStyle(normalButtonStyle);
                    enterButton.setFont(new Font("Arial", 20));
                    enterButton.setFocusTraversable(false);

                    // set all texts for explanation
                    Text upText = new Text("Press Up to Speed Up");
                    upText.setFill(Color.WHITE);
                    upText.setFont(new Font("Arial", 20));

                    Text leftText = new Text("Press Left to Rotate Left");
                    leftText.setFill(Color.WHITE);
                    leftText.setFont(new Font("Arial", 20));

                    Text rightText = new Text("Press Right to Rotate Right");
                    rightText.setFill(Color.WHITE);
                    rightText.setFont(new Font("Arial", 20));

                    Text downText = new Text("Press Down to Slow Down");
                    downText.setFill(Color.WHITE);
                    downText.setFont(new Font("Arial", 20));

                    Text spacebarText = new Text("Press Spacebar to Fire Bullets");
                    spacebarText.setFill(Color.WHITE);
                    spacebarText.setFont(new Font("Arial", 20));

                    Text xText = new Text("Press X to Hyperspace Jump");
                    xText.setFill(Color.WHITE);
                    xText.setFont(new Font("Arial", 20));

                    Text enterText = new Text("Press Enter to Pause Game");
                    enterText.setFill(Color.WHITE);
                    enterText.setFont(new Font("Arial", 20));

                    Text controlsText = new Text("Controls");
                    controlsText.setFill(Color.WHITE);
                    controlsText.setFont(new Font(runnerFont.getName(), 49));

                    // Add heading to VBox
                    container.getChildren().add(controlsText);
                    VBox.setMargin(controlsText, new Insets(10, 300, 20, 0));

                    // Create a GridPane to layout the buttons and texts
                    GridPane grid = new GridPane();
                    grid.setHgap(10);
                    grid.setVgap(10);
                    grid.setAlignment(Pos.CENTER);
                    grid.add(upButton, 0, 0);
                    grid.add(leftButton, 0, 1);
                    grid.add(rightButton, 0, 2);
                    grid.add(downButton, 0, 3);
                    grid.add(spacebarButton, 0, 4);
                    grid.add(xButton, 0, 5);
                    grid.add(enterButton, 0, 6);

                    grid.add(upText, 1, 0);
                    grid.add(leftText, 1, 1);
                    grid.add(rightText, 1, 2);
                    grid.add(downText, 1, 3);
                    grid.add(spacebarText, 1, 4);
                    grid.add(xText, 1, 5);
                    grid.add(enterText, 1, 6);

                    // Add the GridPane to the container (VBox)
                    container.getChildren().add(grid);

                    // back label to close the popup
                    Label backLabel = new Label("Back");
                    backLabel.setFont(Font.font("Arial", 20));
                    backLabel.setTextFill(Color.WHITE);

                    String normalLabelStyle = "-fx-text-fill: white;";
                    String hoverLabelStyle = "-fx-text-fill: red;";

                    backLabel.setStyle(normalLabelStyle);
                    backLabel.setOnMouseEntered(e1 -> {
                        backLabel.setStyle(hoverLabelStyle);
                    });
                    backLabel.setOnMouseExited(e2 -> {
                    backLabel.setStyle(normalLabelStyle);
                    });
                    backLabel.setOnMouseClicked(e3 -> {
                        popup.close();
                    });

                    // Create click events to demonstrate the buttons
                    Map<KeyCode, Button> keyButtonMap = new HashMap<>();
                    keyButtonMap.put(KeyCode.UP, upButton);
                    keyButtonMap.put(KeyCode.LEFT, leftButton);
                    keyButtonMap.put(KeyCode.RIGHT, rightButton);
                    keyButtonMap.put(KeyCode.DOWN, downButton);
                    keyButtonMap.put(KeyCode.SPACE, spacebarButton);
                    keyButtonMap.put(KeyCode.X, xButton);
                    keyButtonMap.put(KeyCode.ENTER, enterButton);

                    // Add backLabel to VBox
                    container.getChildren().add(backLabel);
                    VBox.setMargin(backLabel, new Insets(20, 400, 0, 0));

                    Scene popupScene = new Scene(container);
                    // Calculate the center of the GameStage
                    double centerX = stage.getX() + (scene.getWidth() - container.getPrefWidth()) / 2;
                    double centerY = stage.getY() + (scene.getHeight() - container.getPrefHeight()) / 2;
                    // Set the position of the popup
                    popup.setX(centerX);
                    popup.setY(centerY);
                    popup.setScene(popupScene);
                    popup.show();

                    // Demonstration of buttons clicked
                    popupScene.setOnKeyPressed(e4 -> {
                        KeyCode keyCode = e4.getCode();
                        Button button = keyButtonMap.get(keyCode);
                        if (button != null) {
                            button.setStyle(hoverButtonStyle);
                        }
                    });
                    
                    popupScene.setOnKeyReleased(e5 -> {
                        KeyCode keyCode = e5.getCode();
                        Button button = keyButtonMap.get(keyCode);
                        if (button != null) {
                            button.setStyle(normalButtonStyle);
                        }
                    });
            });

            root.getChildren().add(controlsButton);
            
            Button highscoreButton = new Button("High Scores");
            highscoreButton.setLayoutX((root.widthProperty().doubleValue() - newGameButton.getWidth()) / 2);               
            highscoreButton.setLayoutY(444);
            highscoreButton.setFont(new Font("Arial", 25));
            highscoreButton.setStyle(normalButtonStyle);

            highscoreButton.setOnMouseEntered(event -> {
                highscoreButton.setStyle(hoverButtonStyle);
            });
            
            highscoreButton.setOnMouseExited(event -> {
                highscoreButton.setStyle(normalButtonStyle);
            });

            root.getChildren().add(highscoreButton);

            highscoreButton.setOnMouseClicked(event -> {
                try {
                    // code to be executed when button is clicked
                    engine.setCurrentStage("HighScoreStage");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
             });

            Button exitButton = new Button("Exit Game");
            exitButton.setLayoutX((root.widthProperty().doubleValue() - exitButton.getWidth()) / 2);               
            exitButton.setLayoutY(497);
            exitButton.setFont(new Font("Arial", 25));
            exitButton.setStyle(normalButtonStyle);

            exitButton.setOnMouseEntered(event -> {
                exitButton.setStyle(hoverButtonStyle);
            });
            
            exitButton.setOnMouseExited(event -> {
                exitButton.setStyle(normalButtonStyle);
            });

            root.getChildren().add(exitButton);

            exitButton.setOnMouseClicked(event -> {
                // code to be executed when button is clicked
                System.exit(0);
             });
            
        
            this.stage.initStyle(StageStyle.UNDECORATED);
            // Add the labels to the root pane and position them in the center
            stage.setScene(scene);
            this.stage.show();

            // Calculate the center position of the stage after it is shown
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
            double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

            // Set the position of the stage to the center of the screen
            stage.setX(centerX);
            stage.setY(centerY);
        });
    }
      

    public void start(Stage primaryStage) throws Exception {
        this.loadStage();
    }

    @Override
    public ArrayList<IGameObject> getGameObjects() {
        throw new UnsupportedOperationException("Scene does not contain game objects");
    }

    @Override
    public void setGameObject(ArrayList<IGameObject> gameObjects) {
        throw new UnsupportedOperationException("Scene does not contain game objects");
    }

    @Override
    public void addGameObject(IGameObject object) {
        throw new UnsupportedOperationException("Scene does not contain game objects");
    }

    @Override
    public void removeGameObject(IGameObject object) {
        throw new UnsupportedOperationException("Scene does not contain game objects"); 
    }
    
    @Override
    public void hide() {
        this.stage.hide();
    }
}
