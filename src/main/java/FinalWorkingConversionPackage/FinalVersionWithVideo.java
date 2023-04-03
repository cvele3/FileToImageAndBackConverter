package FinalWorkingConversionPackage;

import ImagesToVideo.ImageToVideo;
import org.apache.commons.io.FileUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FinalVersionWithVideo {

    public static void main(String[] args) throws IOException, InterruptedException {

        //String pathToFile = "C:/Users/paris/OneDrive/Dokumenti/convertfile/nesto.zip";
        String rootPath = "C:/Users/paris/OneDrive/Dokumenti/convertfile/";
        int targetWidth = 1920;
        int targetHeight = 1080;
        int pixelsPerImage = targetHeight * targetWidth;
        Date date = new Date();
        // Convert a file to byte array
        byte[] fileContent = null;
        try {
            fileContent = FileToByteArray.readFileToByteArray(rootPath + "nesto.zip");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Vrijeme potrebno za citanje byte arraya dokumenta: " + ((new Date().getTime() - date.getTime()) / 1000L));

        // Encrypt File byte[]
        try {
            fileContent = FileEncryptor.encryptFile(fileContent, "123456");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Vrijeme potrebno za enkripciju byte arraya dokumenta: " + ((new Date().getTime() - date.getTime()) / 1000L));

        // Convert byte array to binary string
        String binaryString = ByteArrayToBinary.toBinaryString(fileContent);
        System.out.println("Vrijeme potrebno za pretvaranje u binarni string: " + ((new Date().getTime() - date.getTime()) / 1000L));

        // Calculate the dimensions of each image
        int totalPixels = binaryString.length();

        // Adjusting picture
        int[] result = ImageCompleting.calculateMultiplication(totalPixels, targetWidth, targetHeight);
        String conversionBinaryString = ImageCompleting.addRandomize(binaryString, result[1]);
        result = ImageCompleting.calculateMultiplication(conversionBinaryString.length(), targetWidth, targetHeight);
        int numImages = result[0];
        System.out.println("Vrijeme potrebno za izracun kolicine slika: " + ((new Date().getTime() - date.getTime()) / 1000L));

        // Split the binary string into smaller chunks
        List<String> binaryChunks = new ArrayList<>();
        for (int i = 0; i < numImages; i++) {
            int startIndex = i * pixelsPerImage;
            int endIndex = startIndex + pixelsPerImage;
            String chunk = conversionBinaryString.substring(startIndex, endIndex);
            binaryChunks.add(chunk);
        }
        System.out.println("Vrijeme potrebno za podijelu chunkova: " + ((new Date().getTime() - date.getTime()) / 1000L));

        // Save each chunk as an image
        //List<BufferedImage> imageList = new ArrayList<>();
        Path pathToImagesBeforeConversion = Paths.get(rootPath + "slike");
        if (!Files.exists(pathToImagesBeforeConversion)) {
            Files.createDirectory(pathToImagesBeforeConversion);
        }
        for (int i = 0; i < binaryChunks.size(); i++) {
            String chunk = binaryChunks.get(i);
            String filePath = String.format(pathToImagesBeforeConversion + "/image%03d.png", i + 1);
            try {
                BinaryToImage.saveImage(chunk, targetWidth, targetHeight, filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        /*
        for (int i = 0; i < binaryChunks.size(); i++) {
            try {
                imageList.add(BinaryToImage.createImage(binaryChunks.get(i), targetWidth, targetHeight));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        */
        System.out.println("Vrijeme potrebno za stvaranje 1920x1080 slika: " + ((new Date().getTime() - date.getTime()) / 1000L));

        ImageToVideo.convertToVideoAndBack(rootPath, totalPixels);

        // Read each image and convert to binary string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numImages; i++) {
            String filePath = String.format(rootPath + "slikeVidea/image%03d.png", i + 1);
            String chunk = null;
            try {
                chunk = ImageToBinary.getImageBinaryString(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(chunk);
        }
        /*
        try {
            sb.append(ImageToBinary.getBinaryStringFromImageList(imageList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
        //Deleting images

        File imageFileAfterConverion = new File(rootPath + "/slikeVidea");
        FileUtils.deleteDirectory(imageFileAfterConverion);

        System.out.println("Vrijeme potrebno za citanje slika: " + ((new Date().getTime() - date.getTime()) / 1000L));
        String binaryString2 = sb.toString();
        binaryString2 = binaryString2.substring(0, totalPixels);

        // Convert binary string to byte array
        byte[] fileContent2 = BinaryToByteArray.fromBinaryString(binaryString2);

        System.out.println("Vrijeme potrebno za citanje byte arraya iz binarnog stringa: " + ((new Date().getTime() - date.getTime()) / 1000L));
        // Decrypt File byte[]
        try {
            fileContent2 = FileEncryptor.decryptFile(fileContent2, "123456");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Vrijeme potrebno za dekrpiciju byte arraya dokumenta: " + ((new Date().getTime() - date.getTime()) / 1000L));
        // Write byte array to file
        try {
            ByteArrayToFile.writeByteArrayToFile(fileContent2, rootPath + "images.zip");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Vrijeme potrebno za stvaranje dokumenta: " + ((new Date().getTime() - date.getTime()) / 1000L));
    }
}
