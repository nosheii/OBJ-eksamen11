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

        // ── Tittel ──────────────────────────────────────────────────
        Label title = new Label("OBJ Exam – Demo Launcher");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        Label subtitle = new Label("Velg en demo og trykk Open");
        subtitle.setStyle("-fx-text-fill: #666;");

        // ── Dropdown ────────────────────────────────────────────────
        ComboBox<String> combo = new ComboBox<>();
        combo.getItems().addAll(
                "Queue Demonstration",
                "Sort and Search Demonstration",
                "Thread Demonstration",
                "Currency Converter Demonstration");
        combo.setPromptText("— Velg en demo —");
        combo.setPrefWidth(300);

        // ── Feilmelding ─────────────────────────────────────────────
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red;");

        // ── Open-knapp ──────────────────────────────────────────────
        Button openBtn = new Button("Open");
        openBtn.setPrefWidth(120);
        openBtn.setStyle(
                "-fx-background-color: #2563eb;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;");
        openBtn.setOnAction(e -> {
            if (combo.getValue() == null) {
                errorLabel.setText("Velg en demo først!");
                return;
            }
            errorLabel.setText("");
            openDemo(combo.getValue());
        });

        // ── Layout ──────────────────────────────────────────────────
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
     * Åpner riktig demo-vindu basert på valgt alternativ i dropdown.
     * Hvert demo-vindu åpnes i en ny Stage slik at hovedmenyen forblir åpen.
     *
     * @param choice det valgte alternativet fra ComboBox
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
     * Standard Java main-metode – starter JavaFX-applikasjonen.
     *
     * @param args kommandolinjeargumenter (brukes ikke)
     */
    public static void main(String[] args) {
        launch(args);
    }
}