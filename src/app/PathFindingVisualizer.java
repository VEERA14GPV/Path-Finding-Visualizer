package app;

import algorithms.*;
import grid.Cell;
import grid.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PathFindingVisualizer extends Application {

    private Grid grid;
    private Cell startCell, endCell;

    private ComboBox<String> algorithmBox;
    private ComboBox<String> directionBox;
    private Slider speedSlider;

    private ComboBox<Integer> rowBox;
    private ComboBox<Integer> colBox;
    private ComboBox<Integer> sizeBox;

    private Button runBtn, clearBtn, resetBtn, buildGridBtn, mazeBtn;
    private CheckBox eraseModeCheckBox;

    private GridPane gridPane;
    private Timeline timeline;

    private boolean drawingWall = false;

    private int rows = 20;
    private int cols = 30;
    private int size = 25;

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        gridPane = new GridPane();

        // ================= CONTROLS =================

        algorithmBox = new ComboBox<>(FXCollections.observableArrayList(
                "BFS", "DFS", "Dijkstra", "A*"));
        algorithmBox.setValue("BFS");

        directionBox = new ComboBox<>(FXCollections.observableArrayList(
                "4 Directions", "8 Directions"));
        directionBox.setValue("4 Directions");

        speedSlider = new Slider(10, 200, 40);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);

        rowBox = new ComboBox<>(FXCollections.observableArrayList(10, 15, 20, 25, 30, 40));
        rowBox.setValue(rows);

        colBox = new ComboBox<>(FXCollections.observableArrayList(10, 20, 30, 40, 50, 60));
        colBox.setValue(cols);

        sizeBox = new ComboBox<>(FXCollections.observableArrayList(15, 20, 25, 30, 35));
        sizeBox.setValue(size);

        buildGridBtn = new Button("🧱 Create Grid");
        runBtn = new Button("▶ Run");
        clearBtn = new Button("🧹 Clear Path");
        resetBtn = new Button("🔄 Reset Grid");

        eraseModeCheckBox = new CheckBox("🧽 Erase Wall Mode");

        VBox leftPanel = new VBox(10);
        leftPanel.setStyle("""
                -fx-padding: 10;
                -fx-background-color: #eeeeee;
                -fx-border-color: #cccccc;
                """);

        leftPanel.getChildren().addAll(
                new Label("Grid Settings"),
                new Label("Rows"), rowBox,
                new Label("Columns"), colBox,
                new Label("Cell Size"), sizeBox,
                buildGridBtn,
                new Separator(),
                new Label("Algorithm"), algorithmBox,
                new Label("Directions"), directionBox,
                new Separator(),
                new Label("Speed"), speedSlider,
                eraseModeCheckBox,
                new Separator(),
                runBtn, clearBtn, resetBtn
        );

        root.setLeft(leftPanel);
        root.setCenter(gridPane);

        // ================= INITIAL GRID =================
        buildGrid();

        // ================= ACTIONS =================
        buildGridBtn.setOnAction(e -> buildGrid());
        runBtn.setOnAction(e -> runAlgorithm());
        clearBtn.setOnAction(e -> clearPath());
        resetBtn.setOnAction(e -> buildGrid());

        stage.setScene(new Scene(root, 1200, 800));
        stage.setTitle("Pathfinding Visualizer");
        stage.show();
    }

    // ================= GRID BUILD =================

    private void buildGrid() {

        if (timeline != null) timeline.stop();

        rows = rowBox.getValue();
        cols = colBox.getValue();
        size = sizeBox.getValue();

        gridPane.getChildren().clear();
        grid = new Grid(rows, cols, size);
        startCell = null;
        endCell = null;

        for (Cell[] row : grid.getAllCells()) {
            for (Cell cell : row) {
                gridPane.add(cell.rect, cell.col, cell.row);
            }
        }

        gridPane.setOnMousePressed(e -> {
            if (e.getButton() != MouseButton.PRIMARY) return;
            drawingWall = true;
            paintCell(e.getX(), e.getY());
        });

        gridPane.setOnMouseDragged(e -> {
            if (!drawingWall) return;
            paintCell(e.getX(), e.getY());
        });

        gridPane.setOnMouseReleased(e -> drawingWall = false);

        gridPane.setOnMouseClicked(e -> {
            if (e.getButton() != MouseButton.SECONDARY) return;

            Cell cell = getCellFromMouse(e.getX(), e.getY());
            if (cell == null) return;

            if (startCell == null) {
                startCell = cell;
                cell.setStart();
            } else if (endCell == null && cell != startCell) {
                endCell = cell;
                cell.setEnd();
            }
        });
    }

    // ================= MOUSE =================

    private void paintCell(double mouseX, double mouseY) {
        Cell cell = getCellFromMouse(mouseX, mouseY);
        if (cell == null || cell == startCell || cell == endCell) return;

        if (eraseModeCheckBox.isSelected()) {
            if (cell.isWall) {
                cell.setWall(false);
            }
        } else {
            cell.setWall(true);
        }
    }

    private Cell getCellFromMouse(double mouseX, double mouseY) {
        int col = (int) (mouseX / size);
        int row = (int) (mouseY / size);

        if (row < 0 || col < 0 || row >= rows || col >= cols) return null;
        return grid.getAllCells()[row][col];
    }

    // ================= RUN =================

    private void runAlgorithm() {

        if (startCell == null || endCell == null) return;

        clearPath();

        boolean diagonal = directionBox.getValue().equals("8 Directions");

        PathAlgorithm algorithm = switch (algorithmBox.getValue()) {
            case "DFS" -> new DFS();
            case "Dijkstra" -> new Dijkstra();
            case "A*" -> new AStar();
            default -> new BFS();
        };

        PathResult result =
                algorithm.findPathWithVisited(grid, startCell, endCell, diagonal);

        animate(result);
    }

    // ================= ANIMATION =================

    private void animate(PathResult result) {

        if (timeline != null) timeline.stop();
        timeline = new Timeline();

        double speed = speedSlider.getValue();

        for (int i = 0; i < result.visited.size(); i++) {
            Cell cell = result.visited.get(i);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(i * speed),
                            e -> {
                                if (cell != startCell && cell != endCell) {
                                    cell.visit();
                                }
                            })
            );
        }

        int offset = result.visited.size();
        for (int i = 0; i < result.path.size(); i++) {
            Cell cell = result.path.get(i);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis((i + offset) * speed),
                            e -> {
                                if (cell != startCell && cell != endCell) {
                                    cell.setPath();
                                }
                            })
            );
        }

        timeline.play();
    }

    // ================= CLEAR =================

    private void clearPath() {
        if (timeline != null) timeline.stop();

        for (Cell[] row : grid.getAllCells()) {
            for (Cell c : row) {
                if (c.isVisited || c.isPath) {
                    c.clear();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
