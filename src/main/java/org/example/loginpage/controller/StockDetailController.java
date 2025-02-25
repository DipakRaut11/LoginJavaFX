package org.example.loginpage.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StockDetailController {


        @FXML
        private Label stockSymbolLabel;
        @FXML
        private Label stockPriceLabel;
        @FXML
        private Label stockFullNameLabel;
        @FXML
        private Label stockMarketLabel;
        @FXML
        private Label stockNameLabel;

        public void setStockDetails(DashboardController.Stock stock) {
            stockSymbolLabel.setText("Symbol: " + stock.getSymbol());
            stockPriceLabel.setText("Price: $" + stock.getPrice());
            stockFullNameLabel.setText("Full Name: " + stock.getFullName());
            stockMarketLabel.setText("Market: " + stock.getMarket());
            stockNameLabel.setText(stock.getFullName());
        }

        @FXML
        private void handleClose() {
            Stage stage = (Stage) stockSymbolLabel.getScene().getWindow();
            stage.close();
        }


}
