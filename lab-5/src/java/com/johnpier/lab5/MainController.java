package com.johnpier.lab5;

import com.johnpier.lab5.entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;

public class MainController {

    private final int MAX_NUMBER_LENGTH = 110;

    private final Calculator calculator = Calculator.getInstance();

    @FXML
    private Label resultInputLabelText;

    @FXML
    private TextField resultInput;

    @FXML
    private Button deleteButton;

    public MainController() {
    }

    @FXML
    protected void onHelloButtonClick() {
    }

    @FXML
    protected void onNumberButtonClick(ActionEvent event) {
        var button = (Button)event.getSource();
        writeNumberToInput(button.getText());
    }

    @FXML
    protected void onKeyClick(KeyEvent event) {
        try {
            System.out.println("Key pressed: " + event.getCharacter());
            var codeOfCharacter = event.getCharacter().codePointAt(0);
            var character = event.getCharacter();
            if (KeyCode.BACK_SPACE.getChar().equals(character)) {
                onDelButtonClick();
            } else if ("+".equals(character)) {
                onPlusActionClick();
            } else if ("-".equals(character)) {
                onMinusActionClick();
            } else if ("*".equals(character)) {
                onMultipleActionClick();
            } else if (KeyCode.SLASH.getCode() == codeOfCharacter) {
                onDivideActionClick();
            }
            else if (KeyCode.ENTER.getCode() == codeOfCharacter) {
                onGetResultActionClick();
            }
            else if ("@".equals(character)) {
                onSqrtActionClick();
            }
            else if ("^".equals(character)) {
                onQrtActionClick();
            }
            else if (Character.isDigit(codeOfCharacter)) {
                writeNumberToInput(character);
            } else if (character.equals(",") || character.equals(".")) {
                onAddPointClick();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void writeNumberToInput(String number) {
        if(readText().length() >= MAX_NUMBER_LENGTH) {
            return;
        }
        if (calculator.isOperationExist() && calculator.isOperationSecondValueNotDefined()) {
            calculator.setOperationSecondValueIsDefined();
            writeText("0");
        }
        var value = readText();
        if(value.equals("0")) {
            writeText(number);
        } else {
            writeText(readText() + number);
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
            var number = finishPrevOperation();
            calculator.setOperation(value -> number + value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onMinusActionClick() {
        try {
            var number = finishPrevOperation();
            calculator.setOperation(value -> number - value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onDivideActionClick() {
        try {
            var number = finishPrevOperation();
            calculator.setOperation(value -> {
                if (value == 0) {
                    showError();
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
            var number = finishPrevOperation();
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
                showError();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showError() {
        var alert = new Alert(Alert.AlertType.ERROR, "Error in calculation! Please, check typed value.");
        alert.setOnCloseRequest(dialogEvent -> {
            alert.close();
        });
        alert.showAndWait();
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
            finishPrevOperation();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private double finishPrevOperation() throws Exception {
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
        if (calculator.isOperationExist() && !calculator.isOperationSecondValueNotDefined()) {
            result = (double) calculator.getOperationResult(value);
            writeText(result);
            calculator.setOperationSecondValueIsDefined();
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
