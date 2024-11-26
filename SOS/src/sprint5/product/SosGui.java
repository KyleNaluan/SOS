package sprint5.product;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SosGui extends Application {

  private GridSquare[][] gridSquares;
  private GridPane grid;
  private Pane lineOverlay;
  private BorderPane root;

  private Text title;
  private Text blueLabel;
  private Text redLabel;
  private Label sizeLabel;
  private Label gameStatus = new Label("");
  private Label gameScore = new Label("");
  private Label errorLabel;
  private Line blueLabelLine;
  private Line redLabelLine;

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

  private CheckBox recordBox;
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

    setUpText();
    setUpLabels();
    setUpLines();
    setUpButtons();
    setUpCheckBoxes();
    setUpRadioButtons();
    setUpToggleGroups();
    setUpTextFields();
    setUpLayout();
    setUpActionListeners();

    // Scene
    Scene scene = new Scene(root, 1000, 500);
    scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void setUpText() {
    title = new Text("SOS");
    blueLabel = new Text("Blue Player");
    redLabel = new Text("Red Player");

    title.getStyleClass().add("normal-text");
    title.getStyleClass().add("title");
    blueLabel.getStyleClass().add("normal-text");
    redLabel.getStyleClass().add("normal-text");
  }

  private void setUpLabels() {
    sizeLabel = new Label("Board Size:");
    errorLabel = new Label("");
    errorLabel.getStyleClass().add("label-error");
  }

  private void setUpLines() {
    blueLabelLine = new Line();
    blueLabelLine.getStyleClass().add("line-blue");
    blueLabelLine.setStartX(0);
    blueLabelLine.setEndX(110);

    redLabelLine = new Line();
    redLabelLine.getStyleClass().add("line-red");
    redLabelLine.setStartX(0);
    redLabelLine.setEndX(110);
  }

  private void setUpButtons() {
    replayButton = new Button("Replay");
    newGameButton = new Button("New Game");
    newGameButton.getStyleClass().add("button-blue");
    endGameButton = new Button("End Game");

    replayButton.setPrefWidth(100);
    newGameButton.setPrefWidth(100);
    endGameButton.setPrefWidth(100);
    
    replayButton.setDisable(true);
  }

  private void setUpCheckBoxes() {
    recordBox = new CheckBox("Record Game");
  }

  private void setUpRadioButtons() {
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
  }

  private void setUpActionListeners() {
    simpleGame.setOnAction(e -> selectGame());
    generalGame.setOnAction(e -> selectGame());
    sizeTextField.setOnAction(e -> setBoardSize());

    blueS.setOnAction(e -> setBlueMove());
    blueO.setOnAction(e -> setBlueMove());
    redS.setOnAction(e -> setRedMove());
    redO.setOnAction(e -> setRedMove());

    blueHuman.setOnAction(e -> setBluePlayer());
    blueComp.setOnAction(e -> setBluePlayer());
    redHuman.setOnAction(e -> setRedPlayer());
    redComp.setOnAction(e -> setRedPlayer());

    recordBox.setOnAction(e -> setRecording());
    replayButton.setOnAction(e -> replayGame());
    newGameButton.setOnAction(e -> startNewGame());
    endGameButton.setOnAction(e -> endGame());
  }

  private void setUpToggleGroups() {
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
  }

  private void setUpTextFields() {
    sizeTextField = new TextField();
    sizeTextField.setPrefWidth(50);
  }

  private void setUpLayout() {
    // Spacers
    Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    spacer.setMinSize(10, 1);

    // Panes
    lineOverlay = new Pane();
    grid = new GridPane();

    // Set Grid as Center | Translate based on size of lineOverlay because Pane
    // can't be aligned
    grid.setLayoutX(0);
    grid.setLayoutY(0);
    grid.translateXProperty().bind(Bindings.divide(lineOverlay.widthProperty().subtract(grid.widthProperty()), 2));
    grid.translateYProperty().bind(Bindings.divide(lineOverlay.heightProperty().subtract(grid.heightProperty()), 2));

    // Layout
    root = new BorderPane();
    HBox header = new HBox(title, simpleGame, generalGame, spacer, sizeLabel, sizeTextField);
    HBox errorMessage = new HBox(errorLabel);
    VBox top = new VBox(header, errorMessage);
    VBox left = new VBox(blueLabel, blueLabelLine, blueHuman, blueS, blueO, blueComp);
    VBox right = new VBox(redLabel, redLabelLine, redHuman, redS, redO, redComp);
    BorderPane bottom = new BorderPane();
    VBox statusLabels = new VBox(gameScore, gameStatus);
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
  }

  private void setUpBoard() {
    clearBoard();
    int rows = game.getTotalRows();
    int cols = game.getTotalColumns();
    gridSquares = new GridSquare[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid.add(gridSquares[i][j] = new GridSquare(i, j), j, i);
      }
    }

    lineOverlay.getChildren().add(grid);
  }

  private void clearBoard() {
    lineOverlay.getChildren().clear();
    grid.getChildren().clear();
  }

  private void drawBoard() {
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
    Player player;
    Paint color;

    for (int i = 0; i < sosSequences.size(); i++) {
      r1 = sosSequences.get(i).getR1();
      c1 = sosSequences.get(i).getC1();
      r2 = sosSequences.get(i).getR2();
      c2 = sosSequences.get(i).getC2();
      player = sosSequences.get(i).getPlayer();

      if (player.getName() == "Blue") {
        color = Color.BLUE;
      } else {
        color = Color.RED;
      }

      drawLine(r1, c1, r2, c2, color);
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

  private void setRecording() {
    if (game != null) {
      game.setRecording(recordBox.isSelected());
    }
  }

  private void setBluePlayer() {
    if (game != null) {
      if (blueHuman.isSelected()) {
        game.setBluePlayer('H');
        blueS.setDisable(false);
        blueO.setDisable(false);
      } else {
        game.setBluePlayer('C');
        blueS.setDisable(true);
        blueO.setDisable(true);
      }
    }
  }

  private void setRedPlayer() {
    if (game != null) {
      if (redHuman.isSelected()) {
        game.setRedPlayer('H');
        redS.setDisable(false);
        redO.setDisable(false);
      } else {
        game.setRedPlayer('C');
        redS.setDisable(true);
        redO.setDisable(true);
      }
    }
  }

  private void setBlueMove() {
    if (game != null) {
      if (blueS.isSelected()) {
        game.setBlueMove('S');
      } else if (blueO.isSelected()) {
        game.setBlueMove('O');
      }
    }
  }

  private void setRedMove() {
    if (game != null) {
      if (redS.isSelected()) {
        game.setRedMove('S');
      } else if (redO.isSelected()) {
        game.setRedMove('O');
      }
    }
  }

  private void setStatusLabels() {
    gameStatus.setText("Current Turn: Blue");

    if (simpleGame.isSelected()) {
      gameScore.setText("");
    } else if (generalGame.isSelected()) {
      gameScore.setText("Blue: 0 | Red: 0");
    }
  }

  private void startNewGame() {
    try {
      if (!(simpleGame.isSelected() || generalGame.isSelected())) {
        throw new RuntimeException("Please Select a Game Mode");
      }
      setBoardSize();
      setBluePlayer();
      setRedPlayer();
      setBlueMove();
      setRedMove();
      setRecording();
      game.startGame();
      setUpBoard();
      setStatusLabels();
      lineOverlay.layout();
      displayGameStatus();

      if (game.getCurrentPlayer() instanceof ComputerPlayer) {
        handleComputerMovesWithDelay();
      }

      disableSelection();

    } catch (RuntimeException e) {
      errorLabel.setText(e.toString());
    }
  }

  private void replayGame() {
    ArrayList<String[]> moves = game.replayGame();
    setUpBoard();
    setStatusLabels();
    lineOverlay.layout();
    displayGameStatus();
    handleReplayMovesWithDelay(moves, 0);
  }

  private void handleReplayMovesWithDelay(ArrayList<String[]> moves, int index) {
    if (index == moves.size()) {
      return;
    }
    
    String[] move = moves.get(index);
    
    int row = Integer.parseInt(move[0]);
    int col = Integer.parseInt(move[1]);
    char moveType = move[2].charAt(0);
    
    PauseTransition pause = new PauseTransition(Duration.seconds(1));

    pause.setOnFinished(event -> {

      game.makeMove(row, col, moveType);
      drawBoard();
      displayGameStatus();
      
      handleReplayMovesWithDelay(moves, index+1);
    });
    pause.play();
  }

  private void endGame() {
    // Set game window back to clean
    game = null;
    clearBoard();
    gameStatus.setText("");
    gameScore.setText("");

    enableSelection();
  }

  private void enableSelection() {
    // Allow Game Setup Buttons to be Interactable Again
    simpleGame.setDisable(false);
    generalGame.setDisable(false);
    sizeTextField.setDisable(false);
    blueHuman.setDisable(false);
    blueComp.setDisable(false);
    redHuman.setDisable(false);
    redComp.setDisable(false);
    recordBox.setDisable(false);
    newGameButton.setDisable(false);
    replayButton.setDisable(true);

    // Clear Previous Game Settings
    simpleGame.setSelected(false);
    generalGame.setSelected(false);
    sizeTextField.clear();
  }

  private void disableSelection() {
    simpleGame.setDisable(true);
    generalGame.setDisable(true);
    sizeTextField.setDisable(true);
    newGameButton.setDisable(true);
    recordBox.setDisable(true);
    replayButton.setDisable(true);
    blueHuman.setDisable(true);
    blueComp.setDisable(true);
    redHuman.setDisable(true);
    redComp.setDisable(true);
  }

  private void drawLine(int r1, int c1, int r2, int c2, Paint color) {
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

  private void handleComputerMovesWithDelay() {
    if (game.getGameStatus() == SosGame.GameStatus.PLAYING) {
      PauseTransition pause = new PauseTransition(Duration.seconds(1));
      pause.setOnFinished(event -> {

        game.selectMove(0, 0);
        drawBoard();
        displayGameStatus();

        if (game.getCurrentPlayer() instanceof ComputerPlayer && game.getGameStatus() == SosGame.GameStatus.PLAYING) {
          handleComputerMovesWithDelay();
        }

      });
      pause.play();
    }
  }

  private void displayGameStatus() {
    gameScore.setText(game.showScore());

    if (game.getGameStatus() == SosGame.GameStatus.PLAYING) {
      if (game.getCurrentPlayer().getName() == "Blue") {
        gameStatus.setText("Current Turn: Blue");
      } else {
        gameStatus.setText("Current Turn: Red");
      }
    } else {
      if (game.getGameStatus() == SosGame.GameStatus.BLUE_WON) {
        gameStatus.setText("Blue Won!");
      } else if (game.getGameStatus() == SosGame.GameStatus.RED_WON) {
        gameStatus.setText("Red Won!");
      } else if (game.getGameStatus() == SosGame.GameStatus.DRAW) {
        gameStatus.setText("Draw Game");
      }

      if (game.isGameRecorded()) {
        replayButton.setDisable(false);
      } else {
        enableSelection();
      }
    }
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
      if (game.getGameStatus() == SosGame.GameStatus.PLAYING) {
        game.selectMove(row, col);
        game.updateGameStatus();
        drawBoard();

        if (game.getCurrentPlayer() instanceof ComputerPlayer) {
          handleComputerMovesWithDelay();
        }
      }

      displayGameStatus();

    }

    public void drawS() {
      Text letter = new Text("S");
      this.getChildren().add(letter);
      letter.setX(this.getHeight() / 2);
      letter.setY(this.getWidth() / 2);

    }

    public void drawO() {
      Text letter = new Text("O");
      this.getChildren().add(letter);
      letter.setX(this.getHeight() / 2);
      letter.setY(this.getWidth() / 2);
    }

  }

}