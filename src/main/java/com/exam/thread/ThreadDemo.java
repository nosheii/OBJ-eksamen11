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
    private Label descLabel;

    @Override
    public void start(Stage stage) {

        // Make the components
        inputLabel  = new Label("Enter N (number of integers):");
        inputField  = new TextField();
        startButton = new Button("Start");
        outputArea  = new TextArea();
        statusLabel = new Label("");
        descLabel = new Label(
            "This program demonstrates two threads running simultaneously.\n" +
            "Thread 1 calculates Fibonacci numbers recursively and displays them on screen.\n" +
            "Thread 2 finds all odd Fibonacci numbers and saves them to a file."
        );
        descLabel.setWrapText(true);

        // Put them in a VBox
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                inputLabel,
                descLabel,
                inputField,
                startButton,
                outputArea,
                statusLabel);

        // Button starts the thread
        startButton.setOnAction(e -> {
            int N = Integer.parseInt(inputField.getText());
            long[] fibArray = new long[N];

            // Thread 1 – calculate and show the Fibonacci numbers
            Thread fibThread = new Thread(() -> {
                for (int i = 0; i < N; i++) {
                    long result = fibRecursive(i);
                    fibArray[i] = result;

                    int index = i; // copy of i that doesn't change
                    Platform.runLater(() -> {
                        outputArea.appendText("F(" + index + ") = " + result + "\n");
                    });
                }
            });
            fibThread.start();

            // Thread 2 – Find Odd numbers and save to a separate text file
            Thread oddThread = new Thread(() -> {
                try {
                    fibThread.join(); // wait until the Fibonacci thread is done

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
                        statusLabel.setText("Odd numbers have been saved in myobjfile/fibo1.txt!");
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            oddThread.start();
        });

        // Show window
        stage.setTitle("Fibonacci Threads");
        stage.setScene(new Scene(root, 400, 500));
        stage.show();
    }

    //Recursively Fibonacci-method
    public static long fibRecursive(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }
}