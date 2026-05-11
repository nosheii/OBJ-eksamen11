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
        //En metode for å sortere data[] og vise resultatet i outputArea
        if (data == null) { // hvis ingen data er lastet inn
            outputArea.setText("Please load data before sorting."); // vis feilmelding
            return; 
        }
        quicksort(data, 0, data.length - 1);
        sorted = true;
        StringBuilder sb= new StringBuilder(); // bygg opp en streng for å vise sortert data
        for (int i = 0; i < data.length; i++) { // iterer gjennom data[] og legg til i sb
            sb.append(data[i]) .append(" "); // legg til tallet og et mellomrom
        }
        outputArea.setText(sb.toString()); // vis sortert data i outputArea 
    }
    // TODO: kall quicksort, sett sorted = true
    private void handleShow() {
        // TODO: vis innholdet av data[] i outputArea
    }

    private void handleSearch() {
        // TODO: kall binærsøk, vis resultat i outputArea
    }

    private void handleRem3() {
        // TODO: fjern 3 elementer fra indeks 3
    }

    private void quicksort(int[] arr, int low, int high) {
        if (low < high) { // base case: hvis low >= high, er det ingen elementer å sortere
            int pivotIndex = partition(arr, low, high); // finn pivot-indeks etter partisjonering
            quicksort(arr, low, pivotIndex - 1);  // venstre side av pivot
            quicksort(arr, pivotIndex + 1, high); // høyre side av pivot
            }
    }
    private int partition(int[] arr, int low, int high) { 
        int pivot = arr[high]; // velg siste element som pivot
        int i = low - 1; // i vil holde styr på den siste posisjonen for elementer mindre enn eller lik pivot

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
        // TODO: returner indeks eller -1
        return -1;
    }
}