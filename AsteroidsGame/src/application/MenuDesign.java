package application;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuDesign extends Application {
    
//    Stage window;
//    BorderPane layout;
    
    // entry point
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Pane root = new Pane();
        // size of our menu screen (will stay standard size consistently
        root.setPrefSize(800, 600);
        // file location
//        String imgUrl = "images/800x600_menu_bg.jpg";
        // takes input string (url from folder where we added our background images
        // create input stream from the file
        InputStream is = Files.newInputStream(Paths.get("images/800x600_menu_bg.jpg"));
        // takes input string (url from folder where we added our background images
        Image bg = new Image(is);
        // need to close to prevent stream from being "blocked"
        is.close();
            
        ImageView imgView = new ImageView(bg);
            
        root.getChildren().addAll(imgView);
            
        Scene scene = new Scene(root);
            
        primaryStage.setScene(scene);
        primaryStage.show();
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            
    }    
}