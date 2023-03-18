package FinalWorkingConversionPackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BinaryToMultipleImages {

    public static void saveImages(String binaryString, int totalPixels, int aspectRatioWidth, int aspectRatioHeight, String filePathPrefix) throws IOException {
        int numBinaryDigits = binaryString.length();
        int pixelsPerDigit = totalPixels / numBinaryDigits;
        int imageWidth = (int) Math.sqrt(pixelsPerDigit * aspectRatioWidth / aspectRatioHeight);
        int imageHeight = pixelsPerDigit / imageWidth;
        int numImages = (int) Math.ceil((double) numBinaryDigits / (imageWidth * imageHeight));

        for (int i = 0; i < numImages; i++) {
            int startIndex = i * imageWidth * imageHeight;
            int endIndex = Math.min(startIndex + imageWidth * imageHeight, numBinaryDigits);
            String chunk = binaryString.substring(startIndex, endIndex);
            BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_BYTE_BINARY);
            int index = 0;
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    int pixelValue = (chunk.charAt(index++) == '0') ? 0 : 255;
                    image.setRGB(x, y, (pixelValue << 16) | (pixelValue << 8) | pixelValue);
                }
            }
            File output = new File(filePathPrefix + "_" + i + ".png");
            javax.imageio.ImageIO.write(image, "png", output);
        }
    }

    public static String getImageBinaryString(String filePath) throws IOException {
        BufferedImage image = javax.imageio.ImageIO.read(new File(filePath));
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int pixelValue = (pixel & 0xff) > 128 ? 1 : 0;
                sb.append(pixelValue);
            }
        }
        return sb.toString();
    }

    public static String concatenateBinaryStrings(List<String> binaryStrings) {
        StringBuilder sb = new StringBuilder();
        for (String binaryString : binaryStrings) {
            sb.append(binaryString);
        }
        return sb.toString();
    }
}
