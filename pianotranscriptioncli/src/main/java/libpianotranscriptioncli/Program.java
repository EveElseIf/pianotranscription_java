package libpianotranscriptioncli;

import ai.onnxruntime.OrtException;
import libpianotranscription.Transcriptor;

import javax.sound.midi.InvalidMidiDataException;
import java.io.*;
import java.nio.file.Files;

public class Program {
    public static void main(String[] args) throws IOException, InvalidMidiDataException, OrtException {
        var fileName = "cut_liszt.mp3";
        preProcessFile(fileName);
        byte[] a = Files.readAllBytes(new File("test.pcm").toPath());
        var b = Utils.normalizeShort(Utils.toShortLE(a));

        var t = new Transcriptor("transcription.onnx");
        var out = t.transcript(b);
        var file = new FileOutputStream("out.mid");
        file.write(out);

        System.out.println("OK");
        System.exit(0);
    }

    private static void preProcessFile(String fileName) throws IOException {
        String[] cmd = {"ffmpeg", "-i", fileName, "-ac", "1", "-ar", "16000", "-f", "s16le", "test.pcm", "-y"};
        var is = Runtime.getRuntime().exec(cmd).getErrorStream();
        var isr = new InputStreamReader(is);
        var br = new BufferedReader(isr);
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
    }
}
