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
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onNumberButtonClick(ActionEvent event) {
        var button = (Button)event.getSource();
        if (calculator.isOperationExist() && calculator.isOperationSecondValueNotDefined()) {
            resultInput.setText("");
            calculator.setOperationSecondValueIsDefined();
        }
        if(resultInput.getText().length() < MAX_NUMBER_LENGTH) {
            resultInput.setText(resultInput.getText() + button.getText());
        }
        setActive(deleteButton);
    }

    private void setActive(Button button) {
        if (button.isDisabled()) {
            button.setDisable(false);
        }
    }

    @FXML
    protected void onDelButtonClick(ActionEvent event) {
        var value = resultInput.getText();
        if(value.length() > 0) {
            resultInput.setText(value.substring(0, value.length() - 1));
        }

        if (resultInput.getText().length() == 0) {
            this.setInactive(deleteButton);
        }
    }

    private void setInactive(Button button) {
        if (!button.isDisabled()) {
            button.setDisable(true);
        }
    }

    @FXML
    protected void onCEButtonClick(ActionEvent event) {
        resultInput.setText("");
        this.setInactive(deleteButton);
    }

    @FXML
    protected void onPlusActionClick() {
        try {
            var number = checkPrevOperation();
            calculator.setOperation(new BinaryCalcOperation(number) {
                @Override
                public double getResult() {
                    return (double)firstValue + (double)secondValue;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onMinusActionClick() {
        try {
            var number = checkPrevOperation();
            calculator.setOperation(new BinaryCalcOperation(number) {
                @Override
                public double getResult() {
                    return (double)firstValue - (double)secondValue;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onChangeSignActionClick() {
        try {
            var number = getCurrentNumber();
            if (number != 0) {
                resultInput.setText(String.valueOf((-number)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onSqrtActionClick() {
        try {
            var number = getCurrentNumber();
//            calculator.setOperation(new MonoCalcOperation(number) {
//                @Override
//                public double getResult() {
//                    return Math.sqrt((double)value); // TODO: exeption
//                }
//            });
            if (number > 0) {
                resultInput.setText(String.valueOf(Math.sqrt(number)));
            } else {
                // TODO: exeption
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onQrtActionClick() {
        try {
            var number = getCurrentNumber();
//            calculator.setOperation(new MonoCalcOperation(number) {
//                @Override
//                public double getResult() {
//                    return Math.pow((double)value, 2);
//                }
//            });
            resultInput.setText(String.valueOf(Math.pow(number, 2)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onDivideActionClick() {
        try {
            var number = getCurrentNumber();
            calculator.setOperation(new BinaryCalcOperation(number) {
                @Override
                public double getResult() {
                    return (double)firstValue / (double)secondValue;  // TODO: exeption
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onMultipleActionClick() {
        try {
            var number = getCurrentNumber();
            calculator.setOperation(new BinaryCalcOperation(number) {
                @Override
                public double getResult() {
                    return (double)firstValue * (double)secondValue;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onGetResultActionClick() {
        try {
            checkPrevOperation(); //TODO: rename
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private double checkPrevOperation() throws Exception { //TODO: rename
        return setResultNumber(getCurrentNumber());
    }

    private double getCurrentNumber() throws Exception {
        var textValue = resultInput.getText();
        if (textValue.length() == 0) {
           throw new Exception();
        }

        return Double.parseDouble(textValue);
    }

    private void setResultNumber(){
        if (calculator.isOperationExist()) {
            resultInput.setText(calculator.getOperationResult().toString());
        }
    }

    private double setResultNumber(double value){
        double result = value;
        if (calculator.isOperationExist()) {
            result = (double) calculator.getOperationResult(value);
            resultInput.setText(String.valueOf(result));
        }
        return result;
    }
}


