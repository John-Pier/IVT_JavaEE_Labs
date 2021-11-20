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
    protected void onPlusActionClick() {
        try {
            var number = getCurrentNumber();
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
            var number = getCurrentNumber();
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

    }

    @FXML
    protected void onSqrtActionClick() {
        try {
            var number = getCurrentNumber();
            calculator.setOperation(new MonoCalcOperation(number) {
                @Override
                public double getResult() {
                    return Math.sqrt((double)value); // TODO: exeption
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onQrtActionClick() {
        try {
            var number = getCurrentNumber();
            calculator.setOperation(new MonoCalcOperation(number) {
                @Override
                public double getResult() {
                    return Math.pow((double)value, 2);
                }
            });
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

    private void checkPrevOperation(double value) {
        if (calculator.currentOperation != null) {
            setResultNumber(value);
        }
    }

    private double getCurrentNumber() throws Exception {
        var textValue = resultInput.getText();
        if (textValue.length() == 0) {
           throw new Exception();
        }

        return Double.parseDouble(textValue);
    }

    private void setResultNumber(){
        if (calculator.currentOperation != null) {
            resultInput.setText(calculator.getOperationResult().toString());
        }
    }

    private void setResultNumber(double value){
        if (calculator.currentOperation != null) {
            resultInput.setText(calculator.getOperationResult(value).toString());
        }
    }
}


