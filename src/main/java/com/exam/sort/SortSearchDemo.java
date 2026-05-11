package com.exam.sort;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SortSearchDemo extends Application {

    private int[] data;
    private boolean sorted = false;

    // UI-felt vi trenger tilgang til i flere metoder
    private TextField inputField;
    private TextField searchField;
    private TextArea outputArea;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sort and Search Demonstration");

        // ── Tittel ──────────────────────────────────────────
        Label title = new Label("Sort and Search Demonstration");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        // ── Inputfelt for tall ───────────────────────────────
        inputField = new TextField();
        inputField.setPromptText("Enter 8-15 integers separated by spaces");
        inputField.setPrefWidth(350);

        Button loadBtn = new Button("Load");
        loadBtn.setOnAction(e -> handleLoad());

        HBox inputRow = new HBox(8, new Label("Numbers:"), inputField, loadBtn);
        inputRow.setAlignment(Pos.CENTER_LEFT);

        // ── Operasjonsknapper ────────────────────────────────
        Button sortBtn = new Button("Sort");
        Button showBtn = new Button("Show");
        Button rem3Btn = new Button("REM3");

        sortBtn.setOnAction(e -> handleSort());
        showBtn.setOnAction(e -> handleShow());
        rem3Btn.setOnAction(e -> handleRem3());

        // ── Søkefelt + knapp ─────────────────────────────────
        searchField = new TextField();
        searchField.setPromptText("Search value");
        searchField.setPrefWidth(120);

        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(e -> handleSearch());

        HBox searchRow = new HBox(8, new Label("Search:"), searchField, searchBtn);
        searchRow.setAlignment(Pos.CENTER_LEFT);

        HBox btnRow = new HBox(10, sortBtn, showBtn, rem3Btn);
        btnRow.setAlignment(Pos.CENTER_LEFT);

        // ── Output-område ────────────────────────────────────
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(10);
        outputArea.setPromptText("Results will appear here...");

        // ── Samle alt i VBox ─────────────────────────────────
        VBox root = new VBox(12,
                title,
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

    // ── Metodeskjelett ────────────────────────────────────────

    private void handleLoad() {
        // Steg 1 - sjekk at feltet ikke er tomt
        if (inputField.getText().isEmpty()) {
            outputArea.setText("Please enter some numbers first.");
            return;
        }

        // Steg 2 - del opp teksten i enkelt-tall
        String[] tokens = inputField.getText().trim().split("\\s+");

        // Steg 3 - valider at antallet er mellom 8 og 15
        if (tokens.length < 8 || tokens.length > 15) {
            outputArea.setText("Error: Please enter between 8 and 15 integers.");
            return;
        }

        // Steg 4 - lagre tallene i data[]
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

    // TODO: les inputField, valider 8-15 tall, lagre i data[]

    private void handleSort() {
        // En metode for å sortere data[] og vise resultatet i outputArea
        if (data == null) { // hvis ingen data er lastet inn
            outputArea.setText("Please load data before sorting."); // vis feilmelding
            return;
        }
        quicksort(data, 0, data.length - 1);
        sorted = true;
        StringBuilder sb = new StringBuilder(); // bygg opp en streng for å vise sortert data
        for (int i = 0; i < data.length; i++) { // iterer gjennom data[] og legg til i sb
            sb.append(data[i]).append(" "); // legg til tallet og et mellomrom
        }
        outputArea.setText("Tallene er sortert!"); // vis sortert data i outputArea
    }

    // TODO: kall quicksort, sett sorted = true
    private void handleShow() {
        // Steg 1 - sjekk at data er sortert og lastet inn
        if (data == null || !sorted) {
            outputArea.setText(
                    (data == null) ? "Please load data before showing." : "Please sort the data before showing.");
            return;
        }

        // Steg 3 - bygg opp og vis innholdet
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

            // Binærsøk
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
        // Må ha minst 7 elementer - indeks 0,1,2 før + indeks 3,4,5 som fjernes + minst
        // 1 etter
        if (data.length < 7) {
            outputArea.setText("Error: Not possible to remove 3 more elements.");
            return;
        }

        int startIndex = 3;
        int removeCount = 3;

        // Nytt array er 3 mindre
        int[] newData = new int[data.length - removeCount];

        // Kopier elementene FØR indeks 3
        for (int i = 0; i < startIndex; i++) {
            newData[i] = data[i];
        }

        // Kopier elementene ETTER de fjernede
        for (int i = startIndex + removeCount; i < data.length; i++) {
            newData[i - removeCount] = data[i];
        }

        data = newData;
        outputArea.setText("Removed 3 elements from index 3. Array now has " + data.length + " elements.");
    }

    private void quicksort(int[] arr, int low, int high) {

        if (low < high) { // base case: hvis low >= high, er det ingen elementer å sortere
            int pivotIndex = partition(arr, low, high); // finn pivot-indeks etter partisjonering
            quicksort(arr, low, pivotIndex - 1); // venstre side av pivot
            quicksort(arr, pivotIndex + 1, high); // høyre side av pivot
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // velg siste element som pivot
        int i = low - 1; // i vil holde styr på den siste posisjonen for elementer mindre enn eller lik
                         // pivot

        for (int j = low; j < high; j++) { // iterer gjennom elementene fra low til high-1
            if (arr[j] <= pivot) { // hvis elementet er mindre enn eller lik pivot
                i++; // flytt i opp for å inkludere dette elementet
                int temp = arr[i]; // bytt arr[i] og arr[j]
                arr[i] = arr[j]; // flytt arr[j] til posisjonen i
                arr[j] = temp; // flytt det tidligere arr[i] til posisjonen j
            }
        }
        int temp = arr[i + 1]; // bytt arr[i + 1] og arr[high] for å plassere pivot på riktig sted
        arr[i + 1] = arr[high]; // flytt pivot til posisjonen i + 1
        arr[high] = temp; // flytt det tidligere arr[i + 1] til posisjonen high

        return i + 1; // returner den nye pivot-indeksen
    }

    private int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return mid; // funnet!
            } else if (arr[mid] < target) {
                low = mid + 1; // søk i høyre halvdel
            } else {
                high = mid - 1; // søk i venstre halvdel
            }
        }
        return -1; // ikke funnet
    }
}