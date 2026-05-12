package com.exam.sort;

import java.lang.reflect.Method;

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
 * Sort and Search Demonstration:
 * This program demonstrates sorting and searching algorithms.
 * Users can input a list of integers, sort them using quicksort, display the sorted array, 
 * search for specific values using binary search, and remove elements from the array.
 * Authors: 7180, 7186, 7237
 */

public class SortSearchDemo extends Application {

    private int[] data;
    private boolean sorted = false;

    // UI fields we need access to in multiple methods
    private TextField inputField;
    private TextField searchField;
    private TextArea outputArea;

    /**
     * Starts the sort and search demonstration application.
     * The UI includes a title, subtitle, input field for numbers, buttons for sorting,
     * showing, removing elements, a search field with a button, and an output area for results.
     * The load button validates the input and stores it in an array. The sort button sorts the array using quicksort.
     * The show button displays the sorted array. The search button performs a binary search for the specified value.
     * The rem3 button removes 3 elements starting from index 3. All operations check for proper loading and sorting before executing.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Sort and Search Demonstration");

        // ── Title ────────────────────────────────────────────
        Label title = new Label("Sort and Search Demonstration");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        // –– Subtitle –––––––––––––––––––––––––––––––––––––––––
        Label subtitle = new Label(
                "Enter 8-15 numbers separated by space, sort them, then search or remove 3 elements at a time.");
        subtitle.setStyle("-fx-text-fill: #666; -fx-font-size: 12;");

        // ── Input field for numbers ──────────────────────────
        inputField = new TextField();
        inputField.setPromptText("Enter 8-15 integers separated by spaces");
        inputField.setPrefWidth(350);

        Button loadBtn = new Button("Load");
        loadBtn.setOnAction(e -> handleLoad());

        HBox inputRow = new HBox(8, new Label("Numbers:"), inputField, loadBtn);
        inputRow.setAlignment(Pos.CENTER_LEFT);

        // ── Operation buttons ────────────────────────────────
        Button sortBtn = new Button("Sort");
        Button showBtn = new Button("Show");
        Button rem3Btn = new Button("REM3");

        sortBtn.setOnAction(e -> handleSort());
        showBtn.setOnAction(e -> handleShow());
        rem3Btn.setOnAction(e -> handleRem3());

        // ── Search field + button ────────────────────────────
        searchField = new TextField();
        searchField.setPromptText("Search value");
        searchField.setPrefWidth(120);

        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(e -> handleSearch());

        HBox searchRow = new HBox(8, new Label("Search:"), searchField, searchBtn);
        searchRow.setAlignment(Pos.CENTER_LEFT);

        HBox btnRow = new HBox(10, sortBtn, showBtn, rem3Btn);
        btnRow.setAlignment(Pos.CENTER_LEFT);

        // ── Output area ──────────────────────────────────────
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(10);
        outputArea.setPromptText("Results will appear here...");

        // ── Combine everything in VBox ───────────────────────
        VBox root = new VBox(12,
                title,
                subtitle,
                new Separator(),
                inputRow,
                btnRow,
                searchRow,
                new Label("Output:"),
                outputArea);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #ffffff;");

        stage.setScene(new Scene(root, 560, 440));
        stage.setResizable(false);
        stage.show();
    }

 

    /** 
     * Method to load data from the input field. It validates that the input is not empty, 
     * contains between 8 and 15 integers, and stores them in the data[] array. 
     * If validation fails, an appropriate error message is displayed in the output area.
     */
    private void handleLoad() {
        // Step 1 - check if the field is empty
        if (inputField.getText().isEmpty()) {
            outputArea.setText("Please enter some numbers first.");
            return;
        }

        // Step 2 - split the text into individual numbers
        String[] tokens = inputField.getText().trim().split("\\s+");

        // Step 3 - validate that the number of elements is between 8 and 15
        if (tokens.length < 8 || tokens.length > 15) {
            outputArea.setText("Error: Please enter between 8 and 15 integers.");
            return;
        }

        // Step 4 - store the numbers in data[]
        try {
            data = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                data[i] = Integer.parseInt(tokens[i]);
            }
            sorted = false;
            outputArea.setText("Loaded " + tokens.length + " numbers successfully. Press Sort!");
        } catch (NumberFormatException e) {
            outputArea.setText("Error: Only integers allowed!");
        }
    }

    /**
    Method to sort data[] using the quicksort algorithm.
    If data[] is not loaded, an error message is displayed.
    After sorting, sorted is set to true.
     */
    private void handleSort() {
        if (data == null) {
            outputArea.setText("Please load data before sorting.");
            return;
        }
        quicksort(data, 0, data.length - 1);
        sorted = true;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]).append(" ");
        }
        outputArea.setText("Numbers are sorted!");
    }
    /**
    Method to display the contents of data[] in the output area. 
    It checks if data[] is loaded and sorted before displaying.
     */
    private void handleShow() {
        // Step 1 - check that data is sorted and loaded
        if (data == null || !sorted) {
            outputArea.setText(
                    (data == null) ? "Please load data before showing." : "Please sort the data before showing.");
            return;
        }

        // Step 3 - build and display the contents
        StringBuilder sb = new StringBuilder("Array contents: ");
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]).append(" ");
        }
        outputArea.setText(sb.toString());
    }

    private void handleSearch() {
        if (data == null) {
            outputArea.setText("Please load data before searching.");
            return;
        }
        if (!sorted) {
            outputArea.setText("Please sort the data before searching.");
            return;
        }
        String input = searchField.getText();
        if (input.isEmpty()) {
            outputArea.setText("Please enter a number to search for.");
            return;
        }
        try {
            int target = Integer.parseInt(input);

            // Too high / Too low hint
            if (target > data[data.length - 1]) {
                outputArea.setText("Too high! Max value is " + data[data.length - 1]);
                return;
            }
            if (target < data[0]) {
                outputArea.setText("Too low! Min value is " + data[0]);
                return;
            }

            // Binary search
            int index = binarySearch(data, target);
            if (index != -1) {
                outputArea.setText("Value " + target + " was found at position " + index + " in the array.");
            } else {
                outputArea.setText("Value " + target + " was not found in the array.");
            }
        } catch (NumberFormatException e) {
            outputArea.setText("Error: Please enter a valid integer.");
        }
    }

    private void handleRem3() {
        if (data == null) {
            outputArea.setText("Please load data before removing elements.");
            return;
        }
        if (!sorted) {
            outputArea.setText("Please sort the data before removing elements.");
            return;
        }
        // Must have at least 6 elements - index 0,1,2 before + index 3,4,5 to be removed + at least
        // 1 after
        if (data.length < 6) {
            outputArea.setText("Error: Not possible to remove 3 more elements.");
            return;
        }

        int startIndex = 3;
        int removeCount = 3;

        // New array is 3 elements smaller
        int[] newData = new int[data.length - removeCount];

        // Copy elements BEFORE index 3
        for (int i = 0; i < startIndex; i++) {
            newData[i] = data[i];
        }

        // Copy elements AFTER the removed ones
        for (int i = startIndex + removeCount; i < data.length; i++) {
            newData[i - removeCount] = data[i];
        }

        data = newData;
        outputArea.setText("Removed 3 elements from index 3. Array now has " + data.length + " elements.");
    }

    /*
     * Quicksort algorithm. It takes an array and sorts it in-place between indices
     * low and high.
     * If low is less than high, it means there is more than one element to sort.
     * We call partition to find the pivot index, and then recursively sort
     * the two parts of the array.
     */
    private void quicksort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quicksort(arr, low, pivotIndex - 1);
            quicksort(arr, pivotIndex + 1, high);
        }
    }

    /*
     * Partition method for quicksort. It takes the last element as the pivot,
     * and arranges the elements such that all elements less than or equal to the pivot are on the
     * left,
     * and all elements greater than the pivot are on the right.
     * It returns the new index of the pivot after partitioning.
     */
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        /*
         * We iterate through all elements from low to high-1. If we find an
         * element that is less than or equal to the pivot,
         * we increment i and swap that element with the element at position i.
         */
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        /*
         * After the loop, i is at the last position for elements less than or equal to
         * the pivot.
         * Now we swap the pivot (arr[high]) with the element at i+1 to place the pivot in
         * the correct position.
         */
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    /*
     * Binary search algorithm. It takes a sorted array and a target,
     * and returns the index of the target if it exists,
     * otherwise returns -1.
     */
    private int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return mid; // found!
            } else if (arr[mid] < target) {
                low = mid + 1; // search in the right half
            } else {
                high = mid - 1; // search in the left half
            }
        }
        return -1; // not found
    }
}