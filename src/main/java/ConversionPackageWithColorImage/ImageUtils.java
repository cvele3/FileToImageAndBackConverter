package ConversionPackageWithColorImage;

import java.util.Arrays;
import java.util.Random;

public class ImageUtils {
    public static int[] calculateMultiplication(int input, int num1, int num2) {
        int multiplication = (num1) * (num2);
        int count = input / multiplication;
        int missing = multiplication - (input % multiplication);
        if (missing == multiplication) {
            missing = 0;
        }
        int[] result = {count, missing};
        return result;
    }

    public static byte[] randomByteGenerator(byte[] imageBytes, int missingBytes) {
        byte[] b = new byte[missingBytes];
        new Random().nextBytes(b);

        return Arrays.copyOf(imageBytes, imageBytes.length + b.length);
    }
}
