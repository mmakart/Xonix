package com.github.mmakart.Xonix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.mmakart.Xonix.Balls.Direction;
import com.github.mmakart.Xonix.Balls.InnerBall;
import com.github.mmakart.Xonix.Balls.OuterBall;
import com.github.mmakart.Xonix.Balls.Player;
import com.github.mmakart.Xonix.Field.CellType;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
	private GameState gameState;
	private Canvas canvas;
	private GraphicsContext gc;

	private final int APP_WIDTH = 640;
	private final int APP_HEIGHT = 480;
	private final int CELL_SIZE = 16;
	private final int CANVAS_WIDTH = APP_WIDTH;
	private final int CANVAS_HEIGHT = APP_HEIGHT;
	private final int LOWER_PANE_HEIGHT = 2 * CELL_SIZE;
	private final int LOWER_PANE_WIDTH = CANVAS_WIDTH;
	private final int FIELD_HEIGHT = CANVAS_HEIGHT - LOWER_PANE_HEIGHT;
	private final int FIELD_WIDTH = CANVAS_WIDTH;
	private final int FIELD_WIDTH_IN_CELLS = FIELD_WIDTH / CELL_SIZE;
	private final int FIELD_HEIGHT_IN_CELLS = FIELD_HEIGHT / CELL_SIZE;
	private final int TICK_DURATION_IN_MILLISECONDS = 150;

	@Override
	public void start(Stage stage) {
		StackPane root = initRoot();
		Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		stage.setScene(scene);
		stage.show();

		startGame();
	}

	private StackPane initRoot() {
		StackPane root = new StackPane();
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

		canvas.setOnKeyPressed((e) -> {
			KeyCode key = e.getCode();
			if (key.equals(KeyCode.UP)) {
				gameState.getPlayer().setDirection(Player.TO_UP);
			} else if (key.equals(KeyCode.DOWN)) {
				gameState.getPlayer().setDirection(Player.TO_DOWN);
			} else if (key.equals(KeyCode.LEFT)) {
				gameState.getPlayer().setDirection(Player.TO_LEFT);
			} else if (key.equals(KeyCode.RIGHT)) {
				gameState.getPlayer().setDirection(Player.TO_RIGHT);
			} else if (key.equals(KeyCode.SPACE)) {
				gameState.getPlayer().setDirection(Player.STOP);
			}
		});

		canvas.setFocusTraversable(true);

		root.getChildren().add(canvas);
		return root;
	}

	private void startGame() {
		initGame();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					drawField();
					drawUnits();
					drawLowerPane();
					doLogic();

					if (gameState.isGameOver()) {
						break;
					}

					try {
						Thread.sleep(TICK_DURATION_IN_MILLISECONDS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	private void doLogic() {
		for (InnerBall inner : gameState.getInnerBalls()) {
			inner.move(gameState);
		}

		for (OuterBall outer : gameState.getOuterBalls()) {
			outer.move(gameState);
		}

		gameState.getPlayer().move(gameState);
	}

	private void drawLowerPane() {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, FIELD_HEIGHT_IN_CELLS * CELL_SIZE, LOWER_PANE_WIDTH, LOWER_PANE_HEIGHT);
	}

	private void drawUnits() {
		List<InnerBall> inners = gameState.getInnerBalls();
		List<OuterBall> outers = gameState.getOuterBalls();
		Player player = gameState.getPlayer();

		gc.setFill(Color.WHITE);
		for (var inner : inners) {
			gc.fillOval(inner.getX() * CELL_SIZE, inner.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		}

		gc.setFill(Color.BLACK);
		for (var outer : outers) {
			gc.fillOval(outer.getX() * CELL_SIZE, outer.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		}

		gc.setFill(Color.RED);
		gc.fillOval(player.getX() * CELL_SIZE, player.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
	}

	private void drawField() {
		gc.clearRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT);

		final int height = gameState.getField().getHeight();
		final int width = gameState.getField().getWidth();

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				CellType type = gameState.getField().getCells()[i][j].getCellType();

				switch (type) {
				case OUTER:
					gc.setFill(Color.GREENYELLOW);
					break;
				case INNER:
					gc.setFill(Color.DARKGREEN);
					break;
				case DRAWING:
					gc.setFill(Color.WHITE);
					break;
				}

				gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
			}
		}
	}

	private void initGame() {
		gameState = new GameState(FIELD_WIDTH_IN_CELLS, FIELD_HEIGHT_IN_CELLS, new Player(0, 0),
				new ArrayList<>(Arrays.asList(new InnerBall(3, 5, new Direction(1, -1)),
						new InnerBall(3, 8, new Direction(-1, 1)), new InnerBall(4, 5, new Direction(-1, -1)))),
				new ArrayList<>(Arrays.asList(new OuterBall(6, FIELD_HEIGHT_IN_CELLS - 1, new Direction(1, 1)))));
	}

	public static void main(String[] args) {
		launch();
	}

}