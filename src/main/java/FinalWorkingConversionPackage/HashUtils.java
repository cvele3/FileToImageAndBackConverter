package FinalWorkingConversionPackage;


public class HashUtils {

    public static byte[] encrypt(byte[] fileData, String password) {
        byte[] passwordBytes = password.getBytes();
        byte[] encryptedData = new byte[fileData.length];
        for (int i = 0; i < fileData.length; i++) {
            encryptedData[i] = (byte) (fileData[i] ^ passwordBytes[i % passwordBytes.length]);
        }
        return encryptedData;
    }

    public static byte[] decrypt(byte[] encryptedData, String password) {
        byte[] passwordBytes = password.getBytes();
        byte[] decryptedData = new byte[encryptedData.length];
        for (int i = 0; i < encryptedData.length; i++) {
            decryptedData[i] = (byte) (encryptedData[i] ^ passwordBytes[i % passwordBytes.length]);
        }
        return decryptedData;
    }

}
