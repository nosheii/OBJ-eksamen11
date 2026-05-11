// Eksempel – lim tilsvarende inn i alle fire demo-filer med riktig pakkenavn
package com.exam.sort;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SortSearchDemo extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Sort and Search Demonstration");
        stage.setScene(new Scene(new StackPane(new Label("Under bygging...")), 400, 300));
        stage.show();
    }
}