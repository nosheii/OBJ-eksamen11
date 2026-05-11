package com.exam.thread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ThreadDemo extends Application {

    private Label inputLabel;
    private TextField inputField;
    private Button startButton;
    private TextArea outputArea;
    private Label statusLabel;

    @Override
    public void start(Stage stage) {

        // Lag komponentene
        inputLabel  = new Label("Skriv inn N (antall tall):");
        inputField  = new TextField();
        startButton = new Button("Start");
        outputArea  = new TextArea();
        statusLabel = new Label("");

        // Plasser dem i en VBox
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                inputLabel,
                inputField,
                startButton,
                outputArea,
                statusLabel);

        // Knappen starter trådene
        startButton.setOnAction(e -> {
            int N = Integer.parseInt(inputField.getText());
            long[] fibArray = new long[N];

            // Tråd 1 – beregn og vis Fibonacci
            Thread fibThread = new Thread(() -> {
                for (int i = 0; i < N; i++) {
                    long result = fibRecursive(i);
                    fibArray[i] = result;

                    int index = i; // kopi av i som ikke endrer seg
                    Platform.runLater(() -> {
                        outputArea.appendText("F(" + index + ") = " + result + "\n");
                    });
                }
            });
            fibThread.start();

            // Tråd 2 – finn oddetall og skriv til fil
            Thread oddThread = new Thread(() -> {
                try {
                    fibThread.join(); // vent til Tråd 1 er ferdig

                    new File("myobjfile").mkdirs();

                    BufferedWriter bw = new BufferedWriter(
                            new FileWriter("myobjfile/fibo1.txt"));

                    for (int i = 0; i < N; i++) {
                        if (fibArray[i] % 2 != 0) {
                            bw.write("F(" + i + ") = " + fibArray[i]);
                            bw.newLine();
                        }
                    }

                    bw.close();

                    Platform.runLater(() -> {
                        statusLabel.setText("Odde tall lagret i myobjfile/fibo1.txt!");
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            oddThread.start();
        });

        // Vis vinduet
        stage.setTitle("Fibonacci Threads");
        stage.setScene(new Scene(root, 400, 500));
        stage.show();
    }

    // Rekursiv Fibonacci-metode
    public static long fibRecursive(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }
}