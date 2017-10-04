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
     * Method to convert the Fully Parenthesized Arithmetic Infix Expression
     * into a postfix expression with the Infix to Postfix algorithm.
     * @return String of converted Postfix expression
     */
    public String convert() throws NumberFormatException {
        String postfix;
        Double d;
        Stack<String> opStack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(expression);
        String currentToken;
        StringBuilder stringBuilder = new StringBuilder();

        while (tokenizer.hasMoreTokens()) {
            currentToken = tokenizer.nextToken();
            try {
                d = Double.parseDouble(currentToken);
                stringBuilder.append(currentToken);
                stringBuilder.append(" ");
            } catch (NumberFormatException e) {
                if (currentToken.matches("[-+*/%]")) {
                    opStack.push(currentToken);
                } else if (currentToken.equals(")")) {
                    stringBuilder.append(opStack.pop());
                    stringBuilder.append(" ");
                }
            }
        }

        postfix = stringBuilder.toString();
        return postfix;
    }

    /**
     * Test method
     * @param args
     */
    public static void main(String[] args) {
        // Constructor test
        ArithmeticTerm term = new ArithmeticTerm("1 2 3 4 5");

        // toString() test
        System.out.println("term.toString()      : " + term.toString());

        // reverse() test
        term.reverse();
        System.out.println("term.reverse()       : " + term.toString());

        // evaluate() test
        ArithmeticTerm term2 = new ArithmeticTerm("5.1 9 8.88 + 4 6 * * 7 + *");
        System.out.println("term2.toString()     : " + term2.toString());
        System.out.printf("term2.evaluate()     : %f\n", term2.evaluate());

        // Illegal character test
        ArithmeticTerm term4 = new ArithmeticTerm("5.1 9 8.88 ? 4 6 * * 7 + *");
        System.out.printf("term4.toString()     : %s\n", term4.toString());
        System.out.printf("\nterm4.evaluate()    : %f", term4.evaluate());
    }

    /**
     * Second test method
     * @param args
     */
    public static void main2(String[] args) {
        // convert() test
        ArithmeticTerm term3 = new ArithmeticTerm("( ( 4.3 * 1e-1 ) - .4 )");
        System.out.println("term3.convert()      : " + term3.convert());
        System.out.printf("term3.evaluate()     : %f\n", term3.evaluate());

    }
}