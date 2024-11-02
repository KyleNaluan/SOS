package sprint3.product;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SosGui extends Application {

  private GridSquare[][] gridSquares;
  private GridPane grid;
  private Pane lineOverlay;

  private Label gameStatus = new Label("");
  private Label gameScore = new Label("");
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
    
    replayButton.setDisable(true);

    // Check boxes
    CheckBox recordBox = new CheckBox("Record Game");
    recordBox.setDisable(true);

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
    
    blueComp.setDisable(true);
    redComp.setDisable(true);

    // Text Fields
    sizeTextField = new TextField();
    sizeTextField.setPrefWidth(50);
    
    sizeTextField.setOnAction(e -> setBoardSize());
    
    // Spacers
    Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    spacer.setMinSize(10, 1);

    // Panes
    lineOverlay = new Pane();
    grid = new GridPane();
    
    // Set Grid as Center | Translate based on size of lineOverlay because Pane can't be aligned
    grid.setLayoutX(0);
    grid.setLayoutY(0);
    grid.translateXProperty().bind(Bindings.divide(lineOverlay.widthProperty().subtract(grid.widthProperty()), 2));
    grid.translateYProperty().bind(Bindings.divide(lineOverlay.heightProperty().subtract(grid.heightProperty()), 2));

    // Layout
    BorderPane root = new BorderPane();
    HBox header = new HBox(title, simpleGame, generalGame, spacer, sizeLabel, sizeTextField);
    HBox errorMessage = new HBox(errorLabel);
    VBox top = new VBox(header, errorMessage);
    VBox left = new VBox(blueLabel, blueLabelLine, blueHuman, blueS, blueO, blueComp);
    VBox right = new VBox(redLabel, redLabelLine, redHuman, redS, redO, redComp);
    BorderPane bottom = new BorderPane();
    VBox statusLabels = new VBox(gameScore,gameStatus);
    VBox resetButtons = new VBox(replayButton, newGameButton, endGameButton);

    root.setTop(top);
    root.setLeft(left);
    root.setCenter(lineOverlay);
    root.setRight(right);
    root.setBottom(bottom);
    bottom.setLeft(recordBox);
    bottom.setCenter(statusLabels);
    bottom.setRight(resetButtons);

    // Alignment
    header.setAlignment(Pos.CENTER_LEFT);
    errorMessage.setAlignment(Pos.CENTER);
    left.setAlignment(Pos.CENTER);
    right.setAlignment(Pos.CENTER);
    statusLabels.setAlignment(Pos.CENTER);

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
  
  public void setUpBoard() {
    clearBoard();
    int rows = game.getTotalRows();
    int cols = game.getTotalColumns();
    gridSquares = new GridSquare[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid.add(gridSquares[i][j] = new GridSquare(i,j), j, i);
      }
    }
    
    lineOverlay.getChildren().add(grid);
  }
  
  public void clearBoard() {
    lineOverlay.getChildren().clear();
    grid.getChildren().clear();
  }
  
  public void drawBoard() {
    // Draw S & O Characters
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
    
    // Draw Lines for SOS Sequences
    ArrayList<SosSequence> sosSequences = game.getSosSequences();
    
    int r1;
    int c1;
    int r2;
    int c2;
    char player;
    Paint color;
    
    
    for (int i = 0; i < sosSequences.size(); i++) {
      r1 = sosSequences.get(i).getR1();
      c1 = sosSequences.get(i).getC1();
      r2 = sosSequences.get(i).getR2();
      c2 = sosSequences.get(i).getC2();
      player = sosSequences.get(i).getPlayer();
      
      if (player == 'B') {
        color = Color.BLUE;
      } else {
        color = Color.RED;
      }
      
      drawLine(r1,c1,r2,c2,color);
    }
  }
  
  private void selectGame() {
    if (simpleGame.isSelected()) {
      game = new SimpleGame();
    } else if (generalGame.isSelected()) {
      game = new GeneralGame();
    }
  }
  
  private void setBoardSize() {
    String textInput = sizeTextField.getText();
    
    if (!(game == null)) {
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
  
  public void setStatusLabels() {
    gameStatus.setText("Current Turn: Blue");
    
    if (simpleGame.isSelected()) {
      gameScore.setText("");
    } else if (generalGame.isSelected()) {
      gameScore.setText("Blue: 0 | Red: 0"); 
    }
  }
  
  public void startNewGame() {
    try {
      if (!(simpleGame.isSelected() || generalGame.isSelected())) {
        throw new RuntimeException("Please Select a Game Mode");
      }
      setBoardSize();
      game.startGame();
      setUpBoard();
      setStatusLabels();
      // Make sure moves still correspond to what is selected, instead of reset to S
      setBlueMove();
      setRedMove();
      // Disable the ability to change board size and game mode once game has started
      simpleGame.setDisable(true);
      generalGame.setDisable(true);
      sizeTextField.setDisable(true);
      newGameButton.setDisable(true);
    } catch (RuntimeException e) {
      errorLabel.setText(e.toString());
    }
  }
  
  public void resetGame() {
    game.resetGame();
    setUpBoard();
    setStatusLabels();
  }
  
  public void endGame() {
    // Set game window back to clean
    game = null;
    clearBoard();
    gameStatus.setText("");
    gameScore.setText("");
    
    enableSelection();
  }
  
  public void enableSelection() {
    // Allow Game Setup Buttons to be Interactable Again
    simpleGame.setDisable(false);
    generalGame.setDisable(false);
    sizeTextField.setDisable(false);
    newGameButton.setDisable(false);
    
    // Clear Previous Game Settings
    simpleGame.setSelected(false);
    generalGame.setSelected(false);
    sizeTextField.clear();
  }
  
  public void drawLine(int r1, int c1, int r2, int c2, Paint color) {
    // Line starts/ends (row/col) cells over, then halfway into cell
    double startX = (c1 * 50) + 25;
    double startY = (r1 * 50) + 25;
    double endX = (c2 * 50) + 25;
    double endY = (r2 * 50) + 25;
    
    // Offset from centering grid
    startX += grid.getTranslateX();
    startY += grid.getTranslateY();
    endX += grid.getTranslateX();
    endY += grid.getTranslateY();
    
    Line line = new Line(startX, startY, endX, endY);
    line.setStroke(color);
    line.setStrokeWidth(2);
   
    // Add to lineOverlay, rather than gridSquares, so line can overlap the board
    lineOverlay.getChildren().add(line);
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
      if (game.getGameState() == SosGame.GameState.PLAYING) {
        game.makeMove(row, col);
        drawBoard();
      }
      
      if (game.getGameState() != SosGame.GameState.PLAYING) {
        enableSelection();
      }
      
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
      gameScore.setText(game.showScore());
      
      if(game.getGameState() == SosGame.GameState.PLAYING) {
        if(game.getTurn() == 'B') {
          gameStatus.setText("Current Turn: Blue");
        } else {
          gameStatus.setText("Current Turn: Red");
        }
      } else if (game.getGameState() == SosGame.GameState.BLUE_WON) {
        gameStatus.setText("Blue Won!");
      } else if (game.getGameState() == SosGame.GameState.RED_WON) {
        gameStatus.setText("Red Won!");
      } else if (game.getGameState() == SosGame.GameState.DRAW) {
        gameStatus.setText("Draw Game");
      }
    }

  }

}