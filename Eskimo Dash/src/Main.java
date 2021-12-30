import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/*	ICS4U1 CPT: Eskimo Dash
 * 	Ankush
 * 	Mr.Conway
 * 	
 * 	Description:
 * 	The game will start with displaying the title screen. Here you can check the high score (high score is displayed),
 *  controls (controls are displayed), and choose to play. If you click the play button it will switch to a different 
 *  window asking for your name. This name is used to idenitfy your score, you cannot have numbers in your name and you
 *  cannot have a blank name. Once the name is correctly inputted, a new window will pop up asking you to select a character.
 *  Once a character is selected then the game will start. You must move side to side dodging the incoming train and barrier.
 *  You must also collect the coins to gain score and letters to gain score. If you die all your information will be displayed
 *  and you can play again or exit.
 *  
 *  Details:
 * 	The JavaFX compnents I used were buttons, textfields, and layout panes. Buttons are used in the title screen and at the 
 *  end screen, textfields are used to recieve the name, and layout panes are used to organize the nodes (HBox, VBox, GridPane).
 *  I used 1D arrays for the score to be sorted and I used arrays to store images in the Eskimo class and letters/images in the letter 
 *  class. I used a selection sort to find the high score, I sorted an array of scores and took the last value. I used object-oriented 
 *  programming by making the classes to control the objects and collisions. I used animation to swap player images to create a running 
 *  animation and I used animation to move the objects down the screen to emmulate the player running up. I used the file class to 
 *  store the name and scores of the player. The file keeps all the history of plays and scores. ArrayLists are used to add the names and scores
 *  from the file while reading. This is so you can we find the highscore and the holder of that score. I use various classes to control collisions
 *  and score tracking.
 */

public class Main extends Application {

	// Declare global variables
	private Scene titleScreen, enterName, characterSelect, level, endScreen;
	private int character, cols, score, index;
	private Timeline t2;
	private Letter letter;
	private boolean word;
	private Label lblEskimoCollect;
	private File scoresFile;
	private String holder;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {

		// Calls the methods to create the scenes
		createTitleScreen(primaryStage);
		createEnterName(primaryStage);
		createCharacterSelect(primaryStage);
	}

