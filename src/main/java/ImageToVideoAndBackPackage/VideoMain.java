package ImageToVideoAndBackPackage;

import FinalWorkingConversionPackage.*;
import org.jcodec.api.JCodecException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoMain {
    public static void main(String[] args) {


        String pathToFile = "C:/Users/jcvetko/Desktop/stuff/school/6. semestar/Programsko injzinjerstvo/imageStorage/imageStorage.zip";
        int targetWidth = 3840;
        int targetHeight = 2160;
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
        List<BufferedImage> imageList = new ArrayList<>();

        for (int i = 0; i < binaryChunks.size(); i++) {
            try {
                imageList.add(BinaryToImage.createImage(binaryChunks.get(i), targetWidth, targetHeight));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Vrijeme potrebno za stvaranje 1920x1080 slika: " + ((new Date().getTime() - date.getTime()) / 1000L));


        String filePath = "C:/Users/jcvetko/Desktop/stuff/tmpImageFolder/test.mp4"; //"test.mov"

        try {
            CreateVideo.imagesToVideo(imageList, filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<BufferedImage> newList = null;
        try {
            newList = CreateVideo.videoToImages(filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // Read each image and convert to binary string
        StringBuilder sb = new StringBuilder();

        try {
            sb.append(ImageToBinary.getBinaryStringFromImageList(newList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        Za Provjeru binarnog zapisa
//        try {
//            String binaryString1 = ImageToBinary.getBinaryStringFromImageList(newList);
//            String binaryString2 = ImageToBinary.getBinaryStringFromImageList(imageList);
//
//            File file1 = new File("C:/Users/jcvetko/Desktop/stuff/tmpImageFolder/file1.txt");
//            File file2 = new File("C:/Users/jcvetko/Desktop/stuff/tmpImageFolder/file2.txt");
//
//            try {
//                FileWriter writer1 = new FileWriter(file1);
//                writer1.write(binaryString1);
//                writer1.close();
//
//                FileWriter writer2 = new FileWriter(file2);
//                writer2.write(binaryString2);
//                writer2.close();
//
//                System.out.println("Files written successfully.");
//            } catch (IOException e) {
//                System.out.println("An error occurred while writing the files: " + e.getMessage());
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

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
            ByteArrayToFile.writeByteArrayToFile(fileContent2, "C:/Users/jcvetko/Desktop/stuff/tmpImageFolder/imageStorage.zip");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Vrijeme potrebno za stvaranje dokumenta: " + ((new Date().getTime() - date.getTime()) / 1000L));
    }
}
