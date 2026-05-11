/**
 * Queue Demonstration:
 * Array-based circular queue implementation.
 * Supports add, remove, show, and sort operations.
 * F = front index, R = rear index, COUNT = number of elements.
 */

package com.exam.queue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class QueueDemo extends Application {

    /**
     Queue variables:
     front = F
     rear = R
     count = number of elements
     capacity = Max size
     */

    private int[] queue;
    private int front = 0;
    private int rear = -1;
    private int count = 0;
    private int capacity = 0;

    // JavaFX elements (fields so button logic can access them)
    private TextField valueInput  = new TextField();
    private Label countLabel      = new Label("COUNT: 0");
    private Label frontLabel      = new Label("F: 0");
    private Label rearLabel       = new Label("R: -1");
    private Label messageLabel    = new Label("");

    // Add/Remove/Show/Sort buttons (disabled until queue is created)
    private Button addButton    = new Button("Add");
    private Button removeButton = new Button("Remove");
    private Button showButton   = new Button("Show Queue");
    private Button sortButton   = new Button("Sort Queue");

    @Override
    public void start(Stage stage) {
        stage.setTitle("Queue Demonstration");

        // ── Title ─────────────────────────────────────────────────────
        Label titleLabel = new Label("Queue Demonstration");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // ── Step 1: Create queue ───────────────────────────────────────
        Label step1Label = new Label("Step 1 – Create a queue");
        step1Label.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Label sizeHintLabel = new Label("Enter the maximum number of elements (N) and click 'Create Queue':");

        TextField sizeInput = new TextField();
        sizeInput.setPromptText("e.g. 5");
        sizeInput.setMaxWidth(80);

        Button initButton = new Button("Create Queue");

        // Disable queue buttons until queue is created
        addButton.setDisable(true);
        removeButton.setDisable(true);
        showButton.setDisable(true);
        sortButton.setDisable(true);

        // ── Step 2: Add/Remove values ──────────────────────────────────
        Label step2Label = new Label("Step 2 – Add or remove values");
        step2Label.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Label valueHintLabel = new Label(
            "Enter an integer value and click 'Add' to enqueue,\n" +
            "or click 'Remove' to dequeue the front element:"
        );

        valueInput.setPromptText("e.g. 42");
        valueInput.setMaxWidth(120);

        // ── Step 3: Show / Sort ────────────────────────────────────────
        Label step3Label = new Label("Step 3 – View or sort the queue");
        step3Label.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Label showHintLabel = new Label(
            "'Show Queue' displays all current elements.\n" +
            "'Sort Queue' displays a sorted copy (original queue unchanged)."
        );

        // ── Queue status labels ────────────────────────────────────────
        Label statusTitle = new Label("Queue Status:");
        statusTitle.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        HBox statusBox = new HBox(20, countLabel, frontLabel, rearLabel);

        // ── Message/result label ───────────────────────────────────────
        messageLabel.setStyle("-fx-text-fill: #1565c0; -fx-font-size: 13px;");
        messageLabel.setWrapText(true);

        /**
        Queue demonstration - Array-Based Circular Queue

        A queue follows FIFO: First In, First Out
        front (F): points to the first element (where elements are removed)
        rear (R): points to the last element (where elements are added)
        count: tracks the number of elements currently in the queue
        Circular logic: rear = (rear + 1) % capacity
        Full check: count == capacity
        Empty check: count == 0
        Sort copies the queue into a new array, original remains unchanged
        */

        initButton.setOnAction(e -> {
            try {
                int n = Integer.parseInt(sizeInput.getText().trim());
                if (n <= 0) {
                    messageLabel.setText("N must be greater than 0.");
                    return;
                }

                capacity = n;
                queue    = new int[capacity];
                front    = 0;
                rear     = -1;
                count    = 0;
                updateLabels();
                messageLabel.setText("Queue created with capacity " + n + ". You can now add elements.");

                addButton.setDisable(false);
                removeButton.setDisable(false);
                showButton.setDisable(false);
                sortButton.setDisable(false);

            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid input – please enter a whole number.");
            }
        });

        /**
        Add button logic:
        - If queue is full, show error and stop
        - Circular rear: rear = (rear + 1) % capacity
        - Throws exception if invalid input
        */
        addButton.setOnAction(e -> {
            if (count == capacity) {
                messageLabel.setText("Queue is full – cannot add more items.");
                return;
            }
            try {
                int value = Integer.parseInt(valueInput.getText().trim());
                rear = (rear + 1) % capacity;
                queue[rear] = value;
                count++;
                updateLabels();
                messageLabel.setText(value + " was added in the queue.");
                valueInput.clear();
            } catch (NumberFormatException ex) {
                messageLabel.setText("Invalid input – numbers only.");
            }
        });

        /**
        Remove button logic:
        - Check if queue is empty → show error and stop
        - Get value at queue[front], move front forward, decrement count
        */
        removeButton.setOnAction(e -> {
            if (count == 0) {
                messageLabel.setText("Queue is empty – nothing to remove.");
                return;
            }
            int removed = queue[front];
            front = (front + 1) % capacity;
            count--;
            updateLabels();
            messageLabel.setText(removed + " was deleted from the queue.");
        });

        /**
        Show button logic:
        - Loop through count elements starting from front
        - Use (front + i) % capacity to handle circular wrapping
        - Display each element separated by "|"
        */
        showButton.setOnAction(e -> {
            if (count == 0) {
                messageLabel.setText("Queue is empty.");
                return;
            }
            StringBuilder sb = new StringBuilder("Queue: ");
            for (int i = 0; i < count; i++) {
                sb.append(queue[(front + i) % capacity]);
                if (i < count - 1) sb.append(" | ");
            }
            messageLabel.setText(sb.toString());
        });

        /**
        Sort button logic (copies queue, does not modify original):
        - Copy current queue contents into a new array
        - Bubble sort on the copy
        - Display sorted copy
        */
        sortButton.setOnAction(e -> {
            if (count == 0) {
                messageLabel.setText("Queue is empty – nothing to sort.");
                return;
            }

            int[] sorted = new int[count];
            for (int i = 0; i < count; i++) {
                sorted[i] = queue[(front + i) % capacity];
            }

            for (int i = 0; i < sorted.length - 1; i++) {
                for (int j = 0; j < sorted.length - 1 - i; j++) {
                    if (sorted[j] > sorted[j + 1]) {
                        int temp      = sorted[j];
                        sorted[j]     = sorted[j + 1];
                        sorted[j + 1] = temp;
                    }
                }
            }

            StringBuilder sb = new StringBuilder("Sorted copy: ");
            for (int i = 0; i < sorted.length; i++) {
                sb.append(sorted[i]);
                if (i < sorted.length - 1) sb.append(" | ");
            }
            messageLabel.setText(sb.toString());
        });

        // Layout
        VBox layout = new VBox(8,
            titleLabel,
            new Separator(),
            step1Label,
            sizeHintLabel,
            new HBox(10, sizeInput, initButton),
            new Separator(),
            step2Label,
            valueHintLabel,
            new HBox(10, valueInput, addButton, removeButton),
            new Separator(),
            step3Label,
            showHintLabel,
            new HBox(10, showButton, sortButton),
            new Separator(),
            statusTitle,
            statusBox,
            messageLabel
        );
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 440, 520));
        stage.show();
    }

    // Updates the F, R, and COUNT labels
    private void updateLabels() {
        countLabel.setText("COUNT: " + count);
        frontLabel.setText("F: " + front);
        rearLabel.setText("R: " + rear);
    }
}