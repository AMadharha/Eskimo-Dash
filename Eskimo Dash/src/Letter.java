
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Description:
/*
* This class is responsible for the letter going down the screen for the player to collect. In the main method, it is called letter.
*/
public class Letter {
	// Global Variables/Fields
	private double x, y;
	private double width,height;
	private Image[] imgLetter;
	private ImageView iview;
	private int index;
	private char[] characters;
	public ArrayList<Character> lettersFound;

	// Constructors (set defaults)
	public Letter() {
		// Initalize the global variables
		x = 0;
		y = 0;
		index = 0;

		characters = new char[]{'e','s','k','i','m','o'};
		imgLetter = new Image[7];
		for(int i = 0; i < imgLetter.length; i++) {
			if(i == 6) {
				imgLetter[6] = new Image("file:images/blank.png");
			}
			else {
				imgLetter[i] = new Image("file:images/" + characters[i] + ".png");
			}
		}
		lettersFound = new ArrayList<Character>();

		width = imgLetter[index].getWidth();
		height = imgLetter[index].getHeight();

		iview = new ImageView(imgLetter[index]);
		iview.setX(x);
		iview.setY(y);
	}

	// Methods
	
	// Moves the iview
	public void move() {
		// Increases the y pos and sets the y pos.
		y += 10;
		iview.setY(y);
	}

	// Add the letter to the lettersFound arraylist from the characters array at index
	public void add() {
		lettersFound.add(characters[index]);
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
		// Sets the image array
		imgLetter[index] = img;
	}

	public void setIndex(int i) {
		// Sets the index
		index = i;
	}

	public double getX() {
		// Retrieves the x. Returns x
		return x;
	}

	public double getY() {
		// Retrieves the y. Returns y.
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
		// Retrieves the Image. Returns imgLetter at index
		return imgLetter[index];
	}

	public ImageView getNode() {
		// Retrieves the Image View. Sets the image of the iview and the x and the y. Returns iview.
		iview.setImage(imgLetter[index]);
		iview.setX(x);
		iview.setY(y);
		return iview;
	}


}
