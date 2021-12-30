
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Description:
/*
 * This class is responsible for the train going dewn the screen killing the player. In the main method, it is called train 
 */
public class Train {
	
	//Global Variables/Fields
	private double x,y;
	private double width,height;
	private Image imgTrain;
	private ImageView iview;
	
	//Constructors (set defaults)
	public Train() {
		//Initialize the global variables
		x = 0;
		y = 0;
		
		imgTrain = new Image("file:images/train.png");
		iview = new ImageView(imgTrain);
		iview.setX(x);
		iview.setY(y);
		
		width = imgTrain.getWidth();
		height = imgTrain.getHeight();
	}
	
	public Train(double xPos, double yPos) {
		//Initizlie the global vairables but set the x and y to the passed values
		x = xPos;
		y = yPos;
		
		imgTrain = new Image("file:images/train.png");
		iview = new ImageView(imgTrain);
		iview.setX(x);
		iview.setY(y);
		
		width = imgTrain.getWidth();
		height = imgTrain.getHeight();
	}
	
	//Methods
	
	// Moves the iview
	public void move() {
		// Increases the y pos and sets the y pos
		y += 10;
		iview.setY(y);
	}
	
	public void setX(double xPos) {
		// Sets the x pos
		x = xPos;
		iview.setX(x);
	}
	
	public void setY(double yPos) {
		// Sets the y pos
		y = yPos;
		iview.setY(y);
	}
	
	public void setWidth(double w) {
		// Sets the widht
		width = w;
	}
	
	public void setHeight(double h) {
		// Sets the height
		height = h;
	}
	
	public void setImage(Image img) {
		// Sets the image
		imgTrain = img;
	}
	
	public double getX() {
		// Retrieves the x. Return x
		return x;
	}
	
	public double getY() {
		// Retrieves the y. Return y
		return y;
	}
	
	public double getWidth() {
		// Retrieves the width. Returns width
		return width;
	}
	
	public double getHeight() {
		// Retirives the height. Returns height
		return height;
	}
	
	public Image getImage() {
		// Retrieves the image. Returns imgTrain
		return imgTrain;
	}
	
	public ImageView getNode() {
		// Retrives the Image View. Sets the image of the iview and the x and the y. Return iview.
		iview.setImage(imgTrain);
		iview.setX(x);
		iview.setY(y);
		return iview;
	}
	
	
}
