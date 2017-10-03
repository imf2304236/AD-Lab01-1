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

    public ArithmeticTerm(String input) {
        expression = input;
    }

    public String toString() {
        return expression;
    }

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


    /*public double evaluate() {

    }*/
}