
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Description:
/*
 * This class is responsible for the object/player the user controls. In the main method, it is called player.
 */
public class Eskimo {

	// Declare global Variables/Fields
	private double x, y;
	private double width, height;
	private Image[] imgRun;
	private ImageView iview;
	private int index;
	public boolean isDead;

	//Constructors (set Defaults)
	public Eskimo() {
		
		// Initalize all the global variables
		x = 0;
		y = 0;
		index = 0;

		imgRun = new Image[2];

		imgRun[0] = new Image("file:images/charOne-run1.png");
		imgRun[1] = new Image("file:images/charOne-run2.png");

		width = imgRun[index].getWidth();
		height = imgRun[index].getHeight();
		iview = new ImageView(imgRun[index]);
		iview.setX(x);
		iview.setY(y);
		isDead = false;
	}

	public Eskimo(int character) {
		x = 0;
		y = 0;
		index = 0;

		imgRun = new Image[2];

		// Unlike the previous constructor this constructor initalizes the imgRun array depending on the in passed 
		if(character == 1) {
			imgRun[0] = new Image("file:images/charOne-run1.png");
			imgRun[1] = new Image("file:images/charOne-run2.png");
		}
		else if(character == 2) {
			imgRun[0] = new Image("file:images/charTwo-run1.png");
			imgRun[1] = new Image("file:images/charTwo-run2.png");
		}
		else {
			imgRun[0] = new Image("file:images/charOne-run1.png");
			imgRun[1] = new Image("file:images/charOne-run2.png");
		}

		width = imgRun[index].getWidth();
		height = imgRun[index].getHeight();
		iview = new ImageView(imgRun[index]);
		iview.setX(x);
		iview.setY(y);
		isDead = false;
	}

	//Methods
	
	// Animates the player by switching images
	public void move() {
		
		// IF the index vairbale reaches the end of the array reset it to -
		if(index == imgRun.length) {
			index = 0;
		}
		// Set the image and increase the index by one
		iview.setImage(imgRun[index]);
		index++;
	}

	// Kills the player
	public void killEskimo() {
		
		// Sets the isDead variable to true and switched the image and sets the imageview.
		isDead = true;
		imgRun[0] = new Image("file:images\\blood-splatter.png");
		iview.setImage(imgRun[0]);
	}

	public void setX(double xPos) {
		// Sets the x to the passed value
		x = xPos;
	}

	public void setY(double yPos) {
		// Sets the y to the passed value
		y = yPos;
	}

	public void setWidth(double w) {
		// Sets the width to the passed value
		width = w;
	}

	public void setHeight(double h) {
		// Sets the height to the passed value
		height = h;
	}

	public void setImages(Image[] img) {
		// Sets the imgRun array to the passed array
		imgRun = img;
	}

	public void setIndex(int i) {
		// Sets the index to the passed value
		index = i;
	}

	public double getX() {
		// Used to retrieve the x postition. Returns x
		return x;
	}

	public double getY() {
		// Used to retrieve the y position. Returns y
		return y;
	}

	public double getWidth() {
		// Used to retriece the width. Returns width
		return width;
	}

	public double getHeight() {
		// Used to retrieve the height. Return height
		return height;
	}

	public Image getImage() {
		// Used to retrieve the image. Returns the image at the index.
		return imgRun[index];
	}

	public ImageView getNode() {
		
		// If the player is alive the following code will execute
		if(isDead == false ) {
			
			// Reset the index if it reaches the imgRun length
			if(index == imgRun.length) {
				index = 0;
			}
			
			// Set the the iview image and x and y.
			iview.setImage(imgRun[index]);
			iview.setX(x);
			iview.setY(y);
			
			//Return iview
			return iview;
		// If the player is dead then set the iview to the blood splatter. return iview
		} else {
			imgRun[0] = new Image("file:images/blood-splatter.png");
			iview.setImage(imgRun[0]);
			return iview;
		}
	}
}