	private void createTitleScreen(Stage primaryStage) {
		// Inializes the File varaibles scoresFile 
		scoresFile = new File("scores.txt");

		// Create an image and image view that is then added to the pane as a background
		Image imgBack = new Image("file:images/eskimodash-background.png");
		ImageView ivBack = new ImageView(imgBack);
		Pane root = new Pane();
		root.getChildren().add(ivBack);

		//Inialize the titleScreen scene
		titleScreen = new Scene(root,imgBack.getWidth(),imgBack.getHeight());

		// Creates the play button and sets its properties. If clicked, it will set the scene to the enterName scene
		Button btnPlay = new Button();
		btnPlay.setPrefSize(300,80);
		btnPlay.setText("PLAY");
		btnPlay.setFont(Font.font("Britannic Bold", FontWeight.BOLD, FontPosture.REGULAR,40));
		btnPlay.setStyle("-fx-background-color: lightblue");
		btnPlay.setOnMouseEntered(e -> btnPlay.setStyle("-fx-background-color: white"));
		btnPlay.setOnMouseExited(e -> btnPlay.setStyle("-fx-background-color: lightblue"));
		btnPlay.setOnAction(e -> primaryStage.setScene(enterName));

		// Creates the controls button and sets its properties. If clicked, it will display the controls in an alert
		Button btnControls = new Button();
		btnControls.setPrefSize(300,80);
		btnControls.setText("CONTROLS");
		btnControls.setFont(Font.font("Britannic Bold", FontWeight.BOLD, FontPosture.REGULAR,40));
		btnControls.setStyle("-fx-background-color: lightblue");
		btnControls.setOnMouseEntered(e -> btnControls.setStyle("-fx-background-color: white"));		
		btnControls.setOnMouseExited(e -> btnControls.setStyle("-fx-background-color: lightblue"));
		btnControls.setOnAction(e -> {
			Alert outputControls = new Alert(AlertType.INFORMATION);
			outputControls.setHeaderText(null);
			outputControls.setContentText("A - MOVE LEFT\nD - MOVE RIGHT");
			outputControls.setTitle("Controls");
			outputControls.getButtonTypes().clear();
			outputControls.getButtonTypes().add(ButtonType.OK);
			outputControls.showAndWait();
		});

		// Inialize and declare the highscoreNum variables by calling the findHighScore method
		int highscoreNum = findHighScore();

		//Creates the high score button and sets its properties. If clicked, then it will display the highscore and the holder in an alert
		Button btnHighScore = new Button();
		btnHighScore.setPrefSize(300,80);
		btnHighScore.setText("HIGH SCORE");
		btnHighScore.setFont(Font.font("Britannic Bold", FontWeight.BOLD, FontPosture.REGULAR,40));
		btnHighScore.setStyle("-fx-background-color: lightblue");
		btnHighScore.setOnMouseEntered(e -> btnHighScore.setStyle("-fx-background-color: white"));
		btnHighScore.setOnMouseExited(e -> btnHighScore.setStyle("-fx-background-color: lightblue"));
		btnHighScore.setOnAction(e -> {
			Alert highscore = new Alert(AlertType.INFORMATION);
			highscore.setHeaderText(null);
			highscore.setContentText("HIGHSCORE: " + highscoreNum + "\nHELD BY: " + holder);
			highscore.setTitle("High Score");
			highscore.getButtonTypes().clear();
			highscore.getButtonTypes().add(ButtonType.OK);
			highscore.showAndWait();
		});

		// Creates a HBox and sets its properties. Adds the three buttons to the HBox
		HBox hButtons = new HBox();
		hButtons.setSpacing(40);
		hButtons.getChildren().addAll(btnPlay, btnControls, btnHighScore);
		hButtons.setLayoutX(imgBack.getWidth()/2 - 980/2);
		hButtons.setLayoutY(imgBack.getHeight() - 110);

		// Adds the HBox to the pane
		root.getChildren().add(hButtons);

		// Sets the scene to titleScreen. sets the primaryStage properties
		primaryStage.setScene(titleScreen);

		// An alert will be displayed asking for conifrmation if the user clicks the close button on the window
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent e) {
				Alert confirm = new Alert(AlertType.CONFIRMATION);
				confirm.setContentText("Are you sure you want to exit?");
				confirm.setTitle("Exit");
				confirm.setHeaderText(null);
				confirm.getButtonTypes().clear();
				confirm.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

				Optional<ButtonType> result = confirm.showAndWait();

				if(result.get() == ButtonType.NO) {
					e.consume();
				}
			}
		});
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.setTitle("Eskimo Dash");
		primaryStage.show();
	}

	private void createEnterName(Stage primaryStage) {		
		//Declare and Inialize the vEnterName VBox
		VBox vEnterName = new VBox();

		// Declare and Inialize the lblEnterName label and sets its properties
		Label lblEnterName = new Label();
		lblEnterName.setText("Enter Name:");
		lblEnterName.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		// Delcares and Inialize the lblEnter label and sets its properties
		Label lblEnter = new Label();
		lblEnter.setText("");
		lblEnter.setPrefSize(100,30);
		lblEnter.setFont(Font.font("Arial", FontWeight.BOLD, 15));

		// Declare and Initalize the name textfield. 
		TextField name = new TextField();
		name.setMinWidth(100);
		name.setMinHeight(40);
		name.setAlignment(Pos.CENTER);

		// Listens for when the user types in the field. The lblEnter label text will change if they type and if it is blank.
		name.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				lblEnter.setText("Click ENTER");
				if(name.getText().isEmpty()) {
					lblEnter.setText("");
				}
			}
		});

		// Sets the properties of the VBox vEnterName
		vEnterName.setSpacing(15);
		vEnterName.getChildren().addAll(lblEnterName, name, lblEnter);
		vEnterName.setAlignment(Pos.CENTER);
		vEnterName.setPadding(new Insets(10,10,10,10));

		// Initalize the enterName scene.
		enterName = new Scene(vEnterName,300,200);
		word = true;

		// If keys are pressed on the enterName scene then the following code will execute
		enterName.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (e.getEventType() == KeyEvent.KEY_PRESSED) {

					// If the key is ENTER then the following code will execute
					if(e.getCode() == KeyCode.ENTER) {

						// If the name textfield is empty the following code will execute
						if(name.getText().isEmpty()) {

							// Declare and Initalize the incorrectInput alert and sets its properties. Sets the word variable to false
							Alert incorrectInput = new Alert(AlertType.ERROR);
							incorrectInput.setHeaderText(null);
							incorrectInput.setContentText("Please enter your name!");
							incorrectInput.getButtonTypes().clear();
							incorrectInput.getButtonTypes().add(ButtonType.OK);
							incorrectInput.setTitle("Error");
							incorrectInput.showAndWait();
							word = false;

							// If there is text in the field the following code will execute
						} else if(name.getText().length() > 0) {

							// Loop through the word in the name textfield
							for(int i = 0; i < name.getText().length(); i++) {

								// If the character if a digit the following code will execute
								if(Character.isDigit(name.getText().charAt(i))) {

									// Declare and Initalize the incorrectInput alert and sets its properties. Sets the word variables to false and break out of the loop.
									Alert incorrectInput = new Alert(AlertType.ERROR);
									incorrectInput.setHeaderText(null);
									incorrectInput.setContentText("Please enter only characters!");
									incorrectInput.getButtonTypes().clear();
									incorrectInput.getButtonTypes().add(ButtonType.OK);
									incorrectInput.setTitle("Error");
									incorrectInput.showAndWait();
									word = false;
									break;

									// If none of the characters are digits then the word variable will be set to true
								} else {
									word = true;
								}
							}

							// If the word varaible is true the following code will execute
							if(word == true) {

								// Checks if the file exists, if it does not the following code will execute
								if(scoresFile.exists() == false) {

									// Declare and Initalize the doesNotExist alert and sets its properties
									Alert doesNotExist = new Alert(AlertType.ERROR);
									doesNotExist.setHeaderText(null);
									doesNotExist.setContentText("Issue saving information!");
									doesNotExist.getButtonTypes().clear();
									doesNotExist.getButtonTypes().add(ButtonType.OK);
									doesNotExist.showAndWait();
								}

								try {
									// Declare and Inialize the FileWriter and the BufferedWriter
									FileWriter out = new FileWriter(scoresFile, true);
									BufferedWriter writeFile = new BufferedWriter(out);

									// Writes the name to the file and moves to a new line
									writeFile.write(name.getText());
									writeFile.newLine();

									// Closes the FileWriter and the BufferedWriter
									writeFile.close();
									out.close();

								} catch (IOException e1) {
									e1.printStackTrace();
								}

								// Sets the scene to characterSelect
								primaryStage.setScene(characterSelect);
							}
						} 
					}
				}
			}
		});
	}

	private void createCharacterSelect(Stage primaryStage) {

		// Declare and Initialize the image, image view, and pane. Adds the imageview to the pane.
		Image imgBack = new Image("file:images/character-select-background.png");
		ImageView ivBack = new ImageView(imgBack);
		Pane root = new Pane();
		root.getChildren().add(ivBack);

		// Initialize the characterSelect scene
		characterSelect = new Scene(root,imgBack.getWidth(),imgBack.getHeight());

		// Declare and Initialize the character one image and imageview.
		Image imgCharOne = new Image("file:images/characterOne.png");
		ImageView ivCharOne = new ImageView(imgCharOne);

		//Declare and Inialize the btnCharOne and sets the graphic to the ivCharOne and other properties. If it is clicked the character vairable is set to one and 
		//createLevel is called. The scene is set to level.
		Button btnCharOne = new Button();
		btnCharOne.setStyle("-fx-background-color: transparent");
		btnCharOne.setGraphic(ivCharOne);
		btnCharOne.setStyle("-fx-background-color: transparent");
		btnCharOne.setOnMouseEntered(e -> btnCharOne.setStyle("-fx-background-color: rgb(154,255,252,0.6)"));
		btnCharOne.setOnMouseExited(e -> btnCharOne.setStyle("-fx-background-color: transparent"));
		btnCharOne.setOnAction(e -> {
			character = 1;
			createLevel(primaryStage);
			primaryStage.setScene(level);
		});

		// Declare and Initialize the character one image and imageview.
		Image imgCharTwo = new Image("file:images/characterTwo.png");
		ImageView ivCharTwo = new ImageView(imgCharTwo);

		//Declare and Inialize the btnCharTwo and sets the graphic to the ivCharTwo and other properties. If it is clicked the character vairable is set to one and 
		//createLevel is called. The scene is set to level.
		Button btnCharTwo = new Button();
		btnCharTwo.setGraphic(ivCharTwo);
		btnCharTwo.setStyle("-fx-background-color: transparent");
		btnCharTwo.setOnMouseEntered(e -> btnCharTwo.setStyle("-fx-background-color: rgb(154,255,252,0.6)"));
		btnCharTwo.setOnMouseExited(e -> btnCharTwo.setStyle("-fx-background-color: transparent"));
		btnCharTwo.setOnAction(e -> {
			character = 2;
			createLevel(primaryStage);
			primaryStage.setScene(level);
		});

		//Declare and Initialize the lblChoose label and sets its properties
		Label lblChoose = new Label();
		lblChoose.setPrefSize(250,30);
		lblChoose.setText("Select a Character");
		lblChoose.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR,25));
		lblChoose.setAlignment(Pos.CENTER);
		lblChoose.setTextFill(Color.BLACK);
		lblChoose.setLayoutX(imgBack.getWidth()/2 - lblChoose.getPrefWidth()/2);
		lblChoose.setLayoutY(10);

		//Decalre and Initialize the HBox and sets its properties. Adds the two button with the characters
		HBox hCharacters = new HBox();
		hCharacters.setSpacing(40);
		hCharacters.setAlignment(Pos.CENTER);
		hCharacters.getChildren().addAll(btnCharOne, btnCharTwo);

		// Adds the Hbox and label to the pane
		root.getChildren().addAll(hCharacters, lblChoose);
	}

	private void createLevel(Stage primaryStage) {

		// Declare and Initalize the image and imageview for the background. Adds it to the pane.
		Image imgBack = new Image("file:images/level-background.png");
		ImageView ivBack = new ImageView(imgBack);
		Pane root = new Pane();
		root.getChildren().add(ivBack);

		//Initalize the level scene
		level = new Scene(root, imgBack.getWidth(), imgBack.getHeight());

		//Declare and Initialize the lblscore label and sets its properties
		Label lblScore = new Label();
		lblScore.setText("SCORE: " + score);
		lblScore.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
		lblScore.setLayoutX(20);
		lblScore.setLayoutY(20);

		//Declare and Initialize the player object and sets the x and y to the middle of the screen
		Eskimo player = new Eskimo(character);
		player.setX(imgBack.getWidth()/2 - player.getWidth()/2);
		player.setY(imgBack.getHeight()/2 - player.getHeight()/2);

		//If a key is pressed on the level scene the following code will execute
		level.setOnKeyPressed(e -> {

			//If the isDead variable is false (in Eskimo class) then the following code will execute
			if(player.isDead == false) {

				// Checks if a key is pressed
				if(e.getEventType() == KeyEvent.KEY_PRESSED) {

					//If the key A is pressed then the following code will execute
					if(e.getCode() == KeyCode.A) {

						// The x position will move left 100 pixels
						player.setX(player.getX() - 100);

						// If the players x position is less than or equal to 300 set the x pos to 320
						if(player.getX() <= 300) {
							player.setX(320);
						}
					}

					//If the key D is pressed the following code will execute:
					if(e.getCode() == KeyCode.D) {

						// The x pos will move right 100 pixels
						player.setX(player.getX() + 100);

						// If the the player (x pos + width) is greater than or equal to 600 then set the x pos to 520
						if(player.getX() + player.getWidth() >= 600) {
							player.setX(520);
						}
					}
				}
				player.getNode();
			}
		});

		// Declare and initizlize a Keyframe object.
		KeyFrame kf = new KeyFrame(Duration.millis(200), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				// If the player is not dead call the move method
				if(player.isDead == false) {
					player.move();
				}
			}
		});

		// Declare and initialize a Timeline object and sets cycle count. Plays the timeline.
		Timeline t = new Timeline(kf);
		t.setCycleCount(Timeline.INDEFINITE);
		t.play();

		// Declare and initialize a Random object and initialize index variable
		Random rnd = new Random();
		index = 0;

		// Declare and initialize a Board, Train, Coin object. Initialize the letter object and lblEskimoCollect label
		Board board = new Board();
		Train train = new Train();
		Coin coin = new Coin();
		letter = new Letter();	
		lblEskimoCollect = new Label();

		// Sets the y pos of the board, train, coin, and letter
		board.setY(-100);
		train.setY(-400);
		coin.setY(-200);
		letter.setY(-500);

		// Randomizes the column of the board
		cols = rnd.nextInt(3) + 1;
		if(cols == 1) {
			board.setX(300);
		} else if(cols == 2) {
			board.setX(400);
		} else {
			board.setX(500);
		}

		// Randomizes the colunm of the train
		cols = rnd.nextInt(3) + 1;
		if(cols == 1) {
			train.setX(300);
		} else if(cols == 2) {
			train.setX(400);
		} else {
			train.setX(500);
		}

		// Randomizes the colunm of the coin
		cols = rnd.nextInt(3) + 1;
		if(cols == 1) {
			coin.setX(310);
		} else if(cols == 2) {
			coin.setX(410);
		} else {
			coin.setX(510);
		}

		// Randomizes the colunm of the letter
		cols = rnd.nextInt(3) + 1;
		if(cols == 1) {
			letter.setX(310);
		} else if(cols == 2) {
			letter.setX(410);
		} else {
			letter.setX(510);
		}

		// Declare and Initialize a KeyFrame object.
		KeyFrame kf2 = new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				// The following code will execute if the player is alive
				if(player.isDead == false) {

					// Call the move method for the four objects
					board.move();
					train.move();
					coin.move();
					letter.move();

					// Call the getNode method for the five objects
					player.getNode();
					train.getNode();
					board.getNode();
					coin.getNode();
					letter.getNode();

					//Randomizes the column of the board if it goes past the bottom of the screen
					if(board.getY() >= 650) {
						cols = rnd.nextInt(3) + 1;
						if(cols == 1) {
							board.setX(300);
						} else if(cols == 2) {
							board.setX(400);
						} else {
							board.setX(500);
						}
						board.setY(-200);
					}

					//Randomizes the column of the train if it goes past the bottom of the screen
					if(train.getY() >= 650) {
						cols = rnd.nextInt(3) + 1;
						if(cols == 1) {
							train.setX(300);
						} else if(cols == 2) {
							train.setX(400);
						} else {
							train.setX(500);
						}
						train.setY(-300);
					}

					//randomizes the column of the coin if it goes past the bottom of the screen
					if(coin.getY() >= 650) {
						cols = rnd.nextInt(3) + 1;
						if(cols == 1) {
							coin.setX(310);
						} else if(cols == 2) {
							coin.setX(410);
						} else {
							coin.setX(510);
						}
						coin.setY(-200);
					}

					// Randomizes the column of the coin if it goes past the bottom of the screen
					if(letter.getY() >= 650) {
						cols = rnd.nextInt(3) + 1;
						if(cols == 1) {
							letter.setX(310);
						} else if(cols == 2) {
							letter.setX(410);
						} else {
							letter.setX(510);
						}
						letter.setY(-3000);
					}

					// If the player collides with the board or the train the following code will execute
					if(player.getNode().getBoundsInParent().intersects(board.getNode().getBoundsInParent()) ||
							player.getNode().getBoundsInParent().intersects(train.getNode().getBoundsInParent())) {

						// Call the killEskimo method and the getNode method
						player.killEskimo();
						player.getNode();
					}

					// If the coin collides with the train or the board the following code will execute
					if(coin.getNode().getBoundsInParent().intersects(train.getNode().getBoundsInParent()) || coin.getNode().getBoundsInParent().intersects(board.getNode().getBoundsInParent())) {

						// Randomizes the column of the coin and sets its y pos to -200
						cols = rnd.nextInt(3) + 1;
						if(cols == 1) {
							coin.setX(310);
						} else if(cols == 2) {
							coin.setX(410);
						} else {
							coin.setX(510);
						}
						coin.setY(-200);
					}

					// If the player collides with the letter the following code will execute
					if(player.getNode().getBoundsInParent().intersects(letter.getNode().getBoundsInParent())) {

						// Randomizes the column of the letter and sets its y pos to -3000
						cols = rnd.nextInt(3) + 1;
						if(cols == 1) {
							letter.setX(310);
						} else if(cols == 2) {
							letter.setX(410);
						} else {
							letter.setX(510);
						}
						letter.setY(-3000);

						// if the index vairable is below 6 the following code will execute
						if(index < 6) {

							//Call the add method. Set the text of lblEskimoCollect. Increment index by one and call the setIndex method passing the index variable
							letter.add();
							lblEskimoCollect.setText(lblEskimoCollect.getText() + Character.toUpperCase(letter.lettersFound.get(index)) + " ");
							index++;
							letter.setIndex(index);
						}

					}

					// if the letter colliced with the board, train, or coin then the following code will execute
					if(letter.getNode().getBoundsInParent().intersects(board.getNode().getBoundsInParent()) ||
							letter.getNode().getBoundsInParent().intersects(train.getNode().getBoundsInParent())||
							letter.getNode().getBoundsInParent().intersects(coin.getNode().getBoundsInParent())) {

						// Randomizes the column of the coin and sets its y pos to -1000
						cols = rnd.nextInt(3) + 1;
						if(cols == 1) {
							letter.setX(310);
						} else if(cols == 2) {
							letter.setX(410);
						} else {
							letter.setX(510);
						}
						letter.setY(-1000);
					}

					// If the board collides with the train the following code will execute
					if(board.getNode().getBoundsInParent().intersects(train.getNode().getBoundsInParent())) {

						// Randomizes the column of the board and sets the y pos to -200
						cols = rnd.nextInt(3) + 1;
						if(cols == 1) {
							board.setX(300);
						} else if(cols == 2) {
							board.setX(400);
						} else {
							board.setX(500);
						}
						board.setY(-200);
					}

					// if the player collides with the coin the following code will execute
					if(player.getNode().getBoundsInParent().intersects(coin.getNode().getBoundsInParent())) {

						// Increase the score by one
						score++;

						// If the score is 5, 20, 30, or 40 then increase the speed of the t2 and t timelines
						if(score == 5 || score == 20 || score == 30 || score == 40) {
							t2.setRate(t2.getRate() + 1);
							t.setRate(t.getRate() + 0.4);
						}

						// Set the text of the lblscore label
						lblScore.setText("SCORE: " + score);

						// Randomize the column of the coin and set the y pos to -200
						cols = rnd.nextInt(3) + 1;
						if(cols == 1) {
							coin.setX(310);
						} else if(cols == 2) {
							coin.setX(410);
						} else {
							coin.setX(510);
						}
						coin.setY(-200);	
					}
				}

				// if the player is dead this code will execute
				else {

					// Stop the t2 and t timeline (stop the animation)
					t2.stop();
					t.stop();

					// Add a two second delay so the user can see the blood splatter
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					// Call the createEndScreen method and set the scene to endScreen
					createEndScreen(primaryStage);
					primaryStage.setScene(endScreen);
				}
			}
		});
		// Add all the nodes from the classes to the pane
		root.getChildren().addAll(board.getNode(), train.getNode(), coin.getNode(), player.getNode(), letter.getNode(), lblScore);

		// Initialize the t2 timeline object and set its properties
		t2 = new Timeline(kf2);
		t2.setCycleCount(Timeline.INDEFINITE);
		t2.play();
	}

	public void createEndScreen(Stage primaryStage) {
		// Declare and initialize a GridPane object. Initialize the endScreen scene.
		GridPane root = new GridPane();
		endScreen = new Scene(root);

		// Declare and Initialize a Image and ImageView object
		Image imgTitle = new Image("file:images/youdied.png");
		ImageView iviewTitle = new ImageView(imgTitle);

		// Delcare and Initalize the lblTitle label and set is properties
		Label lblTitle = new Label();
		lblTitle.setGraphic(iviewTitle);
		lblTitle.setPadding(new Insets(10,0,10,0));

		// Add the label to the gridpane
		GridPane.setConstraints(lblTitle,0,0,3,1, HPos.CENTER, VPos.CENTER);
		root.add(lblTitle,0,0,3,1);

		// Declare and Initialize the lblyourscore and set its properties
		Label lblyourScore = new Label("YOUR SCORE");
		lblyourScore.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 25));

		// set the properties of the lblEskimoCollect label
		lblEskimoCollect.setTextFill(Color.GREEN);
		lblEskimoCollect.setFont(Font.font("System", FontWeight.BOLD, 20));

		// Declare and Initialize the tenPoints label and set its properties
		Label fivePoints = new Label("Each Letter is 5 Points!");
		fivePoints.setFont(Font.font("Arial", FontPosture.ITALIC, 15));

		// Calculate the score by multiplying the amount of letters they found by 5 and adding it to the score
		score += letter.lettersFound.size() * 5;

		// If the file does not exist the following code will execute
		if(scoresFile.exists() == false) {

			// Declare and Initialize the doesNotExist alert and sets its properties
			Alert doesNotExist = new Alert(AlertType.ERROR);
			doesNotExist.setHeaderText(null);
			doesNotExist.setContentText("Issue saving information!");
			doesNotExist.getButtonTypes().clear();
			doesNotExist.getButtonTypes().add(ButtonType.OK);
			doesNotExist.showAndWait();
		}

		try {
			//Delcare and Initialize the FileWriter and BufferedWriter 
			FileWriter out = new FileWriter(scoresFile, true);
			BufferedWriter writeFile = new BufferedWriter(out);

			// write the score to the file and move to a new linc
			writeFile.write(String.valueOf(score));
			writeFile.newLine();

			// Close the FileWriter writeFile and the BufferedWriter out
			writeFile.close();
			out.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}


		//Declare and Initialize the lblScore label and set its properties
		Label lblScore = new Label(String.valueOf(score));
		lblScore.setFont(Font.font("Britannic Bold", 30));

		// Declare and Initialize the VBox scoreInfo and sets its properties. Adds the yourscore label, eskimoCollect label, fivePoints label, and the score label.
		VBox scoreInfo = new VBox(15);
		scoreInfo.setAlignment(Pos.CENTER);
		scoreInfo.getChildren().addAll(lblyourScore, lblEskimoCollect, fivePoints, lblScore);

		//Declare and Initialize a TitledPane and sets its properties. Sets the content tot he vBox scoreInfo
		TitledPane scoreTitle = new TitledPane();
		scoreTitle.setText("SCORE:");
		scoreTitle.setCollapsible(false);
		scoreTitle.setContent(scoreInfo);
		// Adds the TitledPane to the GridPane
		root.add(scoreTitle,0,1);

		//Delcare and Initialize the lblHighScore label and sets its properties
		Label lblHighScore = new Label("HIGH SCORE:");
		lblHighScore.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 25));

		// Initalize and Declare the highscore variable by calling the findHighScore method
		int highscore = findHighScore();

		// Declare and Initialize the lblHighScoreNum label and sets its properties
		Label lblHighScoreNum = new Label(String.valueOf(highscore));
		lblHighScoreNum.setFont(Font.font("Britannic Bold", 30));
		lblHighScoreNum.setTextFill(Color.rgb(242,200,36));

		// Declare and Initialize the lblHeldBy and sets its properties
		Label lblHeldBy = new Label("HELD BY:");
		lblHeldBy.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 25));

		// Declare and Initialize the lblHolder label and sets its properties 
		Label lblHolder = new Label(holder);
		lblHolder.setFont(Font.font("Britannic Bold", 30));

		// Declare and Initialize the VBox highscoreinfo and sets its properties. Add the highscore text, highscorenum, held by text, and the name of the holder (All of these in labels)
		VBox highscoreinfo = new VBox(10);
		highscoreinfo.setAlignment(Pos.CENTER);
		highscoreinfo.getChildren().addAll(lblHighScore, lblHighScoreNum, lblHeldBy, lblHolder);

		// Declare and Initialize the btnPlayAgain Button and sets its properties. If clicked reset the score to 0 and call the create TitleScreenMethod and set the scene to titleScreen
		Button btnPlayAgain = new Button("PLAY AGAIN");
		btnPlayAgain.setPrefSize(150,50);
		btnPlayAgain.setMinWidth(150);
		btnPlayAgain.setFont(Font.font("Britannic Bold"));
		btnPlayAgain.setStyle("-fx-background-color: white");
		btnPlayAgain.setOnMouseEntered(e -> btnPlayAgain.setStyle("-fx-background-color: lightblue"));
		btnPlayAgain.setOnMouseExited(e -> btnPlayAgain.setStyle("-fx-background-color: white"));
		btnPlayAgain.setOnAction(e -> {
			score = 0;
			createTitleScreen(primaryStage);
			primaryStage.setScene(titleScreen);
		});

		//Declare and Initalize the btnExit button and set its properties. If clicked ask the user for confirmation if they want to quit. 
		// Yes = close the program, NO = return to the window
		Button btnExit = new Button("EXIT");
		btnExit.setPrefSize(150,50);
		btnExit.setMinWidth(150);
		btnExit.setFont(Font.font("Britannic Bold"));
		btnExit.setStyle("-fx-background-color: white");
		btnExit.setOnMouseEntered(e -> btnExit.setStyle("-fx-background-color: lightblue"));
		btnExit.setOnMouseExited(e -> btnExit.setStyle("-fx-background-color: white"));
		btnExit.setOnAction(e -> {
			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setHeaderText(null);
			confirm.setContentText("Are you sure you want to exit?");
			confirm.setTitle("Exit");
			confirm.getButtonTypes().clear();
			confirm.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

			Optional<ButtonType> result = confirm.showAndWait();

			if(result.get() == ButtonType.YES) {
				Platform.exit();
				System.exit(0);
			}
		});

		// Declare and Initialize the VBox buttons and set its properties. Adds the two buttons to it.
		VBox buttons = new VBox(39);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(btnPlayAgain, btnExit);
		buttons.setPadding(new Insets(15,15,15,15));

		// Declare and Initialize the playagaintitle TitledPane and add the VBox buttons to it
		TitledPane playagaintitle = new TitledPane();
		playagaintitle.setText("Play Again?");
		playagaintitle.setCollapsible(false);
		playagaintitle.setContent(buttons);
		// Adds the TitledPane to the GridPane 
		root.add(playagaintitle,2,1);

		// Declare and Initialize the TitledPane highscoreTitle. Adds the vBox highscoreinfor to it.
		TitledPane highscoreTitle = new TitledPane();
		highscoreTitle.setText("HIGH SCORE:");
		highscoreTitle.setCollapsible(false);
		highscoreTitle.setContent(highscoreinfo);
		// adds the TitledPane to the GridPane
		root.add(highscoreTitle,1,1);
	}

	private int findHighScore() {
		// Declare and Initialize the temp variable, highscore variable, and the scores/names arraylists. Initialize the holder variable.
		String temp = "";
		int highscore = 0;
		ArrayList<Integer> scores = new ArrayList<Integer>();
		ArrayList<String> names = new ArrayList<String>();
		holder = "N/A";

		// IF the file does not exist display to the user the error.
		if(scoresFile.exists() == false) {
			Alert doesNotExist = new Alert(AlertType.ERROR);
			doesNotExist.setHeaderText(null);
			doesNotExist.setContentText("Issue retriving information!");
			doesNotExist.getButtonTypes().clear();
			doesNotExist.getButtonTypes().add(ButtonType.OK);
			doesNotExist.showAndWait();
		} else {

			try {
				// Declare and Initialize the FileReader and the BufferedReader
				FileReader in = new FileReader(scoresFile);
				BufferedReader readFile = new BufferedReader(in);
				
				// Reader through the file while the temp variable does not equal null
				while((temp = readFile.readLine()) != null) {
					
					// Try to add the temp variable to the arraylist scores by parseing it. IF this catches a NumberFormatException
					// then it means its a name at that line, so add it to the arraylist names
					try {
						scores.add(Integer.parseInt(temp));
					} catch (NumberFormatException e) {
						names.add(temp);
					}
				}
				
				// Close the BufferedReader readFile and the FileReader in
				readFile.close();
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Declare and Initalize the arrScores array
			int[] arrScores = new int[scores.size()];
			
			// Fill the arrScores array using the arraylist
			for(int i = 0; i < scores.size(); i++) {
				arrScores[i] = scores.get(i);
			}

			// Sort the arrScores array by using selection sort
			for(int i = 0; i < arrScores.length - 1; i++) {
				int index = i;
				
				// Searching for the lowest index
				for(int j = i+1; j < arrScores.length; j++) {
					if(arrScores[j] < arrScores[index]) {
						index = j;
					}
				}
				
				// Swap the elements
				int smallerNumber = arrScores[index];
				arrScores[index] = arrScores[i];
				arrScores[i] = smallerNumber;
			}

			// IF the arrScores array length is greater than 0 then initalize assign the highscore variable the last value of the arrScores array
			// This value should be the highest since its sorted from least to greatest
			if(arrScores.length > 0) {
				highscore = arrScores[arrScores.length-1];
			}
			
			// Cycle through the scores arraylist
			for(int i = 0; i < scores.size(); i++) {
				
				// If it finds the highscore in the arraylist, then the holder of that score should be in the same position in the names array list
				if (scores.get(i) == highscore) {
					
					// Assign the holder vairable
					holder = names.get(i);
				}
			}
		}
		
		// Return the highscore variable
		return highscore;
	}

}
