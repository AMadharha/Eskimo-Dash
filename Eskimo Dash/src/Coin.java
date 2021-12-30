
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Description:
/*
* This class is responsible for the coin going down the screen for the player to gain score. In the main method, it is called coin.
*/
public class Coin {
	
	//Global Variables/Fields
	private double x,y;
	private double width, height;
	private Image imgCoin;
	private ImageView iview;
	
	//Constructors (set Defaults)
	public Coin() {
		// Initialize the global variables
		x = 0;
		y = 0;
		
		imgCoin = new Image("file:images/coin.png");
		iview = new ImageView(imgCoin);
		iview.setX(x);
		iview.setY(y);
		
		width = imgCoin.getWidth();
		height = imgCoin.getHeight();
	}
	
	public Coin(double xPos, double yPos) {
		// Initialize the global variables but set the x and y to the passed values 
		x = xPos;
		y = yPos;
		
		imgCoin = new Image("file:images/coin.png");
		iview = new ImageView(imgCoin);
		iview.setX(x);
		iview.setY(y);
		
		width = imgCoin.getWidth();
		height = imgCoin.getHeight();
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
		// Sets the width
		width = w;
	}
	
	public void setHeight(double h) {
		// Sets the height
		height = h;
	}
	
	public void setImage(Image img) {
		// Sets the image
		imgCoin = img;
	}
	
	public double getX() {
		// Retrieves the x. Returns x
		return x;
	}
	
	public double getY() {
		// Retrieves the y. Returns y
		return y;
	}
	
	public double getWidth() {
		// Retrieves the width. Returns width
		return width;
	}
	
	public double getHeight() {
		// Retrieves the height. Returns height
		return height;
	}
	
	public Image getImage() {
		// Retrieves the Image. Returns imgCoin
		return imgCoin;
	}
	
	public ImageView getNode() {
		// Retrives the Image View. Sets the image of the iview and the x and the y. Return iview.
		iview.setImage(imgCoin);
		iview.setX(x);
		iview.setY(y);
		return iview;
	}
}
