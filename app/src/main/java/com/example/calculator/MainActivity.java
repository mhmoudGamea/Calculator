package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String SAVED_OPERAND = "saved_operand"; // save number in result EditText
    private static final String SAVED_OPERATOR = "saved_operator";// save operation in displayOperation TextView

    // declaration of result, newNumber, displayOperation
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    // declaration of variables to hold operands and operator
    private Double operand1 = null;
    private String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);


        View.OnClickListener opListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String operation = b.getText().toString();
                String value = newNumber.getText().toString();
                if (value.length() != 0) {
                   try {// this chech if the newNumber contain "." then throw NumberFormatException
                       Double doubleValue = Double.valueOf(value);
                       performOperation(doubleValue, operation); // this function will be performed first before i assign any operation to pendingOperation
                   }catch(NumberFormatException ex) {
                       newNumber.setText("");
                   }
                }
                pendingOperation = operation;
                displayOperation.setText(pendingOperation);

            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);

        findViewById(R.id.buttonNeg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = newNumber.getText().toString();
                if(value.length() == 0) {
                    newNumber.setText("-");
                }else {
                    try {// this chech if the newNumber contain "." then throw NumberFormatException
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    }catch(NumberFormatException ex) {
                        newNumber.setText("");
                    }
                }
            }
        });

    }

    private void performOperation(double value, String operation) throws NumberFormatException{
        if(operand1 == null) {
            operand1 = value;
        }else {
            // here i assign operand2 = value;
            if(pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch(pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if(value == 0)
                        operand1 = 0.0;
                    else
                        operand1 /= value;
                    break;
                case "*":
                    operand1 *= value;
                    Log.d(TAG, "onClick: value of pending"+pendingOperation);
                    break;
                case "+":
                    operand1 += value;
                    Log.d(TAG, "onClick: value of pending"+pendingOperation);
                    break;
                case "-":
                    operand1 -= value;
                    break;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if(operand1 != null) {
            outState.putDouble(SAVED_OPERAND, operand1);
        }
        outState.putString(SAVED_OPERATOR, pendingOperation);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        double operandValueResult = savedInstanceState.getDouble(SAVED_OPERAND);
        String operatorValue = savedInstanceState.getString(SAVED_OPERATOR);
        result.setText(String.valueOf(operandValueResult));
        displayOperation.setText(operatorValue);
        operand1 = operandValueResult;
        pendingOperation = operatorValue;
    }
}