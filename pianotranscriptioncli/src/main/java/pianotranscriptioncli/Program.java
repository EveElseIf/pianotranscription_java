package pianotranscriptioncli;

import libpianotranscription.Transcriptor;

import java.io.*;
import java.nio.file.Files;

public class Program {
    public static void main(String[] args) throws Exception {

        // Modify this please
        var inputFilePath = "path to your audio file";

        preProcessFile(inputFilePath);
        byte[] a = Files.readAllBytes(new File("test.pcm").toPath());
        var b = Utils.normalizeShort(Utils.toShortLE(a));
        new File("test.pcm").delete();

        var t = new Transcriptor("transcription.onnx");
        var out = t.transcript(b);
        var file = new FileOutputStream("out.mid");
        file.write(out);

        System.out.println("OK");
        System.exit(0);
    }

    private static void preProcessFile(String fileName) throws Exception {
        String[] cmd = {"ffmpeg", "-i", fileName, "-ac", "1", "-ar", "16000", "-f", "s16le", "test.pcm", "-y"};
        var process = Runtime.getRuntime().exec(cmd);
        var is = process.getErrorStream();
        var isr = new InputStreamReader(is);
        var br = new BufferedReader(isr);
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
        process.waitFor();
        var file = new File("test.pcm");
        if (!file.exists()) throw new Exception("ffmpeg execute failed, check if the input file does not exist");
    }
}
