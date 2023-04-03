//package application;
//
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//public class PlayerShip extends ImageView {
//    private double xVelocity = 0;
//    private double yVelocity = 0;
//    private double rotation = 0;
//    private final double acceleration = 0.1;
//    private final double maxSpeed = 5;
//    private final double bulletSpeed = 10;
//    private final double hyperspaceChance = 0.1;
//    
//    public PlayerShip(Image image) {
//        super(image);
//        setFitWidth(50);
//        setFitHeight(50);
//    }
//    
//    public void rotateRight() {
//        rotation += 10;
//        setRotate(rotation);
//    }
//    
//    public void rotateLeft() {
//        rotation -= 10;
//        setRotate(rotation);
//    }
//    
//    public void fire(Group root) {
//        Bullet bullet = new Bullet(getRotate(), bulletSpeed);
//        bullet.setX(getX() + getFitWidth() / 2 - bullet.getFitWidth() / 2);
//        bullet.setY(getY() + getFitHeight() / 2 - bullet.getFitHeight() / 2);
//        root.getChildren().add(bullet);
//    }
//    
//    public void applyThrust() {
//        xVelocity += acceleration * Math.sin(Math.toRadians(rotation));
//        yVelocity -= acceleration * Math.cos(Math.toRadians(rotation));
//        xVelocity = Math.min(Math.max(xVelocity, -maxSpeed), maxSpeed);
//        yVelocity = Math.min(Math.max(yVelocity, -maxSpeed), maxSpeed);
//        setX(getX() + xVelocity);
//        setY(getY() + yVelocity);
//    }
//    
//    public void hyperspace(Group root) {
//        if (Math.random() < hyperspaceChance) {
//            while (true) {
//                double newX = Math.random() * (root.getScene().getWidth() - getFitWidth());
//                double newY = Math.random() * (root.getScene().getHeight() - getFitHeight());
//                Bounds newBounds = new Rectangle2D(newX, newY, getFitWidth(), getFitHeight());
//                boolean collision = false;
//                for (Node node : root.getChildren()) {
//                    if (node.getBoundsInParent().intersects(newBounds)) {
//                        collision = true;
//                        break;
//                    }
//                }
//                if (!collision) {
//                    setX(newX);
//                    setY(newY);
//                    break;
//                }
//            }
//        }
//    }
//}
