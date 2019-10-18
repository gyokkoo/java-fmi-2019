import java.util.stream.Stream;

public class FractionSimplifier {

    public static String simplify(String fraction) {
        int[] numbers = Stream.of(fraction.split("/")).mapToInt(Integer::parseInt).toArray();

        return getAsFraction(numbers[0], numbers[1]);
    }

    private static String getAsFraction(int numerator, int denominator) {
        if (numerator % denominator == 0) {
            return Integer.toString(numerator / denominator);
        }

        int gcm = greatestCommonDivisor(numerator, denominator);

        return String.format("%d/%d", numerator / gcm, denominator / gcm);
    }

    private static int greatestCommonDivisor(int first, int second) {
        return second == 0 ? first : greatestCommonDivisor(second, first % second);
    }
}
