package FinalWorkingConversionPackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageToBinary {
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

    public static String getBinaryStringFromImageList(List<BufferedImage> list) throws IOException {
        StringBuilder sb = new StringBuilder();

        for(BufferedImage image : list){
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, y);
                    int pixelValue = (pixel & 0xff) > 128 ? 1 : 0;
                    sb.append(pixelValue);
                }
            }
        }
        return sb.toString();
    }
}
