package ConversionPackageWithColorImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageSaver {
    public static void saveImages(List<BufferedImage> images, int usableByteLength) {
        for (int i = 0; i < images.size(); i++) {
            String filePath = String.format("C:/Users/jcvetko/Desktop/stuff/tmpImageFolder/image#%d#-%d-.png", i, usableByteLength);
            BufferedImage image = images.get(i);
            try {
                ImageIO.write(image, "png", new File(filePath));
            } catch (IOException e) {
                throw new RuntimeException("Error saving image to file: " + filePath, e);
            }
        }
    }
}
