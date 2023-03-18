package FinalWorkingConversionPackage;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class FinalVersion {
    public static void main(String[] args) {

        String pathToFile = "C:/Users/jcvetko/Desktop/stuff/school/6. semestar/Programsko injzinjerstvo/imageStorage/input.txt";
        int targetWidth = 1920;
        int targetHeight = 1080;
        int pixelsPerImage = targetHeight * targetWidth;

        // Convert a file to byte array
        byte[] fileContent = null;
        try {
            fileContent = FileToByteArray.readFileToByteArray(pathToFile);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        // Encrypt File byte[]
        try {
            fileContent = FileEncryptor.encryptFile(fileContent, "123456");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }


        // Convert byte array to binary string
        String binaryString = ByteArrayToBinary.toBinaryString(fileContent);

        // Calculate the dimensions of each image
        int totalPixels = binaryString.length();

        // Adjusting picture
        int[] result = ImageCompleting.calculateMultiplication(totalPixels, targetWidth, targetHeight);
        String conversionBinaryString = ImageCompleting.addRandomize(binaryString, result[1]);
        result = ImageCompleting.calculateMultiplication(conversionBinaryString.length(), targetWidth, targetHeight);
        int numImages = result[0];
        System.out.println(numImages);

        // Split the binary string into smaller chunks
        List<String> binaryChunks = new ArrayList<>();
        for (int i = 0; i < numImages; i++) {
            int startIndex = i * pixelsPerImage;
            int endIndex = startIndex + pixelsPerImage;
            String chunk = conversionBinaryString.substring(startIndex, endIndex);
            binaryChunks.add(chunk);
            System.out.println(chunk.length());
        }

        // Save each chunk as an image
        for (int i = 0; i < binaryChunks.size(); i++) {
            String chunk = binaryChunks.get(i);
            String filePath = String.format("C:/Users/jcvetko/Desktop/stuff/tmpImageFolder/image#%d#-%d-.png", i, totalPixels);
            try {
                BinaryToImage.saveImage(chunk, targetWidth, targetHeight, filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Read each image and convert to binary string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numImages; i++) {
            String filePath = String.format("C:/Users/jcvetko/Desktop/stuff/tmpImageFolder/image#%d#-%d-.png", i, totalPixels);
            String chunk = null;
            try {
                chunk = ImageToBinary.getImageBinaryString(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(chunk);
        }
        String binaryString2 = sb.toString();
        binaryString2 = binaryString2.substring(0, totalPixels);

        // Convert binary string to byte array
        byte[] fileContent2 = BinaryToByteArray.fromBinaryString(binaryString2);

        // Decrypt File byte[]
        try {
            fileContent2 = FileEncryptor.decryptFile(fileContent2, "123456");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Write byte array to file
        try {
            ByteArrayToFile.writeByteArrayToFile(fileContent2, "C:/Users/jcvetko/Desktop/stuff/tmpImageFolder/input.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
