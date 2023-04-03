package application;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
public class GameMenu extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		
		Pane root = new Pane();
		root.setPrefSize(800, 600);
		
		InputStream is = Files.newInputStream(Paths.get("res/images/asteroids_bg.png"));
		
		Image img = new Image(is);
		is.close();
		
		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(800);
		imgView.setFitWidth(600);
		
		
		root.getChildren().addAll(imgView);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	private static class MenuButton extends StackPane {
		private Text text;
		
		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(text.getFont().font(20));
			
			Rectangle bg = new Rectangle(250, 30);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));
			
			setAlignment(Pos.CENTER_LEFT);
			setRotate(-0.5);
			getChildren().addAll(bg, text);
			
			setOnMouseEntered(event -> {
				bg.setTranslateX(10);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
			
			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());
			
			setOnMousePressed(event -> setEffect(drop));
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
