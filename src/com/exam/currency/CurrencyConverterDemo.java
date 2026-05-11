package com.exam.currency;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class CurrencyConverterDemo extends Application {

    // Exchange rates – all relative to EUR
    double getRate(String currency) {
        if (currency.equals("EUR")) return 1.0;
        if (currency.equals("USD")) return 1.1762;
        if (currency.equals("JPY")) return 183.74;
        if (currency.equals("GBP")) return 0.86370;
        if (currency.equals("SEK")) return 10.8335;
        if (currency.equals("DKK")) return 7.4725;
        if (currency.equals("CHF")) return 0.9165;
        if (currency.equals("CAD")) return 1.5999;
        if (currency.equals("AUD")) return 1.6238;
        if (currency.equals("NOK")) return 10.8980;
        return 1.0;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Currency Converter");

        // COMPONENTS
        ComboBox<String> fromCurrency = new ComboBox<>();
        fromCurrency.getItems().addAll("EUR","USD","JPY","GBP","SEK","DKK","CHF","CAD","AUD","NOK");
        fromCurrency.setValue("EUR");

        ComboBox<String> toCurrency = new ComboBox<>();
        toCurrency.getItems().addAll("EUR","USD","JPY","GBP","SEK","DKK","CHF","CAD","AUD","NOK");
        toCurrency.setValue("NOK");

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount...");

        Button convertButton = new Button("Convert");
        Label resultLabel = new Label("");
        Label errorLabel = new Label("");

        // BUTTON LOGIC
        convertButton.setOnAction(e -> {
            String inputText = amountField.getText();

            // Validation
            if (inputText.isEmpty()) {
                errorLabel.setText("Please enter an amount");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(inputText);
            } catch (NumberFormatException ex) {
                errorLabel.setText("Invalid input – numbers only");
                return;
            }

            if (amount < 0) {
                errorLabel.setText("Amount cannot be negative");
                return;
            }

            // Conversion
            double fromRate        = getRate(fromCurrency.getValue());
            double toRate          = getRate(toCurrency.getValue());
            double amountInEUR     = amount / fromRate;
            double convertedAmount = amountInEUR * toRate;

            // Display result
            errorLabel.setText("");
            if (toCurrency.getValue().equals("JPY")) {
                resultLabel.setText(amount + " " + fromCurrency.getValue() + " = " + Math.round(convertedAmount) + " JPY");
            } else {
                resultLabel.setText(amount + " " + fromCurrency.getValue() + " = " + String.format("%.2f", convertedAmount) + " " + toCurrency.getValue());
            }
        });

        // LAYOUT
        VBox layout = new VBox(10, fromCurrency, toCurrency, amountField, convertButton, resultLabel, errorLabel);
        layout.setPadding(new Insets(20));

        stage.setScene(new Scene(layout, 400, 300));
        stage.show();
    }
}