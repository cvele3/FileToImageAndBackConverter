package ConversionPackageWithColorImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ByteArrayToColorImage {

    public static BufferedImage createImage(byte[] bytes, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = byteToColor(bytes[index++]);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    public static Color byteToColor(byte b) {
        int value = b & 0xFF;
        int red = value % 255;
        int green = (value * 2) % 255;
        int blue = (value * 3) % 255;

        return new Color(red, green, blue);
    }
}


