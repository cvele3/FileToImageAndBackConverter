package FinalWorkingConversionPackage;

import java.io.FileOutputStream;
import java.io.IOException;

public class ByteArrayToFile {
    public static void writeByteArrayToFile(byte[] byteArray, String filePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(byteArray);
        fos.close();
    }
}

