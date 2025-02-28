package org.example.loginpage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.loginpage.timeUpdater.LoginTimeUpdater;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DashboardController {

    @FXML
    private Button logoutButton;
    @FXML
    private ListView<String> stockList;
    @FXML
    private ListView<String> priceList;
    @FXML
    private TextField stockInput;
    @FXML
    private Label stockPriceLabel;


    private String loggedInUsername;

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
    }

    private final Map<String, Stock> stockData = new HashMap<>();

    public void initialize() {
        stockData.put("AAPL", new Stock("AAPL", "Apple Icorporated.", 175.50, "NASDAQ"));
        stockData.put("GOOGL", new Stock("GOOGL", "Alphabet Incorporated", 2800.75, "NASDAQ"));
        stockData.put("AMZN", new Stock("AMZN", "Amazon.com, Incorporated", 3400.60, "NASDAQ"));
        stockData.put("TSLA", new Stock("TSLA", "Tesla, Incorporated", 950.30, "NASDAQ"));

        for (Stock stock : stockData.values()) {
            stockList.getItems().add(stock.getSymbol());
            priceList.getItems().add("$" + stock.getPrice());
        }

        stockList.setOnMouseClicked(event -> {
            String selectedStock = stockList.getSelectionModel().getSelectedItem();
            if (selectedStock != null) {
                openStockDetail(stockData.get(selectedStock));
            }
        });
    }

    @FXML
    private void checkStockPrice() {
        String stockSymbol = stockInput.getText().toUpperCase();
        if (stockData.containsKey(stockSymbol)) {
            stockPriceLabel.setText("Price: $" + stockData.get(stockSymbol).getPrice());
            openStockDetail(stockData.get(stockSymbol));
        } else {
            showAlert("Stock Not Found", "Please enter a valid stock symbol.");
        }
    }

    private void openStockDetail(Stock stock) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/loginpage/StockDetail.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            StockDetailController controller = loader.getController();
            if (controller != null) {
                controller.setStockDetails(stock);
            } else {
                showAlert("Error", "Stock detail page failed to load.");
            }

            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to open stock detail page: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleLogout() {
        if (loggedInUsername != null) {
            LoginTimeUpdater.updateLogoutTime(loggedInUsername);
        }
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }
    public static class Stock {
        private final String symbol;
        private final String fullName;
        private final double price;
        private final String market;

        public Stock(String symbol, String fullName, double price, String market) {
            this.symbol = symbol;
            this.fullName = fullName;
            this.price = price;
            this.market = market;
        }

        public String getSymbol() { return symbol; }
        public String getFullName() { return fullName; }
        public double getPrice() { return price; }
        public String getMarket() { return market; }
    }
}
