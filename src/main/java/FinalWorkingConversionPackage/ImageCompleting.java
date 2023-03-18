package FinalWorkingConversionPackage;

import java.util.Random;

public class ImageCompleting {
    public static int[] calculateMultiplication(int input, int num1, int num2) {
        int multiplication = num1 * num2;
        int count = input / multiplication;
        int missing = multiplication - (input % multiplication);
        if (missing == multiplication) {
            missing = 0;
        }
        int[] result = {count, missing};
        return result;
    }
    public static String addRandomize(String binary, int missing) {
        StringBuilder sb = new StringBuilder(binary);
        Random rand = new Random();
        for (int i = 0; i < missing; i++) {
            sb.append(rand.nextInt(2));
        }
        return sb.toString();
    }
}
