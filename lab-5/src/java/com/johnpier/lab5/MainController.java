package com.johnpier.lab5;

import com.johnpier.lab5.entities.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    private final Calculator calculator = Calculator.getInstance();

    @FXML
    private Label welcomeText;

    @FXML
    private TextField resultInput;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onNumberButtonClick(ActionEvent event) {
        var button = (Button)event.getSource();
        try {
            int number = Integer.parseInt(button.getText());
            System.out.println(number);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public MainController() {
    }
}