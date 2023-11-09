package Scaryville2;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MapePane extends Application {
	Guard guard = new Guard();
	private ArrayList<Integer> numbers = new ArrayList<Integer>();

	Label[][] labelArray = new Label[19][19];
	
	Coordinate[][] coordinateArray = new Coordinate[19][19];
	AsylumMap as = new AsylumMap(coordinateArray);
	GridPane gp = new GridPane();
	
	BorderPane bp = new BorderPane();
	HBox bottomBox = new HBox();
	Button reset = new Button("Reset");
	
	private int numMoves = 0;
	private int lunaticX = 1;
	private int lunaticY = 1;

	public int getNumMoves() {
		return numMoves;
	}

	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}

	public int getLunaticX() {
		return lunaticX;
	}

	public void setLunaticX(int lunaticX) {
		this.lunaticX = lunaticX;
	}

	public int getLunaticY() {
		return lunaticY;
	}

	public void setLunaticY(int lunaticY) {
		this.lunaticY = lunaticY;
	}

	private ArrayList<Integer> getNumbers() {
		return numbers;
	}

	private void setNumbers(ArrayList<Integer> numbers) {
		this.numbers = numbers;
	}

	private void resetNumbers() {
		numbers.clear();
	}

	public void start(Stage stage) {

		
		StackPane spW = new StackPane();
		spW.getStyleClass().add("stackPane");
		Label winLabel = new Label("You Won");
		winLabel.getStyleClass().add("winLabel");
		winLabel.setAlignment(Pos.CENTER);
		spW.getChildren().add(winLabel);
		
		StackPane spL = new StackPane();
		spL.getStyleClass().add("stackPaneL");
		Label loseLabel = new Label("You Lose");
		loseLabel.getStyleClass().add("loseLabel");
		loseLabel.setAlignment(Pos.CENTER);
		spL.getChildren().add(loseLabel);

		reset.getStyleClass().add("reset");
		reset.setOnAction(e -> {
			bp.setCenter(gp);
			gp.getChildren().clear();
			guard.setGuardX(1);
			guard.setGuardY(1);
			setLunaticX(1);
			setLunaticY(1);
			setNumMoves(0);
			createGrid();

		});

		bottomBox.getStyleClass().add("hBox");
		bottomBox.getChildren().add(reset);
		bottomBox.setAlignment(Pos.CENTER);

		bp.setBottom(bottomBox);

		createGrid();

		bp.setCenter(gp);

		Scene sc = new Scene(bp);
		sc.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		sc.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@SuppressWarnings("incomplete-switch")
			public void handle(KeyEvent e) {
				int oldX = guard.getGuardX();
				int oldY = guard.getGuardY();

				switch (e.getCode()) {
				case UP:
					increaseMoves();
					
					if (oldY != 1) {
						if (coordinateArray[oldX][oldY - 1].getCordinateValue() != 'W') {
							guard.setGuardY((oldY - 1));
							updateGuard(oldX, oldY, labelArray, gp);
						}
					}
					if(getNumMoves() > 2)
						lunaticNextMove();
					if(checkLossConditions()) {
						bp.setCenter(spL);
					}
					break;
				case DOWN:
					increaseMoves();
					if (oldY != 17 && oldY != 18) {
						if (coordinateArray[oldX][oldY + 1].getCordinateValue() != 'W') {
							guard.setGuardY((oldY + 1));
							updateGuard(oldX, oldY, labelArray, gp);
						}
					}
					if (oldY == 17 && oldX == 17) {
						guard.setGuardY((oldY + 1));
						updateGuard(oldX, oldY, labelArray, gp);
					}
					if(getNumMoves() > 2)
						lunaticNextMove();
					
					if(checkLossConditions()) {
						bp.setCenter(spL);
					}
					if(checkWin()) {
						bp.setCenter(spW);
					}
					
					break;
				case LEFT:
					increaseMoves();
					
					if (oldX != 1) {
						if (coordinateArray[oldX - 1][oldY].getCordinateValue() != 'W') {
							guard.setGuardX((oldX - 1));
							updateGuard(oldX, oldY, labelArray, gp);
						}
					}
					if(getNumMoves() > 2)
						lunaticNextMove();
					if(checkLossConditions()) {
						bp.setCenter(spL);
					}
					break;
				case RIGHT:
					increaseMoves();
					if (oldX != 17) {
						if (coordinateArray[oldX + 1][oldY].getCordinateValue() != 'W') {
							guard.setGuardX((oldX + 1));
							updateGuard(oldX, oldY, labelArray, gp);
						}
					}
					if(getNumMoves() > 2)
						lunaticNextMove();
					if(checkLossConditions()) {
						bp.setCenter(spL);
					}
					break;
				case F10:
					bp.setCenter(gp);
					gp.getChildren().clear();
					guard.setGuardX(1);
					guard.setGuardY(1);
					setLunaticX(1);
					setLunaticY(1);
					setNumMoves(0);
					createGrid();
				}
			}

		});

		stage.setScene(sc);
		stage.setTitle("Scaryville");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void updateGuard(int oldX, int oldY, Label labelArray[][],
			@SuppressWarnings("exports") GridPane gp) {
		int x = guard.getGuardX();
		int y = guard.getGuardY();
		if (oldX == 1 && oldY == 1) {
			labelArray[oldX][oldY].setStyle("-fx-background-color: green; ");
		} else if (x == 17 && y == 18) {
			System.out.println("Game over You won");
		} else
			labelArray[oldX][oldY].setStyle("-fx-background-color:  #635a5a; ");
		labelArray[x][y].setStyle("-fx-background-color: blue; ");
	}

	public void generateRandomArray() {
		resetNumbers();
		ArrayList<Integer> tempRandomArray = new ArrayList<Integer>();
		int randomNumberWallsPerRow = 1 + (int) (Math.random() * (18 * 0.25));
		while (tempRandomArray.size() <= randomNumberWallsPerRow) {
			int randomLocation = 1 + (int) (Math.random() * 17);
			if (!tempRandomArray.contains(randomLocation)) {
				tempRandomArray.add(randomLocation);
			}
		}

		setNumbers(tempRandomArray);

	}
	
	public void increaseMoves() {
		numMoves++;
	}

	public void lunaticNextMove() {
		int guardRow = guard.getGuardX();
		int guardColumn = guard.getGuardY();

		int oldLunaticX = getLunaticX();
		int oldLunaticY = getLunaticY();
		
		
		if (oldLunaticX == guardRow) {
			if (guardColumn > oldLunaticY) {
				// down
				if (oldLunaticY != 17) {
					if (coordinateArray[oldLunaticX][oldLunaticY + 2].getCordinateValue() != 'W' &&
							coordinateArray[oldLunaticX][oldLunaticY + 1].getCordinateValue() != 'W') {
						if(oldLunaticX + 1 == guardRow) {
							setLunaticY(oldLunaticY + 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
						else if(!(oldLunaticY + 2 >= 18)) {
							setLunaticY(oldLunaticY + 2);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}					
					}
					else if(coordinateArray[oldLunaticX][oldLunaticY + 1].getCordinateValue() != 'W') {
						if(!(oldLunaticY + 1 >= 18)) {
							setLunaticY(oldLunaticY + 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}	
					}
				}
			} else {
				// move up
				if (oldLunaticY != 1) {
					if (coordinateArray[oldLunaticX][oldLunaticY - 2].getCordinateValue() != 'W' && 
							coordinateArray[oldLunaticX][oldLunaticY - 1].getCordinateValue() != 'W') {
						if(oldLunaticY - 1 == guardColumn) {
							setLunaticY(oldLunaticY - 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
						else if(oldLunaticY - 2 > 0) {
							setLunaticY(oldLunaticY - 2);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
					else if(coordinateArray[oldLunaticX][oldLunaticY - 1].getCordinateValue() != 'W') {
						if(oldLunaticY - 1 > 0) {
							setLunaticY(oldLunaticY - 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
				}
			}
		} else if (oldLunaticY == guardColumn) {
			if (guardRow > oldLunaticX) {
				// move right
				if (oldLunaticX != 17) {
					if (coordinateArray[oldLunaticX + 2][oldLunaticY].getCordinateValue() != 'W' &&
							coordinateArray[oldLunaticX + 1][oldLunaticY].getCordinateValue() != 'W') {
						if(oldLunaticX + 1 == guardRow) {
							setLunaticX(oldLunaticX + 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
						else if(!(oldLunaticX + 2 >= 18)) {
							setLunaticX(oldLunaticX + 2);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
					else if(coordinateArray[oldLunaticX + 1][oldLunaticY].getCordinateValue() != 'W') {
						if(!(oldLunaticX + 1 >= 18)) {
							setLunaticX(oldLunaticX + 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
				}
			} else {
				// move left
				if (oldLunaticX != 1) {
					if (coordinateArray[oldLunaticX - 2][oldLunaticY].getCordinateValue() != 'W' && 
							coordinateArray[oldLunaticX - 1][oldLunaticY].getCordinateValue() != 'W') {
						if(oldLunaticX -1 == guardRow) {
							setLunaticX(oldLunaticX - 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
						else if((oldLunaticX - 2 >= 1)) {
							setLunaticX(oldLunaticX - 2);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
					else if(coordinateArray[oldLunaticX - 1][oldLunaticY].getCordinateValue() != 'W') {
						if((oldLunaticX - 1 >= 1)) {
							setLunaticX(oldLunaticX - 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
				}
			}
		} else {
			int randomDirrection = 1 + (int) (Math.random() * 4);
			if (randomDirrection == 1) {
				if (oldLunaticY != 1) {
					if (coordinateArray[oldLunaticX][oldLunaticY - 2].getCordinateValue() != 'W' && 
							coordinateArray[oldLunaticX][oldLunaticY - 1].getCordinateValue() != 'W') {
						if(oldLunaticY - 1 == guardColumn) {
							setLunaticY(oldLunaticY - 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
						else if(oldLunaticY - 2 > 0) {
							setLunaticY(oldLunaticY - 2);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
					else if(coordinateArray[oldLunaticX][oldLunaticY - 1].getCordinateValue() != 'W') {
						if(oldLunaticY - 1 > 0) {
							setLunaticY(oldLunaticY - 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
				}
			} else if (randomDirrection == 2) {
				if (oldLunaticY != 17) {
					if (coordinateArray[oldLunaticX][oldLunaticY + 2].getCordinateValue() != 'W' &&
							coordinateArray[oldLunaticX][oldLunaticY + 1].getCordinateValue() != 'W') {
						if(oldLunaticX + 1 == guardRow) {
							setLunaticY(oldLunaticY + 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
						else if(!(oldLunaticY + 2 >= 18)) {
							setLunaticY(oldLunaticY + 2);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}					
					}
					else if(coordinateArray[oldLunaticX][oldLunaticY + 1].getCordinateValue() != 'W') {
						if(!(oldLunaticY + 1 >= 18)) {
							setLunaticY(oldLunaticY + 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}	
					}
				}
			} else if (randomDirrection == 3) {
				if (oldLunaticX != 1) {
					if (coordinateArray[oldLunaticX - 2][oldLunaticY].getCordinateValue() != 'W' && 
							coordinateArray[oldLunaticX - 1][oldLunaticY].getCordinateValue() != 'W') {
						if(oldLunaticX -1 == guardRow) {
							setLunaticX(oldLunaticX - 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
						else if((oldLunaticX - 2 >= 1)) {
							setLunaticX(oldLunaticX - 2);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
					else if(coordinateArray[oldLunaticX - 1][oldLunaticY].getCordinateValue() != 'W') {
						if((oldLunaticX - 1 >= 1)) {
							setLunaticX(oldLunaticX - 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
				}
			} else if (randomDirrection == 4) {
				if (oldLunaticX != 17) {
					if (coordinateArray[oldLunaticX + 2][oldLunaticY].getCordinateValue() != 'W' &&
							coordinateArray[oldLunaticX + 1][oldLunaticY].getCordinateValue() != 'W') {
						if(oldLunaticX + 1 == guardRow) {
							setLunaticX(oldLunaticX + 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
						else if(!(oldLunaticX + 2 >= 18)) {
							setLunaticX(oldLunaticX + 2);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
					else if(coordinateArray[oldLunaticX + 1][oldLunaticY].getCordinateValue() != 'W') {
						if(!(oldLunaticX + 1 >= 18)) {
							setLunaticX(oldLunaticX + 1);
							updateLunatic(oldLunaticX, oldLunaticY, labelArray, gp);
						}
					}
				}
			}
		}

	}

	public void updateLunatic(int oldX, int oldY, Label labelArray[][],
			@SuppressWarnings("exports") GridPane gp) {
		int x = getLunaticX();
		int y = getLunaticY();
		
		if (oldX == 1 && oldY == 1) {
			labelArray[oldX][oldY].setStyle("-fx-background-color: green; ");
		} else
			labelArray[oldX][oldY].setStyle("-fx-background-color:  #635a5a; ");
		labelArray[x][y].setStyle("-fx-background-color: orange; ");
	}
	
	public boolean checkLossConditions() {
		if(guard.getGuardX() == getLunaticX() && guard.getGuardY() == getLunaticY()) {
			return true;
		}
		return false;
	}
	
	public boolean checkWin() {
		if(guard.getGuardX() == 17 && guard.getGuardY() == 18) {
			return true;
		}
		return false;
	}

	public void createGrid() {
		for (int column = 0; column < labelArray.length; column++) {
			for (int row = 0; row < labelArray[column].length; row++) {
				if (row == 1 && column == 1) {
					Label l = new Label("S");
					l.setAlignment(Pos.CENTER);
					l.getStyleClass().add("start");
					labelArray[column][row] = l;
					gp.add(l, column, row);
					Coordinate c = new Coordinate(row, column, 'S');
					coordinateArray[row][column] = c;

				}

				else if (row == 18 && column == 17) {
					Label l = new Label("E");
					l.setAlignment(Pos.CENTER);
					l.getStyleClass().add("end");
					labelArray[column][row] = l;
					gp.add(l, column, row);
					Coordinate c = new Coordinate(row, column, 'E');
					coordinateArray[column][row] = c;

				}

				else if (row == 0 || column == 0 || row == 18 || column == 18) {
					Label l = new Label();
					l.getStyleClass().add("wall");
					labelArray[column][row] = l;
					gp.add(l, column, row);
					Coordinate c = new Coordinate(row, column, 'W');
					coordinateArray[row][column] = c;
				}

				else {
					Label l = new Label();
					l.getStyleClass().add("label");
					labelArray[column][row] = l;
					gp.add(l, column, row);
					Coordinate c = new Coordinate(row, column, ' ');
					coordinateArray[row][column] = c;
				}

			}
		}
		for (int x = 1; x < 18; x++) {
			generateRandomArray();
			ArrayList<Integer> tempList = getNumbers();
			for (int z = 0; z < tempList.size(); z++) {
				if (!(x == 1 && tempList.get(z) == 1)) {
					int location = tempList.get(z);
					labelArray[x][location].getStyleClass().add("wall");
					Coordinate c = new Coordinate(location, x, 'W');
					coordinateArray[x][location] = c;
				}

			}

		}
		
	}
	
}


