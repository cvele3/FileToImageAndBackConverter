package FinalWorkingConversionPackage;

public class BinaryToByteArray {
    public static byte[] fromBinaryString(String binaryString) {
        int len = binaryString.length();
        byte[] data = new byte[len / 8];
        for (int i = 0; i < len; i += 8) {
            data[i / 8] = (byte) Integer.parseInt(binaryString.substring(i, i + 8), 2);
        }
        return data;
    }
}
