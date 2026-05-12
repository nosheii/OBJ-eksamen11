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

        // –– Subtittel –––––––––––––––––––––––––––––––––––––––––
        Label subtitle = new Label(
                "Enter 8-15 numbers sepereated by space, sort them, then search or remove 3 elements at a time.");
        subtitle.setStyle("-fx-text-fill: #666; -fx-font-size: 12;");

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

    /**
     * Metode for å sortere data[] ved hjelp av quicksort-algoritmen.
     * Hvis data[] ikke er lastet inn, vises en feilmelding.
     * Etter sortering settes sorted = true.
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
        outputArea.setText("Tallene er sortert!");
    }

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
        // Må ha minst 6 elementer - indeks 0,1,2 før + indeks 3,4,5 som fjernes + minst
        // 1 etter
        if (data.length < 6) {
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

    /*
     * Quicksort-algoritme. Den tar et array og sorterer det in-place mellom indeks
     * low og high.
     * Hvis low er mindre enn high, betyr det at det er mer enn ett element å
     * sortere.
     * Vi kaller partition for å finne pivot-indeksen, og deretter rekursivt sortere
     * de to delene av arrayet.
     */
    private void quicksort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quicksort(arr, low, pivotIndex - 1);
            quicksort(arr, pivotIndex + 1, high);
        }
    }

    /*
     * Partisjoneringsmetode for quicksort. Den tar det siste elementet som pivot,
     * og ordner elementene slik at alle elementer mindre enn eller lik pivot er til
     * venstre,
     * og alle elementer større enn pivoter til høyre.
     * Den returnerer den nye indeksen for pivot etter partisjoneringen.
     */
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        /*
         * Vi itererer gjennom alle elementene fra low til high-1. Hvis vi finner et
         * element som er mindre enn eller lik pivot,
         * flytter vi i opp og bytter det elementet med elementet på posisjonen i.
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
         * Etter løkken, i er på den siste posisjonen for elementer mindre enn eller lik
         * pivot.
         * Nå bytter vi pivot (arr[high]) med elementet på i+1 for å plassere pivot på
         * riktig sted.
         */
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    /*
     * Binærsøk-algoritme. Den tar et sortert array og et mål,
     * og returnerer indeksen til målet hvis det finnes,
     * ellers returnerer -1.
     */
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