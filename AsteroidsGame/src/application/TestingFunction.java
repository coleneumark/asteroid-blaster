//package application;
//
//public class TestingFunction {
//	
//
//}
//
//public abstract class SpaceObject {
//    private final Group parent; // encapsulate as private
//    private final Point2D position;
//    private Point2D velocity;
//    double theta;
//    double omega;
//    final Node transform;
//    // constructor of our space object
//    public SpaceObject(Group parent, Point2D position, Point2D velocity, double theta, double omega) {
//        this.parent = parent;
//        this.position = position;
//        this.velocity = velocity;
//        this.theta = theta;
//        this.omega = omega;
//        transform = new Group(); // add the transform to the group
//        parent.getChildren().add(transform); // add the transform to the parent node
//    }
//    // method for updating our space object
//    public void update(double delta) {
//        position.add(velocity.multiply(delta)); // update the position
//        theta += omega * delta; // update the angle
//        transform.setTranslateX(position.getX()); // update the position of the transform
//        transform.setTranslateY(position.getY());
//        transform.setRotate(Math.toDegrees(theta)); // update the rotation of the transform
//    }
//    // add getters for private variables to implement encapsulation
//    public Point2D getPosition() { return position; }
//    public Point2D getVelocity() { return velocity; }
//    public double getTheta() { return theta; }
//    public double getOmega() { return omega; }
//    public abstract Shape getBounds(); // add abstract method for getting the bounds of the space object
//    // add method for checking if the space object is leaving the bounds
//    public boolean isLeavingBounds(Rectangle2D bounds) {
//        double x = position.getX();
//        double y = position.getY();
//        double boundWidth = bounds.getWidth();
//        double boundHeight = bounds.getHeight();
//        // determine if shape is leaving the bounds
//        return x <= 0 && velocity.getX() <= 0 || x >= boundWidth && velocity.getX() >= 0 ||
//                y <= 0 && velocity.getY() <= 0 || y >= boundHeight && velocity.getY() >= 0;
//    }
//    // add method for calculating angled vectors based on angle and magnitude
//    public static Point2D angledVector(double angle, double magnitude) {
//        return new Point2D(Math.cos(angle), Math.sin(angle)).multiply(magnitude);
//    }
//    // add method for generating random values between min and max
//    public static double rand(double min, double max) {
//        return Math.random() * (max - min) + min;
//    }
//}
//
//// ship implements from our abstract SpaceObject
//class Ship extends SpaceObject {
//    private final Polygon polygon; // encapsulate as private
//    private final double thrust = 150; // make final as it's a constant value
//
//    // constructor of our spaceship
//    public Ship(Group parent, Point2D position) { // initial position passed in
//        super(parent, position, Point2D.ZERO, 0, 0);
//        polygon = new Polygon(
//                0.7, 0, -0.7, -0.4, -0.7, 0.4
//        );
//        transform.getChildren().add(polygon);
//        polygon.setStroke(Color.rgb(226, 76, 181));
//        polygon.setStrokeWidth(0.1);
//        polygon.getTransforms().add(new Scale(30, 30));
//        update(0, 0, 0);
//    }
//
//    // based on the input, we need to have some rotation for our ship
//    // also have some thrust
//    public void update(double delta, double omega, double throttle) {
//        if (throttle != 0
//
