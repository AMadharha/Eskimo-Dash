
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Description:
/*
* This class is responsible for the baord going down the screen killing the player. In the main method, it is called board.
*/
public class Board {

	// Global Variables/Fields
	private double x,y;
	private double width, height;
	private Image imgBoard;
	private ImageView iview;
	
	//Constructors (set Defaults)
	public Board() {
		// Initialize the global variables
		x = 0;
		y = 0;
		
		imgBoard = new Image("file:images/board.png");
		iview = new ImageView(imgBoard);
		iview.setX(x);
		iview.setY(y);
		
		width = imgBoard.getWidth();
		height = imgBoard.getHeight();
		
	}
	
	public Board(double xPos, double yPos) {
		// Initialize the global variables but set the x and y to the passed values
		x = xPos;
		y = yPos;
		
		imgBoard = new Image("file:images/board.png");
		iview = new ImageView(imgBoard);
		iview.setX(x);
		iview.setY(y);
		
		width = imgBoard.getWidth();
		height = imgBoard.getHeight();
	}
	
	//Methods
	
	// Moves the iview
	public void move() {
		// Increases the y pos and sets the y pos.
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
		// sets the height
		height = h;
	}
	
	public void setImage(Image img) {
		// sets the image
		imgBoard = img;
	}
	
	public double getX() {
		// Retrieves the x. Return x
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
		// Retrieves the image. Return imgBoard
		return imgBoard;
	}
	
	public ImageView getNode() {
		// Retrives the Image View. Sets the image of the iview and the x and the y. Return iview.
		iview.setImage(imgBoard);
		iview.setX(x);
		iview.setY(y);
		return iview;
	}
}
