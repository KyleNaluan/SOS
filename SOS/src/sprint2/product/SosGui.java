package sprint2.product;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SosGui extends Application {

  private GridSquare[][] gridSquares;
  private GridPane pane;

  private Label gameStatus = new Label("Current Turn: Blue");
  private Label errorLabel;
  
  private RadioButton simpleGame;
  private RadioButton generalGame;
  private RadioButton blueHuman;
  private RadioButton blueS;
  private RadioButton blueO;
  private RadioButton blueComp;
  private RadioButton redHuman;
  private RadioButton redS;
  private RadioButton redO;
  private RadioButton redComp;
  
  private Button replayButton;
  private Button newGameButton;
  private Button endGameButton;
  
  private TextField sizeTextField;
  private int boardSize;
  
  static private SosGame game;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    if (game==null) {
      game = new SosGame();
    }
    
    primaryStage.setTitle("SOS");

    // Plain Text
    Text title = new Text("SOS");
    Text blueLabel = new Text("Blue Player");
    Text redLabel = new Text("Red Player");

    title.getStyleClass().add("normal-text");
    title.getStyleClass().add("title");
    blueLabel.getStyleClass().add("normal-text");
    redLabel.getStyleClass().add("normal-text");

    // Labels
    Label sizeLabel = new Label("Board Size:");
    errorLabel = new Label("");
    errorLabel.getStyleClass().add("label-error");

    // Lines
    Line blueLabelLine = new Line();
    blueLabelLine.getStyleClass().add("line-blue");
    blueLabelLine.setStartX(0);
    blueLabelLine.setEndX(110);

    Line redLabelLine = new Line();
    redLabelLine.getStyleClass().add("line-red");
    redLabelLine.setStartX(0);
    redLabelLine.setEndX(110);

    // Buttons
    replayButton = new Button("Replay");
    newGameButton = new Button("New Game");
    newGameButton.getStyleClass().add("button-blue");
    endGameButton = new Button("End Game");

    replayButton.setPrefWidth(100);
    newGameButton.setPrefWidth(100);
    endGameButton.setPrefWidth(100);
    
    replayButton.setOnAction(e -> resetGame());
    newGameButton.setOnAction(e -> startNewGame());
    endGameButton.setOnAction(e -> endGame());

    // Check boxes
    CheckBox recordBox = new CheckBox("Record Game");

    // Radio Buttons
    simpleGame = new RadioButton("Simple Game");
    generalGame = new RadioButton("General Game");
    blueHuman = new RadioButton("Human");
    blueS = new RadioButton("S");
    blueO = new RadioButton("O");
    blueComp = new RadioButton("Computer");
    redHuman = new RadioButton("Human");
    redS = new RadioButton("S");
    redO = new RadioButton("O");
    redComp = new RadioButton("Computer");
    
    // Action Listeners
    simpleGame.setOnAction(e -> selectGame());
    generalGame.setOnAction(e -> selectGame());
    
    blueS.setOnAction(e -> setBlueMove());
    blueO.setOnAction(e -> setBlueMove());
    redS.setOnAction(e -> setRedMove());
    redO.setOnAction(e -> setRedMove());

    // Toggle Groups
    ToggleGroup gameSelectButtons = new ToggleGroup();
    ToggleGroup bluePlayerButtons = new ToggleGroup();
    ToggleGroup blueLetterButtons = new ToggleGroup();
    ToggleGroup redPlayerButtons = new ToggleGroup();
    ToggleGroup redLetterButtons = new ToggleGroup();

    simpleGame.setToggleGroup(gameSelectButtons);
    generalGame.setToggleGroup(gameSelectButtons);
    blueHuman.setToggleGroup(bluePlayerButtons);
    blueS.setToggleGroup(blueLetterButtons);
    blueO.setToggleGroup(blueLetterButtons);
    blueComp.setToggleGroup(bluePlayerButtons);
    redHuman.setToggleGroup(redPlayerButtons);
    redS.setToggleGroup(redLetterButtons);
    redO.setToggleGroup(redLetterButtons);
    redComp.setToggleGroup(redPlayerButtons);

    blueHuman.setSelected(true);
    blueS.setSelected(true);
    redHuman.setSelected(true);
    redS.setSelected(true);

    // Text Fields
    sizeTextField = new TextField();
    sizeTextField.setPrefWidth(50);
    
    sizeTextField.setOnAction(e -> setBoardSize());
    
 

    // Spacers
    Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    spacer.setMinSize(10, 1);

    // GridPane
    pane = new GridPane();
    /*
    setUpGrid(pane);
    pane.getChildren().clear();
    */
    drawBoard();

    // Layout
    BorderPane root = new BorderPane();
    HBox header = new HBox(title, simpleGame, generalGame, spacer, sizeLabel, sizeTextField);
    HBox errorMessage = new HBox(errorLabel);
    VBox top = new VBox(header, errorMessage);
    VBox left = new VBox(blueLabel, blueLabelLine, blueHuman, blueS, blueO, blueComp);
    VBox right = new VBox(redLabel, redLabelLine, redHuman, redS, redO, redComp);
    BorderPane bottom = new BorderPane();
    VBox resetButtons = new VBox(replayButton, newGameButton, endGameButton);

    root.setTop(top);
    root.setLeft(left);
    root.setCenter(pane);
    root.setRight(right);
    root.setBottom(bottom);
    bottom.setLeft(recordBox);
    bottom.setCenter(gameStatus);
    bottom.setRight(resetButtons);

    // Alignment
    header.setAlignment(Pos.CENTER_LEFT);
    errorMessage.setAlignment(Pos.CENTER);
    left.setAlignment(Pos.CENTER);
    pane.setAlignment(Pos.CENTER);
    right.setAlignment(Pos.CENTER);

    // Padding and Spacing
    header.setPadding(new Insets(10, 10, 30, 10));
    left.setPadding(new Insets(10, 10, 10, 10));
    right.setPadding(new Insets(10, 10, 10, 10));
    bottom.setPadding(new Insets(10, 10, 10, 10));
    header.setSpacing(25);
    left.setSpacing(5);
    right.setSpacing(5);
    resetButtons.setSpacing(5);
    

    // Scene
    Scene scene = new Scene(root, 1000, 500);
    scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  public void setUpGrid(GridPane grid) {
    grid.getChildren().clear();
    int rows = game.getTotalRows();
    int cols = game.getTotalColumns();
    gridSquares = new GridSquare[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid.add(gridSquares[i][j] = new GridSquare(i,j), j, i);
      }
    }
  }
  
  public void resetGrid(GridPane grid) {
    int rows = grid.getRowCount();
    int cols = grid.getColumnCount();
    grid.getChildren().clear();
    gridSquares = new GridSquare[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid.add(gridSquares[i][j] = new GridSquare(i,j), j, i);
      }
    }
  }
  
  public void drawBoard() {
    for (int row = 0; row < game.getTotalRows(); row++) {
      for (int col = 0; col < game.getTotalColumns(); col++) {
        gridSquares[row][col].getChildren().clear();
        if (game.getCell(row, col) == SosGame.Cell.S) {
          gridSquares[row][col].drawS();
        } else if (game.getCell(row, col) == SosGame.Cell.O) {
          gridSquares[row][col].drawO();
        }
      }
    }
  }
  
  private void selectGame() {
    if (simpleGame.isSelected()) {
      game.setGameMode(SosGame.GameMode.SIMPLE);
    } else if (generalGame.isSelected()) {
      game.setGameMode(SosGame.GameMode.GENERAL);
    }
  }
  
  private void setBoardSize() {
    String textInput = sizeTextField.getText();

    try {
      boardSize = Integer.parseInt(textInput);
      game.setBoardSize(boardSize);
      errorLabel.setText("");
    } catch (IllegalArgumentException e) {
      errorLabel.setText(e.toString());
    } catch (Exception e) {
      errorLabel.setText("Invalid Input");
    }
  }
  
  private void setBlueMove() {
    if (blueS.isSelected()) {
      game.setBlueMove('S');
    } else if (blueO.isSelected()) {
      game.setBlueMove('O');
    }
  }
  
  public void setRedMove() {
    if (redS.isSelected()) {
      game.setRedMove('S');
    } else if (redO.isSelected()) {
      game.setRedMove('O');
    }
  }
  
  public void startNewGame() {
    try {
      game.startGame();
      setUpGrid(pane);
      // Disable the ability to change board size and game mode once game has started
      simpleGame.setDisable(true);
      generalGame.setDisable(true);
      sizeTextField.setDisable(true);
    } catch (RuntimeException e) {
      errorLabel.setText(e.toString());
    }
  }
  
  public void resetGame() {
    game.resetGame();
    setUpGrid(pane);
  }
  
  public void endGame() {
    // Set game window back to clean
    game = new SosGame();
    
    // Re-enable game settings so user can choose
    simpleGame.setDisable(false);
    generalGame.setDisable(false);
    sizeTextField.setDisable(false);
  }

  public class GridSquare extends Pane {

    private int row, col;
    
    public GridSquare(int row, int col) {
      this.row = row;
      this.col = col;
      getStyleClass().clear();
      getStyleClass().add("grid-square");
      this.setPrefSize(50, 50);
      this.setOnMouseClicked(e -> handleMouseClick());
    }
    
    private void handleMouseClick() {
      if (game.getGameMode() == SosGame.GameMode.SIMPLE) {
        game.makeMoveSimpleGame(row, col);
      }
      if (game.getGameMode() == SosGame.GameMode.GENERAL) {
        game.makeMoveGeneralGame(row, col);
      }
      drawBoard();
      displayGameStatus();
    }
    
    public void drawS() {
      Text letter = new Text("S");
      this.getChildren().add(letter);
      letter.setX(this.getHeight()/2);
      letter.setY(this.getWidth()/2);
      
    }
    
    public void drawO() {
      Text letter = new Text("O");
      this.getChildren().add(letter);
      letter.setX(this.getHeight()/2);
      letter.setY(this.getWidth()/2);
    }
    
    private void displayGameStatus() {
      if(game.getGameState() == SosGame.GameState.PLAYING) {
        if(game.getTurn() == 'B') {
          gameStatus.setText("Current Turn: Blue");
        } else {
          gameStatus.setText("Current Turn: Red");
        }
        
      }
    }

  }

}