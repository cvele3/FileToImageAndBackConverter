package ImagesToVideo;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageToVideo {

    public static void convertToVideoAndBack(String rootPath, int totalPixels) throws IOException, InterruptedException {


        File pathToVideo = new File(rootPath + "video");
        FileUtils.cleanDirectory(pathToVideo);

        String videoFile = rootPath + "video/"+totalPixels+".mp4";

        // Frame rate of the output video
        int frameRate = 10;

        // Convert images to video
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe",
                "/c",
                "ffmpeg -r " + frameRate + " -f image2 -s 1920x1080 -i " + rootPath + "slike/image%03d.png" + " -vcodec libx264 -crf 25 -pix_fmt yuv420p " + videoFile);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {break;}
            System.out.println(line);
        }
        int exitCode = p.waitFor();
        if (exitCode != 0) {
            System.err.println("Error: ffmpeg exited with code " + exitCode);
            System.exit(exitCode);
        }

        //Delete original images
        File imageOriginalFile = new File(rootPath + "slike");
        FileUtils.deleteDirectory(imageOriginalFile);

        // Convert video to images

        Path pathToImagesAfterConversion = Paths.get(rootPath + "slikeVidea");
        if (!Files.exists(pathToImagesAfterConversion)) {
            Files.createDirectory(pathToImagesAfterConversion);
        }

        ProcessBuilder videoToImage = new ProcessBuilder(
                "cmd.exe",
                "/c",
                "ffmpeg -i "+ videoFile + " " + pathToImagesAfterConversion + "/image%03d.png");
        builder.redirectErrorStream(true);
        Process process = videoToImage.start();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line22;
        while (true) {
            line22 = bufferedReader.readLine();
            if (line22 == null) { break; }
            System.out.println(line22);
        }
        exitCode = process.waitFor();
        if (exitCode != 0) {
            System.err.println("Error: ffmpeg exited with code " + exitCode);
            System.exit(exitCode);
        }

    }
}
