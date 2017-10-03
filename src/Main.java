public class Main {

    public static void main(String[] args) {
        double value;
        ArithmeticTerm term = new ArithmeticTerm("1 2 3 4 5");
        term.reverse();
        ArithmeticTerm term2 = new ArithmeticTerm("5.1 9 8.88 + 4 6 * * 7 + *");
        value = term2.evaluate();
        ArithmeticTerm term3 = new ArithmeticTerm("5.1 9 8.88 ? 4 6 * * 7 + *");
        value = term3.evaluate();
    }
}
