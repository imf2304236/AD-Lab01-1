import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Labs
 * Created by Ian Fennie on 02.10.17.
 */
public class ArithmeticTerm {
    private String expression;

    /**
     * Constructor method
     * @param input String to be stored as the expression field
     */
    public ArithmeticTerm(String input) {
        expression = input;
    }

    /**
     * Getter method for the expression field
     * @return expression String stored in class
     */
    public String toString() {
        return expression;
    }

    /**
     * Method to reverse the order of tokens in the expression field
     */
    public void reverse() {

        StringTokenizer tokenizer = new StringTokenizer(expression);
        Stack<String> stringStack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();

        while (tokenizer.hasMoreTokens()) {
            stringStack.push(tokenizer.nextToken());
        }

        while (!(stringStack.empty())) {
            stringBuilder.append(stringStack.pop());

            if (!(stringStack.empty())) {
                stringBuilder.append(" ");
            }
        }

        expression = stringBuilder.toString();
    }

    /**
     * Method to evaluate the Postfix expression field
     * @return double value which the expression field evaluates to
     * @throws NumberFormatException if a
     */
    public double evaluate() throws NumberFormatException {
        double value;
        StringTokenizer tokenizer = new StringTokenizer(expression);
        Stack<Double> doubleStack = new Stack<>();

        while (tokenizer.hasMoreTokens()) {
            String currentToken = tokenizer.nextToken();
            double d;

            try {
                d = Double.parseDouble(currentToken);
                doubleStack.push(d);
            } catch (NumberFormatException e) {
                switch (currentToken) {
                    case "+":
                        doubleStack.push(doubleStack.pop() + doubleStack.pop());
                        break;
                    case "-":
                        doubleStack.push(doubleStack.pop() - doubleStack.pop());
                        break;
                    case "*":
                        doubleStack.push(doubleStack.pop() * doubleStack.pop());
                        break;
                    case "/":
                        doubleStack.push(doubleStack.pop() / doubleStack.pop());
                        break;
                    case "%":
                        doubleStack.push(doubleStack.pop() % doubleStack.pop());
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Only numerical or operator characters can be evaluated."
                        );
                }
            }
        }

        value = doubleStack.pop();

        return value;
    }
}