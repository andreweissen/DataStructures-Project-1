/**
 * InfixEval.java - Handles actual computation
 * Begun 10/31/17
 * @author Andrew Eissen
 */

//package infixexpressions;

import javax.swing.JOptionPane;
import java.util.*;

final class InfixEval {

    private final Stack<String> operatorStack = new Stack<>();
    private final Stack<Integer> operandStack = new Stack<>();

    // Default contructor
    public InfixEval() {}

    public int evaluateExpression(String expression) {
        // From https://stackoverflow.com/questions/15633228/how-to-remove-all-white-spaces-in-java
        expression = expression.replaceAll("\\s", "");

        StringTokenizer stringTokenizer = new StringTokenizer(expression, "+-*/()", true);

        /*
         * The following code is an adaptation of the pseudo-code provided on the first page of the
         * grading rubric/assignment sheet. The author endeavored to match that form as closely as
         * possible.
         */
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();

            if (this.isOperand(token)) {
                operandStack.push(Integer.parseInt(token));
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.peek().equals("(")) {
                    this.popPerformAndPush();
                }
                operatorStack.pop();
            } else if (this.isOperator(token)) {
                while (
                    !operatorStack.empty() &&
                    operandStack.size() > 1 &&
                    this.hasPrecedence(token, operatorStack.peek())
                ) {
                    this.popPerformAndPush();
                }
                operatorStack.push(token);
            } else { // Additional error handling to ensure improper input is not evaluated
                this.displayErrorPopup("Error: Invalid input detected.", "Invalid input");
                throw new IllegalArgumentException();
            }
        }

        while (!operatorStack.empty() && operandStack.size() > 1) {
            this.popPerformAndPush();
        }
        return operandStack.pop();
    }

    /**
     * This method is a simple byte saving method that takes two values from the operand stack,
     * performs the operation, and pushes the resultant onto the stack. Since the code was being
     * repeated several times in the above while loop, the use of a method to replace copy/paste was
     * deemed acceptable.
     * @return void
     */
    private void popPerformAndPush() {
        int firstValue, secondValue, operationValue;
        String operator;

        firstValue = operandStack.pop();
        secondValue = operandStack.pop();
        operator = operatorStack.pop();
        operationValue = this.performCalculation(firstValue, secondValue, operator);
        operandStack.push(operationValue);
    }

    /**
     * Method returns boolean value depending on whether the input String exists in the array of
     * predefined legal operators. The Java equivalent of my favorite jQuery method to date,
     * <code>.inArray()</code>
     * @param input
     * @return boolean 
     */
    private boolean isOperator(String input) {
        String[] legalOperators = {"+", "-", "*", "/"};
        return Arrays.asList(legalOperators).contains(input);
    }

    /**
     * Method checks input to determine if it's numeric. This could have also been done with regex,
     * but this way is more readable and intuitive. Idea modified from...
     * http://javaconceptoftheday.com/java-program-to-check-user-input-is-number-or-not/
     * @param input
     * @return boolean
     */
    private boolean isOperand(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Method performs the necessary operations, returning the value. All possible exceptions are
     * appropriately handled. Based on code from http://www.geeksforgeeks.org/expression-evaluation/
     * @param firstValue
     * @param secondValue
     * @param operator
     * @return int
     */
    private int performCalculation(int firstValue, int secondValue, String operator) {
        try {
            switch (operator) {
                case "+":
                    return secondValue + firstValue;
                case "-":
                    return secondValue - firstValue;
                case "*":
                    return secondValue * firstValue;
                case "/":
                    if (firstValue == 0) {
                        throw new DivideByZero();
                    } else {
                        return secondValue / firstValue;
                    }
                default: // Should be unreachable, as improper input error will be caught first
                    this.displayErrorPopup("Error: Unknown operator.", "Unknown operator");
                    throw new IllegalArgumentException();
            }
        } catch (DivideByZero e) {
            this.displayErrorPopup("Error: Division by 0 is not allowed.", "Division by 0");
            return 0;
        }
    }

    /**
     * Method simply displays an error pop-up depending on improper input, division by zero, or an
     * unknown operator.
     * @param message
     * @param title
     * @return void
     */
    private void displayErrorPopup(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Method returns boolean value based upon the precedence of the token and the top value in the
     * stack. Basic idea retrieved/modified from http://www.geeksforgeeks.org/expression-evaluation/
     * @param token
     * @param topOfStack
     * @return boolean
     */
    private boolean hasPrecedence(String token, String topOfStack) {
        if (topOfStack.equals("(") || topOfStack.equals(")")) {
            return false;
        } else if (
            (token.equals("*") || token.equals("/")) &&
            (topOfStack.equals("+") || topOfStack.equals("-"))
        ) {
            return false;
        } else {
            return true;
        }
    }
}