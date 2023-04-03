package FinalWorkingConversionPackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BinaryToImage {
    public static void saveImage(String binaryString, int width, int height, String filePath) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = (binaryString.charAt(index++) == '0') ? 0 : 255;
                image.setRGB(x, y, (pixelValue << 16) | (pixelValue << 8) | pixelValue);
            }
        }
        File output = new File(filePath);
        javax.imageio.ImageIO.write(image, "png", output);
    }

    public static BufferedImage createImage(String binaryString, int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = (binaryString.charAt(index++) == '0') ? 0 : 255;
                image.setRGB(x, y, (pixelValue << 16) | (pixelValue << 8) | pixelValue);
            }
        }

        return image;
    }
}