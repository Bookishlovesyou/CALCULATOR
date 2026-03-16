import java.awt.*;
import javax.swing.*;

class Calculator {
    double calculate(double a, double b, Operation operation) {
        return operation.apply(a, b);
    }

    double add(double a, double b) {
        return calculate(a, b, (x, y) -> x + y);
    }

    double subtract(double a, double b) {
        return calculate(a, b, (x, y) -> x - y);
    }

    double multiply(double a, double b) {
        return calculate(a, b, (x, y) -> x * y);
    }

    double divide(double a, double b) {
        if (b == 0) throw new CalculatorException("Cannot divide by zero");
        return calculate(a, b, (x, y) -> x / y);
    }
}

// gui
public class CalculatorUI extends JFrame {

    private static CalculatorUI calcu;
    private JTextField display;
    private JPanel buttonPanel;
    private double firstNumber = 0;
    private Operation currentOperation = null;
    private boolean startNewNumber = true;

    private Calculator calc = new Calculator();

    private CalculatorUI() {
        // JFrame settings
        this.setTitle("Calculator");
        this.setSize(300, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));

        // Display
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 36));
        display.setPreferredSize(new Dimension(300, 80));
        this.add(display, BorderLayout.NORTH);

        // Buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(e -> handleButton(e.getActionCommand()));
            buttonPanel.add(btn);
        }

        this.add(buttonPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public static CalculatorUI getInstance() {
        if (calcu == null) calcu = new CalculatorUI();
        return calcu;
    }

    private void setDisplayText(String text) {
        display.setText(text);
    }

    private String getDisplayText() {
        return display.getText();
    }

    private void handleButton(String command) {
        try {
            if ("0123456789".contains(command)) {
                if (startNewNumber) {
                    setDisplayText(command);
                    startNewNumber = false;
                } else {
                    setDisplayText(getDisplayText() + command);
                }
            } else if ("+-*/".contains(command)) {
                firstNumber = Double.parseDouble(getDisplayText());
                currentOperation = getOperation(command);
                startNewNumber = true;
            } else if (command.equals("=")) {
                if (currentOperation != null) {
                    double secondNumber = Double.parseDouble(getDisplayText());
                    double result = calc.calculate(firstNumber, secondNumber, currentOperation);
                    setDisplayText(String.valueOf(result));
                    currentOperation = null;
                    startNewNumber = true;
                }
            } else if (command.equals("C")) {
                setDisplayText("0");
                firstNumber = 0;
                currentOperation = null;
                startNewNumber = true;
            }
        } catch (CalculatorException ex) {
            setDisplayText("Error");
        }
    }

    private Operation getOperation(String op) {
        return switch (op) {
            case "+" -> (x, y) -> calc.add(x, y);
            case "-" -> (x, y) -> calc.subtract(x, y);
            case "*" -> (x, y) -> calc.multiply(x, y);
            case "/" -> (x, y) -> calc.divide(x, y);
            default -> null;
        };
    }

    public static void main(String[] args) {
        CalculatorUI ui = CalculatorUI.getInstance();
    }
}