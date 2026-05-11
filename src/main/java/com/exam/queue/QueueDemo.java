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
import javafx.stage.Stage;

public class QueueDemo extends Application {

    /**
     Queue variables:
     front = F
     rear = R
     count = number of elements
     capasity = Max size
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
    private Button showButton   = new Button("Show");
    private Button sortButton   = new Button("Sort");

    @Override
    public void start(Stage stage) {
        stage.setTitle("Queue Demonstration");

        /**
        Size input + Create button:
        Choose a valid queue capacity and create queue

        disable queue buttons until queue is created

        enter values for queue until capacity is reached
        */
        TextField sizeInput = new TextField();
        sizeInput.setPromptText("Enter queue capacity N...");

        Button initButton = new Button("Create Queue");

        addButton.setDisable(true);
        removeButton.setDisable(true);
        showButton.setDisable(true);
        sortButton.setDisable(true);

        valueInput.setPromptText("Enter value...");


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

        Enables buttons so user can edit the queue

        Throws exception if value is invalid
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
                messageLabel.setText("Queue created with capacity " + n + ".");

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

        If queue is full, user cannot add more items

        Circular logic:
        - rear starts at -1 (empty queue)
        - rear moves forward by 1 each time an element is added
        - % capacity ensures rear wraps back to 0 when it reaches the end

        Throws exception if invalid input
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
        Remove button logic
        - Check if queue is empty, if it is, show error and stop
        - Else
            - get value at queue[front]
            - Move front forward (+1)
            - Count decrements and update label
            - Display message

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
        Show button logic
        Check if queue is empty (count == 0) → show error and stop
        - Loop through count elements starting from front
        - Use (front + i) % capacity to handle circular wrapping
        - Display each element separated by "|"
        Example: "Queue: 20 | 30 | 40 | 50 | 60"

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
        Sort button logic (copies queue, does not modify original)

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
                        int temp    = sorted[j];
                        sorted[j]   = sorted[j + 1];
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

        //Layout
        VBox layout = new VBox(10,
            sizeInput,
            initButton,
            countLabel,
            frontLabel,
            rearLabel,
            valueInput,
            addButton,
            removeButton,
            showButton,
            sortButton,
            messageLabel
        );
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 450));
        stage.show();
    }

    // Updates the F, R, and COUNT labels
    private void updateLabels() {
        countLabel.setText("COUNT: " + count);
        frontLabel.setText("F: " + front);
        rearLabel.setText("R: " + rear);
    }
}