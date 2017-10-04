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
     * @throws IllegalArgumentException if an unknown character is detected in string
     */
    public double evaluate() throws IllegalArgumentException {
        double value;
        StringTokenizer tokenizer = new StringTokenizer(expression);
        Stack<Double> doubleStack = new Stack<>();
        double value1, value2;


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
                        value2 = doubleStack.pop();
                        value1 = doubleStack.pop();
                        doubleStack.push(value1 - value2);
                        break;
                    case "*":
                        doubleStack.push(doubleStack.pop() * doubleStack.pop());
                        break;
                    case "/":
                        value2 = doubleStack.pop();
                        value1 = doubleStack.pop();
                        doubleStack.push(value1 / value2);
                        break;
                    case "%":
                        value2 = doubleStack.pop();
                        value1 = doubleStack.pop();
                        doubleStack.push(value1 % value2);
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

    /**
     * Test method
     */
    public static void main(String[] args) {
        // Constructor test
        ArithmeticTerm term = new ArithmeticTerm("1 2 3 4 5");

        // toString() test
        System.out.println("term.toString() : " + term.toString());

        // reverse() test
        term.reverse();
        System.out.println("term.reverse() : " + term.toString());

        // evaluate() test
        ArithmeticTerm term2 = new ArithmeticTerm("5.1 9 8.88 + 4 6 * * 7 + *");
        System.out.printf("\nterm2 : %s", term2.toString());
        System.out.printf("\nterm2.evaluate() : %f", term2.evaluate());

        // Illegal character
        ArithmeticTerm term3 = new ArithmeticTerm("5.1 9 8.88 ? 4 6 * * 7 + *");
        System.out.printf("\nterm3 : %s", term3.toString());
        System.out.printf("\nterm3.evaluate() : %f", term3.evaluate());
    }
}