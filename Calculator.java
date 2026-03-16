public class Calculator{
    
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
        if (b == 0) {
            throw new CalculatorException("Cannot divide by zero");
            
        }
        return calculate(a, b, (x, y) -> x / y);
    }
}