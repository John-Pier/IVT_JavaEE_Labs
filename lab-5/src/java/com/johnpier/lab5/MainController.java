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
        if (calculator.isOperationExist() && calculator.isOperationSecondValueNotDefined()) {
            writeText("0");
            calculator.setOperationSecondValueIsDefined();
        }
        if(readText().length() < MAX_NUMBER_LENGTH) {
            writeText(readText() + button.getText());
        }
        setActive(deleteButton);
    }

    private void setActive(Button button) {
        if (button.isDisabled()) {
            button.setDisable(false);
        }
    }

    @FXML
    protected void onDelButtonClick() {
        var value = readText();
        if(value.length() > 0) {
            // todo: 0 can not first
            writeText(value.substring(0, value.length() - 1));
        }

        if (readText().length() == 0) {
            this.setInactive(deleteButton);
        }
    }

    private void setInactive(Button button) {
        if (!button.isDisabled()) {
            button.setDisable(true);
        }
    }

    @FXML
    protected void onCEButtonClick() {
        writeText(0);
        this.setInactive(deleteButton);
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
                writeText((-number));
            }
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
            writeText(Math.pow(number, 2));
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
        var textValue = readText();
        if (textValue.length() == 0) {
           throw new Exception();
        }

        return Double.parseDouble(textValue);
    }

    private void setResultNumber(){
        if (calculator.isOperationExist()) {
            writeText(calculator.getOperationResult());
        }
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

//            calculator.setOperation(new MonoCalcOperation(number) {
//                @Override
//                public double getResult() {
//                    return Math.pow((double)value, 2);
//                }
//            });

