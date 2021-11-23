package com.johnpier.lab5;

import com.johnpier.lab5.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    private final int MAX_NUMBER_LENGTH = 11;

    private final Calculator calculator = Calculator.getInstance();

    @FXML
    private Label welcomeText;

    @FXML
    private TextField resultInput;

    @FXML
    private Button deleteButton;

    public MainController() {
    }

    @FXML
    //todo: exceptions, operation trace, *.0 ,
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onNumberButtonClick(ActionEvent event) {
        var button = (Button)event.getSource();
        if(readText().length() >= MAX_NUMBER_LENGTH) {
            return;
        }
        if (calculator.isOperationExist() && calculator.isOperationSecondValueNotDefined()) {
            calculator.setOperationSecondValueIsDefined();
            writeText("0");
        }
        var value = readText();
        if(value.equals("0")) {
            writeText(button.getText());
        } else {
            writeText(readText() + button.getText());
        }
    }

    @FXML
    protected void onDelButtonClick() {
        var value = readText();
        if(value.length() == 0) {
            return;
        }
        if(value.length() == 1) {
            writeText("0");
            return;
        }
        writeText(value.substring(0, value.length() - 1));
    }

    @FXML
    protected void onCEButtonClick() {
        writeText(0);
    }

    @FXML
    protected void onClearButtonClick() {
        onCEButtonClick();
        calculator.resetOperation();
    }

    @FXML
    protected void onAddPointClick() {
        var value = readText();
        if (value.contains(".")) {
            return;
        }
        if(value.length() > 0) {
            writeText(value + ".");
        } else {
            writeText("0.");
        }
    }

    @FXML
    protected void onPlusActionClick() {
        try {
            var number = checkPrevOperation();
            calculator.setOperation(value -> number + value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onMinusActionClick() {
        try {
            var number = checkPrevOperation();
            calculator.setOperation(value -> number - value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onDivideActionClick() {
        try {
            var number = checkPrevOperation();
            calculator.setOperation(value -> {
                if (value == 0) {
                    new Alert(Alert.AlertType.ERROR, "Error in calculation! Please, check typed value.").showAndWait();
                    return number;
                }
                return number / value;
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onMultipleActionClick() {
        try {
            var number = checkPrevOperation();
            calculator.setOperation(value -> number * value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onSqrtActionClick() {
        try {
            var number = getCurrentNumber();
            if (number > 0) {
                writeText(Math.sqrt(number));
            } else {
                new Alert(Alert.AlertType.NONE, "Error in calculation! Please, check typed value.").showAndWait();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onQrtActionClick() {
        try {
            var number = getCurrentNumber();
            writeText(Math.pow(number, 2));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onChangeSignActionClick() {
        try {
            var number = getCurrentNumber();
            if (number != 0) {
                writeText((-number));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onGetResultActionClick() {
        try {
            checkPrevOperation();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private double checkPrevOperation() throws Exception { //TODO: rename
        return setResultNumber(getCurrentNumber());
    }

    private double getCurrentNumber() throws Exception {
        var textValue = readText();
        if (textValue.length() == 0) {
           throw new Exception();
        }

        return Double.parseDouble(textValue);
    }

    private double setResultNumber(double value) {
        double result = value;
        if (calculator.isOperationExist()) {
            result = (double) calculator.getOperationResult(value);
            writeText(result);
        }
        return result;
    }

    private void writeText(String newValue) {
        resultInput.setText(replacePointToComma(newValue));
    }

    private void writeText(Number newValue) {
        writeText(String.valueOf(newValue));
    }

    private String readText() {
        return replaceCommaToPoint(resultInput.getText());
    }

    private String replacePointToComma(String value) {
        return value.replace('.', ',');
    }

    private String replaceCommaToPoint(String value) {
        return value.replace(',', '.');
    }
}
