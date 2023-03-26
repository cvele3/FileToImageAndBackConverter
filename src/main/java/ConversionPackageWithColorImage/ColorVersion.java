package ConversionPackageWithColorImage;

import FinalWorkingConversionPackage.FileEncryptor;
import FinalWorkingConversionPackage.FileToByteArray;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ColorVersion {
    public static void main(String[] args) {
        String pathToFile = "C:/Users/jcvetko/Desktop/stuff/school/6. semestar/Programsko injzinjerstvo/imageStorage/imageStorage.zip";
        int targetWidth = 1920;
        int targetHeight = 1080;
        int pixelsPerImage = targetHeight * targetWidth;
        Date date = new Date();
        // Convert a file to byte array
        byte[] fileContent = null;
        try {
            fileContent = FileToByteArray.readFileToByteArray(pathToFile);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Vrijeme potrebno za citanje byte arraya dokumenta: " + ((new Date().getTime() - date.getTime()) / 1000L));

        try {
            fileContent = FileEncryptor.encryptFile(fileContent, "123456");
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Vrijeme potrebno za enkripciju byte arraya dokumenta: " + ((new Date().getTime() - date.getTime()) / 1000L));


        int usableByteLength = fileContent.length;


        int[] result = ImageUtils.calculateMultiplication(fileContent.length, targetWidth, targetHeight);
        System.out.println("Duljina byte[]: " + fileContent.length);
        System.out.println("Broj slika: " + result[0]);
        System.out.println("Koliko fali bajtova: " + result[1]);

        System.out.println("________________________________________________________________________");
        fileContent = ImageUtils.randomByteGenerator(fileContent, result[1]);
        result = ImageUtils.calculateMultiplication(fileContent.length, targetWidth, targetHeight);
        System.out.println("Duljina byte[]: " + fileContent.length);
        System.out.println("Broj slika: " + result[0]);
        System.out.println("Koliko fali bajtova: " + result[1]);

        int numberOfImages = result[0];


        List<BufferedImage> images = new ArrayList<>();
        for (int i = 0; i < numberOfImages; i++) {
            int startIndex = i * pixelsPerImage;
            int endIndex = startIndex + pixelsPerImage;
            byte[] imageData = Arrays.copyOfRange(fileContent, startIndex, endIndex);
            images.add(ByteArrayToColorImage.createImage(imageData, targetWidth, targetHeight));
        }
        //ImageSaver.saveImages(images, usableByteLength);
        System.out.println("________________________________________________________________________");

    }
}
