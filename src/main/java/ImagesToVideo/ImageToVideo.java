package ImagesToVideo;

import java.io.*;

import static java.lang.Thread.sleep;

public class ImageToVideo {

    public static void convertToVideoAndBack() throws IOException, InterruptedException {

        // Input images directory
        String imagesDir = "C:/Users/paris/OneDrive/Dokumenti/convertfile/slike/image%03d.png";
        String imagesDir2 = "C:/Users/paris/OneDrive/Dokumenti/convertfile/slikeVidea/image%03d.png";

        // Output video file
        String videoFile = "C:/Users/paris/OneDrive/Dokumenti/convertfile/test.mp4";

        File file = new File(videoFile);
        if (file.exists()) file.delete();

        // Frame rate of the output video
        int frameRate = 10;

        // Convert images to video
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe",
                "/c",
                "ffmpeg -r " + frameRate + " -f image2 -s 1920x1080 -i " + imagesDir + " -vcodec libx264 -crf 25 -pix_fmt yuv420p " + videoFile);
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
        // Convert video to images

        ProcessBuilder videoToImage = new ProcessBuilder(
                "cmd.exe",
                "/c",
                "ffmpeg -i "+ videoFile + " " + imagesDir2);
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
