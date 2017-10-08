import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Stack;
import java.util.StringTokenizer;
/**
 * <pre>
 * ADL Lab 01
 * Ian Fennie: 2304236
 * 02.10.17
 *
 * Project file compiled with Javac using JetBrains IntelliJ IDEA 2017.2.5
 *
 * We're submitting our project as two complete solutions, one per author.
 * We have not used each other's evaluate()/convert() methods but rather
 * made our own. Both solutions work and are fairly similar, the main
 * differences being in our error handling and the way we conduct our testing.
 * </pre>
 */
public class ArithmeticTerm {
    private String expression;

    /**
     * Constructor method
     * @param input String to be stored as the expression field
     * @throws IllegalArgumentException if a non-mathematical character is detected in the argument string
     */
    public ArithmeticTerm(String input) throws IllegalArgumentException {
        StringTokenizer tokenizer = new StringTokenizer(input);
        int operands = 0;
        int binaryOps = 0;
        double d;

        while (tokenizer.hasMoreTokens()) {
            String currentToken = tokenizer.nextToken();

            try {
                d = Double.parseDouble(currentToken);
                operands++;
            } catch (NumberFormatException e) {
                if (currentToken.matches("[-+*/%]")) {
                    binaryOps++;
                } else if (currentToken.matches("[()]")) {
                    continue;
                } else {
                    throw new IllegalArgumentException("Only numerical or operator characters can be evaluated.");
                }
            }
        }

        if (operands != binaryOps + 1) {
            throw new IllegalArgumentException("Number of operands and operators does not match.");
        } else if (operands <= 0) {
            throw new IllegalArgumentException("No operands included in expression.");
        } else if (binaryOps <= 0) {
            throw new IllegalArgumentException("No binary operators included in expression.");
        } else {
            expression = input;
        }
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
     */
    public double evaluate() {
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
    public String convert() {
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
     * Main method to success and failure test cases
     * @param args String of arguments passed to main method
     */
    public static void main(String args[]) {
        ArithmeticTerm.test();
        ArithmeticTerm.testIllegalCharacter();
        ArithmeticTerm.testIllegalNumOfOps();
    }

    /**
     * Second main method to test convert() and evaluate()
     * @param args String of arguments passed to main method
     */
    public static void main2(String args[]) {
        ArithmeticTerm.testConvert();
    }

    @Test
    public static void test() {
        ArithmeticTerm.testToString();
        ArithmeticTerm.testEvaluate();
        ArithmeticTerm.testReverse();
    }

    @Test
    public static void testToString() {
        ArithmeticTerm term1 = new ArithmeticTerm("5.1 9 /");
        assertEquals("5.1 9 /", term1.toString());

        ArithmeticTerm term2 = new ArithmeticTerm("5.1 9 8.88 % /");
        assertEquals("5.1 9 8.88 % /", term2.toString());

        ArithmeticTerm term3 = new ArithmeticTerm("5.1 9 8.88 * 4 % /");
        assertEquals("5.1 9 8.88 * 4 % /", term3.toString());

        ArithmeticTerm term4 = new ArithmeticTerm("5.1 9 8.88 + 4 6 * % /");
        assertEquals("5.1 9 8.88 + 4 6 * % /", term4.toString());

        ArithmeticTerm term5 = new ArithmeticTerm("5.1 9 8.88 - 4 6 + * 7 % /");
        assertEquals("5.1 9 8.88 - 4 6 + * 7 % /", term5.toString());
    }

    @Test
    public static void testEvaluate() {
        ArithmeticTerm term1 = new ArithmeticTerm("5.1 9 /");
        assertEquals(5.1/9, term1.evaluate(), 0.000001);

        ArithmeticTerm term2 = new ArithmeticTerm("5.1 9 8.88 % /");
        assertEquals(5.1 / (9 % 8.88), term2.evaluate(), 0.000001);

        ArithmeticTerm term3 = new ArithmeticTerm("5.1 9 8.88 * 4 % /");
        assertEquals(5.1 / ((9 * 8.88) % 4), term3.evaluate(), 0.000001);

        ArithmeticTerm term4 = new ArithmeticTerm("5.1 9 8.88 + 4 6 * % /");
        assertEquals(5.1 / ((9 + 8.88) % (4 * 6)), term4.evaluate(), 0.000001);

        ArithmeticTerm term5 = new ArithmeticTerm("5.1 9 8.88 - 4 6 + * 7 % /");
        assertEquals(5.1 / ((9 - 8.88) * (4 + 6) % 7), term5.evaluate(), 0.000001);
    }

    @Test
    public static void testReverse() {
        ArithmeticTerm term1 = new ArithmeticTerm("5.1 9 /");
        term1.reverse();
        assertEquals("/ 9 5.1", term1.toString());

        ArithmeticTerm term2 = new ArithmeticTerm("5.1 9 8.88 % /");
        term2.reverse();
        assertEquals("/ % 8.88 9 5.1", term2.toString());

        ArithmeticTerm term3 = new ArithmeticTerm("5.1 9 8.88 * 4 % /");
        term3.reverse();
        assertEquals("/ % 4 * 8.88 9 5.1", term3.toString());

        ArithmeticTerm term4 = new ArithmeticTerm("5.1 9 8.88 + 4 6 * % /");
        term4.reverse();
        assertEquals("/ % * 6 4 + 8.88 9 5.1", term4.toString());

        ArithmeticTerm term5 = new ArithmeticTerm("5.1 9 8.88 - 4 6 + * 7 % /");
        term5.reverse();
        assertEquals("/ % 7 * + 6 4 - 8.88 9 5.1", term5.toString());
    }

    @Test
    public static void testConvert() {
        ArithmeticTerm term1 = new ArithmeticTerm("( ( 4.3 * 1e-1 ) - .4 )");
        ArithmeticTerm term2 = new ArithmeticTerm(term1.convert());
        assertEquals("4.3 1e-1 * .4 - ", term2.toString());
        assertEquals(4.3 * 1e-1 - 0.4, term2.evaluate(), 0.0001);
    }

    @Test
    public static void testIllegalCharacter() {
        try {
            ArithmeticTerm term = new ArithmeticTerm("5.1 9 8.88 ? 4 6 * * 7 + *");
            fail("No IllegalArgumentException thrown for illegal character in expression.");
        } catch (IllegalArgumentException e) {
            assertEquals("Only numerical or operator characters can be evaluated.", e.getMessage());
        }
    }

    @Test
    public static void testIllegalNumOfOps() {
        try {
            ArithmeticTerm term1 = new ArithmeticTerm("5.1 9 8.88 - 4 6 + * % /");
            ArithmeticTerm term2 = new ArithmeticTerm("5.1 9 8.88 - 4 6 + * 7 % /");
            ArithmeticTerm term3 = new ArithmeticTerm("+ * % /");
            ArithmeticTerm term4 = new ArithmeticTerm("5.1 9 8.88");
            fail("No IllegalArgumentException thrown for inproportional numbers of operators and operands");
        } catch (IllegalArgumentException e) {
            assertEquals("Number of operands and operators does not match.", e.getMessage());
        }
    }
}