import java.util.Stack;
import java.util.StringTokenizer;

/**
 * ADL Lab 01
 * Ian Fennie: 2304236
 * 02.10.17.
 *
 * We're submitting our project as two complete solutions, one per author.
 * We have not used each other's evaluate()/convert() methods but rather
 * made our own. Both solutions work and are fairly similar, the main
 * differences being in our error handling and the way we conduct our testing.
 */
public class ArithmeticTerm {
    private String expression;

    /**
     * Constructor method
     * @param input String to be stored as the expression field
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
     * Main method to construct test cases and call test() on each
     */
    public static void main(String args[]) {
        // Test case constructors
        ArithmeticTerm term1 = new ArithmeticTerm("5.1 9 /");
        ArithmeticTerm term2 = new ArithmeticTerm("5.1 9 8.88 % /");
        ArithmeticTerm term3 = new ArithmeticTerm("5.1 9 8.88 * 4 % /");
        ArithmeticTerm term4 = new ArithmeticTerm("5.1 9 8.88 + 4 6 * % /");
        ArithmeticTerm term5 = new ArithmeticTerm("5.1 9 8.88 - 4 6 + * 7 % /");

        // Test method call on each test case
        term1.test();
        term2.test();
        term3.test();
        term4.test();
        term5.test();

        // Illegal character test case
        ArithmeticTerm term6 = new ArithmeticTerm("5.1 9 8.88 ? 4 6 * * 7 + *");

        // Insufficient operands test case
        ArithmeticTerm term7 = new ArithmeticTerm("5.1 9 8.88 - 4 6 + * % /");

        // Insufficient operators test case
        ArithmeticTerm term8 = new ArithmeticTerm("5.1 9 8.88 - 4 6 + * 7 %");

    }

    /**
     * Second test method for toString(), convert() & evaluate()
     */
    public static void main2(String args[]) {
        // Test case constructor
        ArithmeticTerm term1 = new ArithmeticTerm("( ( 4.3 * 1e-1 ) - .4 )");

        // toString() test
        System.out.println("term.toString()  : " + term1.toString());

        // convert() test
        String conversion = term1.convert();
        System.out.printf("term.convert()   : %s\n", conversion);

        // evaluate() test
        ArithmeticTerm term2 = new ArithmeticTerm(conversion);
        System.out.printf("term.evaluate()  : %f\n", term2.evaluate());
    }

    /**
     * Test method for toString(), evaluate() & reverse()
     */
    public void test() {
        // toString() test
        System.out.println("term.toString()  : " + this.toString());

        // evaluate() test
        System.out.printf("term.evaluate()  : %f\n", this.evaluate());

        // reverse() test
        this.reverse();
        System.out.println("term.reverse()   : " + this.toString());
    }
}