package com.exam;

import com.exam.currency.CurrencyConverterDemo;
import com.exam.queue.QueueDemo;
import com.exam.sort.SortSearchDemo;
import com.exam.thread.ThreadDemo;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * MainApp – entry point for the OBJ exam demo application.
 *
 * Starts a JavaFX main window with a dropdown menu containing
 * four demo choices. Each choice opens its own window (Stage)
 * while the main window stays open.
 *
 * @author Group [gruppenummer]
 * @version 1.0
 */
public class MainApp extends Application {

    /**
     * Builds and shows the main launcher window.
     *
     * @param stage the primary stage provided by JavaFX
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("OBJ Exam – Demo Launcher");

        // ── Title ────────────────────────────────────────────────────
        Label title = new Label("OBJ Exam – Demo Launcher");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        Label subtitle = new Label("Choose a demo and click Open");
        subtitle.setStyle("-fx-text-fill: #666;");

        // ── Dropdown ─────────────────────────────────────────────────
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(
                "Queue Demonstration",
                "Sort and Search Demonstration",
                "Thread Demonstration",
                "Currency Converter Demonstration");
        combo.setPromptText("— Choose a demo —");
        combo.setPrefWidth(300);

        // ── Error message ────────────────────────────────────────────
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red;");

        // ── Open button ──────────────────────────────────────────────
        Button openBtn = new Button("Open");
        openBtn.setPrefWidth(120);
        openBtn.setStyle(
                "-fx-background-color: #2563eb;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;");
        openBtn.setOnAction(e -> {
            if (combo.getValue() == null) {
                errorLabel.setText("Please choose a demo first!");
                return;
            }
            errorLabel.setText("");
            openDemo(combo.getValue());
        });

        // ── Layout ───────────────────────────────────────────────────
        VBox root = new VBox(14,
                title,
                subtitle,
                new Separator(),
                combo,
                openBtn,
                errorLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f8fafc;");

        stage.setScene(new Scene(root, 420, 280));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Opens the appropriate demo window based on the selected option in the dropdown.
     * Each demo window opens in a new Stage so that the main menu remains open.
     *
     * @param choice the selected option from ComboBox
     */
    private void openDemo(String choice) {
        try {
            switch (choice) {
                case "Queue Demonstration" -> new QueueDemo().start(new Stage());
                case "Sort and Search Demonstration" -> new SortSearchDemo().start(new Stage());
                case "Thread Demonstration" -> new ThreadDemo().start(new Stage());
                case "Currency Converter Demonstration" -> new CurrencyConverterDemo().start(new Stage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Standard Java main method – starts the JavaFX application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}